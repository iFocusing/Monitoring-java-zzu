package dao;

import bean.AdminRegion;
import bean.Line;
import bean.Organization;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/30.
 */
public class LineDao {
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

    public List<Line> searchLine(AdminRegion adminRegion, Organization organization) throws Exception {
        List<Line>  lineList=new ArrayList<Line>();
        this.initConnection();
//        String sql="select DISTINCT line.* from admin_region,organization,pole,line where line.l_id=pole.l_id and admin_region.a_id=? and admin_region.a_id=pole.a_id and pole.o_id=organization.o_id \n" +
//                "                                                                and (organization.o_id=? or organization.parent_ids like ?)";
        String sql="SELECT * FROM line WHERE l_id IN (SELECT DISTINCT pole.l_id FROM pole,admin_region WHERE pole.a_id=admin_region.a_id and (admin_region.a_id=? OR admin_region.parent_ids like ?)  and o_id IN (SELECT organization.o_id FROM organization WHERE organization.o_id=? OR organization.parent_ids like ?)\n" +
                ")";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setLong(1, adminRegion.getAid());
        ps.setString(2,adminRegion.getParentIds()+adminRegion.getAid()+"/%");
        ps.setLong(3,organization.getOid());
        ps.setString(4, organization.getParentIds()+organization.getOid()+"/%");
//        System.out.println(adminRegion.getAid()+organization.getOid()+ organization.getParentIds()+organization.getOid()+"/%");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
                Line line=new Line(rs.getString("name"),rs.getLong("l_id"));
                lineList.add(line);
        }
        this.closeConnection();
        return lineList;
    }

    public  Line searchLine(Long lid) throws Exception {
        Line line =new Line();
        this.initConnection();
        String sql="select * from line where l_id=?";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setLong(1,lid);
        ResultSet rs=ps.executeQuery();
        if(rs.next()) {
            line = new Line(rs.getString("name"), rs.getLong("l_id"));
        }
//        System.out.println("1: "+line.getName());
        this.closeConnection();
        return line;
    }


    public List<Line> searchLine(Organization organization) throws Exception {
        List<Line>  lineList=new ArrayList<Line>();
        this.initConnection();
        String sql="SELECT * FROM line WHERE l_id IN (SELECT DISTINCT pole.l_id FROM pole WHERE  o_id IN (SELECT organization.o_id FROM organization WHERE organization.o_id=? OR organization.parent_ids like ?)) ORDER BY l_id";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setLong(1,organization.getOid());
        ps.setString(2, organization.getParentIds()+organization.getOid()+"/%");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Line line=new Line(rs.getString("name"),rs.getLong("l_id"));
            lineList.add(line);
        }
        this.closeConnection();
        return lineList;
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
