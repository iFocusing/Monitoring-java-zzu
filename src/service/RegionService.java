package service;

import bean.Region;
import dao.OrganizationDao;
import dao.RegionDao;

import java.util.List;

/**
 * Created by root on 3/29/16.
 */
public class RegionService {
    private RegionDao regionDao = new RegionDao();
    private OrganizationDao organizationDao = new OrganizationDao();

    public List<Region> search(Long organizationId) throws Exception {
        return regionDao.searchRegion(organizationDao.findOne(organizationId));
    }
}
