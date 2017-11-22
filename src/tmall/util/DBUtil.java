package tmall.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	private static String driver = "com.mysql.jdbc.Driver";
	private static String host = "127.0.0.1";// 主机地址
	private static int port = 3306;// 端口号
	private static String dataBaseName = "tmall";// 数据库名
	// 连接URL，添加默认编码设置utf-8；
	private static String url = "jdbc:mysql://" + host + ":" + port + "/" + dataBaseName + "?characterEncoding=utf-8&useSSL=false";

	// 数据库用户名与密码
	private static String user = "root";
	private static String password = "admin";

	// 在类加载的时候就加载驱动
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 获取连接
	public static Connection getConnection() throws SQLException {
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	// 关闭连接
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
