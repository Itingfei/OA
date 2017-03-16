package com.dongao.oa.controller;


import com.dongao.oa.pojo.PositionAndOrganization;
import com.dongao.oa.pojo.User;
import com.dongao.oa.service.UserService;
import com.dongao.oa.utils.PageInfo;
import com.dongao.oa.utils.resultType.AppResultModel;
import com.dongao.oa.utils.resultType.ResultMessage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 登录控制类
 *
 * @author fengjifei
 */
@Controller
public class LoginController {

@Autowired
public UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm(Model model) {
        System.out.println("登录进入");
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model, HttpServletRequest request) {
        System.out.println("LoginController.login()");
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exception = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("exception=" + exception);
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                System.out.println("UnknownAccountException -- > 账号不存在：");
                msg = "账号不存在";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                System.out.println("IncorrectCredentialsException -- > 密码不正确：");
                msg = "密码不正确";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                System.out.println("kaptchaValidateFailed -- > 验证码错误");
                msg = "验证码错误";
            } else if (LockedAccountException.class.getName().equals(exception)) {
                System.out.println("LockedAccountException -- > 账号已被锁定");
                msg = "账号已被锁定";
            }
            else {
                msg = "其他错误";
                System.out.println("else -- >" + exception);
            }
        }
        model.addAttribute("message",msg);
        System.out.println(msg);
        // 此方法不处理登录成功,由shiro进行处理.
        return "/login";
//        return "redirect:/index";
    }
    @RequestMapping(value = "/logout")
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
        }
    }
}
