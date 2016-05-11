package service;

import dao.BaiDuDao;

/**
 * Created by huojingjing on 16/5/10.
 */
public class BaiDuService {
    BaiDuDao baiDuDao=new BaiDuDao();

    public boolean insertProvince(String name, String adcode) throws Exception {
        baiDuDao.insertProvince(name, adcode);
        return true;
    }

    public boolean insertCity(String name, String adcode,String provinceAdcode) throws Exception {
        baiDuDao.insertCity(name, adcode,provinceAdcode);
        return true;
    }
}
