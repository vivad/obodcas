package ritchie.backend.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import ritchie.backend.dao.DIVendorDataEntryDAO;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class DIVendorGetAreaAction extends ActionSupport {
	private static Logger logger = Logger
			.getLogger(DIVendorGetAreaAction.class);
	private Map<String, String> states;
	private String vendorarea;
	private TreeMap<String, String> vendorareamap;
	private String searchbutton;
	private String vendoridforsearch;

	public Map<String, String> getStates() {
		return states;
	}

	public void setStates(Map<String, String> states) {
		this.states = populateStates();
	}

	public String getVendorarea() {
		return vendorarea;
	}

	public void setVendorarea(String vendorarea) {
		this.vendorarea = vendorarea;
	}

	public Map<String, String> getVendorareamap() {
		return vendorareamap;
	}

	public void setVendorareamap(TreeMap<String, String> vendorareamap) {
		this.vendorareamap = vendorareamap;
	}

	public void setSearchbutton(String searchbutton) {
		this.searchbutton = searchbutton;
	}

	public String getVendoridforsearch() {
		return vendoridforsearch;
	}

	public void setVendoridforsearch(String vendoridforsearch) {
		this.vendoridforsearch = vendoridforsearch;
	}

	public String getSearchbutton() {
		return searchbutton;
	}

	public String execute() throws SQLException {
		logger.info("in DIVendorGetAreaAction");
		logger.info("getVendorarea():  " + getVendorarea());
		String value = (getVendorarea().split(","))[0].trim();
		DIVendorDataEntryDAO dao = new DIVendorDataEntryDAO();
		try {
			setVendorareamap(dao.getVendorArea((getVendorarea().split(","))[0]
					.trim()));
			// logger.info("Area Map:  "+getVendorareamap());
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw e;
		}
		// populateStates();

		return Action.SUCCESS;
	}

	private Map<String, String> populateStates() {
		states = new HashMap<String, String>();
		states.put("AL", "Alabama");
		states.put("AK", "Alaska");
		states.put("AS", "American Samoa");
		states.put("AZ", "Arizona");
		states.put("AR", "Arkansas");
		states.put("AE", "Armed Forces Europe");
		return states;
	}
}
