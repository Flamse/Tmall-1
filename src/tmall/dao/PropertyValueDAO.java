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
	 * �������ֵ
	 */
	public void addValue(PropertyValue value) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO propertyvalue(id,propertyid,productid,value) VALUE(null,?,?,?);";
		try {
			// ��ȡ����
			conn = DBUtil.getConnection();
			// ���ز�������͵�����
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, value.getProperty().getId());
			pstmt.setInt(2, value.getProduct().getId());
			pstmt.setString(3, value.getValue());
			
			pstmt.executeUpdate();
			// ���ִ�к�ͨ�����·�ʽȡ�÷��ص�����
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				// ע������У��Ա�����
				value.setId(id);
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
	 * �޸ķ���
	 */
	public void updateValue(PropertyValue value) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "UPDATE propertyvalue SET propertyid=?,productid=?,value=? WHERE id=?;";
		try {
			// ��ȡ����
			conn = DBUtil.getConnection();
			// ���ز�������͵�����
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, value.getProperty().getId());
			pstmt.setInt(2, value.getProduct().getId());
			pstmt.setString(3, value.getValue());
			pstmt.setInt(4, value.getId());
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

	// ���ݲ�Ʒ�������ȡ����ֵ
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

	// ��ʼ����Ʒ����ֵ����������Բ����ھʹ���һ��
	public void getValue(Product product) {
		// ��ȡ��Ʒ������
		List<Property> pts = new PropertyDAO().list(product.getCategory().getId());
		for (Property prperty : pts) {
			PropertyValue pv = getValue(prperty.getId(), product.getId());
			if (null == pv) {
				pv = new PropertyValue();
				pv.setProduct(product);
				pv.setProperty(prperty);
				this.addValue(pv);
				System.out.println("��ʼ����Ʒ����");
			}else {
				System.out.println("��Ʒ�����Ѵ���");
			}
		}
	}

	// ���ݲ�Ʒ��ȡ��������
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
