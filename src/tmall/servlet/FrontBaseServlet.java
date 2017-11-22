package tmall.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.dao.CategoryDAO;
import tmall.dao.OrderDAO;
import tmall.dao.OrderItemDAO;
import tmall.dao.ProductDAO;
import tmall.dao.ProductImageDAO;
import tmall.dao.PropertyDAO;
import tmall.dao.PropertyValueDAO;
import tmall.dao.ReviewDAO;
import tmall.dao.UserDAO;
import tmall.util.Page;

public class FrontBaseServlet extends HttpServlet {
	protected CategoryDAO categoryDAO = new CategoryDAO();
    protected OrderDAO orderDAO = new OrderDAO();
    protected OrderItemDAO orderItemDAO = new OrderItemDAO();
    protected ProductDAO productDAO = new ProductDAO();
    protected ProductImageDAO productImageDAO = new ProductImageDAO();
    protected PropertyDAO propertyDAO = new PropertyDAO();
    protected PropertyValueDAO propertyValueDAO = new PropertyValueDAO();
    protected ReviewDAO reviewDAO = new ReviewDAO();
    protected UserDAO userDAO = new UserDAO();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            
            int start= 0;
            int count = 10;
            try {
                start = Integer.parseInt(request.getParameter("page.start"));
            } catch (Exception e) {
            }
            try {
                count = Integer.parseInt(request.getParameter("page.count"));
            } catch (Exception e) {
            }
             
            Page page = new Page(start,count);
             
            String method = (String) request.getAttribute("method");
            Method m = this.getClass().getMethod(method, javax.servlet.http.HttpServletRequest.class,
                    javax.servlet.http.HttpServletResponse.class,Page.class);
             
            String redirect = m.invoke(this,request, response,page).toString();
             
            if(redirect.startsWith("redirect-"))
                response.sendRedirect(redirect.substring("redirect-".length()));
            else if(redirect.startsWith("forward-"))
            	request.getRequestDispatcher(redirect.substring("forward-".length())).forward(request, response);
            else
            	  response.getWriter().print(redirect);
             
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    

}
