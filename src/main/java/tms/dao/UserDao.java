package tms.dao;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tms.model.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao extends MonitorBaseDao{

    public List login(String username,String password) {
        try {
            StringBuilder sql = new StringBuilder("select password from user where username='");
            sql.append(username).append("'");
            String passw = getBySQL(sql.toString());
            if (passw.toString().equals(password)) {
                StringBuilder mysql = new StringBuilder("select role from user where username='");
                mysql.append(username).append("'");
                String role = getBySQL(mysql.toString());
                StringBuilder sql1 = new StringBuilder("SELECT d.employee_name,a.job_no,b.url from user a ,permission b,role_permission c,employee_info d WHERE a.role=c.role AND c.per_id=b.id AND a.job_no=d.job_no AND a.role='");
                sql1.append(role).append("'");
                List filterParams = new ArrayList();
                List<Map<String, Object>> accessibleUrlList = (List<Map<String, Object>>) listBySQL(Transformers.ALIAS_TO_ENTITY_MAP, sql1.toString(), filterParams.toArray());
                return accessibleUrlList;
            } else {
                return null;
            }

        }
        catch (Exception e){
            return null;
        }
    }

    public void edit(String username,String password){
        StringBuilder sql=new StringBuilder("update user set password='");
        sql.append(password).append("' where username='").append(username).append("'");
        updateBySQL( sql.toString());

    }
}
