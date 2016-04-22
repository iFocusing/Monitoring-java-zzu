package dao;

import bean.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
        String sql="select pole.p_id, data.* ,pole.location,line.name,node.source from data,node,pole,line where node.p_id=? and pole.p_id=node.p_id and pole.l_id=line.l_id and node.n_id=data.n_id and data.sampling_time<=? and data.sampling_time>=?";
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



    public List<DataMapDisplay> searchDataMapByOid(Long lid, Organization organization) throws Exception {
        List<DataMapDisplay> dataMapDisplays=new ArrayList<DataMapDisplay>();
        this.initConnection();
        String sql="select DISTINCT pole.p_id, data.* ,pole.location,line.name, node.source\n" +
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
            while (rs.getDouble("location")==location) {
                dataDisplay = new DataDisplay(rs.getLong("p_id"), rs.getLong("d_id"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("sampling_time")).toString(), rs.getDouble("out_temperature"),
                        rs.getDouble("wire_temperature"), rs.getDouble("sag"), rs.getDouble("electricity"), rs.getDouble("voltage"),
                        rs.getDouble("humidity"), rs.getLong("n_id"), rs.getLong("location"), rs.getString("name"), rs.getString("source"));
                dataDisplays = new ArrayList<DataDisplay>();
                dataDisplays.add(dataDisplay);
                location=rs.getDouble("location");
                rs.next();
            }
            rs.previous();
            DataMapDisplay dataMapDisplay = new DataMapDisplay(
                    dataDisplays,
                    rs.getLong("l_id"),
                    rs.getLong("a_id"),
                    rs.getLong("o_id"),
                    rs.getDouble("longitude"),
                    rs.getDouble("latitude"));
            dataMapDisplays.add(dataMapDisplay);
        }
//        System.out.println("22: "+poleList);
        this.closeConnection();
        return dataMapDisplays;
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