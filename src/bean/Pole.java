package bean;

/**
 * Created by Administrator on 2016/3/30.
 */
public class Pole {
    private long pid;
    private double longitude;
    private double latitude;
    private long location;
    private long lid;
    private long aid;
    private long oid;
    private String lname;
    private String aname;
    private String oname;
    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
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


    public Pole(){
    }

    public Pole(long pid, double longitude, double latitude, long location, long lid, long aid, long oid) {
        this.pid = pid;
        this.longitude = longitude;
        this.latitude = latitude;
        this.location = location;
        this.lid = lid;
        this.aid = aid;
        this.oid = oid;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public long getLocation() {
        return location;
    }

    public void setLocation(long location) {
        this.location = location;
    }

    public long getLid() {
        return lid;
    }

    public void setLid(long lid) {
        this.lid = lid;
    }

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }
}
