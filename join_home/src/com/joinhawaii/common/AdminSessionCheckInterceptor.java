package com.joinhawaii.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component("adminSessionCheckInterceptor")
public class AdminSessionCheckInterceptor implements HandlerInterceptor {

	private static Logger logger = Logger.getLogger("AdminSessionCheckInterceptor");
	
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
			Object arg2) throws Exception {
		// TODO Auto-generated method stub
		if (req.getSession().getAttribute("admin_user_id") == null 
				|| "".equals(req.getSession().getAttribute("admin_user_id"))){
			
			String requestUrl = req.getRequestURL().toString();

			//?��?��?�� Url 체크�? ?��?��, login ?��?���??�� ?��?��처리�? ?��줘야 무한 리디?��?��?��?�� 벗어?�� ?�� ?��?��
			if(requestUrl.contains("/admin/login.do")){
				return true;
			}

			res.sendRedirect(req.getContextPath() + "/admin/login.jsp");
			
			return false;
		} 
		
		return true;
	}

}
