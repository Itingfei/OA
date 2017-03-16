package com.dongao.oa.interceptor;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dongao.oa.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
/**
 * 拦截器
 */
public class SecurityInterceptor implements HandlerInterceptor {
	private static final String LOGIN_URL = "/login";
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws IOException {
		User user = (User)request.getSession(true).getAttribute("admin");
		if (user != null) {
//			String requestURI = request.getRequestURI();
//			if (!requestURI.contains("/index")){
//				response.sendRedirect("/index");
//			}
			return true;
		}
		response.sendRedirect(LOGIN_URL);
		return false;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("我是拦截后处理  ");
//		response.sendRedirect("/index");
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
