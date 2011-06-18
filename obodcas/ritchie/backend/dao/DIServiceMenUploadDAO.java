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

import ritchie.backend.utilities.DIServiceMenXLSConstants;
import ritchie.backend.utilities.DIServiceMenXLSProperties;

public class DIServiceMenUploadDAO {
	private static Logger logger = Logger.getLogger(DIServiceMenUploadDAO.class);
	public List getUploadXLSValidUpdateRecords(List list, String user)
			throws SQLException {
		logger.info("inside getInsertAndUpdateRecords method");
		PreparedStatement forUpdate = null;
		Connection con = DBConnection.getConnection();
		List updateList = new ArrayList();

		try {
			forUpdate = con
					.prepareStatement("select count(*) SERVICEMEN from rit_servicemen where servicemenid=? and servicemen=?");

			for (int i = 0; i < list.size(); i++) {
				Long enIDFromXLS = (Long) (((HashMap) list.get(i))
						.get(DIServiceMenXLSProperties.hm
								.get(DIServiceMenXLSConstants.ENROLLMENT_ID)));
				String serviceMenNameFromXLS = (String) (((HashMap) list.get(i))
						.get(DIServiceMenXLSProperties.hm
								.get(DIServiceMenXLSConstants.SERVICE_MEN_NAME)));
				logger.info("enIDFromXLS from xls:  " + enIDFromXLS);
				logger.info("serviceMenNameFromXLS from xls:  "
						+ serviceMenNameFromXLS);
				forUpdate.setLong(1, enIDFromXLS);
				forUpdate.setString(2, serviceMenNameFromXLS);
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

				Long serviceMenArea = (Long) hm
						.get(DIServiceMenXLSProperties.hm
								.get(DIServiceMenXLSConstants.SERVICE_MEN_AREA));

				enrollmentIDST = con.createStatement();
				ResultSet rs = enrollmentIDST
						.executeQuery("SELECT max(SUBSTRING(servicemenid, -6, 6)) as ENROLLMENTID from rit_servicemen");
				String enrollmentNo = null;
				rs.next();
				if (rs.getString("ENROLLMENTID") != null) {
					logger.info("in if");
					String sequenceFormatted = String.format("%06d", (Integer
							.parseInt(rs.getString("ENROLLMENTID")) + 1));
					enrollmentNo = serviceMenArea + sequenceFormatted;
				} else {
					logger.info("in else");
					enrollmentNo = serviceMenArea + "000001";
				}

				logger.info("enrollmentNo:  " + enrollmentNo);

				insertPST = con
						.prepareStatement("insert into rit_servicemen (servicemenid,servicemenarea,servicementypeid,servicemen,servicedescription,"
								+ "areaofcoverage,mobile1,mobile2,telephone,address,email1,email2,website,mapdata,"
								+ "createdfrom,createdby,enrolled,status,approved,draft)"
								+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,null,'BackOffice',?,?,?,?,?)");

				insertPST.setLong(1, Long.parseLong(enrollmentNo));
				insertPST.setLong(2, (Long) hm.get(DIServiceMenXLSProperties.hm
						.get(DIServiceMenXLSConstants.SERVICE_MEN_AREA)));
				insertPST.setLong(3, (Long) hm.get(DIServiceMenXLSProperties.hm
						.get(DIServiceMenXLSConstants.SERVICE_MEN_TYPE)));
				insertPST
						.setString(
								4,
								(String) hm
										.get(DIServiceMenXLSProperties.hm
												.get(DIServiceMenXLSConstants.SERVICE_MEN_NAME)));
				insertPST
						.setString(
								5,
								(String) hm
										.get(DIServiceMenXLSProperties.hm
												.get(DIServiceMenXLSConstants.SERVICE_DESCRIPTION)));
				insertPST
						.setString(
								6,
								(String) hm
										.get(DIServiceMenXLSProperties.hm
												.get(DIServiceMenXLSConstants.AREA_OF_COVERAGE)));
				insertPST.setLong(7, (Long) hm
						.get(DIServiceMenXLSProperties.hm
								.get(DIServiceMenXLSConstants.MOBILE_1)));
				insertPST.setLong(8, (Long) hm
						.get(DIServiceMenXLSProperties.hm
								.get(DIServiceMenXLSConstants.MOBILE_2)));
				insertPST.setString(9, (String) hm
						.get(DIServiceMenXLSProperties.hm
								.get(DIServiceMenXLSConstants.TELEPHONE_1)));

				insertPST.setString(10, (String) hm
						.get(DIServiceMenXLSProperties.hm
								.get(DIServiceMenXLSConstants.VENDOR_ADDRESS)));
				insertPST.setString(11, (String) hm
						.get(DIServiceMenXLSProperties.hm
								.get(DIServiceMenXLSConstants.EMAIL_ID_1)));
				insertPST.setString(12, (String) hm
						.get(DIServiceMenXLSProperties.hm
								.get(DIServiceMenXLSConstants.EMAIL_ID_2)));
				insertPST.setString(13, (String) hm
						.get(DIServiceMenXLSProperties.hm
								.get(DIServiceMenXLSConstants.WEBSITE)));
				insertPST.setString(14, user);

				Object obj = hm.get(DIServiceMenXLSProperties.hm
						.get(DIServiceMenXLSConstants.ENROLLED));
				String value = "";
				if (obj == null) {
					value = "No";
				} else {
					value = obj.toString();
				}
				insertPST.setString(15, value);

				obj = hm.get(DIServiceMenXLSProperties.hm
						.get(DIServiceMenXLSConstants.ACTIVE));
				value = "";
				if (obj == null) {
					value = "Yes";
				} else {
					value = obj.toString();
				}
				insertPST.setString(16, value);

				insertPST.setString(17, "Yes");
				insertPST.setString(18, "No");
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
