package tms.dao;

import org.apache.commons.lang.enums.Enum;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tms.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SystemManageDao extends MonitorBaseDao {
    public Map getEmployeeInfo(int currentPage,int rows) {
        StringBuilder sql = new StringBuilder("select count(job_no) from employee_info");
        List filterParams = new ArrayList();
        Object[] sortParams = new Object[1];
        sortParams[0] = new Object[]{"jobNo", false};
        List<EmployeeInfoModel> employeeInfoList = pageList(EmployeeInfoModel.class, currentPage,rows, sortParams);
        long totalSize = countSQLResult(sql.toString(), filterParams.toArray());
        Map map = new HashMap();
        map.put("employeeInfoList", employeeInfoList);
        map.put("totalSize", totalSize);
        return map;
    }

    public List getEmployeeInfoByName(String param){
        StringBuilder sql = new StringBuilder("select cast(job_no as char) as jobNo ,employee_name as employeeName ," +
                "sex,age,ID_card as idCard,DATE_FORMAT(hire_date,'%Y-%m-%d')hireDate,phone,post from employee_info WHERE employee_name='");
        sql.append(param).append("'");
        List filterParams = new ArrayList();
        List<Map<String, Object>> result = (List<Map<String, Object>>) listBySQL(Transformers.ALIAS_TO_ENTITY_MAP, sql.toString(), filterParams.toArray());
                    return result;
    }

    public List getEmployeeInfoByPost(String param){
        StringBuilder sql = new StringBuilder("select cast(job_no as char) as jobNo ,employee_name as employeeName ," +
                "sex,age,ID_card as idCard,DATE_FORMAT(hire_date,'%Y-%m-%d')hireDate,phone,post from employee_info WHERE post='");
        sql.append(param).append("'");
        List filterParams = new ArrayList();
        List<Map<String, Object>> result = (List<Map<String, Object>>) listBySQL(Transformers.ALIAS_TO_ENTITY_MAP, sql.toString(), filterParams.toArray());
        return result;
    }

    public List getEmployeeInfoById(String param){
        StringBuilder sql = new StringBuilder("select cast(job_no as char) as jobNo ,employee_name as employeeName ," +
                "sex,age,ID_card as idCard,DATE_FORMAT(hire_date,'%Y-%m-%d')hireDate,phone,post from employee_info WHERE job_no=' ");
        sql.append(param).append("'");
        List filterParams = new ArrayList();
        List<Map<String, Object>> result = (List<Map<String, Object>>) listBySQL(Transformers.ALIAS_TO_ENTITY_MAP, sql.toString(), filterParams.toArray());
        if(result.size()==0){
            return null;
        }
        else {
            return result;
        }
    }

    public String addEmployeeInfo(EmployeeInfoModel employeeInfoModel) {
        return create(employeeInfoModel);
    }

    public void updateEmployeeInfo(EmployeeInfoModel employeeInfoModel) {
//       updateByID(EmployeeInfoModel.class,employeeInfoModel,employeeInfoModel.getJobNo());
       update(employeeInfoModel);
    }

    public void deleteEmployeeInfo(String id) {
        deleteByID(EmployeeInfoModel.class,id);

    }

}
