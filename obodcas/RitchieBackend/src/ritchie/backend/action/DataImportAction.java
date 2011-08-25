package ritchie.backend.action;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ritchie.backend.dao.DIXLSDetailsDAO;
import ritchie.backend.utilities.Constants;

public class DataImportAction {
	private static Logger logger = Logger.getLogger(DataImportAction.class); 
	private String disubpage;
	private String disubpagename;
	// This variable is used when disubpage = Constants.VENDOR_LINK_NAME or Constants.SERVICE_CENTER_LINK_NAME or Constants.SERVICE_MEN_LINK_NAME
	private List<Map<String, Object>> fileDetailsList;

	public String getDisubpage() {
		return disubpage;
	}

	public void setDisubpage(String disubpage) {
		this.disubpage = disubpage;
	}

	public String getDisubpagename() {
		return disubpagename;
	}

	public void setDisubpagename(String disubpagename) {
		this.disubpagename = disubpagename;
	}

	public List<Map<String, Object>> getFileDetailsList() {
		return fileDetailsList;
	}

	public void setFileDetailsList(List<Map<String, Object>> fileDetailsList) {
		this.fileDetailsList = fileDetailsList;
	}
	
	public String execute() throws SQLException {
		logger.info("You have clicked: " + getDisubpagename()
				+ " sub page");
		setDisubpage(getDisubpagename());
		
		if (getDisubpagename() != null && (getDisubpagename().equalsIgnoreCase(Constants.VENDOR_LINK_NAME)
				|| getDisubpagename().equalsIgnoreCase(Constants.SERVICE_CENTER_LINK_NAME)
				|| getDisubpagename().equalsIgnoreCase(Constants.SERVICE_MEN_LINK_NAME))) {
			try {
				setFileDetailsList(DIXLSDetailsDAO.getUploadedXLSDetails());
			} catch (SQLException e) {
				throw e;
			}
		}
		
		return "SUCCESS";
	}
}
