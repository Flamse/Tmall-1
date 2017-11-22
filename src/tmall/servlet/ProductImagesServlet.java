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

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.dao.ProductImageDAO;
import tmall.util.ImageUtil;
import tmall.util.Page;

public class ProductImagesServlet extends BackBaseServlet {

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		Map<String, String> params = new HashMap<>();
		InputStream is = parseUpload(request, params);
		int pid = Integer.parseInt(params.get("productId"));
		String type = params.get("type").toString();
		ProductImage image = new ProductImage();
		image.setImageType(type);
		Product product = productDAO.getProduct(pid);
		image.setProduct(product);
		productImageDAO.addImage(image);
		
		//ÐÞÕýÄ¿Â¼Ãû×Ö
		String folder="Single";
		if(type.equals("type_detail")) {
			folder="Detail";
		}else if(type.equals("type_single")){
			folder="Single";
		}

		File imgFolder = new File(request.getServletContext().getRealPath("img/product" + folder));
		File file = new File(imgFolder, image.getId() + ".jpg");
		System.out.println(request.getServletContext().getRealPath("img/product" + type));
		try {
			if (is.available() != 0 && is != null) {
				try (FileOutputStream fos = new FileOutputStream(file)) {
					byte[] b = new byte[1024 * 1024];
					while (is.read(b) != -1) {
						fos.write(b);
					}
					fos.flush();
				}
				BufferedImage img = ImageUtil.change2jpg(file);
				ImageIO.write(img, "jpg", file);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect-admin_productImages_list?id=" + pid;
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		int pid = Integer.parseInt(request.getParameter("pid"));
		productImageDAO.deleteImage(id);
		return "redirect-admin_productImages_list?id=" + pid;
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Product product = productDAO.getProduct(id);
		List<ProductImage> singleImages = productImageDAO.list(product, ProductImageDAO.type_single);
		List<ProductImage> detailImages = productImageDAO.list(product, ProductImageDAO.type_detail);
		request.setAttribute("singleImages", singleImages);
		request.setAttribute("detailImages", detailImages);
		request.setAttribute("product", product);
		return "forward-admin/listProductImages.jsp";
	}

}
