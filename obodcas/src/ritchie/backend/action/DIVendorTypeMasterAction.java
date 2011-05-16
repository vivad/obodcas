package ritchie.backend.action;

import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import ritchie.backend.dao.DIMastersDAO;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DIVendorTypeMasterAction extends ActionSupport implements SessionAware {
	private static Logger logger = Logger
	.getLogger(DIVendorTypeMasterAction.class);
	private String vendorType;
	private String vendorTypeButton;
	private String actionName;
	private Map map;
	
	public String getVendorType() {
		return vendorType;
	}

	public void setVendorType(String vendorType) {
		this.vendorType = vendorType;
	}

	public String getVendorTypeButton() {
		return vendorTypeButton;
	}

	public void setVendorTypeButton(String vendorTypeButton) {
		this.vendorTypeButton = vendorTypeButton;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public void setSession(Map map) {
		this.map = map;
	}

	public Map getSession() {
		return map;
	}

	/*public void validate() {
		if (getVendorType().trim().length() == 0) {
			addFieldError("vendorType", "Vendor Type is required");
		}
	}*/

	public String execute() throws SQLException {
		logger.info("in DIVendorTypeMasterAction");
		setActionName(ActionContext.getContext().getName());
		
		if (getVendorType().trim().length() == 0) {
			addActionError("Vendor Type is required");
			return ERROR;
		}
		
		int approved = 0;
		if (getVendorTypeButton().equalsIgnoreCase("Save")) {
			approved = 1;
		} else if (getVendorTypeButton().equalsIgnoreCase("Save & Send for Approval")) {
			approved = 0;
		}
		try {
			boolean status = DIMastersDAO.insertVendorType(getVendorType(),
					(String) getSession().get("username"), approved,
					1);
			if (status == true) {
				addActionMessage("Vendor Type is saved successfully ");
			} else {
				addActionError("Vendor Type is not saved");
			}
		} catch (SQLException sqle) {
			throw sqle;
		}
		return SUCCESS;
	}
}
