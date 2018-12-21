package tms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tms.model.EmployeeInfoModel;
import tms.model.Result;
import tms.service.SystemManageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@ResponseBody
@RequestMapping(value = "/api/sys/")
public class SystemManageController {
    @Autowired
    private SystemManageService systemManageService;
    @RequestMapping(value = "/getEmployeeInfo",method = RequestMethod.GET)
    public Result getEmploreeInfo(){
        return new Result(systemManageService.getEmployeeInfo());
    }

    @RequestMapping(value="/addEmployeeInfo",method = RequestMethod.POST)
    public Result addEmployeeInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody EmployeeInfoModel employeeInfoModel){
    return systemManageService.addEmployeeInfo(employeeInfoModel);
    }
}
