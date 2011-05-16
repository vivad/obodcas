package ritchie.backend.action;

import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import ritchie.backend.dao.DIVendorDataEntryDAO;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class DIVendorGetVendorTypeAction extends ActionSupport {
	private static Logger logger = Logger
	.getLogger(DIVendorGetVendorTypeAction.class);
	private String vendortype;
	private TreeMap<String, String> vendortypemap;

	public String getVendortype() {
		return vendortype;
	}

	public void setVendortype(String vendortype) {
		this.vendortype = vendortype;
	}

	public Map<String, String> getVendortypemap() {
		return vendortypemap;
	}

	public void setVendortypemap(TreeMap<String, String> vendortypemap) {
		this.vendortypemap = vendortypemap;
	}

	public String execute() throws SQLException{
		logger.info("in DIVendorGetVendorTypeAction");
		logger.info("getVendortype():  "+getVendortype());
		String value = (getVendortype().split(","))[0].trim();
		DIVendorDataEntryDAO dao = new DIVendorDataEntryDAO();
		try{
			setVendortypemap(dao.getVendorType(value));
			//logger.info("Vendor Type Map:  "+getVendortypemap());
		}catch(SQLException e){
			throw e;
		}
		return Action.SUCCESS;
	}
}
