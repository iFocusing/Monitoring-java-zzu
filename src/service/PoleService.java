package service;

import bean.Data;
//import bean.DataMapDisplay;
import bean.Line;
import bean.Pole;
import bean.User;
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
        return poleDao.searchPoleByLine(adminRegionDao.searchRegion(aid), organizationDao.searchOrganization(oid), lineDao.searchLine(lid));
    }

    public List<List<Pole>> searchPoleMapByOid(Long oid) throws Exception {
        List<Line> lineList=lineDao.searchLine(organizationDao.searchOrganization(oid)) ;
        List<List<Pole>> poleListList = new ArrayList<>();
        List<Long> lids=new ArrayList<Long>();
        for(int i=0;i<lineList.size();i++){
            lids.add(lineList.get(i).getLid());
        }
        for (Long lid : lids) {
            System.out.println(lid);
            List<Pole> poleList =poleDao.searchPoleMapByLidOrganization(lid,organizationDao.searchOrganization(oid));
            poleListList.add(poleList);
        }
        return poleListList;
    }
    /**
     * Created by SLJ
     */
    public List<Pole> searchPoleByLine( Long lid) throws Exception {
        return poleDao.searchPoleByLine(lid);
    }
    /**
     * Created by HPY
     */
    public void add(Pole pole )throws Exception{
        poleDao.addPole(pole);
    }
    public void modify(Pole pole )throws Exception{
        poleDao.modifyPole(pole);
    }
    public void deleate(Pole pole)throws Exception{
        poleDao.deleatePole(pole);
    }
    public List<Pole> show(User user)throws Exception{
        return poleDao.showPoles(user);
    }
    public List<Pole> search(User user,Pole pole)throws Exception{
        return poleDao.searchPoles(user,pole);
    }
}



