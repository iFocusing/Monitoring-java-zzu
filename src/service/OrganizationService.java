package service;

import bean.Organization;
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


}
