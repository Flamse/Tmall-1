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
import tmall.bean.User;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

public class OrderDAO {
	public static final String waitPay = "waitPay";
	public static final String waitDelivery = "waitDelivery";
	public static final String waitConfirm = "waitConfirm";
	public static final String waitReview = "waitReview";
	public static final String finish = "finish";
	public static final String delete = "delete";
	
	/*
	 * 添加订单
	 */
	public void addOrder(Order o) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO order_ VALUE(null,?,?,?,?,?,?,?,?,?,?,?,?);";
		try {
			// 获取连接
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, o.getOrderCode());
            pstmt.setString(2, o.getAddress());
            pstmt.setString(3, o.getPost());
            pstmt.setString(4, o.getReceiver());
            pstmt.setString(5, o.getMobile());
            pstmt.setString(6, o.getUserMessage());
             
            pstmt.setTimestamp(7,  DateUtil.d2t(o.getCreateDate()));
            pstmt.setTimestamp(8,  DateUtil.d2t(o.getPayDate()));
            pstmt.setTimestamp(9,  DateUtil.d2t(o.getDeliveryDate()));
            pstmt.setTimestamp(10,  DateUtil.d2t(o.getConfirmDate()));
            pstmt.setInt(11, o.getUser().getId());
            pstmt.setString(12, o.getStatus());

            pstmt.executeUpdate();
			// 语句执行后通过以下方式取得返回的主键
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				// 注入对象中，以备后用
				o.setId(id);
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
	public void deleteOrder(int id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "DELETE FROM order_ WHERE id=" + id;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			stmt.executeQuery(sql);
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
	public void updateOrder(Order o) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "update order_ set address= ?, post=?, receiver=?,mobile=?,userMessage=? ,createDate = ? , payDate =? , deliveryDate =?, confirmDate = ? , orderCode =?, uid=?, status=? where id = ?";
		try {
			// 获取连接
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, o.getAddress());
            pstmt.setString(2, o.getPost());
            pstmt.setString(3, o.getReceiver());
            pstmt.setString(4, o.getMobile());
            pstmt.setString(5, o.getUserMessage());
            pstmt.setTimestamp(6, DateUtil.d2t(o.getCreateDate()));;
            pstmt.setTimestamp(7, DateUtil.d2t(o.getPayDate()));;
            pstmt.setTimestamp(8, DateUtil.d2t(o.getDeliveryDate()));;
            pstmt.setTimestamp(9, DateUtil.d2t(o.getConfirmDate()));;
            pstmt.setString(10, o.getOrderCode());
            pstmt.setInt(11, o.getUser().getId());
            pstmt.setString(12, o.getStatus());
            pstmt.setInt(13, o.getId());
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
	public Order getOrder(int id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Order o = null;
		String sql = "SELECT * FROM order_ WHERE id=" + id;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				o = new Order();
				String orderCode =rs.getString("orderCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                String status = rs.getString("status");
                int uid =rs.getInt("uid");
                Date createDate = DateUtil.t2d( rs.getTimestamp("createDate"));
                Date payDate = DateUtil.t2d( rs.getTimestamp("payDate"));
                Date deliveryDate = DateUtil.t2d( rs.getTimestamp("deliveryDate"));
                Date confirmDate = DateUtil.t2d( rs.getTimestamp("confirmDate"));
                 
                o.setOrderCode(orderCode);
                o.setAddress(address);
                o.setPost(post);
                o.setReceiver(receiver);
                o.setMobile(mobile);
                o.setUserMessage(userMessage);
                o.setCreateDate(createDate);
                o.setPayDate(payDate);
                o.setDeliveryDate(deliveryDate);
                o.setConfirmDate(confirmDate);
                User user = new UserDAO().getUser(uid);
                o.setUser(user);
                o.setStatus(status);
                 
                o.setId(id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, stmt, conn);
		}
		return o;
	}

	// 返回订单总数
	public int getAmount() {
		int amount = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT count(*) FROM order_ ";

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



	// 获取指定数量分类，用于排序
	public List<Order> list(int uid,String excludeStatus, int start, int count) {
		List<Order> orders = new ArrayList<Order>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM order_ WHERE uid=? AND status != ? ORDER BY id DESC LIMIT ?,?";

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, uid);
			pstmt.setString(2, excludeStatus);
			pstmt.setInt(3, start);
			pstmt.setInt(4, count);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Order bean = new Order();
                String orderCode =rs.getString("orderCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                String status = rs.getString("status");
                Date createDate = DateUtil.t2d( rs.getTimestamp("createDate"));
                Date payDate = DateUtil.t2d( rs.getTimestamp("payDate"));
                Date deliveryDate = DateUtil.t2d( rs.getTimestamp("deliveryDate"));
                Date confirmDate = DateUtil.t2d( rs.getTimestamp("confirmDate"));
                
                int id = rs.getInt("id");
                bean.setId(id);
                bean.setOrderCode(orderCode);
                bean.setAddress(address);
                bean.setPost(post);
                bean.setReceiver(receiver);
                bean.setMobile(mobile);
                bean.setUserMessage(userMessage);
                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                User user = new UserDAO().getUser(uid);
                bean.setStatus(status);
                bean.setUser(user);
                orders.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
		return orders;
	}

	// 获取产品全部订单
	public List<Order> list(int uid,String excludeStatus) {
		return list(uid,excludeStatus, 0, Short.MAX_VALUE);

	}
	
	// 获取指定数量分类，用于排序
		public List<Order> list(String excludeStatus, int start, int count) {
			List<Order> orders = new ArrayList<Order>();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = "SELECT * FROM order_ WHERE status != ? ORDER BY id DESC LIMIT ?,?";

			try {
				conn = DBUtil.getConnection();
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, excludeStatus);
				pstmt.setInt(2, start);
				pstmt.setInt(3, count);
				
				rs = pstmt.executeQuery();
				while (rs.next()) {
					Order bean = new Order();
	                String orderCode =rs.getString("orderCode");
	                String address = rs.getString("address");
	                String post = rs.getString("post");
	                String receiver = rs.getString("receiver");
	                String mobile = rs.getString("mobile");
	                String userMessage = rs.getString("userMessage");
	                String status = rs.getString("status");
	                Date createDate = DateUtil.t2d( rs.getTimestamp("createDate"));
	                Date payDate = DateUtil.t2d( rs.getTimestamp("payDate"));
	                Date deliveryDate = DateUtil.t2d( rs.getTimestamp("deliveryDate"));
	                Date confirmDate = DateUtil.t2d( rs.getTimestamp("confirmDate"));
	                int uid = rs.getInt("uid");
	                
	                int id = rs.getInt("id");
	                bean.setId(id);
	                bean.setOrderCode(orderCode);
	                bean.setAddress(address);
	                bean.setPost(post);
	                bean.setReceiver(receiver);
	                bean.setMobile(mobile);
	                bean.setUserMessage(userMessage);
	                bean.setCreateDate(createDate);
	                bean.setPayDate(payDate);
	                bean.setDeliveryDate(deliveryDate);
	                bean.setConfirmDate(confirmDate);
	                User user = new UserDAO().getUser(uid);
	                bean.setStatus(status);
	                bean.setUser(user);
	                orders.add(bean);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBUtil.close(rs, pstmt, conn);
			}
			return orders;
		}

		// 获取产品全部订单
		public List<Order> list(String excludeStatus) {
			return list(excludeStatus, 0, Short.MAX_VALUE);

		}
}
