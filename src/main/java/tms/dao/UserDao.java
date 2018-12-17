package tms.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends MonitorBaseDao{

    public String check(String username,String password){
        StringBuilder sql=new StringBuilder("select password from user where username='");
        sql.append(username).append("'");
        String passw=getBySQL(sql.toString());
        System.out.println(passw);
        if(passw.toString().equals(password)){
            return "success";
        }
        else{
            return "fail";
        }

    }


}
