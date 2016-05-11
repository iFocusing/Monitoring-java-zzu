package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by huojingjing on 16/5/10.
 */
public class BaiDuDao {
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

    public boolean insertProvince(String name, String adcode) throws Exception {
        String parent_id="1";
        String parent_ids="0/1/";
        this.initConnection();
        String sql = "";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, adcode);
        ps.setString(3, parent_id);
        ps.setString(3, parent_ids);
        ResultSet rs = ps.executeQuery();
        return true;
    }



    public boolean insertCity(String name, String adcode, String provinceAdcode) throws Exception {
        this.initConnection();

        String parent_id="";
        String parent_ids="0/1/"+parent_id+"/";

        String sql = "";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, adcode);
        ps.setString(3, parent_id);
        ps.setString(3, parent_ids);
        ResultSet rs = ps.executeQuery();

        return true;
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
