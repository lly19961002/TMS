package tms.dao;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tms.model.EmployeeInfoModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SystemManageDao extends MonitorBaseDao {

    public List getInfo(){
        StringBuilder sql =new StringBuilder("select * from employee_info");
        List filterParams = new ArrayList();
        List<Map<String, Object>> employeeInfoList = (List<Map<String, Object>>) listBySQL(Transformers.ALIAS_TO_ENTITY_MAP, sql.toString(), filterParams.toArray());
        return employeeInfoList;
    }

    public String addEmployeeInfo(EmployeeInfoModel employeeInfoModel){
        return create(employeeInfoModel);
    }

}
