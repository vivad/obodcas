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

import ritchie.backend.utilities.Constants;

public class TETemplateDetailsDAO {
	private static Logger logger = Logger.getLogger(TETemplateDetailsDAO.class);
	public String getTemplateSequenceNumber() throws SQLException {
		logger.info("in getTemplateSequenceNumber ");
		PreparedStatement pst = null;
		Connection con = DBConnection.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		String tokenNumber = null;
		try {
			pst = con
			.prepareStatement("SELECT max(SUBSTRING(templateno, -6, 6)) as FileNo from rit_templatedtl");
			
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
			tokenNumber = sdf.format(new Date())
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

	public String insertUploadedTemplateDetails(String tokenNumber, String fileName,
			String fileSrc, String user, String status, String fileStatus) throws SQLException {
		logger.info("inside insertUploadedTemplateDetails method:::  "+status);
		String fileNo = null;
		PreparedStatement pst = null;
		Connection con = DBConnection.getConnection();
		try {
			pst = con
					.prepareStatement("INSERT INTO rit_templatedtl (templateno,templatename,templatesource,uploadedby,status,templatestatus) VALUES (?,?,?,?,?,?)");
			pst.setString(1, tokenNumber);
			pst.setString(2, fileName);
			pst.setString(3, fileSrc);
			pst.setString(4, user);
			pst.setString(5, status);
			pst.setString(6, fileStatus);
			int result1 = pst.executeUpdate();
			logger.info("result1:  " + result1);
			if (result1 == 1) {
				pst = con
						.prepareStatement("select templateno FILENO FROM rit_templatedtl where uploadedby=? ORDER BY uploadeddate desc limit 0,1");
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

	public boolean manageUploadedTemplateDetails(String fileNo, String status)
			throws SQLException {
		logger.info("inside manageUploadedTemplateDetails method");
		Statement st = null;
		Connection con = DBConnection.getConnection();
		try {
			st = con.createStatement();
			int result = st.executeUpdate("update rit_templatedtl set templatestatus='"
					+ status + "' where templateno='" + fileNo + "'");

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
	
	public static List<Map<String, String>> getUploadedTemplateDetails(String pageFrom) throws SQLException {
		logger.info("in getUploadedTemplateDetails... ");
		PreparedStatement pst = null;
		String fileNO = null;
		Connection con = DBConnection.getConnection();
		List<Map<String, String>> al = new ArrayList<Map<String, String>>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
		try {
			/*if(pageFrom.equalsIgnoreCase(Constants.TE_ENROLL_TEMPLATE_LINK_NAME)){
				pst = con
				.prepareStatement("SELECT fileno as FILENO, filename as FILENAME, uploadedby as UPLOADEDBY, status as STATUS, uploadeddate as UPLOADEDDATE from rit_templateengine_filedtl ORDER BY uploadeddate DESC limit 0,5");
			}else{
				pst = con
				.prepareStatement("SELECT fileno as FILENO, filename as FILENAME, uploadedby as UPLOADEDBY, filestatus as STATUS, uploadeddate as UPLOADEDDATE from rit_templateengine_filedtl ORDER BY FILENO DESC limit 0,5");
			}*/
			
			
			if(pageFrom.equalsIgnoreCase(Constants.TE_ENROLL_TEMPLATE_LINK_NAME)){
				pst = con
				.prepareStatement("SELECT templateno as FILENO, templatename as FILENAME, uploadedby as UPLOADEDBY, status as STATUS, uploadeddate as UPLOADEDDATE from rit_templatedtl ORDER BY FILENO DESC");
			}else{
				pst = con
				.prepareStatement("SELECT templateno as FILENO, templatename as FILENAME, uploadedby as UPLOADEDBY, templatestatus as STATUS, uploadeddate as UPLOADEDDATE from rit_templatedtl ORDER BY FILENO DESC");
			}
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("FILENO", rs.getString("FILENO"));
				map.put("FILENAME", rs.getString("FILENAME"));
				map.put("UPLOADEDBY", rs.getString("UPLOADEDBY"));
				map.put("STATUS", rs.getString("STATUS"));
				map.put("UPLOADEDDATE", sdf.format((java.util.Date)rs.getDate("UPLOADEDDATE")));
				al.add(map);
			}
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
		return al;
	}
	
	
	public static String getTemplateName(String templateNo) throws SQLException {
		logger.info("in getUploadedTemplateDetails... ");
		PreparedStatement pst = null;
		Connection con = DBConnection.getConnection();
		String fileName = null;
		try {
			pst = con.prepareStatement(
					"SELECT templatename as FILENAME from rit_templatedtl where templateno="+templateNo);
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				fileName = rs.getString("FILENAME");
			}
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
		return fileName;
	}
	
	/*public static String getTemplateStatus(String templateNo) throws SQLException {
		logger.info("in getUploadedTemplateDetails... ");
		PreparedStatement pst = null;
		Connection con = DBConnection.getConnection();
		String templateStatus = null;
		try {
			pst = con.prepareStatement(
					"SELECT templatestatus as TEMPLATESTATUS from rit_templatedtl where templateno="+templateNo);
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				templateStatus = rs.getString("TEMPLATESTATUS");
			}
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
		return templateStatus;
	}*/
}
