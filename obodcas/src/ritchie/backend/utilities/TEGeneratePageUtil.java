package ritchie.backend.utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class TEGeneratePageUtil {
	private static Logger logger = Logger.getLogger(TEGeneratePageUtil.class);
	public static String decideColumnName(String category){
		String columnName = null;
		
		if(category.equalsIgnoreCase(Constants.VENDOR_LINK_NAME)){
			columnName = "vendorid";
		}else if(category.equalsIgnoreCase(Constants.SERVICE_CENTER_LINK_NAME)){
			columnName = "servicecenterid";
		}else if(category.equalsIgnoreCase(Constants.SERVICE_MEN_LINK_NAME)){
			columnName = "servicemenid";
		}
		
		return columnName;
	}
	
	public static Map<String,Object> getColumnValue(ResultSet rs,String columnName) throws SQLException{
		Map<String,Object> map = null;
		try{
			rs.next();
			ResultSetMetaData rsMetaData = rs.getMetaData();
	    	//System.out.println("Column Count:  "+rsMetaData.getColumnCount());
	    	//System.out.println("Column Type Name:  "+rsMetaData.getColumnTypeName(rsMetaData.getColumnCount()));
	    	String retrivedMD = rsMetaData.getColumnTypeName(rsMetaData.getColumnCount());
	    	map = new HashMap<String,Object>();
	    	if(retrivedMD.equalsIgnoreCase("VARCHAR")){
	    		map.put("ColumnValue", rs.getString(columnName));
	    	}else if(retrivedMD.equalsIgnoreCase("BIGINT")){
	    		map.put("ColumnValue", rs.getLong(columnName));
	    	}else if(retrivedMD.equalsIgnoreCase("INT")){
	    		map.put("ColumnValue", rs.getInt(columnName));
	    	}else{
	    		System.out.println("handle this data type");
	    	}
		}catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
			throw sqle;
		} 
		
		return map;
	}
	
	public static void writeFile(String contentToBeWrittern,String dest) throws IOException{
		logger.info("in writeFile method");
		logger.info("Target Path: "+dest);
		OutputStream out = new FileOutputStream(dest);

		byte[] buf = contentToBeWrittern.getBytes();
		try{
			out.write(buf);
			out.close();
		}catch(IOException e){
			logger.error(e);
			throw e;
		}
	}
}
