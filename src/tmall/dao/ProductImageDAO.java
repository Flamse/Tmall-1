package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;

public class ProductImageDAO {
	// ����������̬���������ڱ��ͼƬ����
	public static final String type_single = "type_single";
	public static final String type_detail = "type_detail";

	/*
	 * ���ͼƬ
	 */
	public void addImage(ProductImage image) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO productimage(id,pid,type) VALUE(null,?,?);";
		try {
			// ��ȡ����
			conn = DBUtil.getConnection();
			// ���ز�������͵�����
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, image.getProduct().getId());
			pstmt.setString(2, image.getImageType());
			// ���ִ�к�ͨ�����·�ʽȡ�÷��ص�����
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				// ע������У��Ա�����
				image.setId(id);
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
	public void deleteImage(int id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "DELETE FROM productimage WHERE id=" + id;

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
	public void updateImage(ProductImage image) {

	}

	/*
	 * ��ѯ����
	 */
	public ProductImage getImage(int id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ProductImage image = null;
		String sql = "SELECT pid,imageType FROM productimage WHERE id=" + id;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				image = new ProductImage();
				int pid = rs.getInt(1);
				Product product = new ProductDAO().getProduct(pid);
				image.setProduct(product);
				image.setImageType(rs.getString(2));
				image.setId(id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, stmt, conn);
		}
		return image;
	}

	// ������������
	public int getAmount() {
		int amount = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT count(*) FROM productimage ";

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

	// ��ȡָ������ͼƬ
	public List<ProductImage> list(Product product, String imageType, int start, int count) {
		List<ProductImage> images = new ArrayList<ProductImage>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT id FROM productimage WHERE type=? AND pid=? ORDER BY id DESC LIMIT ?,?";

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, imageType);
			pstmt.setInt(2, product.getId());
			pstmt.setInt(3, start);
			pstmt.setInt(4, count);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductImage image = new ProductImage();
				image.setId(rs.getInt(1));
				image.setImageType(imageType);
				image.setProduct(product);
				images.add(image);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
		return images;
	}

	// ��ȡȫ����ͼƬ
	public List<ProductImage> list(Product product, String imageType) {
		return list(product, imageType, 0, Short.MAX_VALUE);

	}

}
