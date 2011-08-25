package ritchie.backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class DIMastersDAO {
	private static Logger logger = Logger
	.getLogger(DIMastersDAO.class);
	public static boolean insertArea(String city, String area, String user,
			int approved, int status) throws SQLException {
		logger.info("in insertArea method");
		PreparedStatement pstArea = null;
		Connection con = DBConnection.getConnection();
		try {
			con.setAutoCommit(false);
			pstArea = con
					.prepareStatement("INSERT INTO rit_areamaster (cityname,areaname,createdby,approved,STATUS) VALUES (?,?,?,?,?)");
			pstArea.setString(1, city);
			pstArea.setString(2, area);
			pstArea.setString(3, user);
			pstArea.setInt(4, approved);
			pstArea.setInt(5, 1);
			int result1 = pstArea.executeUpdate();
			logger.info("result1:  " + result1);

			boolean result2 = false;
			if (approved == 0) {
				result2 = insertApprovalItems(con, city + "-" + area
						+ " - master creation approval", "Area Master", user, 0);
				logger.info("result2:  " + result2);
			}

			con.commit();

			if (approved == 1) {
				if (result1 == 1) {
					return true;
				}
			} else if (approved == 0) {
				if (result1 == 1 && result2 == true) {
					return true;
				}
			}
		} catch (SQLException sqle) {
			if (con != null) {
				logger.info("Transaction is being rolled back");
				con.rollback();
				logger.info("Rolled Back");
			}
			logger.error(sqle.getMessage());
			throw sqle;
		} finally {
			logger.info("in finally");
			try {
				if (pstArea != null) {
					pstArea.close();
				}
				con.setAutoCommit(true);
				con.close();
			} catch (SQLException sqle) {
				logger.error(sqle.getMessage());
				throw sqle;
			}
		}
		return false;
	}

	public static boolean insertVendorType(String vendorType, String user,
			int approved, int status) throws SQLException {
		logger.info("in insertVendorType method");
		PreparedStatement pstVendor = null;
		Connection con = DBConnection.getConnection();
		try {
			con.setAutoCommit(false);

			pstVendor = con
					.prepareStatement("INSERT INTO rit_vendortype (vendortype,createdby,approved,STATUS) VALUES (?,?,?,?)");
			pstVendor.setString(1, vendorType);
			pstVendor.setString(2, user);
			pstVendor.setInt(3, approved);
			pstVendor.setInt(4, 1);
			int result1 = pstVendor.executeUpdate();
			logger.info("result1:  " + result1);

			boolean result2 = false;
			if (approved == 0) {
				result2 = insertApprovalItems(con, vendorType
						+ " - master creation approval", "Vendor Type Master",
						user, 0);
				logger.info("result2:  " + result2);
			}

			con.commit();

			if (approved == 1) {
				if (result1 == 1) {
					return true;
				}
			} else if (approved == 0) {
				if (result1 == 1 && result2 == true) {
					return true;
				}
			}
		} catch (SQLException sqle) {
			if (con != null) {
				logger.info("Transaction is being rolled back");
				con.rollback();
				logger.info("Rolled Back");
			}
			logger.error(sqle.getMessage());
			throw sqle;
		} finally {
			logger.info("in finally");
			try {
				if (pstVendor != null) {
					pstVendor.close();
				}
				con.setAutoCommit(true);
				con.close();
			} catch (SQLException sqle) {
				logger.error(sqle.getMessage());
				throw sqle;
			}
		}
		return false;
	}

	public static boolean insertServiceType(String serviceType, String user,
			int approved, int status) throws SQLException {
		logger.info("in insertServiceType method");
		PreparedStatement pstServiceType = null;
		Connection con = DBConnection.getConnection();
		try {
			con.setAutoCommit(false);
			pstServiceType = con
					.prepareStatement("INSERT INTO rit_servicetype (servicetype,createdby,approved,STATUS) VALUES (?,?,?,?)");
			pstServiceType.setString(1, serviceType);
			pstServiceType.setString(2, user);
			pstServiceType.setInt(3, approved);
			pstServiceType.setInt(4, 1);
			int result1 = pstServiceType.executeUpdate();
			logger.info("result1:  " + result1);

			boolean result2 = false;
			if (approved == 0) {
				result2 = insertApprovalItems(con, serviceType
						+ " - master creation approval", "Service Type Master",
						user, 0);
				logger.info("result2:  " + result2);
			}

			con.commit();

			if (approved == 1) {
				if (result1 == 1) {
					return true;
				}
			} else if (approved == 0) {
				if (result1 == 1 && result2 == true) {
					return true;
				}
			}
		} catch (SQLException sqle) {
			if (con != null) {
				logger.info("Transaction is being rolled back");
				con.rollback();
				logger.info("Rolled Back");
			}
			logger.error(sqle.getMessage());
			throw sqle;
		} finally {
			logger.info("in finally");
			try {
				if (pstServiceType != null) {
					pstServiceType.close();
				}
				con.setAutoCommit(true);
				con.close();
			} catch (SQLException sqle) {
				logger.error(sqle.getMessage());
				throw sqle;
			}
		}
		return false;
	}

	public static boolean insertServiceMenType(String serviceMenType,
			String user, int approved, int status) throws SQLException {
		logger.info("in insertServiceMenType method");
		PreparedStatement pstServiceMenType = null;
		Connection con = DBConnection.getConnection();
		try {
			con.setAutoCommit(false);

			pstServiceMenType = con
					.prepareStatement("INSERT INTO rit_servicementype (servicementype,createdby,approved,STATUS) VALUES (?,?,?,?)");
			pstServiceMenType.setString(1, serviceMenType);
			pstServiceMenType.setString(2, user);
			pstServiceMenType.setInt(3, approved);
			pstServiceMenType.setInt(4, 1);
			int result1 = pstServiceMenType.executeUpdate();
			logger.info("result1:  " + result1);

			boolean result2 = false;
			if (approved == 0) {
				result2 = insertApprovalItems(con, serviceMenType
						+ " - master creation approval",
						"Service Men Type Master", user, 0);
				logger.info("result2:  " + result2);
			}

			con.commit();

			if (approved == 1) {
				if (result1 == 1) {
					return true;
				}
			} else if (approved == 0) {
				if (result1 == 1 && result2 == true) {
					return true;
				}
			}
		} catch (SQLException sqle) {
			if (con != null) {
				logger.info("Transaction is being rolled back");
				con.rollback();
				logger.info("Rolled Back");
			}
			logger.error(sqle.getMessage());
			throw sqle;
		} finally {
			logger.info("in finally");
			try {
				if (pstServiceMenType != null) {
					pstServiceMenType.close();
				}
				con.setAutoCommit(true);
				con.close();
			} catch (SQLException sqle) {
				logger.error(sqle.getMessage());
				throw sqle;
			}
		}
		return false;
	}

	public static boolean insertApprovalItems(Connection con, String itemDesc,
			String srcFrom, String user, int status) throws SQLException {
		logger.info("in insertApprovalItems method");
		PreparedStatement pst = null;
		try {
			pst = con
					.prepareStatement("INSERT INTO rit_approvalitems (itemdesc,sourcefrom,createdby,STATUS) VALUES (?,?,?,?)");
			pst.setString(1, itemDesc);
			pst.setString(2, srcFrom);
			pst.setString(3, user);
			pst.setInt(4, status);
			int result = pst.executeUpdate();
			logger.info("insertApprovalItems result:  " + result);
			if (result == 1) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
			throw sqle;
		} finally {
			logger.info("in finally");
			try {
				pst.close();
			} catch (SQLException sqle) {
				logger.error(sqle.getMessage());
				throw sqle;
			}
		}
	}
}
