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
	// 定义两个静态常量，用于标记图片类型
	public static final String type_single = "type_single";
	public static final String type_detail = "type_detail";

	/*
	 * 添加图片
	 */
	public void addImage(ProductImage image) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO productimage(id,pid,type) VALUE(null,?,?);";
		try {
			// 获取连接
			conn = DBUtil.getConnection();
			// 返回插入的类型的主键
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, image.getProduct().getId());
			pstmt.setString(2, image.getImageType());
			// 语句执行后通过以下方式取得返回的主键
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				// 注入对象中，以备后用
				image.setId(id);
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
	 * 修改方法
	 */
	public void updateImage(ProductImage image) {

	}

	/*
	 * 查询方法
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

	// 返回类型总数
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

	// 获取指定数量图片
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

	// 获取全部的图片
	public List<ProductImage> list(Product product, String imageType) {
		return list(product, imageType, 0, Short.MAX_VALUE);

	}

}
