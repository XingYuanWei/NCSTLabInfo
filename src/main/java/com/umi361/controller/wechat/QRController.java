package com.umi361.controller.wechat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QRController {

    @RequestMapping("/qr.jsp")
    public String qr() {
        return "qr";
    }

}
