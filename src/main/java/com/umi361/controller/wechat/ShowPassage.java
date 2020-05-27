package com.umi361.controller.wechat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ShowPassage {

    @RequestMapping(value = "showPassage", method = RequestMethod.POST)
    public String returnPassageToShow(HttpServletResponse response, @RequestParam String id) {
        Long labId = null;
        try {
            labId = Long.parseLong(id);
            if (labId < 1 || labId > 22) throw new NumberFormatException("实验室 ID 超出范围，id : " + id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "passages/" +
                (labId == null ? "overview" : labId);
    }

}
