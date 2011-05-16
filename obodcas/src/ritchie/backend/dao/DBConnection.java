package ritchie.backend.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;


public class DBConnection {
	public static Logger logger = Logger.getLogger(DBConnection.class); 
	private static Connection connection;
	private static UserTransaction userTransaction;
	private static DataSource ds = null;
	static{
		try {
			logger.info("DBConnection class static block start");
			Context ctx = new InitialContext();
			//DataSource ds = (DataSource)ic.lookup("jdbc/ritchie");
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ritchie");
			//userTransaction = (Current)ctx.lookup("java:comp/env/UserTransaction");
			/*System.out.println("3");
			connection = ds.getConnection();
			System.out.println("");
			if(connection == null){
				System.out.println("DB Connection is not initialized. plz check");
			}*/
			logger.info("DBConnection class static block end");
		} catch (NamingException e) {
			logger.error(e.getMessage());
		}//catch(SQLException sqle){
//			System.out.println("SQLException:  "+sqle);
//		}
	}

	public static Connection getConnection() {
		try{
			connection =  ds.getConnection();
		}catch(SQLException sqle){
			System.out.println("SQLException:  "+sqle);
		}
		return connection;
	}
	
	public static UserTransaction getUserTransaction() {
		return userTransaction;
	}
}
