package ritchie.backend.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import ritchie.backend.dao.UserDAO;

import com.opensymphony.xwork2.ActionSupport;

public class WelcomeUserAction extends ActionSupport implements
		ServletRequestAware, ServletResponseAware, SessionAware {
	private static Logger logger = Logger
	.getLogger(WelcomeUserAction.class);
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;

	private HttpServletRequest servletRequest;
	private HttpServletResponse servletResponse;
	private Map map;

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	@Override
	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}

	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}

	@Override
	public void setSession(Map map) {
		this.map = map;
	}

	public Map getSession() {
		return map;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String execute() throws SQLException, IOException, ServletException {
		logger.info("in WelcomeUserAction");
		logger.info("User Name:  "+getUsername());
		
		if (getUsername() == null || getUsername().trim().length() == 0) {
			addActionError("User Name is required");
			return ERROR;
		}
		if (getPassword() == null || getPassword().trim().length() == 0) {
			addActionError("Password is required");
			return ERROR;
		}
		
		Map<String, String> userMap = null;

		try {
			userMap = new UserDAO().authenticateUser(getUsername(), getPassword());
		} catch (SQLException sqle) {
			throw sqle;
		}
		
		logger.info("Login Status:  "+userMap.get("status"));
		if (userMap.get("status").equalsIgnoreCase("SUCCESS")) {
			getSession().put("username", getUsername());
			getSession().put("firstname", userMap.get("firstname"));
			getSession().put("lastname", userMap.get("lastname"));
			getSession().put("usertype", userMap.get("usertype"));
			return SUCCESS;
		} else{
			addActionError("Login Failed. Please try again.");
			return ERROR;
		}
	}
}
