package bean;

/**
 * Created by hanpengyu on 2016/4/26.
 */
public class Node {
    private Long nid;
    private Long pid;
    private String source;
    private String lname;
    private Long lid;
    private Long oid;
    private String oname;
    public Node(){}
//    public Node(Long nid,Long pid,String source,String lname, Long lid,Long oid,String oname){
//
//    }
    public Long getLid() {
        return lid;
    }

    public void setLid(Long lid) {
        this.lid = lid;
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getNid() {
        return nid;
    }

    public void setNid(Long nid) {
        this.nid = nid;
    }


}
