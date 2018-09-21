package com.app.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.app.util.StringUtils;
import com.app.util.UserUtils;

/**
 * 用户过滤器,这种方式启动类需加上@ServletComponentScan . <br>
 * 
 * @author wangtw <br>
 */
@WebFilter(filterName = "userFilter")
public class UserFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 得到用户个人相关的信息（登陆的用户，用户的语言）
        fillUserInfo((HttpServletRequest) request);

        try {
            chain.doFilter(request, response);
        } finally {
            // 由于tomcat线程重用,记得清空
            clearAllUserInfo();
        }
    }

    private void clearAllUserInfo() {
        UserUtils.clearAllUserInfo();
    }

    private void fillUserInfo(HttpServletRequest request) {
        // 用户信息
        String user = getUserFromSession(request);

        if (StringUtils.isNotEmpty(user)) {
            UserUtils.setUser(user);
        }
        // FIXME 测试,后期去掉
        UserUtils.setUser("测试MDC");
    }

    private String getUserFromSession(HttpServletRequest request) {
        // TODO 如果不参加session，model.addAttribute(UserUtil.KEY_USER, username);报错
        HttpSession session = request.getSession(true);

        if (session == null) {
            return null;
        }

        // 从session中获取用户信息放到工具类中
        return (String) session.getAttribute(UserUtils.KEY_USER);
    }

    @Override
    public void destroy() {

    }

}
