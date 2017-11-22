package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.util.DBUtil;

public class CategoryDAO {

	/*
	 * ��ӷ��෽��
	 * 
	 */
	public void add(Category bean) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String cname = bean.getName();
		String sql = "INSERT INTO tmall.category(name) VALUE(?);";
		try {
			// ��ȡ����
			conn = DBUtil.getConnection();

			// ���ز�������͵�����
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, cname);
			pstmt.executeUpdate();
			// ���ִ�к�ͨ�����·�ʽȡ�÷��ص�����
			rs = pstmt.getGeneratedKeys();
			while (rs.next()) {
				int id = rs.getInt(1);
				// ע������У��Ա�����
				bean.setId(id);
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
	public void deleteCategory(int id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "DELETE FROM category WHERE id=" + id;

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
	public void updateCategory(Category category) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "UPDATE category SET name=? WHERE id=?;";

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category.getName());
			pstmt.setInt(2, category.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
	}

	/*
	 * ��ѯ����
	 */
	public Category getCategory(int id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Category category = null;
		String sql = "SELECT name FROM category WHERE id=" + id;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				category = new Category();
				category.setId(id);
				category.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, stmt, conn);
		}
		return category;
	}

	// ������������
	public int getAmount() {
		int amount = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT count(*) FROM category ";

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

	// ��ȡָ���������࣬��������
	public List<Category> list(int start, int count) {
		List<Category> Categories = new ArrayList<Category>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT id,name FROM category ORDER BY id DESC LIMIT ?,?";

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, count);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Category c = new Category();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				Categories.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
		return Categories;
	}

	// ��ȡ���з���
	public List<Category> list() {

		return list(0, Short.MAX_VALUE);
	}
}
