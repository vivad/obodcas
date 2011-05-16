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

import ritchie.backend.utilities.DIVendorsUploadXLSConstants;
import ritchie.backend.utilities.DIVendorsUploadXLSProperties;

public class DIVendorsUploadDAO {
	private static Logger logger = Logger.getLogger(DIVendorsUploadDAO.class);
	public List getUploadXLSValidUpdateRecords(List list, String user)
			throws SQLException {
		logger.info("inside getInsertAndUpdateRecords method");
		PreparedStatement forUpdate = null;
		Connection con = DBConnection.getConnection();
		List updateList = new ArrayList();

		try {
			forUpdate = con
					.prepareStatement("select count(*) vendors from rit_vendors where vendorid=? and vendorname=?");

			for (int i = 0; i < list.size(); i++) {
				Long enIDFromXLS = (Long)((HashMap) list.get(i))
						.get(DIVendorsUploadXLSProperties.hm
								.get(DIVendorsUploadXLSConstants.ENROLLMENT_ID));

				String vendorNameFromXLS = (String) (((HashMap) list.get(i))
						.get(DIVendorsUploadXLSProperties.hm
								.get(DIVendorsUploadXLSConstants.VENDOR_NAME)));
				logger.info("enIDFromXLS from xls:  " + enIDFromXLS);
				logger.info("vendorNameFromXLS from xls:  "
						+ vendorNameFromXLS);
				forUpdate.setLong(1, enIDFromXLS);
				forUpdate.setString(2, vendorNameFromXLS);
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

				Long vendorArea = (Long) hm.get(DIVendorsUploadXLSProperties.hm
						.get(DIVendorsUploadXLSConstants.VENDOR_AREA));

				enrollmentIDST = con.createStatement();
				ResultSet rs = enrollmentIDST
						.executeQuery("SELECT max(SUBSTRING(vendorid, -6, 6)) as ENROLLMENTID from rit_vendors");
				String enrollmentNo = null;
				rs.next();
				if (rs.getString("ENROLLMENTID") != null) {
					logger.info("in if");
					String sequenceFormatted = String.format("%06d", (Integer
							.parseInt(rs.getString("ENROLLMENTID")) + 1));
					enrollmentNo = vendorArea + sequenceFormatted;
				} else {
					logger.info("in else");
					enrollmentNo = vendorArea + "000001";
				}

				logger.info("enrollmentNo:  "+enrollmentNo);
				insertPST = con
						.prepareStatement("INSERT INTO rit_vendors "
								+ "(vendorid,vendorarea,vendortype,vendorname,vendordescription,"
								+ "contactperson1,contactperson2,telephone1,telephone2,telephone3,"
								+ "mobile1,mobile2,mobile3,fax,address,email1,email2,email3,website,"
								+ "mapdata,createdfrom,createdby,enrolled,STATUS,approved,draft) "
								+ "VALUES "
								+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,NULL,?,?,?,'-',?,'-','Backoffice',?,?,?, NULL,NULL)");

				insertPST.setLong(1, Long.parseLong(enrollmentNo));
				insertPST.setLong(2, (Long) hm
						.get(DIVendorsUploadXLSProperties.hm
								.get(DIVendorsUploadXLSConstants.VENDOR_AREA)));
				insertPST.setLong(3, (Long) hm
						.get(DIVendorsUploadXLSProperties.hm
								.get(DIVendorsUploadXLSConstants.VENDOR_TYPE)));
				insertPST.setString(4, (String) hm
						.get(DIVendorsUploadXLSProperties.hm
								.get(DIVendorsUploadXLSConstants.VENDOR_NAME)));
				insertPST
						.setString(
								5,
								(String) hm
										.get(DIVendorsUploadXLSProperties.hm
												.get(DIVendorsUploadXLSConstants.VENDOR_DESCRIPTION)));
				insertPST
						.setString(
								6,
								(String) hm
										.get(DIVendorsUploadXLSProperties.hm
												.get(DIVendorsUploadXLSConstants.CONTACT_PERSON_1)));
				insertPST
						.setString(
								7,
								(String) hm
										.get(DIVendorsUploadXLSProperties.hm
												.get(DIVendorsUploadXLSConstants.CONTACT_PERSON_2)));
				insertPST.setString(8, (String) hm
						.get(DIVendorsUploadXLSProperties.hm
								.get(DIVendorsUploadXLSConstants.TELEPHONE_1)));
				insertPST.setString(9, (String) hm
						.get(DIVendorsUploadXLSProperties.hm
								.get(DIVendorsUploadXLSConstants.TELEPHONE_2)));
				insertPST.setString(10, (String) hm
						.get(DIVendorsUploadXLSProperties.hm
								.get(DIVendorsUploadXLSConstants.TELEPHONE_3)));
				insertPST.setLong(11, (Long) hm
						.get(DIVendorsUploadXLSProperties.hm
								.get(DIVendorsUploadXLSConstants.MOBILE_1)));
				insertPST.setLong(12, (Long) hm
						.get(DIVendorsUploadXLSProperties.hm
								.get(DIVendorsUploadXLSConstants.MOBILE_2)));
				insertPST.setLong(13, (Long) hm
						.get(DIVendorsUploadXLSProperties.hm
								.get(DIVendorsUploadXLSConstants.MOBILE_3)));
				insertPST
						.setString(
								14,
								(String) hm
										.get(DIVendorsUploadXLSProperties.hm
												.get(DIVendorsUploadXLSConstants.VENDOR_ADDRESS)));
				insertPST.setString(15, (String) hm
						.get(DIVendorsUploadXLSProperties.hm
								.get(DIVendorsUploadXLSConstants.EMAIL_ID_1)));
				insertPST.setString(16, (String) hm
						.get(DIVendorsUploadXLSProperties.hm
								.get(DIVendorsUploadXLSConstants.EMAIL_ID_2)));
				insertPST.setString(17, (String) hm
						.get(DIVendorsUploadXLSProperties.hm
								.get(DIVendorsUploadXLSConstants.WEBSITE)));
				insertPST.setString(18, user);
				
				Object obj =  hm.get(DIVendorsUploadXLSProperties.hm.get(DIVendorsUploadXLSConstants.ENROLLED));
				String value = "";
				if(obj == null){
					value = "No";
				}else{
					value = obj.toString();
				}
				insertPST.setString(19, value);
				/*insertPST.setString(19, (String) hm
						.get(DIVendorsUploadXLSProperties.hm
								.get(DIVendorsUploadXLSConstants.ENROLLED)));*/
				
				obj =  hm.get(DIVendorsUploadXLSProperties.hm.get(DIVendorsUploadXLSConstants.ACTIVE));
				value = "";
				if(obj == null){
					value = "Yes";
				}else{
					value = obj.toString();
				}
				insertPST.setString(20, value);
				
				/*insertPST.setString(20, (String) hm
						.get(DIVendorsUploadXLSProperties.hm
								.get(DIVendorsUploadXLSConstants.ACTIVE)));*/

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
