package tms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tms.model.Result;
import tms.service.UserService;
import java.util.Map;



@Controller

@ResponseBody
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    private UserService userService;
        @RequestMapping(value = "/login", method = RequestMethod.GET)
        public Result login(@RequestParam String username , @RequestParam String password){
           Map resultMap=userService.loginCheck( username, password);
            if(resultMap==null){
                return new Result(1, "登录失败！", null);
            }
            else{
                Result  result=new Result(resultMap);
                return result;
            }
        }
}
