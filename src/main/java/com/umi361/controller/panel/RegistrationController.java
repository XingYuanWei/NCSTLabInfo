package com.umi361.controller.panel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegistrationController {

    @RequestMapping("/registration.jsp")
    public String registrationController() {
        return "registration";
    }

}
