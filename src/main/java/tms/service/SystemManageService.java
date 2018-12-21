package tms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tms.dao.SystemManageDao;
import tms.model.EmployeeInfoModel;
import tms.model.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemManageService {
    @Autowired
    private SystemManageDao systemManageDao;
    public Map getEmployeeInfo(){
        Map map = new HashMap();
        List<Map<String ,Object>> list = systemManageDao.getInfo();
        map.put("employeeInfo",list);
        return map;
    }

    public Result addEmployeeInfo(EmployeeInfoModel employeeInfoModel){
        return new Result(systemManageDao.addEmployeeInfo( employeeInfoModel));
    }
}
