package cn.edu.cdcas.partyschool.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * the util class packaging the common methods to execute CRUD based on JDBC.
 * @author Char Jin(Jin zhichao)
 * @date 2018-07-12
 */
public class DBUtil {
	private Connection conn = null;
	private PreparedStatement preStmt = null;
	private ResultSet rs = null;
	private Properties properties = null;
	private String url, user, password;

	/**
	 * load drivers.
	 */
	public DBUtil() {
		properties = new Properties();
		try {
			properties.load(new FileInputStream("./src/main/resources/db.properties"));
			String driverName = properties.getProperty("jdbc.driverClassName");

			url = properties.getProperty("jdbc.url");
			user = properties.getProperty("jdbc.username");
			password = properties.getProperty("jdbc.password");

			Class.forName(driverName);
			conn = this.getConnection();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		new DBUtil();
	}

	/**
	 * return database connection object.
	 * 
	 * @return Connection
	 */
	public Connection getConnection() {
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("Database Connection Failed!\n" + e.getMessage());
		}
		return conn;
	}

	/**
	 * return ResultSet of query.
	 * 
	 * @param sql
	 * @param params
	 * @return ResultSet
	 */
	public ResultSet query(String sql, Object[] params) {
		try {
			if (conn.isClosed())
				conn = this.getConnection();
			preStmt = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					preStmt.setObject(i + 1, params[i]);
				}
			}
			rs = preStmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("Error!" + e.getMessage());
		}
		return rs;
	}

	/**
	 * database CDU.
	 * 
	 * @param sql
	 * @param params
	 * @return int    affected numbers.
	 */
	public int update(String sql, Object[] params) {
		int affectedLine = 0;
		try {
			if (conn.isClosed())
				conn = this.getConnection();
			preStmt = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					preStmt.setObject(i + 1, params[i]);
				}
			}
			affectedLine = preStmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error!" + e.getMessage());
		}
		return affectedLine;

	}

	/**
	 *  dispose all database resource.
	 */
	public void closeAll() {
		// close resultset.
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

		// close PreparedStatement
		if (preStmt != null) {
			try {
				preStmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

		// close Connection
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * generate an update sql used to update table.
	 * 
	 * @param tableName
	 * @param field
	 * @param where
	 * @return
	 */
	public String generateUpdateSql(String tableName, String[] field, String where) {
		StringBuffer sql = new StringBuffer("update " + tableName + " set ");
		for (int i = 0; i < field.length; i++) {
			sql.append(field[i] + " = ?");
			if (i < field.length - 1)
				sql.append(",");
		}
		sql.append(" where " + where);

		return sql.toString();
	}
	
	public String generateUpdateSql(String tableName, List<String> field, String where) {
		StringBuffer sql = new StringBuffer("update " + tableName + " set ");
		int size = field.size();
		for (int i = 0; i < size; i++) {
			sql.append(field.get(i) + " = ?");
			if (i < size - 1)
				sql.append(",");
		}
		sql.append(" where " + where);
		
		return sql.toString();
	}
	

	/**
	 * generate an update sql used to insert data into table.
	 * 
	 * @param tableName
	 * @param columnCount
	 * @return
	 */
	public String generateInsertSql(String tableName, int columnCount) {
		StringBuffer sql = new StringBuffer("insert into " + tableName + " values(");
		for (int i = 0; i < columnCount; i++) {
			sql.append("?");
			if (i < columnCount - 1) {
				sql.append(",");
			}
		}
		sql.append(")");
		return sql.toString();
	}
	


}
