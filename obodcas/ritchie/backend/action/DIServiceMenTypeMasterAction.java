package ritchie.backend.action;

import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import ritchie.backend.dao.DIMastersDAO;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DIServiceMenTypeMasterAction extends ActionSupport implements SessionAware {
	private static Logger logger = Logger.getLogger(DIServiceMenTypeMasterAction.class);
	private String serviceMenType;
	private String serviceMenTypeButton;
	private String actionName;
	private Map map;
	
	public String getServiceMenType() {
		return serviceMenType;
	}

	public void setServiceMenType(String serviceMenType) {
		this.serviceMenType = serviceMenType;
	}

	public String getServiceMenTypeButton() {
		return serviceMenTypeButton;
	}

	public void setServiceMenTypeButton(String serviceMenTypeButton) {
		this.serviceMenTypeButton = serviceMenTypeButton;
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
		if (getServiceMenType().trim().length() == 0) {
			addFieldError("serviceMenType", "Service Men Type is required");
		}
	}*/

	public String execute() throws SQLException {
		setActionName(ActionContext.getContext().getName());
		
		if (getServiceMenType().trim().length() == 0) {
			addActionError("Service Men Type is required");
			return ERROR;
		}
		
		int approved = 0;
		if (getServiceMenTypeButton().equalsIgnoreCase("Save")) {
			approved = 1;
		} else if (getServiceMenTypeButton().equalsIgnoreCase("Save & Send for Approval")) {
			approved = 0;
		}
		try {
			boolean status = DIMastersDAO.insertServiceMenType(getServiceMenType(),
					(String) getSession().get("username"), approved,
					1);
			if (status == true) {
				addActionMessage("Service Men Type is saved successfully ");
			} else {
				addActionError("Service Men Type is not saved");
			}
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
			throw sqle;
		}
		return SUCCESS;
	}
}
