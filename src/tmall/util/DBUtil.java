package tmall.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	private static String driver = "com.mysql.jdbc.Driver";
	private static String host = "127.0.0.1";// ������ַ
	private static int port = 3306;// �˿ں�
	private static String dataBaseName = "tmall";// ���ݿ���
	// ����URL�����Ĭ�ϱ�������utf-8��
	private static String url = "jdbc:mysql://" + host + ":" + port + "/" + dataBaseName + "?characterEncoding=utf-8&useSSL=false";

	// ���ݿ��û���������
	private static String user = "root";
	private static String password = "admin";

	// ������ص�ʱ��ͼ�������
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// ��ȡ����
	public static Connection getConnection() throws SQLException {
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	// �ر�����
	public static void close(ResultSet rs, Statement stmt, Connection conn) {

		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();

			} finally {
				try {
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();

				}finally {
				}
			}
		}

	}
}
