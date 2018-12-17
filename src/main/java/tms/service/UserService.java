package tms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tms.dao.UserDao;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Transactional
    public  String loginCheck(String username, String password){

        return userDao.check(username,password);
    }

}
