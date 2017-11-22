package tmall.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class BackServletFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		request.setCharacterEncoding("utf-8");
		String URI = request.getRequestURI();
		String contextPath = request.getContextPath();

		String uri = StringUtils.remove(URI, contextPath);

		if (uri.startsWith("/admin_")) {
			String servletName = StringUtils.substringBetween(uri, "_", "_");
			String method = StringUtils.substringAfterLast(uri, "_");
			request.setAttribute("method", method);
			request.setAttribute("servletName", servletName);
			request.getRequestDispatcher("/" + servletName + "Servlet").forward(request, response);
			return;
		} else if (uri.startsWith("/admin")) {
			response.sendRedirect("admin_category_list");
			return;
		} else {
			arg2.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
