package tmall.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.util.ImageUtil;
import tmall.util.Page;

public class CategoryServlet extends BackBaseServlet {

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		Map<String, String> params = new HashMap<>();
		InputStream is = parseUpload(request, params);
		String name = params.get("cName");

		// ���ݿ�������࣬���õ�id����ֵ
		Category bean = new Category();
		bean.setName(name);
		categoryDAO.add(bean);
		int id = bean.getId();

		File imgFloder = new File(request.getServletContext().getRealPath("img/category"));
		File file = new File(imgFloder, id + ".jpg");
		try {
			if (is != null && 0 != is.available()) {
				try (FileOutputStream fos = new FileOutputStream(file)) {
					byte[] b = new byte[1024 * 1024];
					int length = 0;
					while (-1 != (length = is.read(b))) {// ����һ�ζ�ȡb�ֽ���������ݣ��ڵ���һ�δ��ϴε�λ�ÿ�ʼ�ֶ�ȡ
						fos.write(b);
					}
					fos.flush();// �������������д��file�����С���ʱ����һ��ӳ�䣬�����������ļ�

					BufferedImage img = ImageUtil.change2jpg(file);
					ImageIO.write(img, "jpg", file);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect-admin_category_list";
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		categoryDAO.deleteCategory(Integer.parseInt(request.getParameter("cid")));
		return "redirect-admin_category_list";
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		Map<String, String> params = new HashMap<>();
		InputStream is = parseUpload(request, params);
		String name = params.get("cName");
		int id = Integer.parseInt(params.get("cid"));

		// ���ݿ�������࣬���õ�id����ֵ
		Category bean = new Category();
		bean.setId(id);
		bean.setName(name);
		categoryDAO.updateCategory(bean);
		try {
			if (is != null && 0 != is.available()) {
				File imgFloder = new File(request.getServletContext().getRealPath("img/category"));
				File file = new File(imgFloder, id + ".jpg");
				if (file.exists()) {
					file.delete();
				}
				try (FileOutputStream fos = new FileOutputStream(file)) {
					byte[] b = new byte[1024 * 1024];
					int length = 0;
					while (-1 != (length = is.read(b))) {// ����һ�ζ�ȡb�ֽ���������ݣ��ڵ���һ�δ��ϴε�λ�ÿ�ʼ�ֶ�ȡ
						fos.write(b);
					}
					fos.flush();// �������������д��file�����С���ʱ����һ��ӳ�䣬�����������ļ�

					BufferedImage img = ImageUtil.change2jpg(file);
					ImageIO.write(img, "jpg", file);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect-admin_category_list";
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		List<Category> cs = categoryDAO.list(page.getStart(), page.getCount());
		request.setAttribute("categories", cs);
		int total = categoryDAO.getAmount();
		page.setTotal(total);
		request.setAttribute("page", page);
		return "forward-admin/listCategory.jsp";
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category c = categoryDAO.getCategory(cid);
		request.setAttribute("category", c);
		return "forward-admin/editCategory.jsp";
	}

}
