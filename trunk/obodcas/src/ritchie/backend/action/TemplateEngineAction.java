package ritchie.backend.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import ritchie.backend.dao.TEGeneratePageDAO;
import ritchie.backend.dao.TETemplateDetailsDAO;
import ritchie.backend.utilities.ApplicationProperties;
import ritchie.backend.utilities.Constants;

import com.opensymphony.xwork2.ActionContext;

public class TemplateEngineAction implements SessionAware{
	private static Logger logger = Logger.getLogger(TemplateEngineAction.class);
	private String actionName;
	private String disubpage;
	private String disubpagename;
	private Map map;
	private List<Map<String, String>> fileDetailsList;
	private boolean filedetailsexist = false;
	private int enrolledCount;
	private List<Map<String,Object>> enrolledRecords;
	private int noOfLinksVisible;
	private List<Integer> links;
	private boolean hasPrevious;
	private boolean hasNext;
	private int nextValue;
	private int previousValue;
	private int boldedLink;
	
	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
	public String getDisubpage() {
		return disubpage;
	}

	public void setDisubpage(String disubpage) {
		this.disubpage = disubpage;
	}

	public String getDisubpagename() {
		return disubpagename;
	}

	public void setDisubpagename(String disubpagename) {
		this.disubpagename = disubpagename;
	}

	public void setSession(Map map) {
		this.map = map;
	}

	public Map getSession() {
		return map;
	}
	
	public List<Map<String, String>> getFileDetailsList() {
		return fileDetailsList;
	}

	public void setFileDetailsList(List<Map<String, String>> fileDetailsList) {
		this.fileDetailsList = fileDetailsList;
	}
	
	public boolean isFiledetailsexist() {
		return filedetailsexist;
	}

	public void setFiledetailsexist(boolean filedetailsexist) {
		this.filedetailsexist = filedetailsexist;
	}

	public int getEnrolledCount() {
		return enrolledCount;
	}

	public void setEnrolledCount(int enrolledCount) {
		this.enrolledCount = enrolledCount;
	}

	public List<Map<String, Object>> getEnrolledRecords() {
		return enrolledRecords;
	}

	public void setEnrolledRecords(List<Map<String, Object>> enrolledRecords) {
		this.enrolledRecords = enrolledRecords;
	}

	public int getNoOfLinksVisible() {
		return noOfLinksVisible;
	}

	public void setNoOfLinksVisible(int noOfLinksVisible) {
		this.noOfLinksVisible = noOfLinksVisible;
	}

	public List<Integer> getLinks() {
		return links;
	}

	public void setLinks(List<Integer> links) {
		this.links = links;
	}

	public boolean isHasPrevious() {
		return hasPrevious;
	}

	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public int getNextValue() {
		return nextValue;
	}

	public void setNextValue(int nextValue) {
		this.nextValue = nextValue;
	}

	public int HasNext() {
		return previousValue;
	}

	public void setPreviousValue(int previousValue) {
		this.previousValue = previousValue;
	}
	
	public int getPreviousValue() {
		return previousValue;
	}

	public int getBoldedLink() {
		return boldedLink;
	}

	public void setBoldedLink(int boldedLink) {
		this.boldedLink = boldedLink;
	}

	public String execute() throws SQLException {
		logger.info("in TemplateEngineAction class");
		setActionName(ActionContext.getContext().getName());
		logger.info("You have clicked: " + getDisubpagename()
				+ " sub page");
		setDisubpage(getDisubpagename());
		
		if (getDisubpagename() == null) {
			try {
				setFileDetailsList(TETemplateDetailsDAO.getUploadedTemplateDetails(Constants.TE_ENROLL_TEMPLATE_LINK_NAME));
				
				if(getFileDetailsList().size() > 0){
					setFiledetailsexist(true);
				}
			} catch (SQLException e) {
				throw e;
			}
		}
		
		if (getDisubpagename() != null && (getDisubpagename().equalsIgnoreCase(Constants.TE_ENROLL_TEMPLATE_LINK_NAME)
				|| getDisubpagename().equalsIgnoreCase(Constants.TE_MAP_DATA_LINK_NAME))) {
			try {
				setFileDetailsList(TETemplateDetailsDAO.getUploadedTemplateDetails(Constants.TE_ENROLL_TEMPLATE_LINK_NAME));
				if(getFileDetailsList().size() > 0){
					setFiledetailsexist(true);
				}
			} catch (SQLException e) {
				throw e;
			}
		}
		
		if (getDisubpagename() != null && getDisubpagename().equalsIgnoreCase(Constants.TE_MANAGE_TEMPLATE_LINK_NAME)) {
			try {
				setFileDetailsList(TETemplateDetailsDAO.getUploadedTemplateDetails(""));
				if(getFileDetailsList().size() > 0){
					setFiledetailsexist(true);
				}
			} catch (SQLException e) {
				throw e;
			}
		}
		
		if (getDisubpagename() != null && getDisubpagename().equalsIgnoreCase(Constants.TE_GENERATE_PAGE_LINK_NAME)) {
			TEGeneratePageDAO dao = new TEGeneratePageDAO();
			setEnrolledCount(dao.getEnrolledCount());
			getSession().put("enrolledcount", getEnrolledCount());
			setEnrolledRecords(dao.getEnrolledRecords(0, Integer.parseInt(ApplicationProperties.hm.get(Constants.RECORDS_PER_PAGE))));
			setNoOfLinksVisible(Integer.parseInt(ApplicationProperties.hm.get(Constants.NO_OF_LINKS_VISIBLE)));
			List<Integer> list = new ArrayList<Integer>();
			
			setHasPrevious(false);
			getSession().put("hasPrevious", isHasPrevious());
			
			int a = getEnrolledCount();
			int b = Integer.parseInt(ApplicationProperties.hm.get(Constants.RECORDS_PER_PAGE));
			int c = getNoOfLinksVisible();
			
			for(int i=1;i<=c;i++){
				if(i==1 && a > 0){
					list.add(i);
					setBoldedLink(i);
					getSession().put("boldedLink", getBoldedLink());
					getSession().put("linkNo", 1);
				}else{
					if(i*b<=a){
						list.add(i);
					}else{
						int temp = a-((i-1)*b);
						if(temp>0 && temp<b){
							list.add(i);
						}
					}
				}
			}
			setLinks(list);
			getSession().put("links", getLinks());
			if(getLinks().size()>0){
				setPreviousValue(getLinks().get(0));
				getSession().put("previousValue", getPreviousValue());
			}
			
			
			if(getLinks().size()>0){
				if(getLinks().get(getLinks().size()-1)*b<a){
					setHasNext(true);
					getSession().put("hasNext", isHasNext());
					setNextValue((getLinks().get(getLinks().size()-1))+1);
					getSession().put("nextValue", getNextValue());
				}else{
					setHasNext(false);
					getSession().put("hasNext", false);
				}
			}else{
				setHasNext(false);
				getSession().put("hasNext", false);
			}
		}
		return "SUCCESS";
	}
}