//package com.dongao.oa;
//
//import com.dongao.oa.config.SysUsernamePasswordToken;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.web.util.WebUtils;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//
///**
// * 表单验证（包含验证码）过滤类
// *
// * @author ThinkGem
// * @version 2014-5-19
// */
//
//public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {
//
//    public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
//    public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
//    public static final String DEFAULT_MESSAGE_PARAM = "message";
//
//    private String captchaParam = DEFAULT_CAPTCHA_PARAM;
//    private String mobileLoginParam = DEFAULT_MOBILE_PARAM;
//    private String messageParam = DEFAULT_MESSAGE_PARAM;
//    private String loginUrl;
//    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
//        String username = getUsername(request);
//        String password = getPassword(request);
//        if (password == null) {
//            password = "";
//        }
//        boolean rememberMe = isRememberMe(request);
//        String host = getRemoteAddr((HttpServletRequest) request);
//        String captcha = getCaptcha(request);
//        boolean mobile = isMobileLogin(request);
//        return new SysUsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha, mobile);
//    }
//
//    /**
//     * 获得用户远程地址
//     */
//    private static String getRemoteAddr(HttpServletRequest request) {
//        String remoteAddr = request.getHeader("X-Real-IP");
//        if (StringUtils.isNotBlank(remoteAddr)) {
//            remoteAddr = request.getHeader("X-Forwarded-For");
//        } else if (StringUtils.isNotBlank(remoteAddr)) {
//            remoteAddr = request.getHeader("Proxy-Client-IP");
//        } else if (StringUtils.isNotBlank(remoteAddr)) {
//            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
//        }
//        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
//    }
//
//    public String getCaptchaParam() {
//        return captchaParam;
//    }
//
//    protected String getCaptcha(ServletRequest request) {
//        return WebUtils.getCleanParam(request, getCaptchaParam());
//    }
//
//    public String getMobileLoginParam() {
//        return mobileLoginParam;
//    }
//
//    protected boolean isMobileLogin(ServletRequest request) {
//        return WebUtils.isTrue(request, getMobileLoginParam());
//    }
//
//    public String getMessageParam() {
//        return messageParam;
//    }
//
//    /**
//     * 登录成功之后跳转URL
//     */
//    public String getSuccessUrl() {
//        return super.getSuccessUrl();
//    }
//
//    public void setLoginUrl(String loginUrl) {
//        this.loginUrl = loginUrl;
//    }
//
//    @Override
//    protected void issueSuccessRedirect(ServletRequest request,
//                                        ServletResponse response) throws Exception {
////		Principal p = UserUtils.getPrincipal();
////		if (p != null && !p.isMobileLogin()){
//        System.out.println("跳转");
//        WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
////		}else{
////			super.issueSuccessRedirect(request, response);
////		}
//    }
//
//    /**
//     * 登录失败调用事件
//     */
//    @Override
//    protected boolean onLoginFailure(AuthenticationToken token,
//                                     AuthenticationException e, ServletRequest request, ServletResponse response) {
//        String className = e.getClass().getName(), message = "";
//        if (IncorrectCredentialsException.class.getName().equals(className)
//                || UnknownAccountException.class.getName().equals(className)) {
//            message = "用户或密码错误, 请重试.";
//        } else if (LockedAccountException.class.getName().equals(className)) {
//            message = "此用户不允许登录, 请联系管理员或使用别的用户.";
//        } else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")) {
//            message = StringUtils.replace(e.getMessage(), "msg:", "");
//        } else {
//            message = "系统出现点问题，请稍后再试！";
//            e.printStackTrace(); // 输出到控制台
//        }
//        request.setAttribute(getFailureKeyAttribute(), className);
//        request.setAttribute(getMessageParam(), message);
//        return true;
//    }
//
//}