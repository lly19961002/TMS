package tms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tms.common.PaginationView;
import tms.dao.SystemManageDao;
import tms.model.EmployeeInfoModel;
import tms.model.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SystemManageService {
    @Autowired
    private SystemManageDao systemManageDao;
    public PaginationView getEmployeeInfo(int currentPage,int rows){
        Map map = systemManageDao.getEmployeeInfo(currentPage,rows);
        PaginationView<EmployeeInfoModel> view = new PaginationView<>();
        long totalSize = (long) map.get("totalSize");
        long pages = (totalSize + rows - 1) / rows;
        List<EmployeeInfoModel> list = (List<EmployeeInfoModel>) map.get("employeeInfoList");
        view.setDataList(list);
        view.setPages(pages);
        view.setCurrentPage(1);
        view.setCurrentPageSize(list.size());
        view.setTotalSize(totalSize);
        return view;
    }
    public Map getEmployeeInfoByName(String param){
        Map map=new HashMap();
        List<Map<String ,Object>> result = systemManageDao.getEmployeeInfoByName(param);
        if(result.size()==0){
            map=null;
            return map;
        }
        else {
            map.put("result",result);
            return map;
        }
    }

    public Map getEmployeeInfoByPost(String param){
        Map map=new HashMap();
        List<Map<String ,Object>> result = systemManageDao.getEmployeeInfoByPost(param);
        if(result.size()==0){
            map=null;
            return map;
        }
        else {
            map.put("result",result);
            return map;
        }
    }

    public Map getEmployeeInfoById(String param){
        Map map=new HashMap();
        List<Map<String ,Object>> result = systemManageDao.getEmployeeInfoById(param);
        if(result==null){
            map=null;
            return map;
        }
        else {
            map.put("result",result);
            return map;
        }
    }

    public Result addEmployeeInfo(EmployeeInfoModel employeeInfoModel){
        return new Result(systemManageDao.addEmployeeInfo( employeeInfoModel));
    }

    public Result updateEmployeeInfo(EmployeeInfoModel employeeInfoModel){
        systemManageDao.updateEmployeeInfo(employeeInfoModel);
        return Result.SUCCESS;
    }

    public Result deleteEmployeeInfo(EmployeeInfoModel employeeInfoModel){
        systemManageDao.deleteEmployeeInfo(employeeInfoModel.getJobNo());
        return Result.SUCCESS;
    }
}
