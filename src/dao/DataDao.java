package dao;

import bean.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Administrator on 2016/4/10.
 */
public class DataDao {
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

    //查询条件:pid不为空,时间不为空
    public List<DataDisplay> searchDataByPid(long pid,Timestamp startTime,Timestamp endTime) throws Exception {
//        System.out.println("3:"+pid);
        List<DataDisplay> dataList=new ArrayList<>();
        this.initConnection();
        String sql="select pole.p_id, data.* ,pole.location,line.name,node.source from data,node,pole,line where node.p_id=? and pole.p_id=node.p_id and pole.l_id=line.l_id and node.n_id=data.n_id and data.sampling_time<=? and data.sampling_time>=? ORDER BY data.sampling_time desc";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setLong(1,pid);
        ps.setTimestamp(2,endTime);
        ps.setTimestamp(3,startTime);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            DataDisplay data=new DataDisplay(rs.getLong("p_id"),rs.getLong("d_id"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("sampling_time")).toString(),rs.getDouble("out_temperature"),
                    rs.getDouble("wire_temperature"),rs.getDouble("sag"),rs.getDouble("electricity"),rs.getDouble("voltage"),
                    rs.getDouble("humidity"),rs.getLong("n_id"),rs.getLong("location"),rs.getString("name"),rs.getString("source"));
            dataList.add(data);
        }
//        System.out.println("4:"+dataList);
        this.closeConnection();
        return dataList;
    }


    /*查询条件:pid\线路\地区\组织\时间都为空--
    * 查询过去一天内的所有历史数据*/
    public List<DataDisplay> searchAllData(Organization organization) throws Exception {
        List<DataDisplay> dataList=new ArrayList<>();
        this.initConnection();
//        String sql="select pole.p_id, data.* ,pole.location,line.name,node.source from data,node,pole,line where pole.p_id=node.p_id and pole.l_id=line.l_id and node.n_id=data.n_id and data.sampling_time>=now()-101010101 and pole.o_id=?";
        String sql="select DISTINCT pole.p_id, data.* ,pole.location,line.name, node.source\n" +
                "from organization,data,node,pole,line WHERE node.n_id=data.n_id\n" +
                "                     and pole.p_id=node.p_id\n" +
                "                     and pole.l_id=line.l_id\n" +
                "                     and data.n_id in (SELECT node.n_id FROM node WHERE node.p_id IN (SELECT  pole.p_id FROM pole WHERE o_id IN (SELECT organization.o_id FROM organization WHERE organization.o_id=? OR organization.parent_ids like ?)\n" +
                "))";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setLong(1,organization.getOid());
        ps.setString(2, organization.getParentIds()+organization.getOid()+"/%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            DataDisplay data=new DataDisplay(rs.getLong("p_id"),rs.getLong("d_id"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("sampling_time")).toString(),rs.getDouble("out_temperature"),
                    rs.getDouble("wire_temperature"),rs.getDouble("sag"),rs.getDouble("electricity"),rs.getDouble("voltage"),
                    rs.getDouble("humidity"),rs.getLong("n_id"),rs.getLong("location"),rs.getString("name"),rs.getString("source"));
            dataList.add(data);
        }
        System.out.println("searchAllData:"+dataList);
        this.closeConnection();
        return dataList;
    }

    //查询条件:pid为空,组织\地区\线路\时间都不为空
    public List<DataDisplay> searchData(Organization organization, AdminRegion adminRegion, Long lid, Timestamp startTime, Timestamp endTime) throws Exception {
        List<DataDisplay> dataList=new ArrayList<>();
        this.initConnection();
        String sql="select DISTINCT pole.p_id, data.* ,pole.location,line.name, node.source\n" +
                "from organization,data,node,pole,line WHERE node.n_id=data.n_id\n" +
                "                                            and pole.p_id=node.p_id\n" +
                "                                            and pole.l_id=line.l_id AND pole.l_id=?\n" +
                "                                            and data.sampling_time<=? and data.sampling_time>=?\n"+
                "                                            and data.n_id in (SELECT node.n_id\n" +
                "                                                              FROM node WHERE node.p_id IN\n" +
                "                                                             (SELECT  pole.p_id FROM pole,admin_region WHERE pole.a_id=admin_region.a_id\n" +
                "                                                              and (admin_region.a_id=? OR admin_region.parent_ids like ?)\n" +
                "                                                              AND o_id IN (SELECT organization.o_id FROM organization WHERE organization.o_id=? OR organization.parent_ids like ?)\n" +
                "))";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setLong(1,lid);
        ps.setTimestamp(2,endTime);
        ps.setTimestamp(3,startTime);
        ps.setLong(4, adminRegion.getAid());
        ps.setString(5,adminRegion.getParentIds()+adminRegion.getAid()+"/%");
        ps.setLong(6,organization.getOid());
        ps.setString(7, organization.getParentIds()+organization.getOid()+"/%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            DataDisplay data=new DataDisplay(rs.getLong("p_id"),rs.getLong("d_id"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("sampling_time")).toString(),rs.getDouble("out_temperature"),
                    rs.getDouble("wire_temperature"),rs.getDouble("sag"),rs.getDouble("electricity"),rs.getDouble("voltage"),
                    rs.getDouble("humidity"),rs.getLong("n_id"),rs.getLong("location"),rs.getString("name"),rs.getString("source"));
            dataList.add(data);
        }
        this.closeConnection();

        return dataList;

    }
    //查询条件:pid\线路为空,组织\地区\时间不为空
    public List<DataDisplay> searchData(Organization organization, AdminRegion adminRegion, Timestamp startTime, Timestamp endTime) throws Exception {
        List<DataDisplay> dataList=new ArrayList<>();
        this.initConnection();
        String sql="select DISTINCT pole.p_id, data.* ,pole.location,line.name, node.source\n" +
                "from organization,data,node,pole,line WHERE node.n_id=data.n_id\n" +
                "                                            and pole.p_id=node.p_id\n" +
                "                                            and pole.l_id=line.l_id" +
                "                                            and data.sampling_time<=? and data.sampling_time>=?\n"+
                "                                            and data.n_id in (SELECT node.n_id\n" +
                "                                                              FROM node WHERE node.p_id IN\n" +
                "                                                             (SELECT  pole.p_id FROM pole,admin_region WHERE pole.a_id=admin_region.a_id\n" +
                "                                                              and (admin_region.a_id=? OR admin_region.parent_ids like ?)\n" +
                "                                                              AND o_id IN (SELECT organization.o_id FROM organization WHERE organization.o_id=? OR organization.parent_ids like ?)\n" +
                "))";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setTimestamp(1,endTime);
        ps.setTimestamp(2,startTime);
        ps.setLong(3, adminRegion.getAid());
        ps.setString(4,adminRegion.getParentIds()+adminRegion.getAid()+"/%");
        ps.setLong(5,organization.getOid());
        ps.setString(6, organization.getParentIds()+organization.getOid()+"/%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            DataDisplay data=new DataDisplay(rs.getLong("p_id"),rs.getLong("d_id"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("sampling_time")).toString(),rs.getDouble("out_temperature"),
                    rs.getDouble("wire_temperature"),rs.getDouble("sag"),rs.getDouble("electricity"),rs.getDouble("voltage"),
                    rs.getDouble("humidity"),rs.getLong("n_id"),rs.getLong("location"),rs.getString("name"),rs.getString("source"));
            dataList.add(data);
        }
        this.closeConnection();
        return dataList;
    }
    //查询条件:pid\线路\地区为空,组织\时间不为空
    public List<DataDisplay> searchDataByOid(Organization organization, Timestamp startTime, Timestamp endTime) throws Exception {
        List<DataDisplay> dataList=new ArrayList<>();
        this.initConnection();
        String sql="select DISTINCT pole.p_id, data.* ,pole.location,line.name, node.source\n" +
                "from organization,data,node,pole,line WHERE node.n_id=data.n_id\n" +
                "                                            and pole.p_id=node.p_id\n" +
                "                                            and pole.l_id=line.l_id" +
                "                                            and data.sampling_time<=? and data.sampling_time>=?\n"+
                "                                            and data.n_id in (SELECT node.n_id\n" +
                "                                                              FROM node WHERE node.p_id IN\n" +
                "                                                             (SELECT  pole.p_id FROM pole WHERE " +
                "                                                                o_id IN (SELECT organization.o_id FROM organization WHERE organization.o_id=? OR organization.parent_ids like ?)\n" +
                "))";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setTimestamp(1,endTime);
        ps.setTimestamp(2,startTime);
        ps.setLong(3,organization.getOid());
        ps.setString(4, organization.getParentIds()+organization.getOid()+"/%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            DataDisplay data=new DataDisplay(rs.getLong("p_id"),rs.getLong("d_id"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("sampling_time")).toString(),rs.getDouble("out_temperature"),
                    rs.getDouble("wire_temperature"),rs.getDouble("sag"),rs.getDouble("electricity"),rs.getDouble("voltage"),
                    rs.getDouble("humidity"),rs.getLong("n_id"),rs.getLong("location"),rs.getString("name"),rs.getString("source"));
            dataList.add(data);
        }
        this.closeConnection();

        return dataList;
    }

/*这个是废弃的代码,可以删了;
            * 这里有个问题可以优化一下:就是我只需要返回一个List<List<Pole>>就可以了,
            * 而不需要再定义一个数据展示的bean,也不用在数据库中拼如此麻烦的数据,
            * 因为这里只是用来在地图上显示线路,添加线杆的;
            * 至于线杆的数据是由前台异步加载得到的;(之所以我这样做是因为想要将线路和数据一起加载,这样并不合理;)
            这个问题已经优化过了;
    public List<DataMapDisplay> searchDataMapByOid(Long lid, Organization organization) throws Exception {
        List<DataMapDisplay> dataMapDisplays=new ArrayList<DataMapDisplay>();
        this.initConnection();
        String sql="select DISTINCT  pole.*, data.* ,line.name, node.source\n" +
                "from organization,data,node,pole,line WHERE node.n_id=data.n_id\n" +
                "                                            and pole.p_id=node.p_id\n" +
                "                                            and pole.l_id=line.l_id\n" +
                "                                            AND pole.l_id=?\n" +
                "                                            and data.sampling_time>=now()-101010101\n" +
                "                                            and data.n_id in (SELECT node.n_id FROM node\n" +
                "                                                                WHERE node.p_id IN\n" +
                "                                            (SELECT DISTINCT pole.p_id FROM pole\n" +
                "                                              WHERE  l_id=? AND o_id IN\n" +
                "                                            (SELECT organization.o_id FROM organization\n" +
                "                                              WHERE organization.o_id=? OR organization.parent_ids like ?)\n" +
                "))ORDER BY location,data.sampling_time";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setLong(1,lid);
        ps.setLong(2,lid);
        ps.setLong(3,organization.getOid());
        ps.setString(4, organization.getParentIds()+organization.getOid()+"/%");
        ResultSet rs=ps.executeQuery();
        Long nid1=null;
        while (rs.next()) {
            DataDisplay dataDisplay=null;
            List<DataDisplay> dataDisplays =null;
            Double location=rs.getDouble("location");
            //取第一条记录;
            dataDisplay = new DataDisplay(
                    rs.getLong("p_id"),
                    rs.getLong("d_id"),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("sampling_time")).toString(),
                    rs.getDouble("out_temperature"),
                    rs.getDouble("wire_temperature"),
                    rs.getDouble("sag"),
                    rs.getDouble("electricity"),
                    rs.getDouble("voltage"),
                    rs.getDouble("humidity"),
                    rs.getLong("n_id"),
                    rs.getLong("location"),
                    rs.getString("name"),
                    rs.getString("source"));
            dataDisplays = new ArrayList<DataDisplay>();
            dataDisplays.add(dataDisplay);
            //判断下一条记录是不是还是同一根线杆的,如果是的话继续取该线杆的数据放在dataDisplays里面;
            while (rs.next() && rs.getDouble("location")==location) {
                dataDisplay = new DataDisplay(
                        rs.getLong("p_id"),
                        rs.getLong("d_id"),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("sampling_time")).toString(),
                        rs.getDouble("out_temperature"),
                        rs.getDouble("wire_temperature"),
                        rs.getDouble("sag"),
                        rs.getDouble("electricity"),
                        rs.getDouble("voltage"),
                        rs.getDouble("humidity"),
                        rs.getLong("n_id"),
                        rs.getLong("location"),
                        rs.getString("name"),
                        rs.getString("source"));
                dataDisplays = new ArrayList<DataDisplay>();
                dataDisplays.add(dataDisplay);
                location=rs.getDouble("location");
                rs.next();
            }
            //下一条数据不上该线杆的,则说明该线杆的数据取完了,接下来取该线杆的相关信息.
            rs.previous();
            DataMapDisplay dataMapDisplay = new DataMapDisplay(
                    dataDisplays,
                    rs.getLong("l_id"),
                    rs.getLong("a_id"),
                    rs.getLong("o_id"),
                    rs.getDouble("longitude"),
                    rs.getDouble("latitude"));
            //将该线杆的数据list和信息放入存放线路所有线杆数据的listlist里.
            dataMapDisplays.add(dataMapDisplay);
        }
        this.closeConnection();
        return dataMapDisplays;
    }*/

    /*这个是百度地图上线杆的数据展示*/
    public List<DataDisplay> searchData(Double longitude, Double latitude) throws Exception {
        List<DataDisplay> dataList=new ArrayList<>();
        this.initConnection();
//        String sql="select pole.p_id, data.* ,pole.location,line.name,node.source from data,node,pole,line where pole.p_id=node.p_id and pole.l_id=line.l_id and node.n_id=data.n_id and data.sampling_time>=now()-101010101 and pole.o_id=?";
        String sql="  SELECT DISTINCT pole.p_id, data.* ,pole.location,line.name, node.source\n" +
                "        from organization,data,node,pole,line WHERE node.n_id=data.n_id\n" +
                "        and pole.p_id=node.p_id\n" +
                "        and pole.l_id=line.l_id\n" +
                "        AND pole.longitude=? AND pole.latitude=? ORDER BY sampling_time DESC limit 3";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setDouble(1, longitude);
        ps.setDouble(2, latitude);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            DataDisplay data=new DataDisplay(rs.getLong("p_id"),rs.getLong("d_id"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("sampling_time")).toString(),rs.getDouble("out_temperature"),
                    rs.getDouble("wire_temperature"),rs.getDouble("sag"),rs.getDouble("electricity"),rs.getDouble("voltage"),
                    rs.getDouble("humidity"),rs.getLong("n_id"),rs.getLong("location"),rs.getString("name"),rs.getString("source"));
            dataList.add(data);
        }
        System.out.println("searchAllData:"+dataList);
        this.closeConnection();
        return dataList;
    }

    public List<DataDisplay> searchData(Long pid) throws Exception {
        List<DataDisplay> dataList=new ArrayList<>();
        this.initConnection();
//        String sql="select pole.p_id, data.* ,pole.location,line.name,node.source from data,node,pole,line where pole.p_id=node.p_id and pole.l_id=line.l_id and node.n_id=data.n_id and data.sampling_time>=now()-101010101 and pole.o_id=?";
        String sql="select DISTINCT pole.p_id, data.* ,pole.location,line.name, node.source\n" +
                "from organization,data,node,pole,line WHERE node.n_id=data.n_id\n" +
                "                                            and pole.p_id=node.p_id\n" +
                "                                            and pole.l_id=line.l_id\n" +
                "                                            AND pole.p_id=?  ORDER  BY sampling_time DESC";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setDouble(1, pid);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            DataDisplay data=new DataDisplay(rs.getLong("p_id"),rs.getLong("d_id"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("sampling_time")).toString(),rs.getDouble("out_temperature"),
                    rs.getDouble("wire_temperature"),rs.getDouble("sag"),rs.getDouble("electricity"),rs.getDouble("voltage"),
                    rs.getDouble("humidity"),rs.getLong("n_id"),rs.getLong("location"),rs.getString("name"),rs.getString("source"));
            dataList.add(data);
        }
        this.closeConnection();
        return dataList;
    }


    public List<DataDisplay> searchPreviousData(Long pid) throws Exception {
        List<DataDisplay> dataList=new ArrayList<>();
        List<DataDisplay> dataList1=new ArrayList<>();
        this.initConnection();
        String sql="select DISTINCT pole.p_id, data.* ,pole.location,line.name, node.source\n" +
                "from organization,data,node,pole,line WHERE node.n_id=data.n_id\n" +
                "                                            and pole.p_id=node.p_id\n" +
                "                                            and pole.l_id=line.l_id\n" +
                "                                            AND pole.p_id=?  ORDER  BY sampling_time desc limit 10";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setDouble(1, pid);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            DataDisplay data=new DataDisplay(rs.getLong("p_id"),rs.getLong("d_id"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("sampling_time")).toString(),rs.getDouble("out_temperature"),
                    rs.getDouble("wire_temperature"),rs.getDouble("sag"),rs.getDouble("electricity"),rs.getDouble("voltage"),
                    rs.getDouble("humidity"),rs.getLong("n_id"),rs.getLong("location"),rs.getString("name"),rs.getString("source"));
            dataList.add(data);
        }
        System.out.println("searchAllData:"+dataList);
        Iterator it = dataList.iterator();
        ListIterator<DataDisplay> li = dataList.listIterator();// 获得ListIterator对象
        for (li = dataList.listIterator(); li.hasNext();) {// 将游标定位到列表结尾
            li.next();
        }
        for (; li.hasPrevious();) {// 逆序输出列表中的元素
            dataList1.add(li.previous());
        }
        System.out.println("searchAllData:"+dataList1);
        this.closeConnection();
        return dataList;
    }

    public List<DataDisplay> searchCurrentData(Long pid) throws Exception {

        List<DataDisplay> dataList=new ArrayList<>();
        this.initConnection();
//        String sql="select pole.p_id, data.* ,pole.location,line.name,node.source from data,node,pole,line where pole.p_id=node.p_id and pole.l_id=line.l_id and node.n_id=data.n_id and data.sampling_time>=now()-101010101 and pole.o_id=?";
        String sql="select DISTINCT pole.p_id, data.* ,pole.location,line.name, node.source\n" +
                "        from organization,data,node,pole,line WHERE node.n_id=data.n_id\n" +
                "        and pole.p_id=node.p_id\n" +
                "        and pole.l_id=line.l_id\n" +
                "        AND pole.p_id=?\n" +
                "        AND sampling_time <= now()  AND sampling_time >=date_sub(now(), interval '5' day_second) ORDER  BY sampling_time";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setDouble(1, pid);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            DataDisplay data=new DataDisplay(rs.getLong("p_id"),rs.getLong("d_id"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("sampling_time")).toString(),rs.getDouble("out_temperature"),
                    rs.getDouble("wire_temperature"),rs.getDouble("sag"),rs.getDouble("electricity"),rs.getDouble("voltage"),
                    rs.getDouble("humidity"),rs.getLong("n_id"),rs.getLong("location"),rs.getString("name"),rs.getString("source"));
            dataList.add(data);
        }
        this.closeConnection();
        return dataList;
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
