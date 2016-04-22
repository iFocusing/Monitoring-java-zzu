package bean;

/**
 * Created by root on 3/29/16.
 */
public class Organization {
    private Long oid;
    private String name;
    private String address;
    private Long parentId;
    private String parentIds;

    public Organization() {
    }

    public Organization(Long oid, String name, String address, Long parentId, String parentIds) {
        this.oid = oid;
        this.name = name;
        this.address = address;
        this.parentId = parentId;
        this.parentIds = parentIds;
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }
}
