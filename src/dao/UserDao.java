package dao;

import bean.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Administrator on 2016/4/13.
 */
public class UserDao {
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

    public Long searchOidByUid(Long uid) throws Exception {
        this.initConnection();
        Long oid = null;
        String sql="select o_id from user where u_id=?";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setLong(1,uid);
        ResultSet rs=ps.executeQuery();
        while (rs.next()){
            oid=rs.getLong("o_id");
        }
        return oid;
    }

    public User verifyUser(User user) throws Exception {
        this.initConnection();
        User user1=null;
        String username=user.getUsername();
        String password=user.getPassword();
        String sql="select * from user where username=? and password=?";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setString(1,username);
        ps.setString(2,password);
        ResultSet rs=ps.executeQuery();
        if(rs.next()){
            user1=new User(
                    rs.getLong("u_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getBoolean("sex"),
                    rs.getString("address"),
                    rs.getString("tel"),
                    rs.getDate("birthday"),
                    rs.getBoolean("status"),
                    rs.getLong("o_id"));
        }
        this.closeConnection();
        return user1;
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
