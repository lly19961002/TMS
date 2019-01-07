package tms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tms.model.EmployeeInfoModel;
import tms.model.Result;
import tms.service.SystemManageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping(value = "/api/sys/")
public class SystemManageController {
    @Autowired
    private SystemManageService systemManageService;

    @RequestMapping(value = "/getEmployeeInfo",method = RequestMethod.GET)
    public Result getEmployeeInfo(@RequestParam int currentPage,@RequestParam int rows){
        return new Result(systemManageService.getEmployeeInfo(currentPage,rows));
    }

    @RequestMapping(value = "/getEmployeeInfoByPost",method = RequestMethod.GET)
    public Result getEmployeeInfoByyPost(@RequestParam String param ){
        Map resultMap=systemManageService.getEmployeeInfoByPost(param);
        if(resultMap==null){
            return new Result(1, "没有所查询职位的员工！", null);
        }
        else{
            Result  result=new Result(resultMap);
            return result;
        }
    }

    @RequestMapping(value = "/getEmployeeInfoByName",method = RequestMethod.GET)
    public Result getEmployeeInfoByName(@RequestParam String param ){
        Map resultMap=systemManageService.getEmployeeInfoByName(param);
        if(resultMap==null){
            return new Result(1, "没有所查询名字的员工！", null);
        }
        else{
            Result  result=new Result(resultMap);
            return result;
        }
    }

    @RequestMapping(value = "/getEmployeeInfoById",method = RequestMethod.GET)
    public Result getEmployeeInfoById(@RequestParam String param){
        Map resultMap= systemManageService.getEmployeeInfoById(param);
        if(resultMap==null){
            return new Result(1, "没有所查询工号的员工！", null);
        }
        else{
            Result  result=new Result(resultMap);
            return result;
        }
    }

    @RequestMapping(value="/addEmployeeInfo",method = RequestMethod.POST)
    public Result addEmployeeInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody EmployeeInfoModel employeeInfoModel){
        return systemManageService.addEmployeeInfo(employeeInfoModel);
    }

    @RequestMapping(value="/updateEmployeeInfo",method = RequestMethod.POST)
    public Result updateEmployeeInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody EmployeeInfoModel employeeInfoModel){
        return systemManageService.updateEmployeeInfo(employeeInfoModel);
    }

    @RequestMapping(value="/deleteEmployeeInfo",method = RequestMethod.POST)
    public Result deleteEmployeeInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody EmployeeInfoModel employeeInfoModel){
        return systemManageService.deleteEmployeeInfo(employeeInfoModel);
    }
}
