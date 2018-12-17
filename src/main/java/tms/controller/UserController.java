package tms.controller;

import com.sun.net.httpserver.Authenticator;
import org.eclipse.equinox.internal.app.MainApplicationLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tms.service.UserService;

import javax.lang.model.element.NestingKind;
import java.util.Collection;
import java.util.Map;
import java.util.Set;


@Controller

//private UserService userservice;
@ResponseBody
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    private UserService userService;
        @RequestMapping(value = "/login", method = RequestMethod.GET)
        public String login( @RequestParam String username ,@RequestParam String password){
            return userService.loginCheck( username, password);
        }
}
