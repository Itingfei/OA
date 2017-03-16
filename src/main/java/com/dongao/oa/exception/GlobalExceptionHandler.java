package com.dongao.oa.exception;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//@ControllerAdvice
public class GlobalExceptionHandler extends SimpleMappingExceptionResolver {
	
	private static final Log log = LogFactory.getLog(GlobalExceptionHandler.class);

    /**
     * 消息.
     */
    @Autowired
    private MessageSource messageSource;

//    @ExceptionHandler(Exception.class)
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		if(isAjax(request)) {
//			try {
//				Result<?> result = new Result<>();
//				result.setCode("-1");
//				result.setMsg("处理失败，请重试！");
//				response.getWriter().write(JSONObject.toJSONString(result));
//			} catch (IOException e) {
//				log.error(getExceptionDetail(e));
//			}
			return new ModelAndView("500");
		}
		request.setAttribute("url",request.getRequestURI());
		ex.printStackTrace();
		return super.resolveException(request, response, handler, ex);
	}
	public static boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("x-requested-with"));
	}
	/**
	 * 获取异常的详细信息
	 * @param e 异常
	 * @return 异常的详细信息
	 */
	public static String getExceptionDetail(Exception e) {
		StringBuffer sb = new StringBuffer();
		if(null != e) {
			StackTraceElement[] ste = e.getStackTrace();
			sb.append(e.getMessage() + "\n");
			for (int i = 0; i < ste.length; i++) {
				sb.append(ste[i].toString() + "\n");
			}
		}
		return sb.toString();
	}
}
