package com.neu.bloodbank.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

	public String sanitize(String value) {
		System.out.println("Before :-x-x--x-x-x--x " + value);
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]javascript:(.)[\\\"\\\']", "\"\"");
		value = value.replaceAll("<script.*?>", "");
		value = value.replaceAll("</script.*?>", "");
		value = value.replaceAll("</sql.*?>", "");
		value = value.replaceAll("<sql.*?>", "");
		value = value.replaceAll("\\*", "_");
		value = value.replaceAll("=", "_");
		value = value.replaceAll("\\?", "_");
		value = value.replaceAll(",", "_");
		value = value.replaceAll("<script>", "");
		value = value.replaceAll("</script>", "");
		value = value.replaceAll("==", "_");
		value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
		value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
		value = value.replaceAll("'", "& #39;");
		value = value.replaceAll("eval\\((.*)\\)", "");

		System.out.println("After --x-x-x-x-x--x" + value);
		return value;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("Inside pre handle");
		System.out.println("sanitizing interceptor");

		logger.info("Inside pre handle");
		logger.info("Trying sanitizing for each value!");

		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
		request.setAttribute("executionTime", startTime);

		String username = request.getParameter("userName");
		String password = request.getParameter("password");

		String name = request.getParameter("name");
		String fname = request.getParameter("firstName");
		String lname = request.getParameter("lastName");
		
		if (username != null) {
			request.setAttribute("newusername", sanitize(username));
		}
		if (password != null) {
			request.setAttribute("newpass", sanitize(password));
		}
		if (name != null) {
			request.setAttribute("newname", sanitize(name));
		}
		if (fname != null) {
			request.setAttribute("newfname", sanitize(fname));
		}
		if (lname != null) {
			request.setAttribute("newlname", sanitize(lname));
		}
		
		
	
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("Inside post handle");
		logger.info("Inside post handle");

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("Inside after completion");
		logger.info("Inside after completion");
	}
}