package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.util.DBUtil;

public class PropertyValueDAO {
	/*
	 * 添加属性值
	 */
	public void addValue(PropertyValue value) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO propertyvalue(id,propertyid,productid,value) VALUE(null,?,?,?);";
		try {
			// 获取连接
			conn = DBUtil.getConnection();
			// 返回插入的类型的主键
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, value.getProperty().getId());
			pstmt.setInt(2, value.getProduct().getId());
			pstmt.setString(3, value.getValue());
			
			pstmt.executeUpdate();
			// 语句执行后通过以下方式取得返回的主键
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				// 注入对象中，以备后用
				value.setId(id);
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
	public void deleteValue(int id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "DELETE FROM propertyvalue WHERE id=" + id;

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
	public void updateValue(PropertyValue value) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "UPDATE propertyvalue SET propertyid=?,productid=?,value=? WHERE id=?;";
		try {
			// 获取连接
			conn = DBUtil.getConnection();
			// 返回插入的类型的主键
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, value.getProperty().getId());
			pstmt.setInt(2, value.getProduct().getId());
			pstmt.setString(3, value.getValue());
			pstmt.setInt(4, value.getId());
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
	public PropertyValue getValue(int id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		PropertyValue value = null;
		String sql = "SELECT propertyid,productid,value FROM propertyvalue WHERE id=" + id;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				value = new PropertyValue();
				int propertyid = rs.getInt(1);
				Property property = new PropertyDAO().get(propertyid);
				int pid = rs.getInt(2);
				Product product = new ProductDAO().getProduct(pid);
				value.setProduct(product);
				value.setProperty(property);
				value.setValue(rs.getString(3));
				value.setId(id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, stmt, conn);
		}
		return value;
	}

	// 根据产品、分类获取属性值
	public PropertyValue getValue(int propertyId, int productId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PropertyValue value = null;
		String sql = "SELECT id,value FROM propertyvalue WHERE propertyid=? AND productid=?;";

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,propertyId);
			pstmt.setInt(2,productId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				value = new PropertyValue();
				Property property = new PropertyDAO().get(propertyId);
				Product product = new ProductDAO().getProduct(productId);
				value.setProperty(property);
				value.setProduct(product);
				value.setValue(rs.getString(2));
				value.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
		return value;
	}

	// 初始化产品属性值，如果该属性不存在就创建一个
	public void getValue(Product product) {
		// 获取产品的属性
		List<Property> pts = new PropertyDAO().list(product.getCategory().getId());
		for (Property prperty : pts) {
			PropertyValue pv = getValue(prperty.getId(), product.getId());
			if (null == pv) {
				pv = new PropertyValue();
				pv.setProduct(product);
				pv.setProperty(prperty);
				this.addValue(pv);
				System.out.println("初始化产品属性");
			}else {
				System.out.println("产品属性已存在");
			}
		}
	}

	// 根据产品获取所有属性
	public List<PropertyValue> list(int productId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PropertyValue> pvs = new ArrayList<PropertyValue>();
		String sql = "SELECT id,propertyid,value FROM propertyvalue WHERE  productid=? ORDER BY id DESC";

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, productId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PropertyValue value = new PropertyValue();
				Property property = new PropertyDAO().get(rs.getInt("propertyid"));
				Product product = new ProductDAO().getProduct(productId);
				value.setProperty(property);
				value.setProduct(product);
				value.setValue(rs.getString(3));
				value.setId(rs.getInt(1));
				pvs.add(value);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
		return pvs;
	}

}
