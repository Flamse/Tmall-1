package tmall.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.PropertyValue;
import tmall.util.Page;

public class ProductServlet extends BackBaseServlet {

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		String name = request.getParameter("pName");
		String pKeyWord = request.getParameter("pKeyWord");
		Float pOriginalPrice = Float.parseFloat(request.getParameter("pOriginalPrice"));
		Float pPromotePrice = Float.parseFloat((request.getParameter("pPromotePrice")));
		int stock = Integer.parseInt(request.getParameter("pStock"));
		Category c = categoryDAO.getCategory(cid);
		Product p = new Product();
		p.setName(name);
		p.setSubTitle(pKeyWord);
		p.setOriginalPrice(pOriginalPrice);
		p.setPromotePrice(pPromotePrice);
		p.setStock(stock);
		p.setCategory(c);
		productDAO.addProduct(p);

		return "redirect-admin_product_list?cid=" + cid;
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("pid"));
		int cid = Integer.parseInt(request.getParameter("cid"));
		productDAO.deleteProduct(id);
		return "redirect-admin_product_list?cid=" + cid;
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("pid"));
		Product p = productDAO.getProduct(id);
		request.setAttribute("product", p);
		return "forward-admin/editProduct.jsp";
	}

	public String editPropertyValue(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("pid"));
		Product p = productDAO.getProduct(id);
		propertyValueDAO.getValue(p);
		System.out.println("属性初始化完毕");
		List<PropertyValue> pvs = propertyValueDAO.list(id);
		System.out.println("获取属性值完毕");
		request.setAttribute("product", p);
		request.setAttribute("propertyValues", pvs);
		return "forward-admin/editPropertyValues.jsp";
	}
	public String updatePropertyValue(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		String value = request.getParameter("value");
		PropertyValue pvalue = propertyValueDAO.getValue(id);
		pvalue.setValue(value);
		propertyValueDAO.updateValue(pvalue);

		return value;
	}
	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("pid"));
		int cid = Integer.parseInt(request.getParameter("cid"));
		String name = request.getParameter("pName");
		String pKeyWord = request.getParameter("pKeyWord");
		Float pOriginalPrice = Float.parseFloat(request.getParameter("pOriginalPrice"));
		Float pPromotePrice = Float.parseFloat((request.getParameter("pPromotePrice")));
		int stock = Integer.parseInt(request.getParameter("pStock"));
		Product p = productDAO.getProduct(id);
		p.setName(name);
		p.setSubTitle(pKeyWord);
		p.setOriginalPrice(pOriginalPrice);
		p.setPromotePrice(pPromotePrice);
		p.setStock(stock);
		productDAO.updateProduct(p);

		return "redirect-admin_product_list?cid=" + cid;
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		List<Product> ps = productDAO.listByCategory(cid, page.getStart(), page.getCount());
		int total = productDAO.getAmount(cid);
		page.setTotal(total);
		page.setParam("&cid="+cid);
		request.setAttribute("products", ps);
		request.setAttribute("page", page);
		return "forward-admin/listProducts.jsp";
	}

}
