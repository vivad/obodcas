package ritchie.backend.action;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import ritchie.backend.dao.TEManageTemplateDAO;
import ritchie.backend.utilities.Constants;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class TEAssignDataPatternsAction extends ActionSupport implements
		SessionAware,ServletRequestAware {
	
	private static Logger logger = Logger
	.getLogger(TEAssignDataPatternsAction.class);
	
	private Map map;
	private HttpServletRequest req;
	private List<Map<String, String>> fileDetailsList;
	private String disubpage;
	private String actionName;
	private String data;
    private transient InputStream inputStream;
    private String templateno;
    private List<String> tableNames;
    private List<String> columnNames;
	
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getTemplateno() {
		return templateno;
	}

	public void setTemplateno(String templateno) {
		this.templateno = templateno;
	}
	
	public List<String> getTableNames() {
		return tableNames;
	}

	public void setTableNames(List<String> tableNames) {
		this.tableNames = tableNames;
	}
	
	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public String execute() throws FileNotFoundException, IOException,
			SQLException {
		logger.info("in TEAssignDataPatternsAction class");
		setDisubpage(Constants.TE_MAP_DATA_LINK_NAME);
		setActionName(ActionContext.getContext().getName());
		//logger.info("Template No:  "+getTemplateno());
		String templateNo = getServletRequest().getParameter("templateno");
		//logger.info("Template No:  "+templateNo);
		logger.info("Data:   "+getData());
		logger.info("Templateno:   "+getTemplateno());
		TEManageTemplateDAO dao = new TEManageTemplateDAO();
		if(getData() != null && getData().length()>0){
			String[] trs = getData().split("-NTR-");
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			List<String> tNames = new ArrayList<String>() ;
			List<String> cNames = new ArrayList<String>() ;
			for(int i=0;i<trs.length;i++){
				HashMap<String,String> hm = new HashMap<String,String>();
				String[] tds = trs[i].split("-NTD-*");
				hm.put("PatternName",tds[0]);
				hm.put("TableName",tds[1]);
				tNames.add(tds[1]);
				hm.put("ColumnName",tds[2]);
				cNames.add(tds[2]);
				list.add(hm);
			}
			
			logger.info("list.size:   "+list.size());
			logger.info(list);
			
			setTableNames(tNames);
			setColumnNames(cNames);
			
			if(dao.assignDataPattern(templateNo,list)==true){
				//addActionMessage("Data Patterns are assigned successfully");
				inputStream = new ByteArrayInputStream("Data Patterns are assigned successfully".getBytes());
			}else{
				//addActionError("Data Patterns are not assigned successfully");
				inputStream = new ByteArrayInputStream("Data Patterns are not assigned successfully".getBytes());
			}
		}
		
		return SUCCESS;
	}
}
