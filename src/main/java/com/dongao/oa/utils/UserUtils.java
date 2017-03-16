package com.dongao.oa.utils;

import com.dongao.oa.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by fengjifei on 2016/8/24.
 */
@Component
public class UserUtils {
    private static HttpServletRequest request;

    public HttpServletRequest getRequest() {
        return request;
    }
    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * 获取当前用户
     */
    public static User getCurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        return user;
    }

    public static Map<String, Object> getUserInfo(){
        Session session = SecurityUtils.getSubject().getSession();
        return (Map<String, Object>)session.getAttribute("userInfo");
    }
    /**
     * 获取APP当前登录用户
     */
    public static Map<String, Object> getAPPCurrentUser() {
        Map<String, Object> user = (Map<String, Object>) request.getSession(true).getAttribute("user");
        return user;
    }
}
