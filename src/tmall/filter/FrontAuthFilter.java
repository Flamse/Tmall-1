package tmall.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import tmall.bean.User;

public class FrontAuthFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;

		String[] notValidatePage = { "home", "regist", "login", "loginAjax", "product", "checkLogin", "category",
				"search" };

		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String uri = StringUtils.remove(requestURI, contextPath);
		if (uri.startsWith("/front_") && !uri.startsWith("/frontServlet")) {
			String method = StringUtils.substringAfter(uri, "_");
			Boolean flag = Arrays.asList(notValidatePage).contains(method);
			if (!flag) {
				User user = (User) request.getSession().getAttribute("user");
				if (user == null) {
					response.sendRedirect("normal/login.jsp");
					return;
				}
				
			}

		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
