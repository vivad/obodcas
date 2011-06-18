package ritchie.backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import ritchie.backend.utilities.ApplicationProperties;
import ritchie.backend.utilities.Constants;

public class TEManageTemplateDAO {
	private static Logger logger = Logger.getLogger(TEManageTemplateDAO.class);
	
	public Set<String> getTableNamesFromDB() throws SQLException {
		logger.info("in getTableNamesFromDB ");
		Statement st = null;
		Connection con = DBConnection.getConnection();
		Set<String> tableNames = new TreeSet<String>();
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("SHOW TABLES FROM "+ApplicationProperties.hm.get(Constants.DB_NAME));
			
			while (rs.next()) {
				tableNames.add(rs.getString(1));
			}
			
			logger.info(tableNames);
			
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
			throw sqle;
		} finally {
			try {
				if (st != null) {
					st.close();
				}
				con.close();
			} catch (SQLException sqle) {
				logger.error(sqle.getMessage());
				throw sqle;
			}
		}
		return tableNames;
	}
	
	public List<String> getColumnsNamesFromDB(String tableName) throws SQLException {
		logger.info("in getTableNamesFromDB ");
		Statement st = null;
		Connection con = DBConnection.getConnection();
		List<String> columnNames = new ArrayList<String>();
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("SHOW COLUMNS FROM "+ApplicationProperties.hm.get(Constants.DB_NAME)+"."+tableName);
			
			while (rs.next()) {
				columnNames.add(rs.getString(1));
			}
			
			logger.info(columnNames);
			
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
			throw sqle;
		} finally {
			try {
				if (st != null) {
					st.close();
				}
				con.close();
			} catch (SQLException sqle) {
				logger.error(sqle.getMessage());
				throw sqle;
			}
		}
		return columnNames;
	}
	

	public boolean assignDataPattern(String templateNoList,List<Map<String,String>> dataList) throws SQLException {
		logger.info("inside assignDataPattern method");
		PreparedStatement pst = null;
		Connection con = DBConnection.getConnection();
		boolean status = true;
		try {
			pst = con
					.prepareStatement("INSERT INTO rit_assignedpatterns (templateno,pattern,tablename,columnname) VALUES (?,?,?,?)");
			
			for(int i=0;i<dataList.size();i++){
				Map<String,String> map = dataList.get(i);
				pst.setString(1, templateNoList);
				pst.setString(2, map.get("PatternName"));
				pst.setString(3, map.get("TableName"));
				pst.setString(4, map.get("ColumnName"));
				pst.addBatch();
			}
			
			int[] result = pst.executeBatch();
			
			for(int i=0;i<result.length;i++){
				logger.info("result["+i+"]"+result[i]);
				if(result[i] != 1){
					status = false;
				}
			}
			logger.info("status:  "+status);
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
			throw sqle;
		} finally {
			logger.info("in finally");
			try {
				if (pst != null) {
					pst.close();
				}
				con.close();
			} catch (SQLException sqle) {
				logger.error(sqle.getMessage());
				throw sqle;
			}
		}
		return status;
	}
}
