package ritchie.backend.action;

import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import ritchie.backend.dao.DIMastersDAO;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DIServiceTypeMasterAction extends ActionSupport implements SessionAware {
	private static Logger logger = Logger.getLogger(DIServiceTypeMasterAction.class);
	private String serviceType;
	private String serviceTypeButton;
	private String actionName;
	private Map map;
	
	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceTypeButton() {
		return serviceTypeButton;
	}

	public void setServiceTypeButton(String serviceTypeButton) {
		this.serviceTypeButton = serviceTypeButton;
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
		if (getServiceType().trim().length() == 0) {
			addFieldError("serviceType", "Service Type is required");
		}
	}*/

	public String execute() throws SQLException {
		setActionName(ActionContext.getContext().getName());
		
		if (getServiceType().trim().length() == 0) {
			addActionError("Service Type is required");
			return ERROR;
		}
		
		int approved = 0;
		if (getServiceTypeButton().equalsIgnoreCase("Save")) {
			approved = 1;
		} else if (getServiceTypeButton().equalsIgnoreCase("Save & Send for Approval")) {
			approved = 0;
		}
		try {
			boolean status = DIMastersDAO.insertServiceType(getServiceType(),
					(String) getSession().get("username"), approved,
					1);
			if (status == true) {
				addActionMessage("Service Type is saved successfully ");
			} else {
				addActionError("Service Type is not saved");
			}
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
			throw sqle;
		}
		return SUCCESS;
	}
}
