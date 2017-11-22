package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.util.DBUtil;

public class PropertyDAO {

	// 获取某个分类的属性数量，如果不区分分类，查出来的属性没有意义
	public int getAmount(int cid) {
		int Amount = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select count(*) from Property where cid =" + cid;
		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Amount = rs.getInt(1);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			DBUtil.close(rs, stmt, conn);
		}
		return Amount;
	}

	// 添加属性
	public void add(Property property) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "insert into Property values(null,?,?)";
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, property.getCategory().getId());
			pstmt.setString(2, property.getName());
			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				property.setId(id);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
	}

	// 修改属性
	public void update(Property property) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "update Property set cid= ?, name=? where id = ?";
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, property.getCategory().getId());
			pstmt.setString(2, property.getName());
			pstmt.setInt(3, property.getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
	}

	// 删除属性
	public void delete(int id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "delete from property where id =" + id;
		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, stmt, conn);
		}
	}

	// 根据id获取属性
	public Property get(int id) {
		Property bean = new Property();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from Property where id = " + id;
		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				bean.setName(rs.getString("name"));
				Category category = new CategoryDAO().getCategory(rs.getInt("cid"));
				bean.setCategory(category);
				bean.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, stmt, conn);
		}
		return bean;
	}

	// 获取所有属性
	public List<Property> list(int cid) {
		return list(cid, 0, Short.MAX_VALUE);
	}

	//获取一定数量的属性，用于分页
	public List<Property> list(int cid, int start, int count) {
		List<Property> beans = new ArrayList<Property>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from Property where cid = ? order by id desc limit ?,? ";
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, count);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Property bean = new Property();
				int id = rs.getInt(1);
				String name = rs.getString("name");
				bean.setName(name);
				Category category = new CategoryDAO().getCategory(cid);
				bean.setCategory(category);
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
}
