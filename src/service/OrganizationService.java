package service;

import bean.Organization;
import bean.User;
import dao.OrganizationDao;

import java.util.List;

/**
 * Created by huojingjing on 16/4/19.
 */
public class OrganizationService {
    private OrganizationDao organizationDao = new OrganizationDao();

    public List<Organization> searchOrganizationSub(long oid) throws Exception {
        System.out.println("2--");
        return organizationDao.searchOrganizationSub(organizationDao.searchOrganization(oid));
    }
    /**
     * Created by SLJ
     */
    public  List<Organization> QuanOrganization() throws Exception {
        return organizationDao.organizationQuan();
    }
    /**
     * Created by HPY
     */
    public boolean add(User user, Organization organization)throws Exception{
        Organization parent = organizationDao.findOne(organization.getParentId());
        // TODO 判断该父组织是否为当前用户的子组织
        organization.setParentIds(parent.getParentIds() + parent.getOid() + "/");
        String s1=organization.getParentIds();
        String s2=String.valueOf(organizationDao.findOidByUsername(user.getUsername()));
        boolean flag;
        if (s1.contains(s2)) {
// 已经有了了organization.setParentId(parent.getOid());
            organization.setParentIds(parent.getParentIds() + parent.getOid() + "/");
            organizationDao.addNewOrganization(organization);
            flag = true;
//            System.out.println("添加的语句执行了");
        }else {
            flag = false;
//                System.out.println("添加的语句没有执行");
        }
        return flag;
    }
    public boolean DeleateOrganization(User user,Organization organization1)throws Exception{
        //传入的只有id，线根据id查到这个organization
        Organization organization;
        organization=organizationDao.findOneByName(organization1.getName());

        // TODO 判断该父组织是否为当前用户的子组织
//通过本机构的父id查到，父机构的ids，+父机构id。得到本机构ids值
        Organization parent ;
        parent= organizationDao.findOne(organization.getParentId());
//        organization.setParentIds(parent.getParentIds() + parent.getOid() + "/");
        System.out.println(parent.getParentId());
        String s1=organization.getParentIds();
//通过用户名查到用户所在组织机构的id
        String s2=String.valueOf(organizationDao.findOidByUsername(user.getUsername()));
        boolean flag;
        if (s1.contains(s2)) {
//   已经有了了  organization.setParentId(parent.getOid());
            organizationDao.DeleateOrganization(organization);
            flag = true;
            System.out.println("删除的语句执行了");
        }else {
            flag = false;
            System.out.println("删除的语句没有执行");
        }
        return flag;
    }
    public boolean UpdateOrganization(Organization organization)throws Exception{
        Organization parent ;
        parent= organizationDao.findOne(organization.getParentId());
        organization.setParentIds(parent.getParentIds() + parent.getOid() + "/");
        boolean flag;
        if (true){
            organizationDao.UpdateOrganization(organization);
            flag=true;
        }
        return flag;


    }
    public List<Organization> ShowOrganization(User user)throws Exception{
        return organizationDao.ShowOrganization(user);
    }
    public List<Organization> search(User user,Organization organization)throws Exception{
        return organizationDao.searchOrganization(user,organization);

    }


}
