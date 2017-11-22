package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.Review;
import tmall.bean.User;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

public class ReviewDAO {
	/*
	 * �������
	 */
	public void addReview(Review r) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO review(id,content,pid,uid,createDate) VALUE(null,?,?,?,?);";
		try {
			// ��ȡ����
			conn = DBUtil.getConnection();
			// ���ز�������͵�����
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, r.getContent());
			pstmt.setInt(2, r.getProduct().getId());
			pstmt.setInt(3, r.getUser().getId());
			pstmt.setTimestamp(4, DateUtil.d2t(r.getCreateDate()));

			pstmt.executeUpdate();
			// ���ִ�к�ͨ�����·�ʽȡ�÷��ص�����
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				// ע������У��Ա�����
				r.setId(id);
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
	public void deleteReview(int id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "DELETE FROM review WHERE id=" + id;

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
	 * �޸ķ���
	 */
	public void updateReview(Review r) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "UPDATE review SET content=?,pid=?,uid=?,createDate=? WHERE id=?";
		try {
			// ��ȡ����
			conn = DBUtil.getConnection();
			// ���ز�������͵�����
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, r.getContent());
			pstmt.setInt(2, r.getProduct().getId());
			pstmt.setInt(3, r.getUser().getId());
			pstmt.setTimestamp(4, DateUtil.d2t(r.getCreateDate()));
			pstmt.setInt(5, r.getId());

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
	public Review getReview(int id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Review r = null;
		String sql = "SELECT content,uid,pid,createDate FROM review WHERE id=" + id;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				r = new Review();
				int uid = rs.getInt(2);
				int pid = rs.getInt(3);
				User user = new UserDAO().getUser(uid);
				Product product = new ProductDAO().getProduct(pid);

				r.setId(id);
				r.setContent(rs.getString(2));
				r.setUser(user);
				r.setProduct(product);
				r.setCreateDate(DateUtil.t2d(rs.getTimestamp(4)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, stmt, conn);
		}
		return r;
	}

	// ������������
	public int getAmount() {
		int amount = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT count(*) FROM review ";

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

	// ���ز�Ʒ��������
	public int getAmount(int pid) {
		int amount = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT count(*) FROM review WHERE pid=" + pid;

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
	public List<Review> list(int pid, int start, int count) {
		List<Review> reviews = new ArrayList<Review>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT id,content,uid,createDate FROM review WHERE pid=? ORDER BY id DESC LIMIT ?,?";

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, count);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Review review = new Review();
				int uid = rs.getInt(3);
				User user = new UserDAO().getUser(uid);
				Product product = new ProductDAO().getProduct(pid);

				review.setId(rs.getInt(1));
				review.setContent(rs.getString(2));
				review.setUser(user);
				review.setProduct(product);
				review.setCreateDate(DateUtil.t2d(rs.getTimestamp(4)));
				reviews.add(review);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
		return reviews;
	}

	// ��ȡ��Ʒȫ������
	public List<Review> list(int pid) {
		return list(pid, 0, Short.MAX_VALUE);

	}
	
	public boolean isExist(String content, int pid) {
        
        String sql = "select * from review where content = ? and pid = ?";
         
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, content);
            ps.setInt(2, pid);
             
            ResultSet rs = ps.executeQuery();
  
            if (rs.next()) {
                return true;
            }
  
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
 
        return false;
    }
}
