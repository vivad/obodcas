package ritchie.backend.action;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import ritchie.backend.dao.TEManageTemplateDAO;

import com.opensymphony.xwork2.ActionSupport;

public class GetDBTableColumnNamesAction extends ActionSupport implements
		SessionAware,ServletRequestAware {
	
	private static Logger logger = Logger
	.getLogger(GetDBTableColumnNamesAction.class);
	
	private Map map;
	private HttpServletRequest req;
	private List<Map<String, String>> fileDetailsList;
	private String disubpage;
	private String actionName;
	private String tableName;
    private transient InputStream inputStream;
	
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

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String execute() throws FileNotFoundException, IOException,
			SQLException {
		logger.info("in GetDBTableColumnNamesAction class");
		TEManageTemplateDAO dao = new TEManageTemplateDAO();
		logger.info("Table Name:   "+getTableName());
		List<String> columnNames = dao.getColumnsNamesFromDB(getTableName());
		inputStream = new ByteArrayInputStream(toXML(columnNames).getBytes());
		return SUCCESS;
	}
	
	public String toXML(List<String> columnNames){
		StringBuffer sb = new StringBuffer("<ColumnNames>");
		
		for(int i=0;i<columnNames.size();i++){
			sb.append("<ColumnName>");
			sb.append(columnNames.get(i));
			sb.append("</ColumnName>");
		}
		sb.append("</ColumnNames>");
		
		logger.info("XML Column Names:  "+sb.toString());
		
		return sb.toString();
	}
}
