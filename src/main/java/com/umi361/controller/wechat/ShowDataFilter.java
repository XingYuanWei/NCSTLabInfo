package com.umi361.controller.wechat;

import com.umi361.controller.HttpFilter;
import com.umi361.dao.lab.LabDAO;
import com.umi361.dao.userInfo.UserInfoBasicDAO;
import com.umi361.dao.userInfo.UserInfoMainDAO;
import com.umi361.domain.lab.Lab;
import com.umi361.domain.userInfo.UserInfoMain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@WebFilter(filterName = "showDataFilter")
public class ShowDataFilter extends HttpFilter {

    @Autowired
    UserInfoMainDAO userInfoMainDAO;
    @Autowired
    UserInfoBasicDAO userInfoBasicDAO;
    @Autowired
    LabDAO labDAO;
    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public void init() {
        try {
            SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                    getFilterConfig().getServletContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String openid = (String) session.getAttribute("openid");
        if (openid == null) {
            logger.error("没有在 session 中找到 openid，终止请求");
            return;
        }
        Long userId = userInfoBasicDAO.getUserIdByOpenid(openid);
        UserInfoMain userInfoMain = null;
        try {
            userInfoMain = userInfoMainDAO.getEntity("SELECT * FROM user_info_main WHERE user_id = ?", userId);
        } catch (SQLException e) {
            logger.error("从 user_info_main 中获取数据发生错误");
            e.printStackTrace();
            return;
        }

        Long preferredLabId = null;
        Map<Pair<Integer, String>, List<Pair<Integer, String>>> domainLabMap = labDAO.getDomainLabPairListMap();

        request.setAttribute("preferredLabId", preferredLabId);
        request.setAttribute("domainLabMap", domainLabMap);

        chain.doFilter(request, response);
    }

}



















