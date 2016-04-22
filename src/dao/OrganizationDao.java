package dao;

import bean.Organization;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 3/29/16.
 */
public class OrganizationDao {
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

    public Organization findOne(Long organizationId) throws Exception {
        this.initConnection();
        Organization organization = new Organization();
        String sql = "select * from organization where o_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, organizationId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            organization.setOid(rs.getLong("o_id"));
            organization.setName(rs.getString("name"));
            organization.setAddress(rs.getString("address"));
            organization.setParentId(rs.getLong("parent_id"));
            organization.setParentIds(rs.getString("parent_ids"));
        }
        this.closeConnection();
        return organization;
    }



    public List<Organization> searchOrganizationSub(Organization organization) throws Exception {
        System.out.println("3:--");
        this.initConnection();
        List<Organization> organizationList = new ArrayList<Organization>();
        String sql = "SELECT * FROM organization WHERE organization.o_id=? OR organization.parent_ids like ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, organization.getOid());
        ps.setString(2, organization.getParentIds()+organization.getOid()+"/%");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Organization orga=new Organization(rs.getLong("o_id"),rs.getString("name"),rs.getString("address"),rs.getLong("parent_id"),rs.getString("parent_ids"));
            organizationList.add(orga);
            System.out.println("4:--"+organizationList);
        }
        this.closeConnection();
        return organizationList;
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