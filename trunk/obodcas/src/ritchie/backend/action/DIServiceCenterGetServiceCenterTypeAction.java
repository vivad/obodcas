package ritchie.backend.action;

import java.sql.SQLException;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import ritchie.backend.dao.DIServiceCenterDataEntryDAO;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class DIServiceCenterGetServiceCenterTypeAction extends ActionSupport {
	private static Logger logger = Logger.getLogger(DIServiceCenterGetServiceCenterTypeAction.class);
	
	private String servicecentertype;
	private TreeMap<String, String> servicecentertypemap;

	public String getServicecentertype() {
		return servicecentertype;
	}

	public void setServicecentertype(String servicecentertype) {
		this.servicecentertype = servicecentertype;
	}

	public TreeMap<String, String> getServicecentertypemap() {
		return servicecentertypemap;
	}

	public void setServicecentertypemap(TreeMap<String, String> servicecentertypemap) {
		this.servicecentertypemap = servicecentertypemap;
	}

	public String execute() throws SQLException{
		logger.info("in DIServiceCenterGetServiceCenterTypeAction");
		logger.info("getServicecentertype():  "+getServicecentertype());
		String value = (getServicecentertype().split(","))[0].trim();
		DIServiceCenterDataEntryDAO dao = new DIServiceCenterDataEntryDAO();
		try{
			setServicecentertypemap(dao.getServiceCenterType(value));
			//logger.info("Service Center Type Map:  "+getServicecentertypemap());
		}catch(SQLException e){
			logger.error(e.getMessage());
			throw e;
		}
		return Action.SUCCESS;
	}
}
