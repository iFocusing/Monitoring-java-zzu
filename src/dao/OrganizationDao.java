package dao;

import bean.Organization;
import bean.User;

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

    public Organization searchOrganization(Long oid) throws Exception {
        this.initConnection();
        Organization organization = new Organization();
        String sql = "select * from organization where o_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, oid);
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
     *
     * @return  返回所有organazaton
     * @param
     * @author SLJ
     */

    public List<Organization>organizationQuan() throws Exception
    {
        this.initConnection();
        List<Organization> organizationList = new ArrayList<Organization>();
        String sql = "SELECT * FROM organization";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            Organization orga1=new Organization(rs.getLong("o_id"),rs.getString("name"),rs.getString("address"),rs.getLong("parent_id"),rs.getString("parent_ids"));
            organizationList.add(orga1);
            System.out.println("4:--"+organizationList);
        }
        this.closeConnection();
        return organizationList;

    }


    /**
     *
     * @return
     * @param
     * @author SLJ
     */
    public List<Organization> name_searchOrg(String org_name) throws Exception {

        this.initConnection();
        List<Organization> organizationList = new ArrayList<Organization>();
        String sql = "SELECT * FROM organization WHERE organization.name like ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, org_name+"%");

        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Organization orga=new Organization(rs.getLong("o_id"),rs.getString("name"),rs.getString("address"),rs.getLong("parent_id"),rs.getString("parent_ids"));
            organizationList.add(orga);

        }
        this.closeConnection();
        return organizationList;
    }
    /**
     *
     * @return
     * @param
     * @author HPY
     */
    public Organization findOne(Long organizationId) throws Exception {
        this.initConnection();
        Organization organization = new Organization();
        String sql = "select * from organization where o_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, organizationId);
        //把这个得到的organizationId（和jsp页面上得到的对应的），给第一个？问号
        ResultSet rs = ps.executeQuery();
        //执行这句aql语句，查询出来一个结果
        while (rs.next()){
            //把查询到的一条数据给结果集：rs（对象organization），
            organization.setOid(rs.getLong("o_id"));
            organization.setName(rs.getString("name"));
            organization.setAddress(rs.getString("address"));
            organization.setParentId(rs.getLong("parent_id"));
            organization.setParentIds(rs.getString("parent_ids"));
        }
        this.closeConnection();
        return organization;
    }
    public Organization findOneByName(String name) throws Exception{
        this.initConnection();
        Organization organization = new Organization();
        String sql = "select * from organization where name=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,name);
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
    public long findOidByUsername(String username)throws Exception{
        this.initConnection();
        Organization organization = new Organization();
        String sql = "select organization.o_id from organization,user where user.o_id=organization.o_id and username=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,username);
        ResultSet rs = ps.executeQuery();
        rs.next();
        organization.setOid(rs.getLong(1));
        return organization.getOid();

    }
    public void addNewOrganization(Organization organization) throws Exception{
//  public void addNewOrganization(String name, String address, Organization parentOrganization) throws Exception{
        this.initConnection();
        String  sql = "insert into organization(name,address,parent_id,parent_ids)values (?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,organization.getName());
        ps.setString(2, organization.getAddress());
        ps.setLong(3,organization.getParentId());
        ps.setString(4, organization.getParentIds());

//               String sql1 = "select * from organization where o_id=?";
//        PreparedStatement ps1 = conn.prepareStatement(sql1);
//        ps1.setLong(1,organization.getParentId());
//        ResultSet rs = ps.executeQuery();
//        if(rs.next()) {
//            Organization organization1=new Organization();
//            organization1.setParentIds(rs.getString("parent_ids"));
//            organization1.setOid(rs.getLong("o_id"));
//            ps.setString(4, organization1.getParentIds()+organization1.getOid()+"/%");
//        }

        ps.executeUpdate();
        this.closeConnection();

        //这里改怎么判断添加成功与否？
    }
    public void DeleateOrganization(Organization organization) throws Exception {
        this.initConnection();
        String  sql = "DELETE FROM organization WHERE o_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1,organization.getOid());
        ps.executeUpdate();
        this.closeConnection();
    }
    //展示用户所在组织机构的所有低级组织
    public List<Organization> ShowOrganization(User user)throws Exception{
        List<Organization> organizations = new ArrayList<Organization>();
        this.initConnection();
        String sql = "select organization.* from organization where o_id=? OR parent_ids like ? ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, user.getOid());
        ps.setString(2,"%"+user.getOid()+"/%");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Organization organization=new Organization();
            organization.setOid(rs.getLong("o_id"));
            organization.setName(rs.getString("name"));
            organization.setAddress(rs.getString("address"));
            organization.setParentId(rs.getLong("parent_id"));
            organization.setParentIds(rs.getString("parent_ids"));

            organizations.add(organization);
        }
        this.closeConnection();
        return organizations;

    }
    //通过输入的组织名，或地区名模糊查询低级组织机构
    public List<Organization> searchOrganization(User loginUser,Organization organization)throws Exception{

        List<Organization> organizationList = new ArrayList<Organization>();
        this.initConnection();
        String sql = "select organization.* from organization where organization.name LIKE? AND organization.address like ?AND (organization.o_id=? OR organization.parent_ids LIKE ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,"%"+organization.getName()+"%");
        ps.setString(2,"%"+organization.getAddress()+"%");
        ps.setLong(3,loginUser.getOid());
        ps.setString(4,"%"+loginUser.getOid()+"/%");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Organization organization1=new Organization();
            organization1.setOid(rs.getLong("o_id"));
            organization1.setName(rs.getString("name"));
            organization1.setAddress(rs.getString("address"));
            organization1.setParentId(rs.getLong("parent_id"));
            organization1.setParentIds(rs.getString("parent_ids"));
            organizationList.add(organization1);
        }
        this.closeConnection();
        return organizationList;
    }
    public void UpdateOrganization(Organization organization)throws Exception{
        this.initConnection();
//        String  sql = "UPDATE organization SET organization.name=?,organization.address=?,organization.parent_id=?,organization.parent_ids=? WHERE o_id=?";
        String  sql = "UPDATE organization SET organization.name=?,organization.address=?,organization.parent_id=?,organization.parent_ids=? WHERE o_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,organization.getName());
        ps.setString(2, organization.getAddress());
        ps.setLong(3,organization.getParentId());
        ps.setString(4, organization.getParentIds());
        ps.setLong(5,organization.getOid());
        System.out.println(organization.getOid()+organization.getAddress()+organization.getName()+organization.getParentId()+organization.getParentIds());

        ps.executeUpdate();
        this.closeConnection();
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
