package tms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: lly
 * @Date: 2018-12-11 15:57
 */

@Controller
@RequestMapping("/login")
public class LoginController {
    @RequestMapping("/login")
    public String homeHandler(){
        return "yes";
    }
}
