package com.umi361.controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Http协议下 Filter 的简单实现抽象类
 */
public abstract class HttpFilter implements Filter {
    /**
     * 用于保存 filterConfig
     */
    private FilterConfig filterConfig;

    /**
     * 实现接口 init 方法，保存 filterConfig 配置信息，并调用自定义 init
     * @param filterConfig 传入的 filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        init();
    }

    /**
     * 自定义 init 方法，用于实现自定义的初始化
     */
    abstract protected void init();

    /**
     * @return 返回配置信息
     */
    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    /**
     * 实现接口 doFilter 方法，当 request 和 response 均为 Http 协议下的运行时类型时
     * 调用针对 Http 协议的重载 doFilter 方法
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        if (servletRequest instanceof HttpServletRequest &&
                servletResponse instanceof HttpServletResponse) {
            request = (HttpServletRequest) servletRequest;
            response = (HttpServletResponse) servletResponse;
            doFilter(request, response, filterChain);
        } else {
            System.err.println("HTTP support only:\n\t" + servletRequest + "\n\t" + servletResponse);
        }
    }

    /**
     * 重载的 Http 版本 doFilter，用于覆盖实现业务逻辑
     * @param request
     * @param response
     */
    abstract protected void doFilter(HttpServletRequest request,
                                     HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException;

    /**
     * destroy 方法默认为空
     */
    public void destroy() {}
}