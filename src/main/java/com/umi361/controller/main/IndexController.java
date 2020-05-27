package com.umi361.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("")
public class IndexController {
    @RequestMapping({"/index.html", "/index", "/", ""})
	public String directIndex() {
        return "index";
	}
}
