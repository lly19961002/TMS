package tms.controller;

import com.sun.net.httpserver.Authenticator;
import org.eclipse.equinox.internal.app.MainApplicationLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tms.model.Result;
import tms.service.UserService;

import javax.lang.model.element.NestingKind;
import java.util.Collection;
import java.util.Map;
import java.util.Set;


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
}
