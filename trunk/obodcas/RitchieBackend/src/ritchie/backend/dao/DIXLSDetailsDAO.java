package ritchie.backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class DIXLSDetailsDAO {
	private static Logger logger = Logger.getLogger(DIXLSDetailsDAO.class);
	public String getXLSSequenceNumber(String uploadType) throws SQLException {
		logger.info("in getXLSSequenceNumber ");
		PreparedStatement pst = null;
		Connection con = DBConnection.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		String tokenNumber = null;
		try {
			/*pst = con
					.prepareStatement("SELECT fileno as FileNo FROM rit_filedtl ORDER BY fileno DESC LIMIT 1");*/
			/*pst = con
			.prepareStatement("SELECT max(fileno) as FileNo FROM rit_filedtl where fileno like '"+uploadType+"%'");*/
			pst = con
			.prepareStatement("SELECT max(SUBSTRING(fileno, -6, 6)) as FileNo from rit_filedtl");
			
			String fileNO = null;
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				fileNO = rs.getString("FileNo");
			}
			
			int dbTokenNO = 1;
			if (fileNO != null) {
				dbTokenNO = Integer.parseInt(fileNO)+1;
			}
			logger.info("dbTokenNO:  " + dbTokenNO);
			String sequenceFormatted = String.format("%06d", (dbTokenNO));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			tokenNumber = uploadType + sdf.format(new Date())
					+ sequenceFormatted;
			
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
			throw sqle;
		} finally {
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
		return tokenNumber;
	}

	public String insertUploadedXLSDetails(String tokenNumber, String fileName,
			String fileSrc, String user, String status) throws SQLException {
		logger.info("inside insertUploadedXLSDetails method:::  "+status);
		String fileNo = null;
		PreparedStatement pst = null;
		Connection con = DBConnection.getConnection();
		try {
			pst = con
					.prepareStatement("INSERT INTO rit_filedtl (fileno,filename,filesource,uploadedby,STATUS) VALUES (?,?,?,?,?)");
			pst.setString(1, tokenNumber);
			pst.setString(2, fileName);
			pst.setString(3, fileSrc);
			pst.setString(4, user);
			pst.setString(5, status);
			int result1 = pst.executeUpdate();
			logger.info("result1:  " + result1);
			if (result1 == 1) {
				pst = con
						.prepareStatement("select fileno FILENO FROM rit_filedtl where uploadedby=? ORDER BY uploadeddate desc limit 0,1");
				pst.setString(1, user);
				ResultSet rs = pst.executeQuery();

				while (rs.next()) {
					fileNo = rs.getString("FILENO");
				}
			}
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
		return fileNo;
	}

	public boolean updateUploadedXLSDetails(String fileNo, String status)
			throws SQLException {
		logger.info("inside updateUploadedXLSDetails method");
		Statement st = null;
		Connection con = DBConnection.getConnection();
		try {
			st = con.createStatement();
			int result = st.executeUpdate("update rit_filedtl set status='"
					+ status + "' where fileno='" + fileNo + "'");

			logger.info("result:  " + result);
			if (result == 1) {
				return true;
			}
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
			throw sqle;
		} finally {
			logger.info("in finally");
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
		return false;
	}
	
	public static List<Map<String, Object>> getUploadedXLSDetails() throws SQLException {
		logger.info("in getUploadedXLSDetails... ");
		PreparedStatement pst = null;
		String fileNO = null;
		Connection con = DBConnection.getConnection();
		List<Map<String, Object>> al = new ArrayList<Map<String, Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
		try {
			pst = con
					.prepareStatement("SELECT fileno as FILENO, filename as FILENAME, uploadedby as UPLOADEDBY, status as STATUS, uploadeddate as UPLOADEDDATE from rit_filedtl ORDER BY uploadeddate DESC limit 0,5");
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("FILENO", rs.getString("FILENO"));
				map.put("FILENAME", rs.getString("FILENAME"));
				map.put("UPLOADEDBY", rs.getString("UPLOADEDBY"));
				map.put("STATUS", rs.getString("STATUS"));
				map.put("UPLOADEDDATE", sdf.format((java.util.Date)rs.getDate("UPLOADEDDATE")));
				al.add(map);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				con.close();
			} catch (SQLException sqle) {
				throw sqle;
			}
		}
		return al;
	}
}
