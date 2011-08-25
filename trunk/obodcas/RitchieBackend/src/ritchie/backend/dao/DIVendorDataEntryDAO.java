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

import ritchie.backend.utilities.DIVendorsUploadXLSConstants;

public class DIVendorDataEntryDAO {
	private static Logger logger = Logger.getLogger(DIVendorDataEntryDAO.class);
	public TreeMap<String, String> getVendorArea(String vendorArea)
			throws SQLException {
		logger.info("inside getVendorArea method");
		Statement st = null;
		Connection con = DBConnection.getConnection();
		TreeMap<String, String> area = new TreeMap<String, String>();
		ResultSet rs = null;

		try {
			st = con.createStatement();
			
			rs = st
			.executeQuery("SELECT AREACODE,AREANAME from rit_areamasterview where AREANAME like '"
					+ vendorArea + "%' order by AREANAME");

			while (rs.next()) {
				area.put(rs.getString("AREACODE"), rs.getString("AREANAME"));
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
		return area;
	}

	public TreeMap<String, String> getVendorType(String vendorType)
			throws SQLException {
		logger.info("inside getVendorType method");
		Statement st = null;
		Connection con = DBConnection.getConnection();
		TreeMap<String, String> vendorTypeMap = new TreeMap<String, String>();
		ResultSet rs = null;

		try {
			st = con.createStatement();
			rs = st
					.executeQuery("SELECT vendortypeid as VENDORTYPEID,vendortype as VENDORTYPE from rit_vendortype where vendortype like '"
							+ vendorType + "%'");

			while (rs.next()) {
				vendorTypeMap.put(rs.getString("VENDORTYPEID"), rs
						.getString("VENDORTYPE"));
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
		return vendorTypeMap;
	}
	
	public String insertSingleVendorData(Map<String, String> vendorData,
			int approved, int draft, String user) throws SQLException {
		logger.info("inside insertSingleVendorData method");
		PreparedStatement insertPST = null;
		Connection con = DBConnection.getConnection();
		Statement enrollmentIDST = null;
		try {
			String strVendorArea = vendorData
					.get(DIVendorsUploadXLSConstants.VENDOR_AREA);
			logger.info("String vendor area:  " + strVendorArea);
			enrollmentIDST = con.createStatement();
			ResultSet rs = enrollmentIDST
					.executeQuery("SELECT max(SUBSTRING(vendorid, -6, 6)) as ENROLLMENTID from rit_vendors");
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
					.prepareStatement("INSERT INTO rit_vendors "
							+ "(vendorid,vendorarea,vendortype,vendorname,vendordescription,"
							+ "contactperson1,contactperson2,telephone1,telephone2,telephone3,"
							+ "mobile1,mobile2,mobile3,fax,address,email1,email2,email3,website,"
							+ "mapdata,createdfrom,createdby,enrolled,STATUS,approved,draft) "
							+ "VALUES "
							+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,NULL,?,?,?,'-',?,?,'Backoffice',?,?,?,?,?)");

			insertPST.setLong(1, Long.parseLong(enrollmentNo));
			insertPST.setLong(2, Long.parseLong(strVendorArea));
			insertPST.setLong(3, Long.parseLong(vendorData
					.get(DIVendorsUploadXLSConstants.VENDOR_TYPE)));
			insertPST.setString(4, vendorData
					.get(DIVendorsUploadXLSConstants.VENDOR_NAME));
			insertPST.setString(5, vendorData
					.get(DIVendorsUploadXLSConstants.VENDOR_DESCRIPTION));
			insertPST.setString(6, vendorData
					.get(DIVendorsUploadXLSConstants.CONTACT_PERSON_1));
			insertPST.setString(7, vendorData
					.get(DIVendorsUploadXLSConstants.CONTACT_PERSON_2));
			insertPST.setString(8, vendorData
					.get(DIVendorsUploadXLSConstants.TELEPHONE_1));
			insertPST.setString(9, vendorData
					.get(DIVendorsUploadXLSConstants.TELEPHONE_2));
			insertPST.setString(10, vendorData
					.get(DIVendorsUploadXLSConstants.TELEPHONE_3));
			insertPST.setString(11, vendorData
					.get(DIVendorsUploadXLSConstants.MOBILE_1));
			insertPST.setString(12, vendorData
					.get(DIVendorsUploadXLSConstants.MOBILE_2));
			insertPST.setString(13, vendorData
					.get(DIVendorsUploadXLSConstants.MOBILE_3));
			insertPST.setString(14, vendorData
					.get(DIVendorsUploadXLSConstants.VENDOR_ADDRESS));
			insertPST.setString(15, vendorData
					.get(DIVendorsUploadXLSConstants.EMAIL_ID_1));
			insertPST.setString(16, vendorData
					.get(DIVendorsUploadXLSConstants.EMAIL_ID_2));
			insertPST.setString(17, vendorData
					.get(DIVendorsUploadXLSConstants.WEBSITE));
			insertPST.setString(18, vendorData
					.get(DIVendorsUploadXLSConstants.MAP_DATA));
			insertPST.setString(19, user);
			insertPST.setString(20, vendorData
					.get(DIVendorsUploadXLSConstants.ENROLLED));
			insertPST.setString(21, vendorData
					.get(DIVendorsUploadXLSConstants.ACTIVE));

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

	public Map<String, String> getVendorDetails(String vendorID)
			throws SQLException {
		logger.info("inside getVendorDetails method");
		Statement st = null;
		Connection con = DBConnection.getConnection();
		Map<String, String> vendorDetailsMap = new HashMap<String, String>();
		ResultSet rs = null;

		try {
			st = con.createStatement();
			rs = st
					.executeQuery("select rv.vendorid,rv.vendorarea as AREACODE,rv.vendortype as VENDORTYPECODE,rv.vendorname,rv.vendordescription,rv.contactperson1,"
							+ "rv.contactperson2,rv.telephone1,rv.telephone2,rv.telephone3,rv.mobile1,rv.mobile2,rv.mobile3,rv.address,"
							+ "rv.email1,rv.email2,rv.website,rv.mapdata,rv.createdfrom,rv.enrolled,rv.STATUS,"
							+ "CONCAT(ram.cityname,'-',ram.areaname) as AREANAME,rvt.vendortype as VENDORTYPENAME "
							+ "from rit_vendors rv,rit_areamaster ram,rit_vendortype rvt "
							+ "where rv.vendorid="+vendorID+" and rv.vendorarea=ram.areacode and rv.vendortype=vendortypeid");

			while (rs.next()) {
				vendorDetailsMap.put(
						DIVendorsUploadXLSConstants.VENDOR_AREA_CODE, rs
								.getString("AREACODE"));

				vendorDetailsMap.put(DIVendorsUploadXLSConstants.VENDOR_AREA,
						rs.getString("AREANAME"));
				vendorDetailsMap.put(DIVendorsUploadXLSConstants.VENDOR_TYPE_CODE,
						rs.getString("VENDORTYPECODE"));
				vendorDetailsMap.put(DIVendorsUploadXLSConstants.VENDOR_TYPE,
						rs.getString("VENDORTYPENAME"));
				vendorDetailsMap.put(DIVendorsUploadXLSConstants.VENDOR_NAME,
						rs.getString("vendorname"));
				vendorDetailsMap.put(
						DIVendorsUploadXLSConstants.VENDOR_DESCRIPTION, rs
								.getString("vendordescription"));
				vendorDetailsMap.put(
						DIVendorsUploadXLSConstants.VENDOR_ADDRESS, rs
								.getString("address"));
				vendorDetailsMap.put(DIVendorsUploadXLSConstants.MAP_DATA, rs
						.getString("mapdata"));
				vendorDetailsMap.put(DIVendorsUploadXLSConstants.MOBILE_1, rs
						.getString("mobile1"));
				vendorDetailsMap.put(DIVendorsUploadXLSConstants.MOBILE_2, rs
						.getString("mobile2"));
				vendorDetailsMap.put(DIVendorsUploadXLSConstants.MOBILE_3, rs
						.getString("mobile3"));
				vendorDetailsMap.put(
						DIVendorsUploadXLSConstants.CONTACT_PERSON_1, rs
								.getString("contactperson1"));
				vendorDetailsMap.put(
						DIVendorsUploadXLSConstants.CONTACT_PERSON_2, rs
								.getString("contactperson2"));
				vendorDetailsMap.put(DIVendorsUploadXLSConstants.TELEPHONE_1,
						rs.getString("telephone1"));
				vendorDetailsMap.put(DIVendorsUploadXLSConstants.TELEPHONE_2,
						rs.getString("telephone2"));
				vendorDetailsMap.put(DIVendorsUploadXLSConstants.TELEPHONE_3,
						rs.getString("telephone3"));
				vendorDetailsMap.put(DIVendorsUploadXLSConstants.EMAIL_ID_1, rs
						.getString("email1"));
				vendorDetailsMap.put(DIVendorsUploadXLSConstants.EMAIL_ID_2, rs
						.getString("email2"));
				vendorDetailsMap.put(DIVendorsUploadXLSConstants.WEBSITE, rs
						.getString("website"));
				vendorDetailsMap.put(DIVendorsUploadXLSConstants.ENROLLED, rs
						.getString("enrolled"));
				vendorDetailsMap.put(DIVendorsUploadXLSConstants.ACTIVE, rs
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
		return vendorDetailsMap;
	}

	public String updateSingleVendorData(Map<String, String> vendorData,
			int approved, int draft, String user,String enrollmentNo) throws SQLException {
		logger.info("inside updateSingleVendorData method");
		PreparedStatement updatePST = null;
		Connection con = DBConnection.getConnection();
		try {
			logger.info("enrollmentNo:  " + enrollmentNo);
			updatePST = con
					.prepareStatement("update rit_vendors set vendorarea=?,vendortype=?,vendorname=?,vendordescription=?,"
									+ "contactperson1=?,contactperson2=?,telephone1=?,telephone2=?,telephone3=?,"
									+ "mobile1=?,mobile2=?,mobile3=?,address=?,email1=?,email2=?,website=?,"
									+ "mapdata=?,createdby=?,enrolled=?,STATUS=?,approved=?,draft=? "
									+ "where vendorid=?");

			
			updatePST.setLong(1, Long.parseLong(vendorData
					.get(DIVendorsUploadXLSConstants.VENDOR_AREA)));
			updatePST.setLong(2, Long.parseLong(vendorData
					.get(DIVendorsUploadXLSConstants.VENDOR_TYPE)));
			updatePST.setString(3, vendorData
					.get(DIVendorsUploadXLSConstants.VENDOR_NAME));
			updatePST.setString(4, vendorData
					.get(DIVendorsUploadXLSConstants.VENDOR_DESCRIPTION));
			updatePST.setString(5, vendorData
					.get(DIVendorsUploadXLSConstants.CONTACT_PERSON_1));
			updatePST.setString(6, vendorData
					.get(DIVendorsUploadXLSConstants.CONTACT_PERSON_2));
			updatePST.setString(7, vendorData
					.get(DIVendorsUploadXLSConstants.TELEPHONE_1));
			updatePST.setString(8, vendorData
					.get(DIVendorsUploadXLSConstants.TELEPHONE_2));
			updatePST.setString(9, vendorData
					.get(DIVendorsUploadXLSConstants.TELEPHONE_3));
			updatePST.setString(10, vendorData
					.get(DIVendorsUploadXLSConstants.MOBILE_1));
			updatePST.setString(11, vendorData
					.get(DIVendorsUploadXLSConstants.MOBILE_2));
			updatePST.setString(12, vendorData
					.get(DIVendorsUploadXLSConstants.MOBILE_3));
			updatePST.setString(13, vendorData
					.get(DIVendorsUploadXLSConstants.VENDOR_ADDRESS));
			updatePST.setString(14, vendorData
					.get(DIVendorsUploadXLSConstants.EMAIL_ID_1));
			updatePST.setString(15, vendorData
					.get(DIVendorsUploadXLSConstants.EMAIL_ID_2));
			updatePST.setString(16, vendorData
					.get(DIVendorsUploadXLSConstants.WEBSITE));
			updatePST.setString(17, vendorData
					.get(DIVendorsUploadXLSConstants.MAP_DATA));
			updatePST.setString(18, user);
			updatePST.setString(19, vendorData
					.get(DIVendorsUploadXLSConstants.ENROLLED));
			updatePST.setString(20, vendorData
					.get(DIVendorsUploadXLSConstants.ACTIVE));

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
