package ritchie.backend.action;

import java.sql.SQLException;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import ritchie.backend.dao.DIServiceMenDataEntryDAO;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class DIServiceMenGetServiceMenTypeAction extends ActionSupport {
	private static Logger logger = Logger.getLogger(DIServiceMenGetServiceMenTypeAction.class);
	private String servicementype;
	private TreeMap<String, String> servicementypemap;

	public String getServicementype() {
		return servicementype;
	}

	public void setServicementype(String servicementype) {
		this.servicementype = servicementype;
	}

	public TreeMap<String, String> getServicementypemap() {
		return servicementypemap;
	}

	public void setServicementypemap(TreeMap<String, String> servicementypemap) {
		this.servicementypemap = servicementypemap;
	}

	public String execute() throws SQLException{
		logger.info("in DIServiceMenGetServiceMenTypeAction");
		logger.info("getServicementype():  "+getServicementype());
		String value = (getServicementype().split(","))[0].trim();
		DIServiceMenDataEntryDAO dao = new DIServiceMenDataEntryDAO();
		try{
			setServicementypemap(dao.getServiceMenType(value));
			//logger.info("Service men Type Map:  "+getServicementypemap());
		}catch(SQLException e){
			logger.error(e.getMessage());
			throw e;
		}
		return Action.SUCCESS;
	}
}
