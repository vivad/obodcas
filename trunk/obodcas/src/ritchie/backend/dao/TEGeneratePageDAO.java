package ritchie.backend.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ritchie.backend.utilities.TEGeneratePageUtil;

public class TEGeneratePageDAO {
	private static Logger logger = Logger.getLogger(TEGeneratePageDAO.class);
	public int getEnrolledCount() throws SQLException {
		logger.info("in getEnrolledCount ");
		Statement st = null;
		Connection con = DBConnection.getConnection();
		int count = 0;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select enrolledcount() as 'Count'");
			while (rs.next()) {
				count = rs.getInt("Count");
			}
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
			throw sqle;
		} finally {
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
		return count;
	}
	
	public List<Map<String,Object>> getEnrolledRecords(int beginIndex,int noOfRecords) throws SQLException {
		logger.info("in getEnrolledRecords ");
		CallableStatement cst = null;
		Connection con = DBConnection.getConnection();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			cst = con.prepareCall("{call paginatedenrolledrecords(?,?,?,?)}");
			cst.setInt(1, beginIndex);
            cst.setInt(2, noOfRecords);
            cst.registerOutParameter(3, Types.BIGINT);
            cst.registerOutParameter(4, Types.VARCHAR);
            ResultSet rs = cst.executeQuery();
            
            while(rs.next()){
            	Map<String,Object> map = new HashMap<String,Object>();
            	map.put("enrollmentid", rs.getLong(1));
            	
            	Statement st = null;
            	ResultSet rs2 = null;
            	
            	try{
            		st = con.createStatement();
                	rs2 = st.executeQuery("select pagecreated 'PAGESTATUS' from rit_vendorsystem where vendorid="+rs.getLong(1));
                	
                	int status = 0;
                	
                	while(rs2.next()){
                		status = rs2.getInt("PAGESTATUS");
                	}
                	
                	if(status == 1){
                		map.put("pagecreatedstatus", "Page Generated");
                	}else if(status == -1){
                		map.put("pagecreatedstatus", "Failed");
                	}else if(status == 0){
                		map.put("pagecreatedstatus", "Page Not Generated");
                	}
                	
                	logger.info("pagecreatedstatus:  "+map.get("pagecreatedstatus"));
            	}catch(SQLException e){
            		logger.error("Error in getting page generated status");
            		logger.error(e.getMessage());
        			throw e;
            	}finally {
        			try {
        				if (rs2 != null) {
        					rs2.close();
        				}
        				if (st != null) {
        					st.close();
        				}
        			} catch (SQLException e) {
        				logger.error(e.getMessage());
        				throw e;
        			}
        		}
            	
            	map.put("category", rs.getString(2));
            	if(map.get("category").toString().equalsIgnoreCase("Vendor")){
            		map.put("uniqueenrollmentid", "V"+map.get("enrollmentid").toString());
            	}else if(map.get("category").toString().equalsIgnoreCase("ServiceCenter")){
            		map.put("uniqueenrollmentid", "SC"+map.get("enrollmentid").toString());
            	}else if(map.get("category").toString().equalsIgnoreCase("ServiceMen")){
            		map.put("uniqueenrollmentid", "SM"+map.get("enrollmentid").toString());
            	}
            	list.add(map);
            }
            
            
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
			throw sqle;
		} finally {
			try {
				if (cst != null) {
					cst.close();
				}
				con.close();
			} catch (SQLException sqle) {
				logger.error(sqle.getMessage());
				throw sqle;
			}
		}
		return list;
	}
	
	public List<Map<String,Object>> getUserPatternAssignedValue(String templateNo,String enrollmentNo,String category) throws SQLException {
		logger.info("in getUserPatternAssignedValue ");
		logger.info("templateNo: "+templateNo);
		logger.info("enrollmentNo: "+enrollmentNo);
		logger.info("category: "+category);
		
		PreparedStatement pst = null;
		Statement st = null;
		Connection con = DBConnection.getConnection();
		List<Map<String,Object>> patternList = null;
		try {
			pst = con.prepareStatement("select pattern as PATTERNNAME,tablename TABLENAME,columnname COLUMNNAME from rit_assignedpatterns where templateno=?");
			pst.setString(1, templateNo);
            
            ResultSet rs = pst.executeQuery();
            patternList = new ArrayList<Map<String,Object>>();
            while(rs.next()){
            	logger.info("find pattern");
            	Map<String,Object> map = new HashMap<String,Object>();
            	map.put("PATTERNNAME",rs.getString("PATTERNNAME"));
            	
            	StringBuffer columnValueQ = new StringBuffer("select ");
            	columnValueQ.append(rs.getString("COLUMNNAME"));
            	columnValueQ.append(" from ");
            	columnValueQ.append(rs.getString("TABLENAME"));
            	columnValueQ.append(" where ");
            	columnValueQ.append(TEGeneratePageUtil.decideColumnName(category));
            	columnValueQ.append("=");
            	columnValueQ.append(enrollmentNo);
            	
            	logger.info("columnValueQ:  "+columnValueQ);
            	st = con.createStatement();
            	ResultSet cValue = st.executeQuery(columnValueQ.toString());
            	
            	ResultSetMetaData rmd = cValue.getMetaData();
            	logger.info("Column Count:  "+rmd.getColumnCount());
            	logger.info("Column Type Name:  "+rmd.getColumnTypeName(rmd.getColumnCount()));
            	//logger.info("Column Value: "+TEGeneratePageUtil.getColumnValue(cValue,rs.getString("COLUMNNAME")));
            	//logger.info(rs.getString("COLUMNNAME")+" : "+TEGeneratePageUtil.getColumnValue(cValue,rs.getString("COLUMNNAME")));
            	
            	map.put("PATTERNVALUE",TEGeneratePageUtil.getColumnValue(cValue,rs.getString("COLUMNNAME")).get("ColumnValue"));
            	patternList.add(map);
            }
            logger.info("patternList:  "+patternList);
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
		return patternList;
	}
	
	/*public String getUserPatternAssignedValue(String templateNo,String enrollmentNo,String category) throws SQLException {
		logger.info("in getUserPatternAssignedValue ");
		PreparedStatement pst = null;
		Statement st = null;
		Connection con = DBConnection.getConnection();
		try {
			pst = con.prepareStatement("select pattern as PATTERNNAME,tablename TABLENAME,columnname COLUMNNAME from rit_assignedpatterns where templateno=?");
			pst.setString(1, templateNo);
            
            ResultSet rs = pst.executeQuery();
            List<Map<String,String>> patternList = new ArrayList<Map<String,String>>();
            while(rs.next()){
            	Map<String,String> map = new HashMap<String,String>();
            	map.put("PATTERNNAME",rs.getString("PATTERNNAME"));
            	
            	StringBuffer columnValueQ = new StringBuffer("select ");
            	columnValueQ.append(rs.getString("COLUMNNAME"));
            	columnValueQ.append(" from ");
            	columnValueQ.append(rs.getString("TABLENAME"));
            	columnValueQ.append(" where ");
            	columnValueQ.append(TEGeneratePageUtil.decideColumnName(category));
            	columnValueQ.append("=");
            	columnValueQ.append(enrollmentNo);
            	
            	st = con.createStatement();
            	ResultSet cValue = st.executeQuery(columnValueQ.toString());
            	
            	while(cValue.next()){
            		cValue.
            	}
            	
            	
            	
            	map.put("TABLENAME",rs.getString("TABLENAME"));
            	map.put("COLUMNNAME",rs.getString("COLUMNNAME"));
            	patternList.add(map);
            }
            
            for(int i=0;i<patternList.size();i++){
            	
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
		return templateNo;
	}*/
	
	public String getTemplateNo(String category,String templateStatus) throws SQLException {
		logger.info("in getTemplateNo ");
		logger.info("category: "+category);
		logger.info("templateStatus: "+templateStatus);
		PreparedStatement pst = null;
		Connection con = DBConnection.getConnection();
		String templateNo = null;
		try {
			pst = con.prepareStatement("select templateno as TEMPLATENO from rit_templatedtl where templatesource=? and templatestatus=? order by uploadeddate DESC");
			pst.setString(1, category);
			pst.setString(2, templateStatus);
            
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()){
            	templateNo = rs.getString("TEMPLATENO");
            	break;
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
		return templateNo;
	}
	
	public boolean insertPageEntry(Long id,int pageStatus,String user) throws SQLException {
		logger.info("in insertPageEntry ");
		logger.info("id: "+id);
		logger.info("pageStatus: "+pageStatus);
		logger.info("user: "+user);
		PreparedStatement pst = null;
		Connection con = DBConnection.getConnection();
		boolean pageEntryStatus = false;
		try {
			pst = con.prepareStatement("insert into rit_vendorsystem (vendorid,pagecreated,pageupdatedby) values (?,?,?)");
			pst.setLong(1, id);
			pst.setInt(2, pageStatus);
			pst.setString(3, user);
            
            int result = pst.executeUpdate();
            
            if(result == 1){
            	pageEntryStatus = true;
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
		return pageEntryStatus;
	}
	
	public int viewPage(Long enrollmentid) throws SQLException {
		logger.info("in viewPage ");
		Statement st = null;
		Connection con = DBConnection.getConnection();
		ResultSet rs = null;
		int status = 0;
    	try{
    		st = con.createStatement();
    		rs = st.executeQuery("select pagecreated 'PAGESTATUS' from rit_vendorsystem where vendorid="+enrollmentid);
        	
        	while(rs.next()){
        		status = rs.getInt("PAGESTATUS");
        		break;
        	}
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
			throw sqle;
		} finally {
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
		return status;
	}
}
