package tmall.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.util.Page;

public class PropertyServlet extends BackBaseServlet {

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category c = categoryDAO.getCategory(cid);
		String pName = request.getParameter("cName");
		Property p = new Property();
		p.setName(pName);
		p.setCategory(c);
		propertyDAO.add(p);

		return "redirect-admin_property_list?cid=" + cid;
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		System.out.println("…æ≥˝ Ù–‘");
		int pid = Integer.parseInt(request.getParameter("pid"));
		int cid = propertyDAO.get(pid).getCategory().getId();
		propertyDAO.delete(pid);
		return "redirect-admin_property_list?cid=" + cid;
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		Property p = propertyDAO.get(pid);
		Category c = p.getCategory();
		request.setAttribute("property", p);
		return "forward-admin/editProperty.jsp";
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		int cid = Integer.parseInt(request.getParameter("cid"));
		String pName = request.getParameter("pName");
		Property p = propertyDAO.get(pid);
		p.setName(pName);
		propertyDAO.update(p);
		
		return "redirect-admin_property_list?cid="+cid;
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		List<Property> ps = propertyDAO.list(cid, page.getStart(), page.getCount());
		int total = propertyDAO.getAmount(cid);
		page.setTotal(total);
		page.setParam("&cid="+cid);
		request.setAttribute("properties", ps);
		request.setAttribute("page", page);
		return "forward-admin/listProperties.jsp";
	}

}
