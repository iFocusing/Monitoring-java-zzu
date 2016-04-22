package service;

import bean.Data;
import bean.DataMapDisplay;
import bean.Line;
import bean.Pole;
import dao.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/10.
 */
public class PoleService {
    private OrganizationDao organizationDao = new OrganizationDao();
    private AdminRegionDao adminRegionDao = new AdminRegionDao();
    private PoleDao poleDao = new PoleDao();
    private LineDao lineDao = new LineDao();

    public List<Pole> searchPoleByLine(Long oid, Long aid, Long lid) throws Exception {
        return poleDao.searchPoleByLine(adminRegionDao.searchRegion(aid), organizationDao.findOne(oid), lineDao.searchLine(lid));
    }
}



