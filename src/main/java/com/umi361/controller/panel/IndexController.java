package com.umi361.controller.panel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"/index.jsp", "index.html", "/index", ""})
    public String indexController() {
        return "index";
    }

}
