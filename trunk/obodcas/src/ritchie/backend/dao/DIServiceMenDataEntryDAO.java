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

import ritchie.backend.utilities.DIServiceMenXLSConstants;
import ritchie.backend.utilities.DIVendorsUploadXLSConstants;

public class DIServiceMenDataEntryDAO {
	private static Logger logger = Logger.getLogger(DIServiceMenDataEntryDAO.class);
	
	public TreeMap<String, String> getServiceMenType(String serviceMenType)
			throws SQLException {
		logger.info("inside getServiceMenType method");
		Statement st = null;
		Connection con = DBConnection.getConnection();
		TreeMap<String, String> serviceMenTypeMap = new TreeMap<String, String>();
		ResultSet rs = null;

		try {
			st = con.createStatement();
			rs = st
					.executeQuery("SELECT servicementypeid as SERVICEMENTYPEID,servicementype as SERVICEMENTYPE" +
							" from rit_servicementype where servicementype like '"+serviceMenType+"%'");

			while (rs.next()) {
				serviceMenTypeMap.put(rs.getString("SERVICEMENTYPEID"), rs
						.getString("SERVICEMENTYPE"));
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
		return serviceMenTypeMap;
	}
	
	public String insertSingleServiceMenData(Map<String, String> ServiceMenData,
			int approved, int draft, String user) throws SQLException {
		logger.info("inside insertSingleServiceMenData method");
		PreparedStatement insertPST = null;
		Connection con = DBConnection.getConnection();
		Statement enrollmentIDST = null;
		try {
			String strVendorArea = ServiceMenData
					.get(DIVendorsUploadXLSConstants.VENDOR_AREA);
			logger.info("String vendor area:  " + strVendorArea);
			enrollmentIDST = con.createStatement();
			ResultSet rs = enrollmentIDST
					.executeQuery("SELECT max(SUBSTRING(servicemenid, -6, 6)) as ENROLLMENTID from rit_servicemen");
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
					.prepareStatement("insert into rit_servicemen (servicemenid,servicemenarea," 
							+ "servicementypeid,servicemen,servicedescription,areaofcoverage," 
							+ "mobile1,mobile2,telephone,address,email1,email2,website,mapdata," 
							+ "createdby,status,approved,draft) " 
							+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			insertPST.setLong(1, Long.parseLong(enrollmentNo));
			insertPST.setLong(2, Long.parseLong(strVendorArea));
			insertPST.setLong(3, Long.parseLong(ServiceMenData
					.get(DIServiceMenXLSConstants.SERVICE_MEN_TYPE)));
			insertPST.setString(4, ServiceMenData
					.get(DIServiceMenXLSConstants.SERVICE_MEN_NAME));
			insertPST.setString(5, ServiceMenData
					.get(DIServiceMenXLSConstants.SERVICE_DESCRIPTION));
			insertPST.setString(6, ServiceMenData
					.get(DIServiceMenXLSConstants.AREA_OF_COVERAGE));
			insertPST.setString(7, ServiceMenData
					.get(DIServiceMenXLSConstants.MOBILE_1));
			insertPST.setString(8, ServiceMenData
					.get(DIServiceMenXLSConstants.MOBILE_2));
			insertPST.setString(9, ServiceMenData
					.get(DIServiceMenXLSConstants.TELEPHONE_1));
			insertPST.setString(10, ServiceMenData
					.get(DIServiceMenXLSConstants.VENDOR_ADDRESS));
			insertPST.setString(11, ServiceMenData
					.get(DIServiceMenXLSConstants.EMAIL_ID_1));
			insertPST.setString(12, ServiceMenData
					.get(DIServiceMenXLSConstants.EMAIL_ID_2));
			insertPST.setString(13, ServiceMenData
					.get(DIServiceMenXLSConstants.WEBSITE));
			insertPST.setString(14, ServiceMenData
					.get(DIServiceMenXLSConstants.MAP_DATA));
			insertPST.setString(15, user);
			insertPST.setString(16, ServiceMenData
					.get(DIServiceMenXLSConstants.ACTIVE));

			insertPST.setInt(17, approved);
			insertPST.setInt(18, draft);

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

	public Map<String, String> getServiceMenDetails(String serviceMenID)
			throws SQLException {
		logger.info("inside getServiceMenDetails method");
		Statement st = null;
		Connection con = DBConnection.getConnection();
		Map<String, String> serviceMenDetailsMap = new HashMap<String, String>();
		ResultSet rs = null;

		try {
			st = con.createStatement();
			rs = st.executeQuery("select CONCAT(am.cityname,'-',am.areaname) as AREANAME,"
					+ "smt.servicementype as TYPENAME,sm.servicemenarea," 
					+ "sm.servicementypeid,sm.servicemen,sm.servicedescription,sm.areaofcoverage," 
					+ "sm.mobile1,sm.mobile2,sm.telephone,sm.address,sm.email1,sm.email2,sm.website," 
					+ "sm.mapdata,sm.status " 
					+ "from rit_servicemen sm,rit_areamaster am, rit_servicementype smt " 
					+ "where sm.servicemenid="+serviceMenID+" and sm.servicemenarea=am.areacode " 
					+ "and sm.servicementypeid=smt.servicementypeid");

			while (rs.next()) {
				serviceMenDetailsMap.put(
						DIServiceMenXLSConstants.SERVICE_MEN_AREA, rs
								.getString("servicemenarea"));

				serviceMenDetailsMap.put(DIServiceMenXLSConstants.SERVICE_MEN_AREA_NAME,
						rs.getString("AREANAME"));
				serviceMenDetailsMap.put(DIServiceMenXLSConstants.SERVICE_MEN_TYPE,
						rs.getString("servicementypeid"));
				serviceMenDetailsMap.put(DIServiceMenXLSConstants.SERVICE_MEN_TYPE_NAME,
						rs.getString("TYPENAME"));
				serviceMenDetailsMap.put(DIServiceMenXLSConstants.SERVICE_MEN_NAME,
						rs.getString("servicemen"));
				serviceMenDetailsMap.put(
						DIServiceMenXLSConstants.SERVICE_DESCRIPTION, rs
								.getString("servicedescription"));
				serviceMenDetailsMap.put(
						DIServiceMenXLSConstants.AREA_OF_COVERAGE, rs
								.getString("areaofcoverage"));
				serviceMenDetailsMap.put(
						DIServiceMenXLSConstants.VENDOR_ADDRESS, rs
								.getString("address"));
				serviceMenDetailsMap.put(DIServiceMenXLSConstants.MAP_DATA, rs
						.getString("mapdata"));
				serviceMenDetailsMap.put(DIServiceMenXLSConstants.MOBILE_1, rs
						.getString("mobile1"));
				serviceMenDetailsMap.put(DIServiceMenXLSConstants.MOBILE_2, rs
						.getString("mobile2"));
				serviceMenDetailsMap.put(DIServiceMenXLSConstants.TELEPHONE_1,
						rs.getString("telephone"));
				serviceMenDetailsMap.put(DIServiceMenXLSConstants.EMAIL_ID_1, rs
						.getString("email1"));
				serviceMenDetailsMap.put(DIServiceMenXLSConstants.EMAIL_ID_2, rs
						.getString("email2"));
				serviceMenDetailsMap.put(DIServiceMenXLSConstants.WEBSITE, rs
						.getString("website"));
				serviceMenDetailsMap.put(DIServiceMenXLSConstants.ACTIVE, rs
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
		return serviceMenDetailsMap;
	}

	public String updateSingleServiceMenData(Map<String, String> serviceMenData,
			int approved, int draft, String user,String enrollmentNo) throws SQLException {
		logger.info("inside updateSingleServiceMenData method");
		PreparedStatement updatePST = null;
		Connection con = DBConnection.getConnection();
		try {
			logger.info("enrollmentNo:  " + enrollmentNo);
			updatePST = con
					.prepareStatement("update rit_servicemen set servicemenarea=?,servicementypeid=?,"
										+ "servicemen=?,servicedescription=?,areaofcoverage=?,mobile1=?,mobile2=?,"
										+ "telephone=?,address=?,email1=?,email2=?,website=?,mapdata=?,createdby=?,"
										+ "status=?,approved=?,draft=? "
										+ "where servicemenid=?");
			
			updatePST.setLong(1, Long.parseLong(serviceMenData
					.get(DIVendorsUploadXLSConstants.VENDOR_AREA)));
			updatePST.setLong(2, Long.parseLong(serviceMenData
					.get(DIServiceMenXLSConstants.SERVICE_MEN_TYPE)));
			updatePST.setString(3, serviceMenData
					.get(DIServiceMenXLSConstants.SERVICE_MEN_NAME));
			updatePST.setString(4, serviceMenData
					.get(DIServiceMenXLSConstants.SERVICE_DESCRIPTION));
			updatePST.setString(5, serviceMenData
					.get(DIServiceMenXLSConstants.AREA_OF_COVERAGE));
			updatePST.setString(6, serviceMenData
					.get(DIServiceMenXLSConstants.MOBILE_1));
			updatePST.setString(7, serviceMenData
					.get(DIServiceMenXLSConstants.MOBILE_2));
			updatePST.setString(8, serviceMenData
					.get(DIServiceMenXLSConstants.TELEPHONE_1));
			updatePST.setString(9, serviceMenData
					.get(DIServiceMenXLSConstants.VENDOR_ADDRESS));
			updatePST.setString(10, serviceMenData
					.get(DIServiceMenXLSConstants.EMAIL_ID_1));
			updatePST.setString(11, serviceMenData
					.get(DIServiceMenXLSConstants.EMAIL_ID_2));
			updatePST.setString(12, serviceMenData
					.get(DIServiceMenXLSConstants.WEBSITE));
			updatePST.setString(13, serviceMenData
					.get(DIServiceMenXLSConstants.MAP_DATA));
			updatePST.setString(14, user);
			updatePST.setString(15, serviceMenData
					.get(DIServiceMenXLSConstants.ACTIVE));

			updatePST.setInt(16, approved);
			updatePST.setInt(17, draft);
			
			updatePST.setLong(18, Long.parseLong(enrollmentNo));

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
