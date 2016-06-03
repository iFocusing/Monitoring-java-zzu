package dao;

import bean.Node;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanpengyu on 2016/4/27.
 */
public class NodeDao {
    private Connection conn = null;
    // 连接数据库方法
    public void initConnection() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monitoring","root","root");
    }
    // 关闭数据库方法
    public void closeConnection() throws Exception{
        conn.close();
    }
    public void add(String source)throws Exception{
        this.initConnection();
        String sql="INSERT INTO node(source)VALUES (?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,source);
        ps.executeUpdate();
        this.closeConnection();
    }
    public List<Node> show()throws Exception{
        this.initConnection();

        List<Node> nodeList = new ArrayList<Node>();
        String sql ="SELECT node.n_id ,node.source,node.p_id,line.name,organization.name FROM node,pole,line,organization\n" +
                " WHERE node.p_id=pole.p_id AND pole.l_id=line.l_id AND pole.o_id=organization.o_id";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Node node = new Node();
            node.setNid(rs.getLong("n_id"));
            node.setPid(rs.getLong("p_id"));
            node.setLname(rs.getString("line.name"));
            node.setOname(rs.getString("organization.name"));
            node.setSource(rs.getString("source"));
            nodeList.add(node);
        }
        this.closeConnection();
        return nodeList;
    }
    public List<Node> search(Node node)throws Exception{
        this.initConnection();
//        System.out.println("--值有源地址:"+node.getSource()+"--线路id"+node.getLid()+"--组织机构id:"+node.getOid());

        List<Node> nodeList = new ArrayList<Node>();
        String sql ="SELECT node.n_id ,node.source,node.p_id,line.name,organization.name\n" +
                "FROM node,pole,line,organization\n" +
                "WHERE node.p_id=pole.p_id AND pole.l_id=line.l_id AND pole.o_id=organization.o_id AND source LIKE ?";
        if (!(node.getLid()==null)){
            sql+="AND line.l_id=? ";
        }
        if (!(node.getOid()==null)){
            sql+="AND organization.o_id=? ";
        }

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,"%"+node.getSource()+"%");
        int i=2;
        if (!(node.getLid()==null)){
            ps.setLong(i,node.getLid());
            i++;
        }
        if (!(node.getOid()==null)){
           ps.setLong(i,node.getOid());
            i++;
        }

//        System.out.println(i);
//        System.out.println(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Node node1 = new Node();
            node1.setNid(rs.getLong("n_id"));
            node1.setPid(rs.getLong("p_id"));
            node1.setLname(rs.getString("line.name"));
            node1.setOname(rs.getString("organization.name"));
            node1.setSource(rs.getString("source"));
            nodeList.add(node1);
        }
        this.closeConnection();
        return nodeList;
    }
    public List<Node> searchNotUse(String source)throws Exception{
        this.initConnection();

        List<Node> nodeList = new ArrayList<Node>();
        String sql ="select  DISTINCT * from node WHERE p_id IS NULL AND source LIKE ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,"%"+source+"%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Node node1 = new Node();
            node1.setNid(rs.getLong("n_id"));
            node1.setSource(rs.getString("source"));
            nodeList.add(node1);
        }
        this.closeConnection();
        return nodeList;

    }
    public void deleate(Long nid)throws Exception{
        this.initConnection();
        String sql ="DELETE FROM node WHERE n_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1,nid);
        ps.executeUpdate();
        this.closeConnection();
    }
    public void modidy(Node node)throws Exception{
        this.initConnection();
        String sql ="UPDATE node SET node.p_id=? WHERE node.n_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1,node.getPid());
//        System.out.println("DAO"+node.getPid());
        ps.setLong(2,node.getNid());
        ps.executeUpdate();
        this.closeConnection();

    }
    public Long searchPolesNodeNumber(Long pid)throws Exception{
        this.initConnection();
        String sql ="SELECT COUNT(*) FROM node WHERE node.p_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1,pid);
        ResultSet rs = ps.executeQuery();
        if (rs.next());
        Long number= Long.valueOf(rs.getInt(1));
        System.out.println(number);
        this.closeConnection();
        return number;
    }
}
