package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.User;
import tmall.util.DBUtil;

public class UserDAO {
	// 添加用户
	public void addUser(User user) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO user(name,password) VALUE(?,?);";
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPassword());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			while (rs.next()) {
				user.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
	}

	// 删除用户
	public void deleteUser(int id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "DELETE FROM user WHERE id=" + id;

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

	// 修改用户
	public void updateUser(User user) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "UPDATE user SET name=?,password=? WHERE id=?;";
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getName());
			pstmt.setInt(3, user.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
	}

	// 查询用户
	public User getUser(int id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		User user = null;
		String sql = "SELECT name,password FROM user WHERE id=" + id;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				user = new User();
				user.setId(id);
				user.setName(rs.getString(1));
				user.setPassword(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, stmt, conn);
		}
		return user;
	}

	// 获取一定数量的用户列表，用于分页查询
	public List<User> list(int start, int count) {
		List<User> users = new ArrayList<User>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT id,name,password FROM user ORDER BY id LIMIT ?,?;";
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, count);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setPassword(rs.getString(3));
				users.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
		return users;
	}

	// 查询所有用户
	public List<User> list() {
		return list(0, Short.MAX_VALUE);
	}

	// 根据用户名查询用户，用于注册判断是否存在
	public User getUser(String name) {
		User user = null;
		Boolean flag = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT id,passwrd FROM user WHERE name=?";

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt(1));
				user.setPassword(rs.getString(2));
				user.setName(name);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
		return user;
	}

	// 判断用户是否存在
	public boolean isExist(String name) {
		User user = getUser(name);
		return user != null;
	}

	// 根据用户名、密码查询用户，用于登录功能
	public User getUser(String name, String password) {
		User user = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT id,name,password FROM user WHERE name=? AND password=?;";
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setPassword(rs.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
		return user;
	}

	// 获取用户总数
	public int getAmount() {
		int Amount = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT count(*) FROM user;";

		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				Amount = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, stmt, conn);
		}
		return Amount;
	}
}
