package tmall.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import tmall.bean.Category;
import tmall.bean.OrderItem;
import tmall.bean.User;
import tmall.dao.CategoryDAO;
import tmall.dao.OrderItemDAO;

public class FrontServletFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		// 获取用户信息部分
		User user = (User) request.getSession().getAttribute("user");
		// 显示购物车商品数
		int cartTotalNumber = 0;
		if (user != null) {
			OrderItemDAO oiDao = new OrderItemDAO();
			List<OrderItem> its = oiDao.listByUser(user.getId());
			for (OrderItem orderItem : its) {
				cartTotalNumber += orderItem.getNumber();
			}
		}
		request.setAttribute("cartTotalNumber", cartTotalNumber);
		List<Category> cs=(List<Category>) request.getAttribute("cs");
        if(null==cs){
            cs=new CategoryDAO().list();
            request.setAttribute("cs", cs);         
        }
		// 请求解析部分
		String requestURL = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = StringUtils.remove(requestURL, contextPath);

		if (url.startsWith("/front_")) {
			String methodName = StringUtils.substringAfter(url, "_");
			request.setAttribute("method", methodName);
			request.getRequestDispatcher("/frontServlet").forward(request, response);
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
