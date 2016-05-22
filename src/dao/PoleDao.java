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



