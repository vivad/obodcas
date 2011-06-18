package ritchie.backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import ritchie.backend.utilities.DIServiceCentersXLSConstants;
import ritchie.backend.utilities.DIVendorsUploadXLSConstants;

public class DIServiceCenterDataEntryDAO {
	private static Logger logger = Logger.getLogger(DIServiceCenterDataEntryDAO.class);
	public TreeMap<String, String> getServiceCenterType(String serviceCenterType)
			throws SQLException {
		logger.info("inside getServiceCenterType method");
		Statement st = null;
		Connection con = DBConnection.getConnection();
		TreeMap<String, String> serviceCenterTypeMap = new TreeMap<String, String>();
		ResultSet rs = null;

		try {
			st = con.createStatement();
			rs = st
					.executeQuery("SELECT servicetypeid as SERVICECENTERTYPEID,servicetype as SERVICETYPE " +
							"from rit_servicetype where servicetype like '"+serviceCenterType+"%'");

			while (rs.next()) {
				serviceCenterTypeMap.put(rs.getString("SERVICECENTERTYPEID"), rs
						.getString("SERVICETYPE"));
			}
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
			throw sqle;
		} finally {
			logger.info("in finally");
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				con.close();
			} catch (SQLException sqle) {
				logger.error(sqle.getMessage());
				throw sqle;
			}
		}
		return serviceCenterTypeMap;
	}
	
	public String insertSingleServiceCenterData(Map<String, String> ServiceCenterData,
			int approved, int draft, String user) throws SQLException {
		logger.info("inside insertSingleServiceCenterData method");
		PreparedStatement insertPST = null;
		Connection con = DBConnection.getConnection();
		Statement enrollmentIDST = null;
		try {
			String strVendorArea = ServiceCenterData
					.get(DIVendorsUploadXLSConstants.VENDOR_AREA);
			logger.info("String vendor area:  " + strVendorArea);
			enrollmentIDST = con.createStatement();
			ResultSet rs = enrollmentIDST
					.executeQuery("SELECT max(SUBSTRING(servicecenterid, -6, 6)) as ENROLLMENTID from rit_servicecenter");
			String enrollmentNo = null;
			rs.next();
			if (rs.getString("ENROLLMENTID") != null) {
				logger.info("in if");
				String sequenceFormatted = String.format("%06d", (Integer
						.parseInt(rs.getString("ENROLLMENTID")) + 1));
				enrollmentNo = strVendorArea + sequenceFormatted;
			} else {
				logger.info("in else");
				enrollmentNo = strVendorArea + "000001";
			}

			logger.info("enrollmentNo:  " + enrollmentNo);
			insertPST = con
					.prepareStatement("insert into rit_servicecenter (" 
								+ "servicecenterid,serviceCenterarea,servicecentertype," 
								+ "servicecenter,description,contactperson1,contactperson2," 
								+ "telephone1,telephone2,telephone3,mobile1,mobile2,mobile3," 
								+ "address,email1,email2,website,mapdata," 
								+ "createdby,enrolled,status,approved," 
								+ "draft) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			insertPST.setLong(1, Long.parseLong(enrollmentNo));
			insertPST.setLong(2, Long.parseLong(strVendorArea));
			insertPST.setLong(3, Long.parseLong(ServiceCenterData
					.get(DIServiceCentersXLSConstants.SERVICE_CENTER_TYPE)));
			insertPST.setString(4, ServiceCenterData
					.get(DIServiceCentersXLSConstants.SERVICE_CENTER_NAME));
			insertPST.setString(5, ServiceCenterData
					.get(DIServiceCentersXLSConstants.SERVICE_DESCRIPTION));
			insertPST.setString(6, ServiceCenterData
					.get(DIServiceCentersXLSConstants.CONTACT_PERSON_1));
			insertPST.setString(7, ServiceCenterData
					.get(DIServiceCentersXLSConstants.CONTACT_PERSON_2));
			insertPST.setString(8, ServiceCenterData
					.get(DIServiceCentersXLSConstants.TELEPHONE_1));
			insertPST.setString(9, ServiceCenterData
					.get(DIServiceCentersXLSConstants.TELEPHONE_2));
			insertPST.setString(10, ServiceCenterData
					.get(DIServiceCentersXLSConstants.TELEPHONE_3));
			insertPST.setString(11, ServiceCenterData
					.get(DIServiceCentersXLSConstants.MOBILE_1));
			insertPST.setString(12, ServiceCenterData
					.get(DIServiceCentersXLSConstants.MOBILE_2));
			insertPST.setString(13, ServiceCenterData
					.get(DIServiceCentersXLSConstants.MOBILE_3));
			insertPST.setString(14, ServiceCenterData
					.get(DIServiceCentersXLSConstants.ADDRESS));
			insertPST.setString(15, ServiceCenterData
					.get(DIServiceCentersXLSConstants.EMAIL_ID_1));
			insertPST.setString(16, ServiceCenterData
					.get(DIServiceCentersXLSConstants.EMAIL_ID_2));
			insertPST.setString(17, ServiceCenterData
					.get(DIServiceCentersXLSConstants.WEBSITE));
			insertPST.setString(18, ServiceCenterData
					.get(DIServiceCentersXLSConstants.MAP_DATA));
			insertPST.setString(19, user);
			insertPST.setString(20, ServiceCenterData
					.get(DIServiceCentersXLSConstants.ENROLLED));
			insertPST.setString(21, ServiceCenterData
					.get(DIServiceCentersXLSConstants.ACTIVE));

			insertPST.setInt(22, approved);
			insertPST.setInt(23, draft);

			if (insertPST.executeUpdate() == 1) {
				logger.info("success");
				return enrollmentNo;
			} else {
				logger.info("failure");
				return "FAILURE";
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
	}

	public Map<String, String> getServiceCenterDetails(String serviceCenterID)
			throws SQLException {
		logger.info("inside getServiceCenterDetails method");
		Statement st = null;
		Connection con = DBConnection.getConnection();
		Map<String, String> serviceCenterDetailsMap = new HashMap<String, String>();
		ResultSet rs = null;

		try {
			st = con.createStatement();
			rs = st
					.executeQuery("select CONCAT(am.cityname,'-',am.areaname) as AREANAME,sc.serviceCenterarea as AREACODE,"
							+ "st.servicetype as TYPENAME,sc.servicecentertype as TYPECODE,sc.servicecenter,"
							+ "sc.description,sc.contactperson1,sc.contactperson2,sc.telephone1,sc.telephone2,sc.telephone3,"
							+ "sc.mobile1,sc.mobile2,sc.mobile3,sc.address,sc.email1,sc.email2,sc.website,sc.mapdata," 
							+ "sc.enrolled,sc.status from rit_servicecenter sc,rit_areamaster am, rit_servicetype st "
							+ "where sc.servicecenterid="+serviceCenterID+" and sc.serviceCenterarea=am.areacode " 
							+ "and sc.servicecentertype=st.servicetypeid");

			while (rs.next()) {
				serviceCenterDetailsMap.put(
						DIServiceCentersXLSConstants.AREA_CODE, rs
								.getString("AREACODE"));

				serviceCenterDetailsMap.put(DIServiceCentersXLSConstants.AREA_NAME,
						rs.getString("AREANAME"));
				serviceCenterDetailsMap.put(DIServiceCentersXLSConstants.SERVICE_CENTER_TYPE,
						rs.getString("TYPECODE"));
				serviceCenterDetailsMap.put(DIServiceCentersXLSConstants.SERVICE_CENTER_TYPE_NAME,
						rs.getString("TYPENAME"));
				serviceCenterDetailsMap.put(DIServiceCentersXLSConstants.SERVICE_CENTER_NAME,
						rs.getString("servicecenter"));
				serviceCenterDetailsMap.put(
						DIServiceCentersXLSConstants.SERVICE_DESCRIPTION, rs
								.getString("description"));
				serviceCenterDetailsMap.put(
						DIServiceCentersXLSConstants.ADDRESS, rs
								.getString("address"));
				serviceCenterDetailsMap.put(DIServiceCentersXLSConstants.MAP_DATA, rs
						.getString("mapdata"));
				serviceCenterDetailsMap.put(DIServiceCentersXLSConstants.MOBILE_1, rs
						.getString("mobile1"));
				serviceCenterDetailsMap.put(DIServiceCentersXLSConstants.MOBILE_2, rs
						.getString("mobile2"));
				serviceCenterDetailsMap.put(DIServiceCentersXLSConstants.MOBILE_3, rs
						.getString("mobile3"));
				serviceCenterDetailsMap.put(
						DIServiceCentersXLSConstants.CONTACT_PERSON_1, rs
								.getString("contactperson1"));
				serviceCenterDetailsMap.put(
						DIServiceCentersXLSConstants.CONTACT_PERSON_2, rs
								.getString("contactperson2"));
				serviceCenterDetailsMap.put(DIServiceCentersXLSConstants.TELEPHONE_1,
						rs.getString("telephone1"));
				serviceCenterDetailsMap.put(DIServiceCentersXLSConstants.TELEPHONE_2,
						rs.getString("telephone2"));
				serviceCenterDetailsMap.put(DIServiceCentersXLSConstants.TELEPHONE_3,
						rs.getString("telephone3"));
				serviceCenterDetailsMap.put(DIServiceCentersXLSConstants.EMAIL_ID_1, rs
						.getString("email1"));
				serviceCenterDetailsMap.put(DIServiceCentersXLSConstants.EMAIL_ID_2, rs
						.getString("email2"));
				serviceCenterDetailsMap.put(DIServiceCentersXLSConstants.WEBSITE, rs
						.getString("website"));
				serviceCenterDetailsMap.put(DIServiceCentersXLSConstants.ENROLLED, rs
						.getString("enrolled"));
				serviceCenterDetailsMap.put(DIServiceCentersXLSConstants.ACTIVE, rs
						.getString("status"));
			}
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
			throw sqle;
		} finally {
			logger.info("in finally");
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				con.close();
			} catch (SQLException sqle) {
				logger.error(sqle.getMessage());
				throw sqle;
			}
		}
		return serviceCenterDetailsMap;
	}

	public String updateSingleServiceCenterData(Map<String, String> serviceCenterData,
			int approved, int draft, String user,String enrollmentNo) throws SQLException {
		logger.info("inside updateSingleServiceCenterData method");
		PreparedStatement updatePST = null;
		Connection con = DBConnection.getConnection();
		try {
			logger.info("enrollmentNo:  " + enrollmentNo);
			updatePST = con
					.prepareStatement("update rit_servicecenter set serviceCenterarea=?,servicecentertype=?,"
									+ "servicecenter=?,description=?,contactperson1=?,contactperson2=?,telephone1=?,telephone2=?,"
									+ "telephone3=?,mobile1=?,mobile2=?,mobile3=?,address=?,email1=?,email2=?,website=?,mapdata=?,"
									+ "createdby=?,enrolled=?,status=?,approved=?,draft=? where servicecenterid=?");
			
			updatePST.setLong(1, Long.parseLong(serviceCenterData
					.get(DIVendorsUploadXLSConstants.VENDOR_AREA)));
			updatePST.setLong(2, Long.parseLong(serviceCenterData
					.get(DIServiceCentersXLSConstants.SERVICE_CENTER_TYPE)));
			updatePST.setString(3, serviceCenterData
					.get(DIServiceCentersXLSConstants.SERVICE_CENTER_NAME));
			updatePST.setString(4, serviceCenterData
					.get(DIServiceCentersXLSConstants.SERVICE_DESCRIPTION));
			updatePST.setString(5, serviceCenterData
					.get(DIServiceCentersXLSConstants.CONTACT_PERSON_1));
			updatePST.setString(6, serviceCenterData
					.get(DIServiceCentersXLSConstants.CONTACT_PERSON_2));
			updatePST.setString(7, serviceCenterData
					.get(DIServiceCentersXLSConstants.TELEPHONE_1));
			updatePST.setString(8, serviceCenterData
					.get(DIServiceCentersXLSConstants.TELEPHONE_2));
			updatePST.setString(9, serviceCenterData
					.get(DIServiceCentersXLSConstants.TELEPHONE_3));
			updatePST.setString(10, serviceCenterData
					.get(DIServiceCentersXLSConstants.MOBILE_1));
			updatePST.setString(11, serviceCenterData
					.get(DIServiceCentersXLSConstants.MOBILE_2));
			updatePST.setString(12, serviceCenterData
					.get(DIServiceCentersXLSConstants.MOBILE_3));
			updatePST.setString(13, serviceCenterData
					.get(DIServiceCentersXLSConstants.ADDRESS));
			updatePST.setString(14, serviceCenterData
					.get(DIServiceCentersXLSConstants.EMAIL_ID_1));
			updatePST.setString(15, serviceCenterData
					.get(DIServiceCentersXLSConstants.EMAIL_ID_2));
			updatePST.setString(16, serviceCenterData
					.get(DIServiceCentersXLSConstants.WEBSITE));
			updatePST.setString(17, serviceCenterData
					.get(DIServiceCentersXLSConstants.MAP_DATA));
			updatePST.setString(18, user);
			updatePST.setString(19, serviceCenterData
					.get(DIServiceCentersXLSConstants.ENROLLED));
			updatePST.setString(20, serviceCenterData
					.get(DIServiceCentersXLSConstants.ACTIVE));

			updatePST.setInt(21, approved);
			updatePST.setInt(22, draft);
			
			updatePST.setLong(23, Long.parseLong(enrollmentNo));

			if (updatePST.executeUpdate() == 1) {
				logger.info("success");
				return enrollmentNo;
			} else {
				logger.info("failure");
				return "FAILURE";
			}

		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
			throw sqle;
		} finally {
			logger.info("in finally");
			try {
				if (updatePST != null) {
					updatePST.close();
				}
				con.close();
			} catch (SQLException sqle) {
				logger.error(sqle.getMessage());
				throw sqle;
			}
		}
	}
}
