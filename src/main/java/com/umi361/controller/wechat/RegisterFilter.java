package com.umi361.controller.wechat;

import com.umi361.controller.HttpFilter;
import com.umi361.dao.userInfo.UserInfoBasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 该层需要对 openid 进行检查
 */
@WebFilter(filterName = "registerFilter")
public class RegisterFilter extends HttpFilter {
    @Autowired
    UserInfoBasicDAO userInfoBasicDAO;

    @Override
    protected void init() {
        /*
         * SpringBeanAutowiringSupport 强制执行自动注入
         */
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
        // 如果 openid 为空或者不是有效的 openid，用户可能直接打开了注册页面，重定向到二维码页面
        if (openid == null || !userInfoBasicDAO.checkOpenidValid(openid)) {
            response.sendRedirect(request.getContextPath().concat("/wechat/qr.jsp"));
            return;
        }
        chain.doFilter(request, response);
    }
}








