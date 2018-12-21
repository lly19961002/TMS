package tms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tms.dao.UserDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Transactional
    public  Map login(String username, String password){
        Map map = new HashMap();
        List<Map<String ,Object>> list = userDao.login(username,password);
        if(list==null){
            map=null;
            return map;
        }
       else {
            map.put("accessibleUrl",list);
            return map;
        }

    }

}
