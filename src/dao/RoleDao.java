package dao;

import bean.Role;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hanpengyu on 2016/4/21.
 */
public class RoleDao {
    private Connection conn = null;

    public void initConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monitoring", "root", "root");
    }

    public void closeConnection() throws Exception {
        conn.close();
    }

    public void AddRole(Role role) throws Exception {
        Long[] ruction = role.getFunctions();
        this.initConnection();
        String sql = "SELECT MAX(r_id)  FROM role ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String max = "0";
        if (rs.next()) {
            max = rs.getString(1);
//            System.out.println("max = " + max);
        }
        Long madrid = Long.valueOf(max);
        madrid++;


        String sql1 = "  insert into role(r_id,name,description)values (?,?,?)";
//        String  sql1 = "begin try\n" +
//                "        begin tran\n" +
//                "           insert into role(r_id,name,description)values (?,?,?);\n" +
//                "           INSERT INTO role_function(r_id,f_id)VALUES (?,?)\n" +
//                "           --RAISERROR ('Error raised in TRY block.',16,1);\n" +
//                "        commit tran\n" +
//                "      end try\n" +
//                "      begin catch\n" +
//                "        rollback tran\n" +
//                "      end catch";
        PreparedStatement ps1 = conn.prepareStatement(sql1);
        ps1.setLong(1, madrid);
        ps1.setString(2, role.getName());
        ps1.setString(3, role.getDescription());
        ps1.executeUpdate();

        for (int a = 0; a < role.getFunctions().length; a++) {
            String sql2 = " INSERT INTO role_function(r_id,f_id)VALUES (?,?)";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setLong(1, madrid);
            ps2.setLong(2, ruction[a]);
            ps2.executeUpdate();
        }

        this.closeConnection();
    }

    public List<Role> show(String roleName,String description) throws Exception {

        this.initConnection();
//最终要返回的rolelist
        List<Role> roleList = new ArrayList<Role>();
//不需要先获得多少列
//        String sql="select count(*) from role;";
//        PreparedStatement ps = conn.prepareStatement(sql);
//        ResultSet rs= ps.executeQuery();
//        String a;
//        int a1 = 0;
//        if (rs.next()){
////            a=rs.getString(1);
//            a1= rs.getInt(1);
//        }
        //
        String sql1 = "select role.r_id,role.name,role.description FROM role" +
                " WHERE role.name LIKE ? AND role.description LIKE ?";
        PreparedStatement ps1 = conn.prepareStatement(sql1);
        ps1.setString(1,"%"+roleName+"%");
        ps1.setString(2,"%"+description+"%");
        ResultSet rs1 = ps1.executeQuery();
//所有的rid列表，下一步用来遍历每一个rid对应的多个系统功能
        ArrayList<Long> rids = new ArrayList<Long>();
        while (rs1.next()) {
            rids.add(rs1.getLong(1));
        }
//        这个可以得到


        for (int i = 0; i < rids.size(); i++) {

//得到每一个rid对应多少列系统功能
            String a;
            int a1 = 0;
            String sql = "select count(*) from role_function WHERE role_function.r_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, rids.get(i));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
//            a=rs.getString(1);
                a1 = rs.getInt(1);
            }
            String[] functionnames = new String[a1];


            String sql2 = "select role.r_id, role.name,role.description,function.name  from role,role_function,function where role.r_id=? AND role_function.r_id=? AND role_function.f_id=function.f_id";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
//            System.out.println("现在的id是" + rids.get(i));
            ps2.setLong(1, rids.get(i));
            ps2.setLong(2, rids.get(i));
//            System.out.println(rids.get(i));这个已经得到了
            ResultSet rs2 = ps2.executeQuery();
//
//            Long nnrid = null;
//            String nnname = null;
//            String nndescription = null;
//            List<String> funname=new ArrayList<>();
//            i=1;
//
//            while (rs2.next()){
//                nnrid=rs2.getLong(1);
//                nnname=rs2.getString(2);
//                nndescription=rs2.getString(3);
//
//                funname.set(i, rs2.getString(4));
//                i++;
//            System.out.println("现在的结果集是"+rs2.getLong(1)+rs2.getString(2)+rs2.getString(3)+rs2.getString(4));
//                System.out.println(rs2.getString(4));
//            }
//            role.setRid(nnrid);
//            role.setName(nnname);
//            role.setDescription(nndescription);
//            role.setFunctionsName(String.valueOf(funname));
//            roleList.add(role);
//            System.out.println(role.getFunctionsName());
            int b = 0;
            Role role = new Role();
            while (rs2.next()) {
//                System.out.println("现在的结果集是"+rs2.getLong(1)+rs2.getString(2)+rs2.getString(3)+rs2.getString(4));
                role.setRid(rs2.getLong(1));
                role.setName(rs2.getString(2));
                role.setDescription(rs2.getString(3));
                functionnames[b] = rs2.getString(4);
                b++;
            }
            role.setFunctionsName(Arrays.asList(functionnames).toString());
//            System.out.println("现在role对象中的数据是" + role.getName() + role.getDescription() + Arrays.asList(functionnames).toString());

            roleList.add(role);
        }

        this.closeConnection();

        return roleList;
    }

    public void ModifyRole(Role role) throws Exception {
        //需要的信息由role的id，name，description，和所有功能的ids数组Functions
        Long[] ruction = role.getFunctions();
        this.initConnection();
        String sql = "UPDATE role SET name = ?, description = ? WHERE r_id = ? ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, role.getName());
        ps.setString(2, role.getDescription());
        ps.setLong(3, role.getRid());
        ps.executeUpdate();

        String sql1 = "DELETE FROM role_function WHERE role_function.r_id= ?";
        PreparedStatement ps1 = conn.prepareStatement(sql1);
        ps1.setLong(1, role.getRid());
        ps1.executeUpdate();

        for (int a = 0; a < role.getFunctions().length; a++) {
            String sql2 = " INSERT INTO role_function(r_id,f_id)VALUES (?,?)";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setLong(1, role.getRid());
            ps2.setLong(2, ruction[a]);
            ps2.executeUpdate();

        }
        this.closeConnection();
    }

    public void DeleateRole(Role role) throws Exception {
        //传过来的只有一个rid
        //要先输出role-function表对应的数据，再删除role表
        this.initConnection();
        String sql = "DELETE FROM role_function WHERE role_function.r_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, role.getRid());
        ps.executeUpdate();

        String sql2 = "DELETE FROM user_role WHERE user_role.r_id =?";
        PreparedStatement ps2 = conn.prepareStatement(sql2);
        ps2.setLong(1, role.getRid());
        ps2.executeUpdate();

        String sql1 = "DELETE FROM role WHERE role.r_id=?";
        PreparedStatement ps1 = conn.prepareStatement(sql1);
        ps1.setLong(1, role.getRid());
        ps1.executeUpdate();



        this.closeConnection();
    }
//    public List<Role> search(Role role)throws Exception{
//
//    }
//    public void SearchRole(String name,String description)throws Exception{
//        List<Role> roleList = new ArrayList<Role>() ;
//        this.initConnection();
//        String sql = "select line.* from line ";
//        PreparedStatement ps = conn.prepareStatement(sql);
//        ResultSet rs = ps.executeQuery();
//        while (rs.next()){
//            Role role=new Role();
//            line.setLid(rs.getLong(1));
//            line.setName(rs.getString(2));
//            lineList.add(line);
//        }
//        this.closeConnection();
//        return lineList;
//    }

}
