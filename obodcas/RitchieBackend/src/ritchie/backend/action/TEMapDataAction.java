package ritchie.backend.action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import ritchie.backend.dao.TETemplateDetailsDAO;
import ritchie.backend.utilities.Constants;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class TEMapDataAction extends ActionSupport implements
		SessionAware,ServletRequestAware {
	private static Logger logger = Logger
	.getLogger(TEManageTemplateAction.class);
	private Map map;
	private HttpServletRequest req;
	private List<Map<String, String>> fileDetailsList;
	private String disubpage;
	private String actionName;
	private boolean filedetailsexist = false;
	private String[] checkbox;
	private String button;

	public void setSession(Map map) {
		this.map = map;
	}

	public Map getSession() {
		return map;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest req) {
		this.req = req;
	}

	public HttpServletRequest getServletRequest() {
		return req;
	}
	
	public List<Map<String, String>> getFileDetailsList() {
		return fileDetailsList;
	}

	public void setFileDetailsList(List<Map<String, String>> fileDetailsList) {
		this.fileDetailsList = fileDetailsList;
	}

	public String getDisubpage() {
		return disubpage;
	}

	public void setDisubpage(String disubpage) {
		this.disubpage = disubpage;
	}
	
	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
	public boolean isFiledetailsexist() {
		return filedetailsexist;
	}

	public void setFiledetailsexist(boolean filedetailsexist) {
		this.filedetailsexist = filedetailsexist;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	public String[] getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(String[] checkbox) {
		this.checkbox = checkbox;
	}

	public String execute() throws FileNotFoundException, IOException,
			SQLException {
		logger.info("in TEMapDataAction class");
		setDisubpage(Constants.TE_MANAGE_TEMPLATE_LINK_NAME);
		setActionName(ActionContext.getContext().getName());
		setCheckbox(getServletRequest().getParameterValues("checkbox"));
		
		if(checkbox == null || checkbox.length == 0){
			addActionError("Please check atleast one template to make active/inactive");
			getUploadedFileDetails();
			return ERROR;
		}
		
		if (getButton().equalsIgnoreCase("Make Active")) {
			logger.info("Active button has been pressed");
			for(int i=0;i<checkbox.length;i++){
				logger.info("check box value:  "+checkbox[i]);
				new TETemplateDetailsDAO().manageUploadedTemplateDetails(checkbox[i],"Active");
			}
		} else if (getButton().equalsIgnoreCase("Make Inactive")) {
			logger.info("Inactive button has been pressed");
			for(int i=0;i<checkbox.length;i++){
				logger.info("check box value:  "+checkbox[i]);
				new TETemplateDetailsDAO().manageUploadedTemplateDetails(checkbox[i],"Inactive");
			}
		}
		
		getUploadedFileDetails();
		
		return "SUCCESS";
	}

	public void getUploadedFileDetails() throws SQLException {
		try {
			setFileDetailsList(TETemplateDetailsDAO.getUploadedTemplateDetails(Constants.TE_MANAGE_TEMPLATE_LINK_NAME));
			if(getFileDetailsList().size() > 0){
				setFiledetailsexist(true);
			}
		} catch (SQLException e) {
			throw e;
		}
	}
}
