package tmall.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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

public abstract class BackBaseServlet extends HttpServlet {
	public abstract String add(HttpServletRequest request, HttpServletResponse response, Page page);

	public abstract String delete(HttpServletRequest request, HttpServletResponse response, Page page);

	public abstract String edit(HttpServletRequest request, HttpServletResponse response, Page page);

	public abstract String update(HttpServletRequest request, HttpServletResponse response, Page page);

	public abstract String list(HttpServletRequest request, HttpServletResponse response, Page page);

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
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * ��ȡ��ҳ��Ϣ���������ж�ȡ����
		 */
		int start = 0;
		int count = 10;
		try {// ʹ��try-catch��Χ��Ϊ�˱���parseInt�����³����жϡ�
			start = Integer.parseInt(request.getParameter("page.start"));
		} catch (Exception e) {
		}
		try {// ʹ��try-catch��Χ��Ϊ�˱���parseInt�����³����жϡ�
			count = Integer.parseInt(request.getParameter("page.count"));
		} catch (Exception e) {
		}
		Page page = new Page(start, count);
		/*
		 * ʹ�÷���ʶ����ö�Ӧ����
		 */
		System.out.println("����Servlet------��ʼʹ�÷������");
		try {
			String methodName = (String) request.getAttribute("method");
			System.out.println("��������--------" + methodName);
			Method method = this.getClass().getMethod(methodName, javax.servlet.http.HttpServletRequest.class,
					javax.servlet.http.HttpServletResponse.class, Page.class);
			String result = method.invoke(this, request, response, page).toString();
			if (result.startsWith("forward-")) {
				String url = result.substring("forward-".length());
				request.getRequestDispatcher(url).forward(request, response);
			} else if (result.startsWith("redirect-")) {
				String url = result.substring("redirect-".length());
				response.sendRedirect(url);
			} else {
				response.getWriter().println(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ʵ��form multipart/form-data�ϴ��ļ�
	public InputStream parseUpload(HttpServletRequest request, Map<String, String> params) {
		InputStream is = null;

		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
		try {
			List<FileItem> items = upload.parseRequest(request);// ���������е�������
			Iterator it = items.iterator();
			while (it.hasNext()) {
				FileItem item = (FileItem) it.next();
				if (item.isFormField()) {// �ж��Ƿ�Ϊ�򵥵ı�����
					// ��ȡ������������params�д���
					String name = item.getFieldName();
					String value = item.getString();
					value = new String(value.getBytes("ISO-8859-1"), "utf-8");// �����������
					params.put(name, value);
				} else {
					is = item.getInputStream();// ������ļ������ȡ����������
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return is;
	}

}
