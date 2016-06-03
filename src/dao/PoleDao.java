package dao;

import bean.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/10.
 */
public class PoleDao {
    private Connection conn = null;
    /**
     * 连接数据库
     * @return
     * @param
     * @author 黄诗鹤
     */
    public void initConnection() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monitoring", "root", "root");
    }

    public List<Pole> searchPoleByLine(AdminRegion adminRegion, Organization organization, Line line) throws Exception {
        List<Pole> poleList=new ArrayList<Pole>();
//        System.out.println("2: "+line.getName());
        this.initConnection();
        String sql="SELECT DISTINCT pole.* FROM pole,admin_region WHERE pole.a_id=admin_region.a_id and (admin_region.a_id=? OR admin_region.parent_ids like ?)  and o_id IN (SELECT organization.o_id FROM organization WHERE organization.o_id=? OR organization.parent_ids like ?) and pole.l_id=? order BY pole.location";

        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setLong(1, adminRegion.getAid());
        ps.setString(2,adminRegion.getParentIds()+adminRegion.getAid()+"/%");
        ps.setLong(3,organization.getOid());
        ps.setString(4, organization.getParentIds()+organization.getOid()+"/%");
        ps.setLong(5,line.getLid());
        ResultSet rs=ps.executeQuery();
        while (rs.next()) {
                Pole pole = new Pole(rs.getLong("p_id"),rs.getDouble("longitude"),rs.getDouble("latitude"),rs.getLong("location"),
            rs.getLong("l_id"),rs.getLong("a_id"),rs.getLong("o_id"));
                poleList.add(pole);
        }
//        System.out.println("22: "+poleList);
        this.closeConnection();
        return poleList;
    }


    public List<Pole> searchPoleByOrganization(Organization organization) throws Exception {
        List<Pole> poleList=new ArrayList<Pole>();
//        System.out.println("2: "+line.getName());
        this.initConnection();
        String sql="SELECT DISTINCT pole.* FROM pole WHERE  o_id IN (SELECT organization.o_id FROM organization WHERE organization.o_id=? OR organization.parent_ids like ?)";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setLong(1,organization.getOid());
        ps.setString(2, organization.getParentIds()+organization.getOid()+"/%");
        ResultSet rs=ps.executeQuery();
        while (rs.next()) {
            Pole pole = new Pole(rs.getLong("p_id"),rs.getDouble("longitude"),rs.getDouble("latitude"),rs.getLong("location"),
                    rs.getLong("l_id"),rs.getLong("a_id"),rs.getLong("o_id"));
            poleList.add(pole);
        }
//        System.out.println("22: "+poleList);
        this.closeConnection();
        return poleList;
    }


    public List<Pole> searchPoleMapByLidOrganization(Long lid, Organization organization) throws Exception {
        List<Pole> poleList=new ArrayList<Pole>();
        this.initConnection();
        String sql="SELECT DISTINCT pole.* FROM pole WHERE o_id IN (SELECT organization.o_id FROM organization WHERE organization.o_id=? OR organization.parent_ids like ?) and l_id=?";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setLong(1,organization.getOid());
        ps.setString(2, organization.getParentIds()+organization.getOid()+"/%");
        ps.setLong(3,lid);
        ResultSet rs=ps.executeQuery();
        while (rs.next()) {
            Pole pole = new Pole(rs.getLong("p_id"),rs.getDouble("longitude"),rs.getDouble("latitude"),rs.getLong("location"),
                    rs.getLong("l_id"),rs.getLong("a_id"),rs.getLong("o_id"));
            poleList.add(pole);
        }
//        System.out.println("22: "+poleList);
        this.closeConnection();
        return poleList;
    }

    /**
     * @return
     * @param
     * @author SLJ
     */
    public List<Pole> searchPoleByLine(long lid) throws Exception
    {

        List<Pole> poleList = new ArrayList<Pole>();
        // System.out.println("2: "+line.getName());
        this.initConnection();
        String sql = "SELECT pole.* FROM pole WHERE l_id=?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, lid);

        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            Pole pole = new Pole(rs.getLong("p_id"), rs.getDouble("longitude"), rs.getDouble("latitude"),
                    rs.getLong("location"), rs.getLong("l_id"), rs.getLong("a_id"), rs.getLong("o_id"));
            poleList.add(pole);
        }
        // System.out.println("22: "+poleList);
        this.closeConnection();
        return poleList;
    }
    /**
     * @return
     * @param
     * @author SLJ
     */
    public List<Pole> pid_pole(long pid) throws Exception
    {

        List<Pole> poleList = new ArrayList<Pole>();
        // System.out.println("2: "+line.getName());
        this.initConnection();
        String sql = "SELECT pole.* FROM pole WHERE p_id=?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, pid);

        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            Pole pole = new Pole(rs.getLong("p_id"), rs.getDouble("longitude"), rs.getDouble("latitude"),
                    rs.getLong("location"), rs.getLong("l_id"), rs.getLong("a_id"), rs.getLong("o_id"));
            poleList.add(pole);
        }
        // System.out.println("22: "+poleList);
        this.closeConnection();
        return poleList;
    }
    /**
     * 关闭数据库
     * @return
     * @param
     * @author HPY
     */
//新增线杆时候值输入经纬度，根据经纬度获得aid。默认组织机构为该用户所在本级组织机构，不配置线路，相对位置
    public void addPole( Pole pole)throws Exception{
        this.initConnection();
        String sql="INSERT INTO pole (longitude, latitude, a_id,o_id) VALUES (?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setDouble(1,pole.getLatitude());
        ps.setDouble(2,pole.getLongitude());
        ps.setLong(3,pole.getAid());
        ps.setLong(4,pole.getOid());
        ps.executeUpdate();
        this.closeConnection();
    }
    //配置已知id的线杆所属线路，相对位置，和组织机构
    public void modifyPole(Pole pole)throws Exception{
        this.initConnection();

        String sql="UPDATE  pole SET l_id=?,location=?,o_id=? WHERE p_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1,pole.getLid());
        ps.setLong(2,pole.getLocation());
        ps.setLong(3,pole.getOid());
        ps.setLong(4,pole.getPid());
        ps.executeUpdate();

        String sql1="UPDATE  pole SET location=location+1 WHERE   l_id=? AND location>=? AND p_id<>? ";
        PreparedStatement ps1 = conn.prepareStatement(sql1);
        ps1.setLong(1,pole.getLid());
        ps1.setLong(2,pole.getLocation());
        ps1.setLong(3,pole.getPid());
        ps1.executeUpdate();

        this.closeConnection();
    }
    //需要的参数：pid ，location lid
    public void deleatePole(Pole pole)throws Exception{
        this.initConnection();
        String  sql2 = "UPDATE node SET p_id=NULL WHERE p_id=?";
        PreparedStatement ps2 = conn.prepareStatement(sql2);
        ps2.setLong(1,pole.getPid());
        ps2.executeUpdate();

        String  sql = "DELETE FROM pole WHERE p_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1,pole.getPid());
        ps.executeUpdate();

        String sql1="UPDATE  pole SET location=location-1 WHERE l_id=? AND location>=?  ";
        PreparedStatement ps1 = conn.prepareStatement(sql1);
        ps1.setLong(1,pole.getLid());
        ps1.setLong(2,pole.getLocation());
        ps1.executeUpdate();

        this.closeConnection();
    }
    public List<Pole> showPoles(User user)throws Exception{
        List<Pole> poleList=new ArrayList<Pole>() ;

        this.initConnection();

        String sql ="SELECT pole.*,organization.name,line.name,admin_region.name FROM  pole,organization,line,admin_region WHERE (organization.o_id=? OR organization.parent_ids like ?)\n" +
                "AND pole.o_id=organization.o_id AND pole.a_id=admin_region.a_id AND pole.l_id=line.l_id GROUP BY pole.p_id";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1,user.getOid());
        ps.setString(2,"%"+user.getOid()+"%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Pole pole=new Pole();
            pole.setPid(rs.getLong("p_id"));
            pole.setLid(rs.getLong("l_id"));
            pole.setAid(rs.getLong("a_id"));
            pole.setOid(rs.getLong("o_id"));
            pole.setLongitude(rs.getDouble("longitude"));
            pole.setLatitude(rs.getDouble("latitude"));
            pole.setLocation(rs.getLong("location"));
            pole.setAname(rs.getString("admin_region.name"));
            pole.setLname(rs.getString("line.name"));
            pole.setOname(rs.getString("organization.name"));
            poleList.add(pole);
        }
        this.closeConnection();
        return poleList;
    }
    public List<Pole> searchPoles(User user, Pole pole)throws Exception{
        List<Pole> poleList=new ArrayList<Pole>() ;

        this.initConnection();
        String sql ="SELECT pole.*,organization.name,line.name,admin_region.name FROM  pole LEFT JOIN line ON pole.l_id=line.l_id,organization,admin_region WHERE (organization.o_id=? OR organization.parent_ids like ?)\n" +
                "AND pole.o_id=organization.o_id AND pole.a_id=admin_region.a_id ";
//        AND pole.l_id=line.l_id

        if(!(pole.getOid()==0)){
            sql+="AND pole.o_id=? ";
        }
        if (!(pole.getAid()==0)){
            sql+="AND pole.a_id=? ";
        }
        if (!(pole.getLid()==0)){
            sql+="AND pole.l_id=? ";
        }
        sql+="GROUP BY pole.p_id";
//        System.out.println(sql);
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1,user.getOid());
        ps.setString(2,"%"+user.getOid()+"%");
        int i=3;
        if(!(pole.getOid()==0)){
            ps.setLong(i,pole.getOid());
            i++;
        }
        if (!(pole.getAid() ==0)){
            ps.setLong(i,pole.getAid());
            i++;
        }
        if (!(pole.getLid() ==0)){
            ps.setLong(i,pole.getLid());
            i++;
        }
//        System.out.println(pole.getLid());
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Pole pole1=new Pole();
            pole1.setPid(rs.getLong("p_id"));
            pole1.setLid(rs.getLong("l_id"));
            pole1.setAid(rs.getLong("a_id"));
            pole1.setOid(rs.getLong("o_id"));
            pole1.setLongitude(rs.getDouble("longitude"));
            pole1.setLatitude(rs.getDouble("latitude"));
            pole1.setLocation(rs.getLong("location"));
            pole1.setAname(rs.getString("admin_region.name"));
            pole1.setLname(rs.getString("line.name"));
            pole1.setOname(rs.getString("organization.name"));
            poleList.add(pole1);
        }
        this.closeConnection();
        return poleList;
    }

    /**
     * 关闭数据库
     * @return
     * @param
     * @author 黄诗鹤
     */
    public void closeConnection() throws Exception{
        conn.close();
    }
}



// 百度地图API功能:创建标注
//        var pointArray = new Array();
//$.each(data, function(idx, obj) {
//        alert(obj.longitude+"**"+obj.latitude);
//        var marker = new BMap.Marker(obj.longitude,obj.latitude);
//        map.addOverlay(marker);    //增加点
////            pointArray[idx] = new BMap.Point(obj.longitude,obj.latitude);
////            marker.addEventListener("click",attribute);
//        });
//        //让所有点在视野范围内
//        map.setViewport(pointArray);



// 百度地图API功能:画折线
//        var polyline = new BMap.Polyline([
//            $.each(data, function(idx, obj) {
//                alert(obj.longitude+"**"+obj.latitude);
//                new BMap.Point(obj.longitude,obj.latitude);
//            })
//        ], {strokeColor:"blue", strokeWeight:10, strokeOpacity:0.5});   //创建折线
//        map.addOverlay(polyline);   //增加折线



