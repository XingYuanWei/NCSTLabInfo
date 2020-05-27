package com.umi361.controller.main;

import com.umi361._globalConstants.GlobalConstants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;

@Controller
@RequestMapping("/shiro")
public class ShiroLoginController {
    @Autowired
    private GlobalConstants globalConstants;

    private String successUrl;

    @PostConstruct
    public void init() {
        successUrl = globalConstants.getPanelLoginSuccessUrl();
    }

    @RequestMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {

        Subject currUser = SecurityUtils.getSubject();
        if (!currUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try {
                currUser.login(token);
            } catch (AuthenticationException e) {
                e.printStackTrace();
            }
        }

        return "redirect:" + successUrl;
    }

    @RequestMapping("/logout")
    public String logout() {
        Subject currUser = SecurityUtils.getSubject();
        currUser.logout();
        return "redirect:" + "/panel/login.jsp";
    }

}
