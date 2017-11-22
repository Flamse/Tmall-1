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
	 * ��Ӷ�����
	 */
	public void addOrderItem(OrderItem oi) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO orderitem VALUE(null,?,?,?,?);";
		try {
			// ��ȡ����
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, oi.getProduct().getId());
			int oid;
			if (oi.getOrder() == null) {
				oid = -1;// ���������δ����
			} else {
				oid = oi.getOrder().getId();
			}
			pstmt.setInt(2, oid);
			pstmt.setInt(3, oi.getUser().getId());
			pstmt.setInt(4, oi.getNumber());

			pstmt.executeUpdate();
			// ���ִ�к�ͨ�����·�ʽȡ�÷��ص�����
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				// ע������У��Ա�����
				oi.setId(id);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// �ر�����
			DBUtil.close(rs, pstmt, conn);
		}
	}

	/*
	 * ɾ������
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
	 * �޸ķ���
	 */
	public void updateOrderItem(OrderItem oi) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "update orderitem set pid= ?, oid=?, uid=?,number=?  where id = ?";
		try {
			// ��ȡ����
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
			// �ر�����
			DBUtil.close(rs, pstmt, conn);
		}
	}

	/*
	 * ��ѯ����
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

	// ���ض�������
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

	// ��ȡ�û������ж�����
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

	// ��ȡ�û������Ķ�����
	public List<OrderItem> listByOrder(int oid) {
		return listByOrder(oid, 0, Short.MAX_VALUE);
	}

	// ��ȡ�û������Ķ�����,ָ������
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

	// ��ȡ���ﳵ�����û��oid��orderitem
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

	// Ϊ������䶩�����붩����Ʒ�������ܼ�
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

	// �������㶩���۸�
	public void fill(List<Order> os) {
		for (Order order : os) {
			fill(order);
		}
	}

	// ��ȡĳ��Ʒ��������Ϣ
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

	// ��ȡ����
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
