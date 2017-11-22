package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tmall.bean.Order;
import tmall.bean.OrderItem;
import tmall.bean.Product;
import tmall.bean.User;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

public class OrderItemDAO {

	/*
	 * 添加订单项
	 */
	public void addOrderItem(OrderItem oi) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO orderitem VALUE(null,?,?,?,?);";
		try {
			// 获取连接
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, oi.getProduct().getId());
			int oid;
			if (oi.getOrder() == null) {
				oid = -1;// 如果订单号未生成
			} else {
				oid = oi.getOrder().getId();
			}
			pstmt.setInt(2, oid);
			pstmt.setInt(3, oi.getUser().getId());
			pstmt.setInt(4, oi.getNumber());

			pstmt.executeUpdate();
			// 语句执行后通过以下方式取得返回的主键
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				// 注入对象中，以备后用
				oi.setId(id);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			DBUtil.close(rs, pstmt, conn);
		}
	}

	/*
	 * 删除方法
	 */
	public void deleteOrderItem(int id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "DELETE FROM orderitem WHERE id=" + id;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, stmt, conn);
		}
	}

	/*
	 * 修改方法
	 */
	public void updateOrderItem(OrderItem oi) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "update orderitem set pid= ?, oid=?, uid=?,number=?  where id = ?";
		try {
			// 获取连接
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, oi.getProduct().getId());
			if (oi.getOrder() != null) {
				pstmt.setInt(2, oi.getOrder().getId());
			}else {
				pstmt.setInt(2, -1);
			}
			pstmt.setInt(3, oi.getUser().getId());
			pstmt.setInt(4, oi.getNumber());
			pstmt.setInt(5, oi.getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			DBUtil.close(rs, pstmt, conn);
		}
	}

	/*
	 * 查询方法
	 */
	public OrderItem getOrderItem(int id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		OrderItem oi = null;
		String sql = "SELECT * FROM orderitem WHERE id=" + id;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				oi = new OrderItem();
				int pid = rs.getInt("pid");
				int oid = rs.getInt("oid");
				int uid = rs.getInt("uid");
				Product p = new ProductDAO().getProduct(pid);
				Order o = new OrderDAO().getOrder(oid);
				User user = new UserDAO().getUser(uid);

				oi.setId(id);
				oi.setProduct(p);
				oi.setOrder(o);
				oi.setUser(user);
				oi.setNumber(rs.getInt("number"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, stmt, conn);
		}
		return oi;
	}

	// 返回订单总数
	public int getAmount() {
		int amount = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT count(*) FROM orderitem ";

		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				amount = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, stmt, conn);
		}
		return amount;
	}

	// 获取用户的所有订单项
	public List<OrderItem> listByUser(int uid) {
		return listByUser(uid, 0, Short.MAX_VALUE);
	}

	//
	public List<OrderItem> listByUser(int uid, int start, int count) {
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM orderitem WHERE uid=? and oid= -1 ORDER BY id DESC LIMIT ?,?";

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, uid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, count);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderItem bean = new OrderItem();
				int id = rs.getInt("id");
				int pid = rs.getInt("pid");
				int oid = rs.getInt("oid");
				int number = rs.getInt("number");

				Product p = new ProductDAO().getProduct(pid);
				Order o = new OrderDAO().getOrder(oid);
				User user = new UserDAO().getUser(uid);

				OrderItem oi = new OrderItem();
				oi.setId(id);
				oi.setProduct(p);
				oi.setOrder(o);
				oi.setUser(user);
				oi.setNumber(number);
				orderItems.add(oi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
		return orderItems;
	}

	// 获取用户订单的订单项
	public List<OrderItem> listByOrder(int oid) {
		return listByOrder(oid, 0, Short.MAX_VALUE);
	}

	// 获取用户订单的订单项,指定数量
	public List<OrderItem> listByOrder(int oid, int start, int count) {
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM orderitem WHERE oid=? ORDER BY id DESC LIMIT ?,?";

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, oid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, count);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderItem bean = new OrderItem();
				int id = rs.getInt("id");
				int pid = rs.getInt("pid");
				int uid = rs.getInt("uid");
				int number = rs.getInt("number");

				Product p = new ProductDAO().getProduct(pid);
				Order o = new OrderDAO().getOrder(oid);
				User user = new UserDAO().getUser(uid);

				OrderItem oi = new OrderItem();
				oi.setId(id);
				oi.setProduct(p);
				oi.setOrder(o);
				oi.setUser(user);
				oi.setNumber(number);
				orderItems.add(oi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
		return orderItems;

	}

	// 获取购物车订单项：没有oid的orderitem
	public OrderItem getCartProduct(int pid, int uid) {
		OrderItem orderItem = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM orderitem WHERE pid=" + pid + " AND uid=" + uid + " AND oid =-1";

		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				orderItem = new OrderItem();
				int id = rs.getInt("id");
				int number = rs.getInt("number");

				Product p = new ProductDAO().getProduct(pid);
				User user = new UserDAO().getUser(uid);

				orderItem.setId(id);
				orderItem.setNumber(number);
				orderItem.setProduct(p);
				orderItem.setUser(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, stmt, conn);
		}
		return orderItem;

	}

	// 为订单填充订单项与订单商品数量、总价
	public void fill(Order o) {
		List<OrderItem> items = listByOrder(o.getId());
		o.setOrderItems(items);
		float totalPrice = 0;
		int totalNumber = 0;
		for (OrderItem orderItem : items) {
			totalPrice += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
			totalNumber += orderItem.getNumber();
		}
		o.setTotal(totalPrice);
		o.setOrderItems(items);
		o.setTotalNumber(totalNumber);
	}

	// 批量计算订单价格
	public void fill(List<Order> os) {
		for (Order order : os) {
			fill(order);
		}
	}

	// 获取某产品的销售信息
	public List<OrderItem> listByProduct(int pid) {
		return listByProduct(pid, 0, Short.MAX_VALUE);
	}

	public List<OrderItem> listByProduct(int pid, int start, int count) {
		List<OrderItem> beans = new ArrayList<OrderItem>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from OrderItem where pid = ? AND oid != -1 order by id desc limit ?,? ";

		try {
			pstmt.setInt(1, pid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, count);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderItem bean = new OrderItem();
				int id = rs.getInt(1);

				int uid = rs.getInt("uid");
				int oid = rs.getInt("oid");
				int number = rs.getInt("number");

				Product product = new ProductDAO().getProduct(pid);
				Order order = new OrderDAO().getOrder(oid);
				User user = new UserDAO().getUser(uid);

				bean.setProduct(product);
				bean.setOrder(order);
				bean.setUser(user);
				bean.setNumber(number);
				bean.setId(id);

				beans.add(bean);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
		return beans;
	}

	// 获取销量
	public int getSaleCount(int pid) {
		int total = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select sum(number) from OrderItem where pid = " + pid;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			DBUtil.close(rs, stmt, conn);
		}
		return total;
	}

}
