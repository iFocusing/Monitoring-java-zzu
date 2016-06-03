package bean;
import java.util.Date;

/**
 * Created by hanpengyu on 2016/4/12.
 */
public class User {
    private long uid;
    private String username;
    private String password;
    private Boolean sex;
    private String address;
    private String tel;
    private Date birthday;
    private Boolean status;
    private long oid;

    private String birthdayString;
    private String organizationName;
    private Long[] roleNamesshuzhu;
    private Long[] fIds;
    private String roleNames;
    //放查询时候用的roleid
    private Long roleid;
    private String sexString;
    private String statusString;

    public String getBirthdayString() {
        return birthdayString;
    }

    public void setBirthdayString(String birthdayString) {
        this.birthdayString = birthdayString;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Long[] getRoleNamesshuzhu() {
        return roleNamesshuzhu;
    }

    public void setRoleNamesshuzhu(Long[] roleNamesshuzhu) {
        this.roleNamesshuzhu = roleNamesshuzhu;
    }

    public Long[] getfIds() {
        return fIds;
    }

    public void setfIds(Long[] fIds) {
        this.fIds = fIds;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    public String getSexString() {
        return sexString;
    }

    public void setSexString(String sexString) {
        this.sexString = sexString;
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public User() {
    }

    public User(long uid, String username, String password, Boolean sex, String address, String tel, Date birthday, Boolean status, long oid) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.address = address;
        this.tel = tel;
        this.birthday = birthday;
        this.status = status;
        this.oid = oid;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sex=" + sex +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", birthday=" + birthday.toString() +
                ", status=" + status +
                ", oid=" + oid +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (oid != user.oid) return false;
        if (uid != user.uid) return false;
        if (address != null ? !address.equals(user.address) : user.address != null) return false;
        if (birthday != null ? !birthday.equals(user.birthday) : user.birthday != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (sex != null ? !sex.equals(user.sex) : user.sex != null) return false;
        if (status != null ? !status.equals(user.status) : user.status != null) return false;
        if (tel != null ? !tel.equals(user.tel) : user.tel != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (uid ^ (uid >>> 32));
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (tel != null ? tel.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (int) (oid ^ (oid >>> 32));
        return result;
    }
}