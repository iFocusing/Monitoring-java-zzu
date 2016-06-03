package service;

import bean.Role;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import dao.RoleDao;

import java.util.List;

/**
 * Created by hanpengyu on 2016/4/21.
 */
public class RoleService {
    private RoleDao roleDao=new RoleDao();

    public void AddRole(Role role)throws Exception{
        roleDao.AddRole(role);
    }
    public List<Role> ShowRole(String roleName,String description)throws Exception{
        return roleDao.show(roleName,description);
    }
    public void ModifyRole(Role role)throws Exception{
        roleDao.ModifyRole(role);
    }
    public void  DeleateRole(Role role)throws Exception{
        roleDao.DeleateRole(role);
    }
}
