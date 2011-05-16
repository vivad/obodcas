package ritchie.backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import ritchie.backend.utilities.DIServiceCentersXLSConstants;
import ritchie.backend.utilities.DIServiceCentersXLSProperties;

public class DIServiceCentersUploadDAO {
	private static Logger logger = Logger.getLogger(DIServiceCentersUploadDAO.class);
	public List getUploadXLSValidUpdateRecords(List list, String user)
			throws SQLException {
		logger.info("inside getInsertAndUpdateRecords method");
		PreparedStatement forUpdate = null;
		Connection con = DBConnection.getConnection();
		List updateList = new ArrayList();

		try {
			forUpdate = con
					.prepareStatement("select count(*) SERVICECENTERS from rit_servicecenter where servicecenterid=? and servicecenter=?");

			for (int i = 0; i < list.size(); i++) {
				Long enIDFromXLS = (Long) (((HashMap) list.get(i))
						.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.ENROLLMENT_ID)));
				String serviceCenterNameFromXLS = (String) (((HashMap) list.get(i))
						.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.SERVICE_CENTER_NAME)));
				logger.info("enIDFromXLS from xls:  " + enIDFromXLS);
				logger.info("serviceCenterNameFromXLS from xls:  "
						+ serviceCenterNameFromXLS);
					forUpdate.setLong(1, enIDFromXLS);
					forUpdate.setString(2, serviceCenterNameFromXLS);
					if (forUpdate.execute())
						updateList.add(list.get(i));
			}

			logger.info("updateList.size():  " + updateList.size());

		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
			throw sqle;
		} finally {
			logger.info("in finally");
			try {
				if (forUpdate != null) {
					forUpdate.close();
				}
				con.close();
			} catch (SQLException sqle) {
				logger.error(sqle.getMessage());
				throw sqle;
			}
		}
		return updateList;
	}

	public boolean insertUploadedXLSRecords(List insertList, String user)
			throws SQLException {
		logger.info("inside insertUploadedXLSRecords method");
		PreparedStatement insertPST = null;
		Statement enrollmentIDST = null;
		Connection con = DBConnection.getConnection();

		try {
			for (int i = 0; i < insertList.size(); i++) {
				HashMap hm = (HashMap) insertList.get(i);
				
				Long areaCode = (Long) hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.AREA_CODE));

				enrollmentIDST = con.createStatement();
				ResultSet rs = enrollmentIDST
						.executeQuery("SELECT max(SUBSTRING(servicecenterid, -6, 6)) as ENROLLMENTID from rit_servicecenter");
				String enrollmentNo = null;
				rs.next();
				if (rs.getString("ENROLLMENTID") != null) {
					logger.info("in if");
					String sequenceFormatted = String.format("%06d", (Integer
							.parseInt(rs.getString("ENROLLMENTID")) + 1));
					enrollmentNo = areaCode + sequenceFormatted;
				} else {
					logger.info("in else");
					enrollmentNo = areaCode + "000001";
				}

				logger.info("enrollmentNo:  "+enrollmentNo);
				
				
				insertPST = con
						.prepareStatement("insert into rit_servicecenter (" 
								+ "servicecenterid,serviceCenterarea,servicecentertype," 
								+ "servicecenter,description,contactperson1,contactperson2," 
								+ "telephone1,telephone2,telephone3,mobile1,mobile2,mobile3," 
								+ "fax,address,email1,email2,email3,website,mapdata," 
								+ "createdfrom,createdby,enrolled,status,approved," 
								+ "draft) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,NULL,?,?,?,NULL,?,NULL,'Backoffice',?,?,?,?,?)");
					
				insertPST.setLong(1, Long.parseLong(enrollmentNo));
				insertPST.setLong(2, (Long) hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.AREA_CODE)));
				insertPST.setLong(3, (Long) hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.SERVICE_CENTER_TYPE)));
				insertPST.setString(4, (String) hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.SERVICE_CENTER_NAME)));
				insertPST.setString(5, (String) hm
						.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.SERVICE_DESCRIPTION)));
				insertPST.setString(6, (String) hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.CONTACT_PERSON_1)));
				insertPST.setString(7, (String) hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.CONTACT_PERSON_2)));
				insertPST.setString(8, (String) hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.TELEPHONE_1)));
				insertPST.setString(9, (String) hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.TELEPHONE_2)));
				insertPST.setString(10, (String) hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.TELEPHONE_3)));
				insertPST.setString(11, ""+(Long) hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.MOBILE_1))+"");
				insertPST.setString(12, ""+(Long) hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.MOBILE_2))+"");
				insertPST.setString(13, ""+(Long) hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.MOBILE_3))+"");
				insertPST.setString(14, (String) hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.ADDRESS)));
				insertPST.setString(15, (String) hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.EMAIL_ID_1)));
				insertPST.setString(16, (String) hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.EMAIL_ID_2)));
				insertPST.setString(17, (String) hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.WEBSITE)));
				insertPST.setString(18, user);
				
				Object obj =  hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.ENROLLED));
				String value = "";
				if(obj == null){
					value = "No";
				}else{
					value = obj.toString();
				}
				insertPST.setString(19, value);
				
				obj =  hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.ACTIVE));
				value = "";
				if(obj == null){
					value = "Yes";
				}else{
					value = obj.toString();
				}
				insertPST.setString(20, value);
				
				insertPST.setString(21, "Yes");
				insertPST.setString(22, "No");
				
				insertPST.addBatch();
			}
			int[] insertResult = insertPST.executeBatch();
			boolean status = true;
			for (int i = 0; i < insertResult.length; i++) {
				logger.info("insertResult[" + i + "]:  "
						+ insertResult[i]);
			}
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
			throw sqle;
		} finally {
			logger.info("in finally");
			try {
				if (enrollmentIDST != null) {
					enrollmentIDST.close();
				}
				if (insertPST != null) {
					insertPST.close();
				}
				con.close();
			} catch (SQLException sqle) {
				logger.error(sqle.getMessage());
				throw sqle;
			}
		}
		return true;
	}

	public boolean updateUploadedXLSRecords(List updateList, String user)
			throws SQLException {
		logger.info("inside updateUploadedXLSRecords method");
		Statement updateRecord = null;
		Connection con = DBConnection.getConnection();
		try {
			for (int i = 0; i < updateList.size(); i++) {
				String query = (String) updateList.get(i);
				logger.info("query:  " + query);
				updateRecord = con.createStatement();
				int result = updateRecord.executeUpdate(query);
				logger.info("Update Result:  " + result);
			}
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
			throw sqle;
		} finally {
			logger.info("in finally");
			try {
				if (updateRecord != null) {
					updateRecord.close();
				}
				con.close();
			} catch (SQLException sqle) {
				logger.error(sqle.getMessage());
				throw sqle;
			}
		}
		return true;
	}
}
