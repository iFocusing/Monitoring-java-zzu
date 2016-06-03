package service;

import bean.Node;
import dao.NodeDao;

import java.util.List;

/**
 * Created by hanpengyu on 2016/4/27.
 */
public class NodeService {
    NodeDao nodeDao = new NodeDao();

    public void add(String source)throws Exception{
        nodeDao.add(source);
    }
    public  void deleate(Long nid)throws Exception{
        nodeDao.deleate(nid);
    }
    public boolean modify(Node node)throws Exception{
        if (nodeDao.searchPolesNodeNumber(node.getPid())<3){
            nodeDao.modidy(node);
            return true;
        }else {
            return false;
        }

    }
    public List<Node> show()throws Exception{
        return nodeDao.show();
    }
    public List<Node> search(Node node)throws Exception{
        return nodeDao.search(node);
    }
    public List<Node> searchNotUse(String source)throws Exception{
        return nodeDao.searchNotUse(source);
    }
}

