package dao;

import bean.AdminRegion;
import bean.Organization;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 3/29/16.
 */
public class AdminRegionDao {
    private Connection conn = null;
    /**
     * 连接数据库
     * @return
     * @param
     * @author 黄诗鹤
     */
    public void initConnection() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monitoring","root","root");
    }
    /*
    *根据用户所在组织查询该组织的线杆所在的地区列表；
    * 实现的是 AdminRegionDao类的一个方法；
     *  */
    public List<AdminRegion> searchRegion(Organization organization) throws Exception {
        List<AdminRegion> adminRegionList = new ArrayList<AdminRegion>();
        this.initConnection();
        String sql = "select DISTINCT admin_region.* from admin_region,organization,pole where admin_region.a_id=pole.a_id and pole.o_id=organization.o_id \n" +
                "                and (organization.o_id=? or organization.parent_ids like ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, organization.getOid());
        ps.setString(2, organization.getParentIds()+organization.getOid()+"/%");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            AdminRegion adminRegion = new AdminRegion();
            adminRegion.setAid(rs.getLong("a_id"));
            adminRegion.setName(rs.getString("name"));
            adminRegion.setCode(rs.getLong("code"));
            adminRegion.setParentId(rs.getLong("parent_id"));
            adminRegion.setParentIds(rs.getString("parent_ids"));
            adminRegionList.add(adminRegion);
        }
        this.closeConnection();
        return adminRegionList;
    }

    public AdminRegion searchRegion(long aid) throws Exception {
        AdminRegion adminRegion=new AdminRegion();
        this.initConnection();
        String sql="SELECT admin_region.* from admin_region WHERE a_id=?";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setLong(1,aid);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            adminRegion=new AdminRegion(rs.getLong("a_id"),rs.getString("name"),rs.getLong("code"),rs.getLong("parent_id"),rs.getString("parent_ids"));
        }
        System.out.println("******"+adminRegion);
        this.closeConnection();
        return adminRegion;
    }


    /**
     * @return
     * @param
     * @author SLJ
     */
    public List<AdminRegion> name_searchAdm(String adm_name) throws Exception {
        List<AdminRegion> adminRegionList = new ArrayList<AdminRegion>();
        this.initConnection();

//        String sql = "SELECT * FROM organization WHERE organization.name like ?";
//        PreparedStatement ps = conn.prepareStatement(sql);
//        ps.setString(1, org_name+"%");
//
        String sql = "select * FROM admin_region WHERE admin_region.name like ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, adm_name+"%");

        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            AdminRegion adminRegion = new AdminRegion();
            adminRegion.setAid(rs.getLong("a_id"));
            adminRegion.setName(rs.getString("name"));
            adminRegion.setCode(rs.getLong("code"));
            adminRegion.setParentId(rs.getLong("parent_id"));
            adminRegion.setParentIds(rs.getString("parent_ids"));
            adminRegionList.add(adminRegion);
        }
        this.closeConnection();
        return adminRegionList;
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
