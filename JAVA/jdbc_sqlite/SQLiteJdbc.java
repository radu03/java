package eu.ase.jdbc_sqlite;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteJdbc {

	public static void main(String[] args) {
		String connString = "jdbc:sqlite:test.db";
		Connection c = null;
		try {
			File f = new File("test.db");
			boolean dropTable = false;
			if (f.exists()) {
				dropTable = true;
			}

			Class cls = Class.forName("org.sqlite.JDBC");
			System.out.println("class loaded in JVM RAM = " + cls.toString());
			c = DriverManager.getConnection(connString);
			c.setAutoCommit(false);
			createTable(c, dropTable);
			insertData(c);
			selectData(c);
			c.close();
		} catch (SQLException | ClassNotFoundException sqle) {
			sqle.printStackTrace();
		}

	}

	public static void createTable(Connection conn, boolean dropTable) throws SQLException {

		Statement st = conn.createStatement();
		if (dropTable) {
			String sqlDropTableStr = "drop table COMPANY";
			st.executeUpdate(sqlDropTableStr);
		}
		String sqlCreateTableStr = "create table COMPANY "
					+ "(ID INT PRIMARY KEY NOT NULL, NAME TEXT NOT NULL, AGE INT, ADDRESS CHAR(50), SALARY REAL)";

		st.executeUpdate(sqlCreateTableStr);
		
		st.close();
		conn.commit();
	}
	
	public static void insertData(Connection conn) throws SQLException {
		Statement st = null; String sqlPrepStInsertStr=null;
		String sqlInsertStr=null;
		
		st=conn.createStatement();
		sqlInsertStr= "insert into COMPANY(ID,NAME,AGE,ADDRESS,SALARY) values (1,'Andrei',32,'B',20500)";
		st.executeUpdate(sqlInsertStr);
		st.close();
		
		sqlPrepStInsertStr ="insert into COMPANY(ID,NAME,AGE,ADDRESS,SALARY) values (?,?,?,?,?)";
		PreparedStatement ps=conn.prepareStatement(sqlPrepStInsertStr);
		
		ps.setInt(1, 2);
		ps.setString(2, "Marius");
		ps.setInt(3, 24);
		ps.setString(4, "Brasov");
		ps.setFloat(5, 12000.5f);
		ps.executeUpdate();
		
		ps.setInt(1, 3);
		ps.setString(2, "Alexandra");
		ps.setInt(3, 25);
		ps.setString(4, "Iasi");
		ps.setFloat(5, 15000.5f);
		ps.executeUpdate();
		
		ps.close();
		conn.commit();
	}

	public static void selectData(Connection c) 
			throws SQLException {
		Statement stmt = c.createStatement();
		// String sqlSel = "select * from COMPANY";
		String sqlSel = "select * from COMPANY where AGE < 30;";
		
		
		ResultSet rs = stmt.executeQuery(sqlSel);
		while (rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			int age = rs.getInt("age");
			String address = rs.getString("address");
			float salary = rs.getFloat("salary");
			
			System.out.printf("\nID = %d, Name = %s, age = %d,"
					+ "address = %s, salary = %f", id, name,
					age, address, salary);
		}
		
		rs.close();
		stmt.close();
	}

}
