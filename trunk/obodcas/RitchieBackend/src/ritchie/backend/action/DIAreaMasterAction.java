package ritchie.backend.action;

import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import ritchie.backend.dao.DIMastersDAO;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DIAreaMasterAction extends ActionSupport implements SessionAware {
	public static Logger logger = Logger.getLogger(DIAreaMasterAction.class);
	private String city;
	private String area;
	private String areabutton;
	private String actionName;
	private Map map;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAreabutton() {
		return areabutton;
	}

	public void setAreabutton(String areabutton) {
		this.areabutton = areabutton;
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
		if (getCity().trim().length() == 0) {
			addFieldError("city", "City is required");
		}
		if (getArea().trim().length() == 0) {
			addFieldError("area", "Area is required");
		}
	}*/

	public String execute() throws SQLException {
		setActionName(ActionContext.getContext().getName());
		
		if (getCity().trim().length() == 0) {
			addActionError("City is required");
			return ERROR;
		}
		if (getArea().trim().length() == 0) {
			addActionError("Area is required");
			return ERROR;
		}
		
		int approved = 0;
		if (getAreabutton().equalsIgnoreCase("Save")) {
			approved = 1;
		} else if (getAreabutton().equalsIgnoreCase("Save & Send for Approval")) {
			approved = 0;
		}
		try {
			boolean status = DIMastersDAO.insertArea(getCity(),
					getArea(), (String) getSession().get("username"), approved,
					1);
			if (status == true) {
				addActionMessage("City and Area are saved successfully ");
			} else {
				addActionError("City and Area are not saved");
			}
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
			throw sqle;
		}
		return SUCCESS;
	}
}
