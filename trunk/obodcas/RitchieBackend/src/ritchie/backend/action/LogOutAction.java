package ritchie.backend.action;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LogOutAction extends ActionSupport  {
	private static Logger logger = Logger
	.getLogger(LogOutAction.class);
	public String execute() {
		((org.apache.struts2.dispatcher.SessionMap)
                ActionContext.getContext().getSession()).invalidate();
		logger.info("User's session has been invalidated");
        return SUCCESS;
	}
}
