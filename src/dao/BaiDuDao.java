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
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monitoring?useUnicode=true&characterEncoding=GBK","root","root");
    }

    public boolean insertProvince(String name, String adcode) throws Exception {
        String parent_id="1";
        String parent_ids="0/1/";
        this.initConnection();
        String sql = "INSERT INTO admin_region(name,code,parent_id,parent_ids) VALUES (?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, adcode);
        ps.setString(3, parent_id);
        ps.setString(4, parent_ids);
        if(ps.executeUpdate()==1){
            System.out.println("插入成功");
            return true;
        }else{
            System.out.println("插入失败");
            return false;
        }
    }



    public boolean insertCity(String name, String adcode, String provinceAdcode) throws Exception {
        this.initConnection();

        String parent_id="";
        String sql0 = "select a_id FROM admin_region WHERE code=?";
        PreparedStatement ps0 = conn.prepareStatement(sql0);
        ps0.setString(1, provinceAdcode);
        ResultSet rs = ps0.executeQuery();
        if (rs.next()){
            parent_id=rs.getLong("a_id")+"";
            System.out.println(parent_id+"shikongde???");
        }
        String parent_ids="0/1/"+parent_id+"/";

        String sql = "INSERT INTO admin_region(name,code,parent_id,parent_ids) VALUES (?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, adcode);
        ps.setString(3, parent_id);
        ps.setString(4, parent_ids);
        if(ps.executeUpdate()==1){
            System.out.println("插入成功ßßßß");
            return true;
        }else{
            System.out.println("插入失败");
            return false;
        }
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
