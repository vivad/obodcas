package ritchie.backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class UserDAO {
	private static Logger logger = Logger.getLogger(UserDAO.class);
	
	public Map<String,String> authenticateUser(String userName, String password) throws SQLException{
		logger.info("in authenticateUser method");
		PreparedStatement pst = null;
		Connection con = DBConnection.getConnection();
		Map<String,String> map = new HashMap<String,String>();
		try{
			pst = con.prepareStatement("select count(*) as COUNT,firstname as FN,lastname as LN,usertype as UT from rit_user where username=? and password=?");
			pst.setString(1,userName);
			pst.setString(2,password);
			ResultSet rs = pst.executeQuery();
			rs.next();
			logger.info("row count is "+rs.getInt("COUNT"));
			if(rs.getInt("COUNT") > 0){
				map.put("status", "SUCCESS");
				map.put("firstname", rs.getString("FN"));
				map.put("lastname", rs.getString("LN"));
				map.put("usertype", rs.getString("UT"));
				return map;
			}else{
				map.put("status", "FAILURE");
				return map;
			}
		}catch (SQLException sqle) {
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
	}
}
