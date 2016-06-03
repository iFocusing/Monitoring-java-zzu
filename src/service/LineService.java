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
        return lineDao.searchLine(adminRegionDao.searchRegion(aid) , organizationDao.searchOrganization(oid));
    }

    /*
     * @author HPY
     */
    public List<Line> searchLineInOraganization(Long oid) throws Exception {
        return lineDao.searchLine(organizationDao.searchOrganization(oid));
    }

    public List<Line> ShowLine()throws Exception{
        return lineDao.ShowLine();
    }
    public List<Line> SearchLine(String name)throws Exception{
        return lineDao.SearchLine(name);
    }
    public boolean AddLine(Line line) throws Exception{
        if (lineDao.FineOneByName(line)){
            lineDao.AddLine(line);
            return true;
        }else {
            return false;
        }

    }
    public void ModifyLine(Line line)throws Exception{
        lineDao.ModifyLineName(line);

    }

}
