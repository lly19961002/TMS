package tms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tms.model.Result;
import tms.model.UserModel;
import tms.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;



@Controller

@ResponseBody
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    private UserService userService;
        @RequestMapping(value = "/login", method = RequestMethod.GET)
        public Result login(@RequestParam String username , @RequestParam String password){
           Map resultMap=userService.login( username, password);
            if(resultMap==null){
                return new Result(1, "登录失败！", null);
            }
            else{
                Result  result=new Result(resultMap);
                return result;
            }
        }

        @RequestMapping(value="/edit",method = RequestMethod.POST)
        public Result edit(@RequestBody Map<String,Object> params){
            String username=params.get("username").toString();
            String password=params.get("password").toString();
            return userService.edit(username,password);
            }
}
