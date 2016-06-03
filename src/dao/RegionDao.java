package dao;

import bean.Organization;
import bean.Region;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 3/29/16.
 */
public class RegionDao {
    private Connection conn = null;
    /**
     * 连接数据库
     * @return
     * @param
     */
    public void initConnection() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monitoring","root","root");
    }

    public List<Region> searchRegion(Organization organization) throws Exception {
        List<Region> regionList = new ArrayList<Region>();
        this.initConnection();
        String sql = "select admin_region.* from admin_region,organization,pole where admin_region.a_id=pole.a_id and pole.o_id=organization.o_id " +
                "and (organization.o_id=? or organization.parent_ids like ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, organization.getOid());
        ps.setString(2, organization.getParentIds()+organization.getOid()+"/%");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Region region = new Region();
            region.setAid(rs.getLong("a_id"));
            region.setName(rs.getString("name"));
            region.setCode(rs.getLong("code"));
            region.setParentId(rs.getLong("parent_id"));
            region.setParentIds(rs.getString("parent_ids"));
            regionList.add(region);
        }
        this.closeConnection();
        return regionList;
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
