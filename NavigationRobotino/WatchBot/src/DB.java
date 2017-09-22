import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

	public Connection conn = null;
	
	public DB() { //Connexion à la base de données
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://tp-epu.univ-savoie.fr:3308/simonw";
			conn = DriverManager.getConnection(url, "simonw", "dwxa992m");
			System.out.println("conn built");
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet runSql(String sql) throws SQLException {
		Statement sta = conn.createStatement();
		return sta.executeQuery(sql);
	}
	
	public boolean runSql2 (String sql) throws SQLException {
		Statement sta = conn.createStatement();
		return sta.execute(sql);
	}
	
	protected void finalize() throws Throwable {
		if (conn != null || !conn.isClosed()){
			conn.close();
		}
	}
	
}
