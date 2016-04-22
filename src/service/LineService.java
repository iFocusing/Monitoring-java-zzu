package service;

import bean.Line;
import dao.AdminRegionDao;
import dao.LineDao;
import dao.OrganizationDao;

import java.util.List;

/**
 * Created by Administrator on 2016/3/30.
 */
public class LineService {
    private LineDao lineDao = new LineDao();
    private OrganizationDao organizationDao = new OrganizationDao();
    private AdminRegionDao adminRegionDao = new AdminRegionDao();

    public List<Line> searchLineInOraganizationAdmin(Long aid,Long oid) throws Exception {
//        System.out.println(lineDao.searchLine(adminRegionDao.searchRegion(aid) , organizationDao.findOne(oid)));
        return lineDao.searchLine(adminRegionDao.searchRegion(aid) , organizationDao.findOne(oid));
    }


    public List<Line> searchLineInOraganization(Long oid) throws Exception {
        return lineDao.searchLine(organizationDao.findOne(oid));
    }

}
