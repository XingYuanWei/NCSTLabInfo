package com.umi361.controller.wechat;

import com.umi361.dao.lab.LabDAO;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class ShowTestController {

    @Autowired
    LabDAO labDAO;

    @RequestMapping(value = "show_test.jsp", method = { RequestMethod.POST, RequestMethod.GET })
    public String show(HttpServletRequest request) {
        Map<Pair<Integer, String>, List<Pair<Integer, String>>> domainLabMap = labDAO.getDomainLabPairListMap();
        request.setAttribute("domainLabMap", domainLabMap);
        return "show_test";
    }

}
