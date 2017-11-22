package tmall.servlet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

import tmall.bean.Category;
import tmall.bean.Order;
import tmall.bean.OrderItem;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.bean.PropertyValue;
import tmall.bean.Review;
import tmall.bean.User;
import tmall.comparator.AllComparator;
import tmall.comparator.DateComparator;
import tmall.comparator.PriceComparator;
import tmall.comparator.ReviewCountComparator;
import tmall.comparator.SaleCountComparator;
import tmall.dao.OrderDAO;
import tmall.util.DateUtil;
import tmall.util.Page;

public class FrontServlet extends FrontBaseServlet {

	public String home(HttpServletRequest request, HttpServletResponse response, Page page) {
		List<Category> cs = categoryDAO.list();
		productDAO.fill(cs);
		productDAO.fillByRow(cs);
		request.setAttribute("categories", cs);
		return "forward-/home.jsp";
	}

	public String regist(HttpServletRequest request, HttpServletResponse response, Page page) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		name = HtmlUtils.htmlEscape(name);
		System.out.println(name);
		boolean exist = userDAO.isExist(name);

		if (exist) {
			request.setAttribute("msg", "用户名已经被使用,不能使用");
			return "forward-regist.jsp";
		}

		User user = new User();
		user.setName(name);
		user.setPassword(password);
		System.out.println(user.getName());
		System.out.println(user.getPassword());
		userDAO.addUser(user);

		return "redirect-normal/registSuccessed.jsp";
	}

	public String login(HttpServletRequest request, HttpServletResponse response, Page page) {
		String name = request.getParameter("name");
		name = HtmlUtils.htmlEscape(name);
		String password = request.getParameter("password");
		User user = userDAO.getUser(name, password);
		if (user != null) {
			request.getSession().setAttribute("user", user);
			return "redirect-front_home";
		}
		request.setAttribute("msg", "用户不存在或密码错误");
		return "forward-normal/login.jsp";

	}

	public String loginAjax(HttpServletRequest request, HttpServletResponse response, Page page) {
		String name = request.getParameter("name");
		name = HtmlUtils.htmlEscape(name);
		String password = request.getParameter("password");
		User user = userDAO.getUser(name, password);
		if (user != null) {
			request.getSession().setAttribute("user", user);
			return "success";
		}
		return "fail";

	}

	public String logout(HttpServletRequest request, HttpServletResponse response, Page page) {
		request.getSession().removeAttribute("user");
		return "redirect-front_home";
	}

	/*
	 * 产品页面方法 需求：product、销量与评价、产品图片
	 * 
	 */
	public String product(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product product = productDAO.getProduct(pid);
		List<ProductImage> productDetailImages = productImageDAO.list(product, "type_detail");
		List<ProductImage> productSingleImages = productImageDAO.list(product, "type_single");
		int reviewCount = reviewDAO.getAmount(product.getId());
		int saleCount = orderItemDAO.getSaleCount(product.getId());
		product.setProductDetailImages(productDetailImages);
		product.setProductSingleImages(productSingleImages);
		product.setReviewCount(reviewCount);
		product.setSaleCount(saleCount);
		List<Review> reviews = reviewDAO.list(pid);
		request.setAttribute("reviews", reviews);
		List<PropertyValue> propertyValues = propertyValueDAO.list(product.getId());
		request.setAttribute("propertyValues", propertyValues);
		request.setAttribute("product", product);
		return "forward-normal/productInfo.jsp";
	}

	public String checkLogin(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			return "success";
		}
		return "false";
	}

	public String addToCart(HttpServletRequest request, HttpServletResponse response, Page page) {
		int num = Integer.parseInt(request.getParameter("number"));
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product product = productDAO.getProduct(pid);
		User user = (User) request.getSession().getAttribute("user");
		// 查一查该用户是否已经将该物品添加进购物车了
		OrderItem oi = orderItemDAO.getCartProduct(pid, user.getId());
		if (oi != null) {
			num += oi.getNumber();
			oi.setNumber(num);
			orderItemDAO.updateOrderItem(oi);// 如果有了，只修改数量
		} else {
			oi = new OrderItem();// 如果没有，添加新的订单项
			oi.setNumber(num);
			oi.setProduct(product);
			oi.setUser(user);
			orderItemDAO.addOrderItem(oi);
		}
		return "success";
	}

	// 立即购买
	public String buyNow(HttpServletRequest request, HttpServletResponse response, Page page) {
		int num = Integer.parseInt(request.getParameter("num"));
		int pid = Integer.parseInt(request.getParameter("pid"));
		User user = (User) request.getSession().getAttribute("user");
		Product product = productDAO.getProduct(pid);
		// 查一查该用户是否已经将该物品添加进购物车了
		OrderItem oi = orderItemDAO.getCartProduct(pid, user.getId());
		if (oi != null) {
			num += oi.getNumber();
			oi.setNumber(num);
			orderItemDAO.updateOrderItem(oi);// 如果有了，只修改数量
		} else {
			oi = new OrderItem();// 如果没有，添加新的订单项
			oi.setNumber(num);
			oi.setProduct(product);
			oi.setUser(user);
			orderItemDAO.addOrderItem(oi);
		}

		return "redirect-front_buy?oiids=" + oi.getId();
	}

	// 分类产品
	public String category(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category c = categoryDAO.getCategory(cid);

		productDAO.fill(c);
		productDAO.setSaleAndReviewNumber(c.getProducts());
		// 排序功能
		String sort = request.getParameter("sort");
		if (sort != null) {
			switch (sort) {
			case "all":
				Collections.sort(c.getProducts(), new AllComparator());
				break;
			case "review":
				Collections.sort(c.getProducts(), new ReviewCountComparator());
				break;
			case "date":
				Collections.sort(c.getProducts(), new DateComparator());
				break;
			case "saleCount":
				Collections.sort(c.getProducts(), new SaleCountComparator());
				break;
			case "price":
				Collections.sort(c.getProducts(), new PriceComparator());
				break;
			}

		}

		request.setAttribute("category", c);
		return "forward-normal/productsOfCategory.jsp";
	}

	// 搜索
	public String search(HttpServletRequest request, HttpServletResponse response, Page page) {
		String keyWord = request.getParameter("keyWord");
		List<Product> products = productDAO.search(keyWord, 0, 20);
		request.setAttribute("products", products);
		return "forward-normal/searchResult.jsp";
	}

	// 购物车
	public String cart(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user = (User) request.getSession().getAttribute("user");
		List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
		request.setAttribute("ois", ois);
		return "forward-normal/shoppingCart.jsp";
	}

	// 修改订单项数量
	public String updateOrderItem(HttpServletRequest request, HttpServletResponse response, Page page) {
		String isLogin = checkLogin(request, response, page);
		if (isLogin == "success") {
			int oiid = Integer.parseInt(request.getParameter("oiid"));
			int number = Integer.parseInt(request.getParameter("sum"));
			OrderItem oi = orderItemDAO.getOrderItem(oiid);
			if (number != oi.getNumber()) {
				oi.setNumber(number);
				orderItemDAO.updateOrderItem(oi);
			}

			return "success";
		}
		return "false";
	}

	// 删除订单项
	public String deleteOrderItem(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oiid = Integer.parseInt(request.getParameter("oiid"));
		orderItemDAO.deleteOrderItem(oiid);

		return "success";
	}

	// 结算页面
	public String buy(HttpServletRequest request, HttpServletResponse response, Page page) {
		List<OrderItem> ois = new ArrayList<>();
		float total = 0;
		int num = 0;

		String oiids = request.getParameter("oiids");
		System.out.println(oiids);
		String[] o = StringUtils.split(oiids, ",");
		if (o.length > 0) {
			for (String string : o) {
				int oiid = Integer.parseInt(string);
				OrderItem oi = orderItemDAO.getOrderItem(oiid);
				ois.add(oi);
				int itemNum = oi.getNumber();
				total += itemNum * oi.getProduct().getPromotePrice();
				num += itemNum;
			}
			request.getSession().setAttribute("ois", ois);
			request.setAttribute("total", total);
			request.setAttribute("num", num);
			return "forward-normal/buy.jsp";
		}
		return "false";

	}

	public String createOrder(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user = (User) request.getSession().getAttribute("user");

		List<OrderItem> ois = (List<OrderItem>) request.getSession().getAttribute("ois");
		if (ois.isEmpty()) {
			return "redirect-front_login";
		}

		String address = request.getParameter("address");
		String post = request.getParameter("post");
		String receiver = request.getParameter("receiver");
		String mobile = request.getParameter("mobile");
		String userMessage = request.getParameter("userMessage");

		Order order = new Order();
		String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(0, 10000);

		order.setOrderCode(orderCode);
		order.setAddress(address);
		order.setPost(post);
		order.setReceiver(receiver);
		order.setMobile(mobile);
		order.setUserMessage(userMessage);
		order.setCreateDate(new Date());
		order.setUser(user);
		order.setStatus(OrderDAO.waitPay);

		orderDAO.addOrder(order);
		float total = 0;
		for (OrderItem oi : ois) {
			oi.setOrder(order);
			orderItemDAO.updateOrderItem(oi);
			total += oi.getProduct().getPromotePrice() * oi.getNumber();
		}

		return "redirect-front_alipay?oid=" + order.getId() + "&total=" + total;
	}

	public String alipay(HttpServletRequest request, HttpServletResponse response, Page page) {
		return "forward-normal/alipay.jsp";

	}

	public String payed(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order order = orderDAO.getOrder(oid);
		Date payDate = new Date();
		order.setPayDate(payDate);
		order.setStatus(orderDAO.waitDelivery);
		orderDAO.updateOrder(order);
		request.setAttribute("order", order);
		return "forward-normal/payed.jsp";
	}

	// 订单查询
	public String myOrder(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user = (User) request.getSession().getAttribute("user");
		List<Order> os = orderDAO.list(user.getId(), "delete");
		orderItemDAO.fill(os);
		request.setAttribute("orders", os);
		return "forward-normal/myOrder.jsp";
	}

	// 确认收货
	public String confirmPay(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order o = orderDAO.getOrder(oid);
		orderItemDAO.fill(o);
		request.setAttribute("order", o);

		return "forward-normal/reciveConfirm.jsp";
	}

	public String confirmed(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order o = orderDAO.getOrder(oid);
		Date confirmDate = new Date();
		o.setConfirmDate(confirmDate);
		o.setStatus(orderDAO.waitReview);

		orderDAO.updateOrder(o);

		return "redirect-normal/orderConfirmed.jsp";
	}

	// 删除订单
	public String deleteOrder(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order o = orderDAO.getOrder(oid);
		o.setStatus(orderDAO.delete);
		orderDAO.updateOrder(o);
		return "success";
	}

	// 跳转评价页面
	public String review(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order o = orderDAO.getOrder(oid);
		orderItemDAO.fill(o);
		//添加销量与评论数
		for (OrderItem oi : o.getOrderItems()) {
			int pid = oi.getProduct().getId();
			int saleCount = orderItemDAO.getSaleCount(pid);
			int reviewCount = reviewDAO.getAmount(pid);
			oi.getProduct().setSaleCount(saleCount);
			oi.getProduct().setReviewCount(reviewCount);
		}
		request.setAttribute("order", o);
		
		return "forward-normal/productReview.jsp";
	}
	//添加评价
	public String addReview(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user = (User) request.getSession().getAttribute("user");
		int pid = Integer.parseInt(request.getParameter("pid"));
		String content = request.getParameter("content");
		Product p = productDAO.getProduct(pid);
		
		
		Review r = new Review();
		r.setContent(content);
		r.setUser(user);
		r.setProduct(p);
		r.setCreateDate(new Date());
		
		reviewDAO.addReview(r);
		return "success";
		
	}
	//完成评价，修改订单状态
		public String reviewDone(HttpServletRequest request, HttpServletResponse response, Page page) {
			int oid = Integer.parseInt(request.getParameter("oid"));
			Order order = orderDAO.getOrder(oid);
			order.setStatus(orderDAO.finish);
			orderDAO.updateOrder(order);
			return "success";
					
		}
}
