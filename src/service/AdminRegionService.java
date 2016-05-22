package service;

import bean.AdminRegion;
import dao.OrganizationDao;
import dao.AdminRegionDao;

import java.util.List;

/**
 * Created by root on 3/29/16.
 */
public class AdminRegionService {
    private AdminRegionDao adminRegionDao = new AdminRegionDao();
    private OrganizationDao organizationDao = new OrganizationDao();

    public List<AdminRegion> searchAdminRegionByOid(Long oid) throws Exception {   // System.out.println(adminRegionDao.searchRegion(organizationDao.findOne(oid)));
        System.out.println(organizationDao.searchOrganization(oid));
        return adminRegionDao.searchRegion(organizationDao.searchOrganization(oid));
    }
}
