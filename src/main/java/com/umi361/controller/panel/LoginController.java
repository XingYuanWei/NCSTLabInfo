package com.umi361.controller.panel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login.jsp")
    public String loginController() {
        return "login";
    }

}
