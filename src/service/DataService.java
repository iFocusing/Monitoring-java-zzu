package service;

import bean.*;
import dao.*;
import util.TransferTime;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/10.
 */
public class DataService {
    DataDao dataDao=new DataDao();
    PoleDao poleDao=new PoleDao();
    LineDao lineDao=new LineDao();
    private OrganizationDao organizationDao = new OrganizationDao();
    private AdminRegionDao adminRegionDao=new AdminRegionDao();
    //查询条件:pid不为空,时间不为空
    public List<DataDisplay> searchDataByPid(long pid,Timestamp startTime,Timestamp endTime) throws Exception {
        return dataDao.searchDataByPid(pid,startTime,endTime);
    }
    //查询条件:pid为空,组织\地区\线路\时间都不为空
    public List<DataDisplay> searchData(Long oid, Long aid, Long lid, Timestamp startTime, Timestamp endTime) throws Exception{
        System.out.println("查询条件:pid为空,组织\\地区\\线路\\时间都不为空");
        return dataDao.searchData(organizationDao.findOne(oid),adminRegionDao.searchRegion(aid),lid,startTime,endTime);
    }
    //查询条件:pid\线路为空,组织\地区\时间不为空
    public List<DataDisplay> searchData(Long oid, Long aid, Timestamp startTime, Timestamp endTime)throws Exception {
        System.out.println("查询条件:pid\\线路为空,组织\\地区\\时间不为空");
        return dataDao.searchData(organizationDao.findOne(oid),adminRegionDao.searchRegion(aid),startTime,endTime);
    }
    //查询条件:pid\线路\地区为空,组织\时间不为空
    public List<DataDisplay> searchDataByOid(Long oid, Timestamp startTime, Timestamp endTime) throws Exception {
        System.out.println("查询条件:pid\\线路\\地区为空,组织\\时间不为空");
        return dataDao.searchDataByOid(organizationDao.findOne(oid),startTime,endTime);
    }
    /*查询条件:pid\线路\地区\组织\时间都为空--
    * 查询过去一天内的所有历史数据*/
    public List<DataDisplay> searchAllData(Long oid) throws Exception {
        System.out.println("查询条件:pid\\线路\\地区\\组织\\时间都为空--查询该组织的所有数据");
        return dataDao.searchAllData(organizationDao.findOne(oid));
    }

    /*
     * 查询数据用于直线图展示;
     * 借鉴黄诗鹤代码;
    * */
    public List<List<DataDisplay>> searchChartData(String[] pids, String startDate, String endDate) throws Exception {
        List<List<DataDisplay>> DataListList = new ArrayList<>();
        TransferTime transferTime=new TransferTime();
        Timestamp startTime=transferTime.tansfer(startDate);
        Timestamp endTime=transferTime.tansfer(endDate);
        System.out.println("折线图展示中获取的参数2(Service):"+startTime+endTime+pids[0]);
        Long pid=Long.valueOf(pids[0]);
        System.out.println(pids[0]);
        List<DataDisplay> dataList =dataDao.searchDataByPid(pid, startTime, endTime);
        System.out.println(dataList.get(0).getDid());
        DataListList.add(dataList);
        return DataListList;
    }


    public List<List<DataDisplay>> searchChartData1(Long oid, Long aid, Long lid, String startDate, String endDate) throws Exception {
        System.out.println("图--查询条件:pid为空,组织\\地区\\线路\\时间都不为空");
        List<List<DataDisplay>> DataListList = new ArrayList<>();
        TransferTime transferTime=new TransferTime();
        Timestamp startTime=transferTime.tansfer(startDate);
        Timestamp endTime=transferTime.tansfer(endDate);
        System.out.println("多个线杆折线图展示中获取的参数:"+startTime+endTime+lid);
        //用lid查线杆;
        List<Long> pids=new ArrayList<Long>();
        List<Pole> poleList=poleDao.searchPoleByLine(adminRegionDao.searchRegion(aid),organizationDao.findOne(oid),lineDao.searchLine(lid));
        for(int i=0;i<poleList.size();i++){
            pids.add(poleList.get(i).getPid());
        }
        for (Long pid : pids) {
            System.out.println(pid);
            List<DataDisplay> dataList =dataDao.searchDataByPid(pid, startTime, endTime);
            DataListList.add(dataList);
        }
        System.out.println("DataListList:--"+DataListList);
        return DataListList;
    }


    public List<List<DataMapDisplay>> searchDataMapByOid(Long oid) throws Exception {
        List<Line> lineList=lineDao.searchLine(organizationDao.findOne(oid)) ;
        List<List<DataMapDisplay>> poleListList = new ArrayList<>();
        List<Long> lids=new ArrayList<Long>();
        for(int i=0;i<lineList.size();i++){
            lids.add(lineList.get(i).getLid());
        }
        for (Long lid : lids) {
            System.out.println(lid);
            List<DataMapDisplay> dataList =dataDao.searchDataMapByOid(lid,organizationDao.findOne(oid));
            poleListList.add(dataList);
        }
        return poleListList;
    }

    public List<DataDisplay> searchData(Double longitude, Double latitude) throws Exception {
        return dataDao.searchData(longitude,latitude);
    }

    public List<DataDisplay> searchData(Long pid) throws Exception {
        return dataDao.searchData(pid);
    }
}
