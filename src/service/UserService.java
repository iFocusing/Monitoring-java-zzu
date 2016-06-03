package service;

import bean.Organization;
import bean.User;
import dao.UserDao;

import java.util.List;

/**
 * Created by huojingjing on 16/4/16.
 */
public class UserService {
    private UserDao userDao=new UserDao();
    public User verifyUser(User user) throws Exception {
        User user1=userDao.verifyUser(user);
        return user1;
    }
    /**
     * Created by HPY
     */
    public List<User> search(Organization organization) throws Exception {
        return userDao.findOne(organization);
    }
    //还没完善，返回值没有设置
    public boolean add(User loginuser,User user,String[] roles) throws  Exception {
//        boolean a;
//        if (!(user == null)) {
//            a=true;
        userDao.addNewUser(user,roles);
//        }else{
//            a=false;
//        }
        return true;
    }
    public List<User> showThisUserList(User loginUser)throws Exception{
        return userDao.showUserListUnderLoginUser(loginUser);
    }
    public List<User> searchThisUserList(User loginUser,User user)throws Exception{
        return userDao.searchUserListUnderLoginUser(loginUser, user);
    }
    public boolean modify(User user,String[] rales)throws Exception{
        userDao.modifyUser(user,rales);
        return true;
    }
    public void modify(User user,String newPassword)throws Exception{
        userDao.modifyPassword(user,newPassword);
    }
    public void deleate(User user)throws Exception{
        userDao.deleateUser(user);
    }
}
