package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

public class ProductDAO {

	/*
	 * 添加订产品
	 */
	public void addProduct(Product p) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "insert into product values(null,?,?,?,?,?,?,?)";
		try {
			// 获取连接
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, p.getName());
			pstmt.setString(2, p.getSubTitle());
			pstmt.setFloat(3, p.getOriginalPrice());
			pstmt.setFloat(4, p.getPromotePrice());
			pstmt.setInt(5, p.getStock());
			pstmt.setInt(6, p.getCategory().getId());
			pstmt.setTimestamp(7, DateUtil.d2t(p.getCreateDate()));

			pstmt.executeUpdate();
			// 语句执行后通过以下方式取得返回的主键
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				// 注入对象中，以备后用
				p.setId(id);
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
	public void deleteProduct(int id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "DELETE FROM product WHERE id=" + id;

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
	public void updateProduct(Product p) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "update Product set name= ?, subTitle=?, originalPrice=?,promotePrice=?,stock=?, cid = ?, createDate=? where id = ?";
		try {
			// 获取连接
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, p.getName());
			pstmt.setString(2, p.getSubTitle());
			pstmt.setFloat(3, p.getOriginalPrice());
			pstmt.setFloat(4, p.getPromotePrice());
			pstmt.setInt(5, p.getStock());
			pstmt.setInt(6, p.getCategory().getId());
			pstmt.setTimestamp(7, DateUtil.d2t(p.getCreateDate()));
			pstmt.setInt(8, p.getId());
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
	public Product getProduct(int id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Product p = null;
		String sql = "select * from product where id = " + id;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				p = new Product();
				String name = rs.getString("name");
				String subTitle = rs.getString("subTitle");
				float orignalPrice = rs.getFloat("originalPrice");
				float promotePrice = rs.getFloat("promotePrice");
				int stock = rs.getInt("stock");
				int cid = rs.getInt("cid");
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
				p.setId(id);
				List<ProductImage> ps = new ProductImageDAO().list(p, "type_single", 0, 1);
				if (ps.size() != 0) {
					ProductImage firstProductImage = ps.get(0);
					p.setFirstProductImage(firstProductImage);
				}

				p.setName(name);
				p.setSubTitle(subTitle);
				p.setOriginalPrice(orignalPrice);
				p.setPromotePrice(promotePrice);
				p.setStock(stock);
				Category category = new CategoryDAO().getCategory(cid);
				p.setCategory(category);
				p.setCreateDate(createDate);

				setFirstProductImage(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, stmt, conn);
		}
		return p;
	}

	// 返回产品总数
	public int getAmount() {
		int amount = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT count(*) FROM product ";

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

	// 返回分类产品总数
	public int getAmount(int cid) {
		int amount = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT count(*) FROM product WHERE cid=" + cid;

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

	// 获取分类产品
	public List<Product> listByCategory(int cid) {
		return listByCategory(cid, 0, Short.MAX_VALUE);
	}

	// 获取指定数量产品，用于排序
	public List<Product> listByCategory(int cid, int start, int count) {
		List<Product> products = new ArrayList<Product>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM product WHERE cid=? ORDER BY id DESC LIMIT ?,?";

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, cid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, count);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Product p = new Product();
				int id = rs.getInt(1);
				p.setId(id);
				String name = rs.getString("name");
				String subTitle = rs.getString("subTitle");
				float orignalPrice = rs.getFloat("originalPrice");
				float promotePrice = rs.getFloat("promotePrice");
				int stock = rs.getInt("stock");
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
				Category category = new CategoryDAO().getCategory(cid);

				List<ProductImage> Images = new ProductImageDAO().list(p, "type_single", 0, 1);
				if (null != Images && Images.size() != 0) {
					ProductImage firstImage = Images.get(0);
					p.setFirstProductImage(firstImage);
				}

				p.setName(name);
				p.setSubTitle(subTitle);
				p.setOriginalPrice(orignalPrice);
				p.setPromotePrice(promotePrice);
				p.setStock(stock);
				p.setCreateDate(createDate);

				p.setCategory(category);
				setFirstProductImage(p);
				products.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
		return products;
	}

	// 获取所有产品
	public List<Product> list() {
		return list(0, Short.MAX_VALUE);
	}

	// 获取指定数量产品，用于排序
	public List<Product> list(int start, int count) {
		List<Product> products = new ArrayList<Product>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM product LIMIT ?,?";

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, start);
			pstmt.setInt(2, count);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Product p = new Product();
				int id = rs.getInt(1);
				String name = rs.getString("name");
				String subTitle = rs.getString("subTitle");
				float orignalPrice = rs.getFloat("orignalPrice");
				float promotePrice = rs.getFloat("promotePrice");
				int stock = rs.getInt("stock");
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
				int cid = rs.getInt("cid");
				Category category = new CategoryDAO().getCategory(cid);

				p.setName(name);
				p.setSubTitle(subTitle);
				p.setOriginalPrice(orignalPrice);
				p.setPromotePrice(promotePrice);
				p.setStock(stock);
				p.setCreateDate(createDate);
				p.setId(id);
				p.setCategory(category);
				setFirstProductImage(p);
				products.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
		return products;
	}

	// 批量填充
	public void fill(List<Category> cs) {
		for (Category category : cs) {
			fill(category);
		}
	}

	// 填充Category类中的Product数组
	public void fill(Category c) {
		List<Product> ps = listByCategory(c.getId());
		c.setProducts(ps);
	}

	// 创建包含8个商品的数组，用于首页菜单显示
	public void fillByRow(List<Category> cs) {
		int productsEachRow = 8;
		for (Category c : cs) {
			List<Product> products = c.getProducts();
			List<List<Product>> productsByRow = new ArrayList<>();
			for (int i = 0; i < products.size(); i += productsEachRow) {
				int size = i + productsEachRow;
				if (i > products.size()) {
					i = products.size();
				}
				if (size > products.size()) {
					size = products.size();
				}
				List<Product> productEachRow = products.subList(i, size);
				productsByRow.add(productEachRow);
			}
			c.setProductsByRow(productsByRow);
		}
	}

	// 设置展示图片
	public void setFirstProductImage(Product p) {
		List<ProductImage> pis = new ProductImageDAO().list(p, ProductImageDAO.type_single);
		if (!pis.isEmpty())
			p.setFirstProductImage(pis.get(0));
	}

	// 设置销量与评价数
	public void setSaleAndReviewNumber(Product p) {
		int saleCount = new OrderItemDAO().getSaleCount(p.getId());
		p.setSaleCount(saleCount);

		int reviewCount = new ReviewDAO().getAmount(p.getId());
		p.setReviewCount(reviewCount);

	}

	// 批量设置产品的销量与评价数
	public void setSaleAndReviewNumber(List<Product> products) {
		for (Product p : products) {
			setSaleAndReviewNumber(p);
		}
	}

	// 关键字查询产品
	public List<Product> search(String keyword, int start, int count) {
		List<Product> products = new ArrayList<Product>();

		// 如果为空，返回null，一班不会为空，在搜索栏设置placeholder即可。
		if (null == keyword || 0 == keyword.trim().length()) {
			return products;
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from Product where name like ? limit ?,? ";

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			// 去除关键字前后的空格，并添加sql语句中的"%"通配符
			pstmt.setString(1, "%" + keyword.trim() + "%");
			pstmt.setInt(2, start);
			pstmt.setInt(3, count);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Product bean = new Product();
				int id = rs.getInt(1);
				int cid = rs.getInt("cid");
				String name = rs.getString("name");
				String subTitle = rs.getString("subTitle");
				float orignalPrice = rs.getFloat("originalPrice");
				float promotePrice = rs.getFloat("promotePrice");
				int stock = rs.getInt("stock");
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));

				bean.setName(name);
				bean.setSubTitle(subTitle);
				bean.setOriginalPrice(orignalPrice);
				bean.setPromotePrice(promotePrice);
				bean.setStock(stock);
				bean.setCreateDate(createDate);
				bean.setId(id);

				Category category = new CategoryDAO().getCategory(cid);
				bean.setCategory(category);
				setFirstProductImage(bean);
				products.add(bean);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return products;
	}

}
