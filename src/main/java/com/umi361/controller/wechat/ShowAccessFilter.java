package com.umi361.controller.wechat;

import com.umi361._utils.URLBuilder;
import com.umi361._wechatAPI.userInfoAPI.APIUserInfoWechat;
import com.umi361._wechatBasicService.accessToken.CodeResolvedData;
import com.umi361._wechatBasicService.accessToken.OAuthAccessTokenCodeResolver;
import com.umi361.controller.HttpFilter;
import com.umi361.dao.userInfo.UserInfoBasicDAO;
import com.umi361.dao.userInfo.UserInfoWechatDAO;
import com.umi361.domain.userInfo.UserInfoWechat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.icesea.genericDAO.orm.UpdatePolicy;
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
import java.util.Date;


/**
 * FilterChain：/wechat/show.jsp
 * 第一层，访问控制：
 *      在进入下一层链条之前，会向 request 域对象中放入 openid 和 isRegistered 两个属性
 *      重定向到 register 页面时，以 GET 方式传入 openid 参数
 */
@WebFilter(filterName = "wechatShowAccessFilter")
public class ShowAccessFilter extends HttpFilter {
    @Autowired
    private URLBuilder urlBuilder;
    @Autowired
    private UserInfoBasicDAO userInfoBasicDAO;
    @Autowired
    private UserInfoWechatDAO userInfoWechatDAO;
    @Autowired
    private APIUserInfoWechat apiUserInfoWechat;
    @Autowired
    private OAuthAccessTokenCodeResolver codeResolver;
    private Logger logger = LogManager.getLogger(getClass());

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


    /**
     * 微信业务 show 页面访问第一门槛
     *      首先，负责处理 register 页面的注册流程结束后的页面分发，即 url 中带有参数 bypass 的情形
     *          1. bypass = true，说明用户在 register 页面跳过了注册流程，为当前用户执行 userInfoBasic 服务的 registerDirectlyByOpenid
     *          2. bypass = false，说明用户在 register 页面的信息录入已经完成
     *          然后进入下一流程
     *      最主要的是解析微信服务器重定向链接：redirect_uri/?code=CODE&state=STATE，其中 state 参数中需要有 wechat 字样，标志着微信流程下的 show 页面访问
     *
     *
     */
    @Override
    // 无论是重定向还是继续调用，该方法都将 openid 固化进 session
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 检查是否有 bypass 参数，如果有，则跳过微信业务
        // bypass=true 表示是通过 register 页面的跳过进入的，需要进行 registered 处理；bypass=false 说明已经正常完成了注册流程
        // 带有 bypass 参数时，请求是直接由 register 页面重定向的，不经过 OAuth 微信流程
        String bypass = request.getParameter("bypass");
        if (bypass != null) {
            if (bypass.trim().toLowerCase().equals("true")) userInfoBasicDAO.registerDirectlyByOpenid(
                    (String) request.getSession().getAttribute("openid"));
            chain.doFilter(request, response);
            return;
        }

        if (!assertRequestFromWechat(request)) {
            // TODO 当请求不是来自微信时，暂时处理为重定向到二维码页面
            response.sendRedirect(request.getContextPath().concat("/wechat/qr.jsp"));
        } else {
            HttpSession session = request.getSession();
            String code = (String) session.getAttribute("code");
            CodeResolvedData codeResolvedData = (CodeResolvedData) session.getAttribute("codeResolvedData");
            if (codeResolvedData == null || code == null) {
                code = request.getParameter("code");
                codeResolvedData = codeResolver.resolveCode(code);
                if (codeResolvedData == null) {
                    logger.error("首次解析 code 获取 CodeReslovedData 对象时发生异常，终止流程");
                    return;
                } else {
                    session.setAttribute("code", code);
                    session.setAttribute("codeResolvedData", codeResolvedData);
                }
            } else {
                String currCode = request.getParameter("code");
                // 如果发现本次请求的 code 与之前的 code 不一样，则必须更新 CodeResolvedData 对象
                if (!code.equals(currCode)) {
                    code = currCode;
                    codeResolvedData = codeResolver.resolveCode(code);
                    session.setAttribute("code", code);
                    session.setAttribute("codeResolvedData", codeResolvedData);
                }
            }

            // TODO 立即把 openid 固化进 session
            session.setAttribute("openid", codeResolvedData.getOpenid());

            if (assertUserinfoScope(request)) {
                // 处理 scope 为 snsapi_userinfo 的访问
                if (scopeUserinfoHandler(request, response, codeResolvedData)) {
                    // 如果没有发生异常，根据业务逻辑，接着进入 register 注册流程
                    response.sendRedirect("/ncstlabinfo/wechat/register.jsp");
                }
            } else if (assertBaseScope(request)) {
                // 处理 scope 为 snsapi_base 的访问
                // TODO 这是默认回调访问页面
                // 没有获取抓取数据或者从未访问过的用户都会被重定向到 snsapi_userinfo，随后进入注册流程
                if (scopeBaseHandler(request, response, codeResolvedData)) {
                    chain.doFilter(request, response);
                }
            }
        }
    }

    /**
     * 通过 request 请求的 URL state 参数中是否有 TODO 值为 userinfo 的参数项判断本次请求的 scope 是否为 userinfo
     * @return 如果判断结果认定为 userinfo 发起的请求，返回 true；否则返回 false
     */
    private boolean assertUserinfoScope(HttpServletRequest request) {
        String[] stateParams = request.getParameterValues("state");
        for (String currParam : stateParams) {
            if (currParam.trim().toLowerCase().startsWith("userinfo")) return true;
        }
        return false;
    }

    private boolean assertBaseScope(HttpServletRequest request) {
        String[] stateParams = request.getParameterValues("state");
        for (String currParam : stateParams) {
            if (currParam.trim().toLowerCase().startsWith("base")) return true;
        }
        return false;
    }

    /**
     * 判断请求是否发自微信，要求必须有 code 参数和 state 参数，其中 state 必须含有标识 TODO OAuth Scope 的 base 或 userinfo 字样
     * @return 是则返回 true
     */
    private boolean assertRequestFromWechat(HttpServletRequest request) {
        String code = request.getParameter("code");
        String[] stateParams = request.getParameterValues("state");
        if (code == null || stateParams == null) return false;
        for (String currState : stateParams) {
            String state = currState.trim().toLowerCase();
            if (state.equals("base") || state.equals("userinfo")) {
                return true;
            }
        }
        return false;
    }

    /**
     * userinfoHandler 负责获取用户数据，执行首次数据录入，以及为为接下来的 register 页面注册流程做准备
     * 解析 code 获取用户信息
     * @return 返回值为真，表示没有异常，则继续沿着 chain 进行业务逻辑，否则立即终止 filter chain
     */
    private boolean scopeUserinfoHandler(HttpServletRequest request, HttpServletResponse response, CodeResolvedData codeResolvedData) throws IOException, ServletException {
        // 首先记录 openid
        String openid = codeResolvedData.getOpenid();
        // 如果用户首次点击按钮，要先注册 user_info_basic
        userInfoBasicDAO.addUserByOpenidINExists(openid);
        // 通过 codeResolvedData 解析获取 userInfoWechat
        UserInfoWechat userInfoWechat = apiUserInfoWechat.grabUserInfoWechatByCodeResolvedData(codeResolvedData);
        if (userInfoWechat == null) {
            logger.error(new Date() + "\tuserInfoWechat returned from apiUserInfoWechat was null, aborted inside " + getClass().getCanonicalName());
            // 发生未定义的错误
            return false;
        }
        try {
            // 固话用户微信数据到 user_info_wechat
            userInfoWechatDAO.mapInsertSingleObject(userInfoWechat, UpdatePolicy.UPDATE_NOT_NULL_NOR_DEFAULT);
        } catch (SQLException e) {
            logger.error(new Date() + "userInfoWechatDAO failed to maintain userInfoWechat in " + getClass().getCanonicalName());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean scopeBaseHandler(HttpServletRequest request, HttpServletResponse response, CodeResolvedData codeResolvedData) throws IOException, ServletException {
        String openid = codeResolvedData.getOpenid();
        String state = request.getParameter("state");

        // 检查用户是否曾经访问过该站点；只要用户曾经点击过按钮（访问过回调页面），就会在 userinfo 流程中被记录 openid 以及获取用户信息（如果没有异常）
        boolean isKnown = userInfoBasicDAO.checkOpenidValid(openid);
        if (isKnown) {
            boolean hasGrabbed = userInfoWechatDAO.checkExistenceByOpenid(openid);
            if (!hasGrabbed) {
                // 如果还没有抓取过用户数据，跳转到 scope 为 snsapi_userinfo 的请求上
                response.sendRedirect(urlBuilder.buildOAuthURLByURI(request.getRequestURI(), "userinfo", false));
                return false;
            } else {
                // 微信数据已经获取过；判断是否已经登记 registered
                boolean isRegistered = userInfoBasicDAO.checkRegisteredByOpenid(openid);
                // 检查用户是否已经登记过注册页面
                if (isRegistered) {
                    return true;
                } else {
                    // 用户没有登记注册过，重定向到注册页面
                    response.sendRedirect("/ncstlabinfo/wechat/register.jsp");
                    return false;
                }
            }
        } else {
            // 如果用户从未访问过该站点，重定向请求到 snsapi_userinfo
            String redirectURL = urlBuilder.buildOAuthURLByURI(request.getRequestURI(), "userinfo", false);
            response.sendRedirect(redirectURL);
            return false;
        }
    }

}




























