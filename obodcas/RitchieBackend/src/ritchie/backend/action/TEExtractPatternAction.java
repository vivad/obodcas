package ritchie.backend.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import ritchie.backend.dao.TEManageTemplateDAO;
import ritchie.backend.dao.TETemplateDetailsDAO;
import ritchie.backend.utilities.Constants;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class TEExtractPatternAction extends ActionSupport implements
		SessionAware,ServletRequestAware {
	private static Logger logger = Logger
	.getLogger(TEExtractPatternAction.class);
	private Map map;
	private HttpServletRequest req;
	private List<Map<String, String>> fileDetailsList;
	private String disubpage;
	private String actionName;
	private boolean filedetailsexist = false;
	private String templateno;
	private String templatename;
	private String button;
	private Set<String> keynames;
	private Set<String> tableNames;
	private List<String> columnNames;
	private String pagefrom;
	
	
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

	public String getTemplateno() {
		return templateno;
	}

	public void setTemplateno(String templateno) {
		this.templateno = templateno;
	}

	public String getTemplatename() {
		return templatename;
	}

	public void setTemplatename(String templatename) {
		this.templatename = templatename;
	}

	public Set<String> getKeynames() {
		return keynames;
	}

	public void setKeynames(Set<String> keynames) {
		this.keynames = keynames;
	}

	public Set<String> getTableNames() {
		return tableNames;
	}

	public void setTableNames(Set<String> tableNames) {
		this.tableNames = tableNames;
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public String getPagefrom() {
		return pagefrom;
	}

	public void setPagefrom(String pagefrom) {
		this.pagefrom = pagefrom;
	}

	public String execute() throws FileNotFoundException, IOException,
			SQLException {
		logger.info("in TEExtractPatternAction class");
		
		setActionName(ActionContext.getContext().getName());
		setTemplateno(getServletRequest().getParameter("templateno"));
		
		if(getTemplateno() == null || getTemplateno().length() == 0){
			addActionError("Please select atleast one template to extract the pattern");
			getUploadedFileDetails();
			logger.info("Page From:  "+getPagefrom());
			if(getPagefrom().equalsIgnoreCase("mapdata")){
				setDisubpage(Constants.TE_MAP_DATA_LINK_NAME);
			}else if(getPagefrom().equalsIgnoreCase("enrolltemplate")){
				setDisubpage(Constants.TE_ENROLL_TEMPLATE_LINK_NAME);
			}
			return ERROR;
		}else{
			logger.info("getTemplateno():  "+getTemplateno());
			String targetFolder = Constants.TE_TEMPLATE_UPLOAD_DEST;
			String fileName = targetFolder + "/" + getTemplateno()+".html";
			setKeynames(extractPatterns(fileName));
			
			setTemplatename(TETemplateDetailsDAO.getTemplateName(getTemplateno()));
			
			TEManageTemplateDAO dao = new TEManageTemplateDAO();
			setTableNames(dao.getTableNamesFromDB());
			
			if(getTableNames().size() > 0){
				Iterator<String> it = getTableNames().iterator();
				String tName = null;
				while(it.hasNext()){
					tName = it.next();
				}
				setColumnNames(dao.getColumnsNamesFromDB(tName));
			}
		}
		
		getUploadedFileDetails();
		setDisubpage(Constants.TE_MAP_DATA_LINK_NAME);
		return "SUCCESS";
	}

	public void getUploadedFileDetails() throws SQLException {
		try {
			setFileDetailsList(TETemplateDetailsDAO.getUploadedTemplateDetails(Constants.TE_ENROLL_TEMPLATE_LINK_NAME));
			if(getFileDetailsList().size() > 0){
				setFiledetailsexist(true);
			}
		} catch (SQLException e) {
			throw e;
		}
	}
	
	public Set<String> extractPatterns(String fileName){
		Set<String> set = new TreeSet<String>();
		
		try{
			FileInputStream fin = new FileInputStream(fileName);
			byte[] content = new byte[fin.available()];
			fin.read(content);
			String strContent = new String(content);
			
			logger.info("html content:  "+new String(content));
			
			Pattern p = Pattern.compile("\\.\\{.+?}");
			Matcher m = p.matcher(strContent);
			
			while(m.find()){
				set.add(m.group().toLowerCase());
			}
			logger.info(set.size());
			logger.info(set);
			
		}catch(FileNotFoundException e){
			logger.error(e.getMessage());
		}catch(IOException e){
			logger.error(e.getMessage());
		}
		
		return set;
	}
}
