package service;

import bean.User;
import dao.UserDao;

/**
 * Created by huojingjing on 16/4/16.
 */
public class UserService {
    private UserDao userDao=new UserDao();
    public User verifyUser(User user) throws Exception {
        User user1=userDao.verifyUser(user);
        return user1;
    }
}
