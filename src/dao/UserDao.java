package dao;

import bean.Organization;
import bean.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
     *
     * @return
     * @param
     * @author HPY
     */
    public String findUidsByRoleId(Long rid)throws Exception{
        String uids="0";
        if (!(rid==null)){
            this.initConnection();
            String sql = "  SELECT user.u_id FROM user,user_role WHERE user.u_id=user_role.u_id AND user_role.r_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, rid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                uids+=","+rs.getLong(1);
            }

            this.closeConnection();
        }

        return uids;
    }
    //通过Otganization对象中的organizationId，查询一列用户
    public List<User> findOne(Organization organization) throws Exception {

        this.initConnection();
        List<User> userList = new ArrayList<User>();

        String sql = "select * from user where o_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, organization.getOid());
        //把这个得到的organizationId（和jsp页面上得到的对应的），给第一个？问号
        ResultSet rs = ps.executeQuery();
        //执行这句aql语句，查询出来一个结果
        while (rs.next()) {
            //把查询到的一条数据给结果集：rs（对象organization）
            User user = new User();
            user.setUid(rs.getInt("u_id"));
            user.setOid(rs.getLong("o_id"));
            user.setUsername(rs.getString("username"));
            user.setSex(rs.getBoolean("sex"));
            user.setAddress(rs.getString("address"));
            user.setTel(rs.getString("tel"));
            user.setBirthday(rs.getDate("birthday"));
            user.setStatus(rs.getBoolean("status"));
            //把一个user添加到集合中
            userList.add(user);
        }
//        System.out.println(userList);
        this.closeConnection();
        return userList;
    }
    public List<Long> findFids(String username)throws Exception{

        this.initConnection();
        String sql = "select DISTINCT role_function.f_id  from user,user_role,role_function \n" +
                "WHERE user.u_id=user_role.u_id AND user_role.r_id=role_function.r_id AND user.username=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        ArrayList<Long> fids= new ArrayList<Long>();
        while (rs.next()){

            fids.add(rs.getLong(1));
        }
//        System.out.println("dao"+fids.get(1));
        return fids;
    }
    public User findOneUser(String username) throws  Exception{
        this.initConnection();
        String sql = "select * from user where username=?";
//        String sql = "select DISTINCT user.*,role_function.f_id  from user,user_role,role_function \n" +
//                "WHERE user.u_id=user_role.u_id AND user.username=? ";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        //if re.next()不为空
        User u = null;

        while (rs.next()) {
            u = new User();
            u.setUid(rs.getLong("u_id"));
            u.setUsername(rs.getString("username"));
            u.setAddress(rs.getString("address"));
            u.setPassword(rs.getString("password"));
            u.setTel(rs.getString("tel"));
            u.setBirthday(rs.getDate("birthday"));
            u.setStatus(rs.getBoolean("status"));
            u.setSex(rs.getBoolean("sex"));
            u.setOid(rs.getLong("o_id"));
        }

        this.closeConnection();
        return u;

    }
    public void addNewUser(User user,String[] roles)throws Exception{
        this.initConnection();
        String sql0 = "SELECT MAX(u_id)  FROM  user";
        PreparedStatement ps0 = conn.prepareStatement(sql0);
        ResultSet rs0 = ps0.executeQuery();
        String max = "0";
        if (rs0.next()) {
            max = rs0.getString(1);
//            System.out.println("max = " + max);
        }
        Long madrid = Long.valueOf(max);
        madrid=madrid+1;
        System.out.println("最大值加1:::"+madrid);

        String  sql = "insert into user(username,password,sex,address,tel,birthday,status,o_id,u_id)values (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,user.getUsername());
        ps.setString(2,user.getPassword());
        ps.setBoolean(3,user.getSex());
        ps.setString(4,user.getAddress());
        ps.setString(5,user.getTel());
        ps.setDate(6, java.sql.Date.valueOf(user.getBirthdayString()));
        ps.setBoolean(7,user.getStatus());
        ps.setLong(8,user.getOid());
        ps.setLong(9,madrid);
        ps.executeUpdate();




        for (int i = 0; i <roles.length ; i++) {
//            System.out.println(Long.parseLong(roles[i]));
            String  sql1 = "insert into user_role(u_id, r_id)values (?,?)";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setLong(1,madrid);
            ps1.setLong(2,Long.parseLong(roles[i]));
            System.out.println("u_id：："+madrid+"r_id：："+Long.parseLong(roles[i]));
            ps1.executeUpdate();
        }

        this.closeConnection();
    }
    public List<User> showAll01()throws Exception {
        User user = new User();
        List<User> userList=new ArrayList<User>() ;
        List<String> roleNameList = new ArrayList<String>();
        Long a = 0L;

        this.initConnection();
        String sql = "select user.* ,organization.name,role.name FROM user,user_role,role,organization WHERE organization.o_id=user.o_id AND user.u_id=user_role.u_id ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            a=rs.getLong("u_id");
            rs.beforeFirst();
            while (rs.next()) {

                if (a == rs.getLong("u_id")) {
                    //得到角色名字符串数组，并变成一个字符串set给uesr里面的roleNames字符串

                } else {
                    //提交这个user到uesrlist中去

                    userList.add(user);
                }
                //1.0得到除role之外的所有列
                user.setUid(rs.getLong("u_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setSex(rs.getBoolean("sex"));
                user.setAddress(rs.getString("address"));
                user.setTel(rs.getString("tel"));
                user.setBirthday(rs.getDate("birthday"));
                user.setStatus(rs.getBoolean("status"));
                user.setOid(rs.getLong("o_id"));
                user.setOrganizationName("organization.name");

                //1.1得到rolename放到一个数组中去,并放到user中
                roleNameList.add(rs.getString("role.name"));
                user.setRoleNames(Arrays.asList(roleNameList).toString());
                //给a重新赋值
                a =rs.getLong("u_id");
            }
        }
        this.closeConnection();
        return userList;
    }
    //只显示比用户组织机构以下的用户表
    public List<User> showUserListUnderLoginUser(User loginUser)throws Exception{
        List<User> userList=new ArrayList<User>() ;
        //loginuesr用来找到user所在组织的id，让显示的只有本级和下级用户

        this.initConnection();


        String sql = "SELECT user.*,organization.name,group_concat(role.name)\n" +
                "FROM user INNER JOIN organization ON user.o_id = organization.o_id\n" +
                "  LEFT JOIN (user_role INNER JOIN role ON user_role.r_id = role.r_id)  ON user.u_id = user_role.u_id WHERE organization.o_id=? OR organization.parent_ids like ? GROUP BY user.u_id";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1,loginUser.getOid());
        ps.setString(2,"%"+loginUser.getOid()+"%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            User user = new User();
            user.setUid(rs.getLong("u_id"));
            user.setUsername(rs.getString("username"));
//            user.setPassword(rs.getString("password"));
            user.setSex(rs.getBoolean("sex"));
            user.setAddress(rs.getString("address"));
            user.setTel(rs.getString("tel"));
            user.setBirthday(rs.getDate("birthday"));
            user.setStatus(rs.getBoolean("status"));
            user.setOid(rs.getLong("o_id"));
            user.setOrganizationName(rs.getString("organization.name"));
            user.setRoleNames(rs.getString("group_concat(role.name)"));

            userList.add(user);
        }
        this.closeConnection();
        return userList;
    }
    //如果是从显示的用户中选择的话，也要loginUser参数的
    public List<User> searchUserListUnderLoginUser(User loginUser,User user)throws Exception{
//        System.out.println("输入关键字是："+user.getUsername());
        List<User> userList=new ArrayList<User>() ;
        String sql ="select user.*,organization.name,group_concat(role.name) " +
                " FROM user, organization,user_role,role where user.o_id=organization.o_id AND user.u_id=user_role.u_id AND user_role.r_id=role.r_id AND(organization.o_id=? OR organization.parent_ids like ? ) " ;

        if (!(user.getUsername()==null)){
            sql+=" AND user.username LIKE ?  ";
        }
        if (user.getRoleid()!=null){
            sql+= " AND user.u_id IN ("+findUidsByRoleId(user.getRoleid())+")";

        }
        if (!(user.getOid()==0)){
            sql+=" AND user.o_id= ?  ";
        }

        sql+="GROUP BY user.u_id  ";
//        System.out.println("hhh:"+sql);
        this.initConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
//        System.out.println("sql语句"+sql);
        ps.setLong(1,loginUser.getOid());
        ps.setString(2,"%"+loginUser.getOid()+"%");
        int i=3;
        if (user.getUsername()!=null){
            ps.setString(i,"%"+user.getUsername()+"%");
            i++;
//        System.out.println("用户名是："+user.getUsername());
        }
        if (!(user.getOid()==0)){
            ps.setLong(i,user.getOid());
        }
//        System.out.println("住址机构id是:"+user.getOid());
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            User u = new User();
            u.setUid(rs.getLong("u_id"));
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            u.setSex(rs.getBoolean("sex"));
            u.setAddress(rs.getString("address"));
            u.setTel(rs.getString("tel"));

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
            String dstr=rs.getString("birthday");
            java.util.Date date=sdf.parse(dstr);
            u.setBirthday(date);
//            System.out.println(date);
//            System.out.println(dstr);

            u.setBirthdayString(rs.getString("birthday"));
            u.setStatus(rs.getBoolean("status"));
            u.setOid(rs.getLong("o_id"));
            u.setOrganizationName(rs.getString("organization.name"));
            u.setRoleNames(rs.getString("group_concat(role.name)"));
            if (u.getSex()==true){
                u.setSexString("男");
            }else {
                u.setSexString("女");
            }
            if (u.getStatus()==true){
                u.setStatusString("正常");
            }else {
                u.setStatusString("锁死");
            }
            userList.add(u);
        }
        this.closeConnection();

        return userList;


    }
    //从显示列表中选择，不需要loginUser，不用判断uid是否为空只要得到一个uid数据；先删除user-role，再删除user
    public void deleateUser(User user)throws Exception{
        this.initConnection();
        String sql ="DELETE FROM user_role WHERE user_role.u_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1,user.getUid());
        ps.executeUpdate();

        String sql1="DELETE FROM user WHERE user.u_id=?";
        PreparedStatement ps1 = conn.prepareStatement(sql1);
        ps1.setLong(1,user.getUid());
        ps1.executeUpdate();


        this.closeConnection();

    }
    //必须传入user对应的多个roleid
    public void modifyUser(User user,String[] roles)throws Exception{
        this.initConnection();
        System.out.println("daozhixingl ");
        Long[] ruction = user.getRoleNamesshuzhu();

        String sql ="DELETE FROM user_role WHERE user_role.u_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1,user.getUid());
        ps.executeUpdate();

        for (int a = 0; a < roles.length; a++) {
            String  sql1 = "insert into user_role(u_id, r_id)values (?,?)";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setLong(1,user.getUid());
            ps1.setLong(2,Long.parseLong(roles[a]));
//            System.out.println("u_id：："+user.getUid()+"r_id：："+Long.parseLong(roles[i]));
            ps1.executeUpdate();
        }

        String  sql3 = "UPDATE user SET username=?,password=?,sex=?,address=?,tel=?,birthday=?,status=?,o_id=? WHERE u_id=?";

        PreparedStatement ps3 = conn.prepareStatement(sql3);
        ps3.setString(1,user.getUsername());
        ps3.setString(2,user.getPassword());
        ps3.setBoolean(3,user.getSex());
        ps3.setString(4,user.getAddress());
        ps3.setString(5,user.getTel());
        ps3.setDate(6, java.sql.Date.valueOf(user.getBirthdayString()));
        ps3.setBoolean(7,user.getStatus());
        ps3.setLong(8,user.getOid());
        ps3.setLong(9,user.getUid());
        ps3.executeUpdate();
        this.closeConnection();
        System.out.println("dao执行完了");
    }
    public void modifyPassword(User user,String newPassword)throws Exception{
        this.initConnection();

        String  sql = "UPDATE user SET password= ? WHERE u_id= ? ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,newPassword);
        ps.setLong(2,user.getUid());
        ps.executeUpdate();
//        System.out.println(newPassword+"-----"+user.getUid());
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
