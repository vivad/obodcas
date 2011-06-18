package ritchie.backend.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import ritchie.backend.dao.TEGeneratePageDAO;
import ritchie.backend.utilities.ApplicationProperties;
import ritchie.backend.utilities.Constants;
import ritchie.backend.utilities.TEGeneratePageUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class TEGeneratePageAction extends ActionSupport implements SessionAware,ServletRequestAware{
	private static Logger logger = Logger
	.getLogger(TEGeneratePageAction.class);
	private String disubpage;
	private String actionName;
	private Map map;
	private HttpServletRequest request;
	
	private List<Map<String,Object>> enrolledRecords;
	private int noOfLinksVisible;
	private List<Integer> links;
	private boolean hasPrevious;
	private boolean hasNext;
	private int nextValue;
	private int linkNo;
	private int previousValue;
	private int boldedLink;
	private String uniqueEnrollmentID;
	private InputStream inputStream;
	private String filename;
	private String linkFrom;

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
	
	public void setSession(Map map) {
		this.map = map;
	}

	public Map getSession() {
		return map;
	}

	public HttpServletRequest getServletRequest() {
		return request;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
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

	public int getLinkNo() {
		return linkNo;
	}

	public void setLinkNo(int linkNo) {
		this.linkNo = linkNo;
	}

	public int getPreviousValue() {
		return previousValue;
	}

	public void setPreviousValue(int previousValue) {
		this.previousValue = previousValue;
	}
	
	public int getBoldedLink() {
		return boldedLink;
	}

	public void setBoldedLink(int boldedLink) {
		this.boldedLink = boldedLink;
	}

	public String getUniqueEnrollmentID() {
		return uniqueEnrollmentID;
	}

	public void setUniqueEnrollmentID(String uniqueEnrollmentID) {
		this.uniqueEnrollmentID = uniqueEnrollmentID;
	}
	
	public InputStream getInputStream(){
		return inputStream;
	}
	
	public void setInputStream(InputStream inputStream){
		this.inputStream = inputStream;
	}
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getLinkFrom() {
		return linkFrom;
	}

	public void setLinkFrom(String linkFrom) {
		this.linkFrom = linkFrom;
	}

	public String execute() throws FileNotFoundException, IOException,
			SQLException {
		logger.info("in TEGeneratePageAction class");
		setDisubpage(Constants.TE_GENERATE_PAGE_LINK_NAME);
		setActionName(ActionContext.getContext().getName());
		
		int enrolledcount = Integer.parseInt(getSession().get("enrolledcount").toString());
		setNoOfLinksVisible(Integer.parseInt(ApplicationProperties.hm.get(Constants.NO_OF_LINKS_VISIBLE)));
		
		logger.info("********************************* begin");
		logger.info("previousValue: "+getPreviousValue());
		logger.info("nextValue: "+getNextValue());
		logger.info("linkNo: "+getLinkNo());
		
		boolean validateFlag = false;
		boolean generateFlag = false;
		boolean viewPageFlag = false;
		String EID = null;
		String lFrom = null;
		
		TEGeneratePageDAO dao = new TEGeneratePageDAO();
		
		if(getPreviousValue() == 0 && getNextValue() == 0 && getLinkNo() == 0){
			
			if(getUniqueEnrollmentID() != null){
				getSession().put("validateEID", getUniqueEnrollmentID());
				getSession().put("linkFrom", getLinkFrom());
			}
			
			if(getSession().get("hasPrevious").toString().equalsIgnoreCase("true")){
				setHasPrevious(true);
			}else{
				setHasPrevious(false);
			}
			
			if(getSession().get("hasNext").toString().equalsIgnoreCase("true")){
				setHasNext(true);
			}else{
				setHasNext(false);
			}
			
			logger.info("1");
			
			setPreviousValue(Integer.parseInt(getSession().get("previousValue").toString()));
			
			setLinkNo(Integer.parseInt(getSession().get("linkNo").toString()));
			
			setBoldedLink(Integer.parseInt(getSession().get("boldedLink").toString()));
			
			setNextValue(Integer.parseInt(getSession().get("nextValue").toString()));
			
			logger.info("2");
			
			EID = getSession().get("validateEID").toString();
			lFrom = getSession().get("linkFrom").toString();
			
			if(lFrom.equalsIgnoreCase("Validate")){
				validateFlag = true;
			}else if(lFrom.equalsIgnoreCase("Generate")){
				generateFlag = true;
			}else if(lFrom.equalsIgnoreCase("View Page")){
				viewPageFlag = true;
			}
			
			logger.info("3");
				
		}else{
			if(getPreviousValue() != 0){
				getSession().put("hasPrevious", true);
				getSession().put("previousValue", getPreviousValue());
			}else{
				getSession().put("hasPrevious", false);
				getSession().put("previousValue", 0);
			}
			
			getSession().put("linkNo", getLinkNo());
			
			if(getNextValue() != 0){
				getSession().put("hasNext", true);
				getSession().put("nextValue", getNextValue());
			}else{
				getSession().put("hasNext", false);
				getSession().put("nextValue", 0);
			}
			getSession().put("boldedLink", getBoldedLink());
		}
		
		logger.info("enrolledcount: "+enrolledcount);
		logger.info("previousValue: "+getPreviousValue());
		logger.info("linkNo: "+getLinkNo());
		logger.info("nextValue: "+getNextValue());
		
		int beginIndex = 0;
		int linkIteration = 0;
		
		int a = enrolledcount;
		int b = Integer.parseInt(ApplicationProperties.hm.get(Constants.RECORDS_PER_PAGE));
		int c = getNoOfLinksVisible();
		
		if(getPreviousValue() != 0){ 
			beginIndex = ((getPreviousValue()-1)*b)+1;
			linkIteration = getPreviousValue();
			setHasPrevious(decidePrevious(getPreviousValue(),c));
		}if(getLinkNo() != 0){
			beginIndex = ((getLinkNo()-1)*b)+1;
			
			int clickedLink = getLinkNo();
			int remainder = clickedLink%c;
			
			if(remainder!=0){
				linkIteration = (clickedLink-remainder)+1;
			}else{
				linkIteration = (clickedLink-c)+1;
			}
			
			setHasPrevious(decidePrevious(getLinkNo(),c));
		}else if(getNextValue() != 0){
			beginIndex = ((getNextValue()-1)*b)+1;
			linkIteration = getNextValue();
			setHasPrevious(true);
		}
		
		logger.info("beginIndex: "+beginIndex);
		logger.info("linkIteration: "+linkIteration);
		
		List<Integer> list = new ArrayList<Integer>();
		for(int i=linkIteration;i<(linkIteration+getNoOfLinksVisible());i++){
			if(i==linkIteration){
				list.add(i);
			}else{
				if(i*b<=a && a > 0){
					list.add(i);
				}else if(a%b != 0 && a%b < b){
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
		}
		logger.info("list:  "+list);
		setLinks(list);
		
		if(isHasPrevious()==true && getLinks().size()>0){
			if(getLinks().get(0) !=1 || (getLinks().get(0))-c != 1){
				setPreviousValue((getLinks().get(0))-c);
			}
		}
		if(getLinks().size()>0){
			if(getLinks().get(getLinks().size()-1)*b<a){
				setHasNext(true);
				setNextValue((getLinks().get(getLinks().size()-1))+1);
			}else{
				setHasNext(false);
			}
		}else{
			setHasNext(false);
		}
		
		if(getPreviousValue() != 0){ 
			setBoldedLink(1);
		}	if(getLinkNo() != 0){
			for(int i=0;i<getLinks().size();i++){
				if(getLinks().get(i).equals(getLinkNo())){
					setBoldedLink(i+1);
				}
			}
		}else if(getNextValue() != 0){
			setBoldedLink(1);
		}
		
		logger.info("enrolledcount: "+enrolledcount);
		logger.info("previousValue: "+getPreviousValue());
		logger.info("linkNo: "+getLinkNo());
		logger.info("nextValue: "+getNextValue());
		
		logger.info("********************************* end");
		
		if(getUniqueEnrollmentID() != null){
			getSession().put("validateEID", getUniqueEnrollmentID());
			getSession().put("linkFrom", getLinkFrom());
		}else if(getUniqueEnrollmentID() == null && (validateFlag == true || generateFlag == true || viewPageFlag == true)){
			String[] splitedStr = getCategoryEID(EID);
			
			String category = splitedStr[0];
			String enrollmentID = splitedStr[1];
			
			String destFileName = "";
			
			if(validateFlag == true){
				destFileName = ApplicationProperties.hm.get(Constants.TE_TEMPLATE_VALIDATE_PATH) + "/" + enrollmentID+".html";
				writePage(category,enrollmentID,destFileName);
				setFilename(enrollmentID+".html");
				setInputStream(new FileInputStream(new File(destFileName)));
				return "validate";
			}
			
			if(generateFlag == true){
				destFileName = ApplicationProperties.hm.get(Constants.TE_TEMPLATE_GENERATE_PATH) + "/" + enrollmentID+".html";
				try{
					writePage(category,enrollmentID,destFileName);
				}catch(IOException e){
					dao.insertPageEntry(Long.parseLong(enrollmentID), -1, (String) getSession().get("username"));
					logger.error(e);
					throw e;
				}
				dao.insertPageEntry(Long.parseLong(enrollmentID), 1, (String) getSession().get("username"));
			}
			
			if(viewPageFlag == true){
				int status = dao.viewPage(Long.parseLong(enrollmentID));
				if(status == -1){
            		addActionError("Page Generation Failed.Please try again");
            	}else if(status == 0){
            		addActionError("Page Not Generated. Please generate the page.");
            	}else if(status == 1){
            		destFileName = ApplicationProperties.hm.get(Constants.TE_TEMPLATE_GENERATE_PATH) + "/" + enrollmentID+".html";
            		setFilename(enrollmentID+".html");
    				setInputStream(new FileInputStream(new File(destFileName)));
    				return "viewpage";
            	}
			}
			
			/*if(validateFlag == true || generateFlag == true){
				if(validateFlag == true){
					destFileName = Constants.TE_TEMPLATE_VALIDATE_PATH + "/" + enrollmentID+".html";
				}else if(generateFlag == true){
					destFileName = Constants.TE_TEMPLATE_GENERATE_PATH + "/" + enrollmentID+".html";
				}
				try{
					String templateNo = dao.getTemplateNo(category,"Active");
					List<Map<String,Object>> patternList = dao.getUserPatternAssignedValue(templateNo, enrollmentID, category);
					
					String fileName = Constants.TE_TEMPLATE_UPLOAD_DEST + "/" + templateNo+".html";
					
					try{
						TEGeneratePageUtil.writeFile(assignDataPattern(fileName,patternList),destFileName);
						
						if(generateFlag == true){
							dao.insertPageEntry(Long.parseLong(enrollmentID), 1, (String) getSession().get("username"));
						}
					}catch(IOException e){
						dao.insertPageEntry(Long.parseLong(enrollmentID), -1, (String) getSession().get("username"));
						logger.error(e);
						throw e;
					}
				
					logger.info("fileName:  "+enrollmentID+".html");
					logger.info("filePathName:  "+destFileName);
					
					if(validateFlag == true){
						setFilename(enrollmentID+".html");
						setInputStream(new FileInputStream(new File(destFileName)));
						return "validate";
					}
				}catch(FileNotFoundException e){
					logger.error(e.getMessage());
					throw e;
				}catch(SQLException e){
					logger.error(e.getMessage());
					throw e;
				}catch(IOException e){
					logger.error(e.getMessage());
					throw e;
				}
			}*/
		}
		
		setEnrolledRecords(dao.getEnrolledRecords(beginIndex-1,b));
		
		return "SUCCESS";
	}
	
	private void writePage(String category,String enrollmentID,String destFileName) throws IOException,SQLException{
		TEGeneratePageDAO dao = new TEGeneratePageDAO();
		List<Map<String,Object>> patternList = null;
		String fileName = null;
		
		try{
			String templateNo = dao.getTemplateNo(category,"Active");
			patternList = dao.getUserPatternAssignedValue(templateNo, enrollmentID, category);
			
			fileName = Constants.TE_TEMPLATE_UPLOAD_DEST + "/" + templateNo+".html";
		}catch(SQLException e){
			logger.error(e.getMessage());
			throw e;
		}
		
		try{
			TEGeneratePageUtil.writeFile(assignDataPattern(fileName,patternList),destFileName);
		}catch(IOException e){
			logger.error(e);
			throw e;
		}
	}
	
	private boolean decidePrevious(int link,int linkVisible){
		if(link<=linkVisible){
			return false;
		}else{
			return true;
		}
	}
	
	public String assignDataPattern(String fileName,List<Map<String,Object>> patternList){
		String strContent = null;
		try{
			FileInputStream fin = new FileInputStream(fileName);
			byte[] content = new byte[fin.available()];
			fin.read(content);
			strContent = new String(content);
			
			logger.info("html content:  "+new String(content));
			
			for(int i=0;i<patternList.size();i++){
				String patternName = patternList.get(i).get("PATTERNNAME").toString();
				if(patternList.get(i).get("PATTERNVALUE") != null){
					String patternValue = patternList.get(i).get("PATTERNVALUE").toString();
					patternName = patternName.replaceAll("\\.\\{","");
					patternName = patternName.replaceAll("}","");
					Pattern p = Pattern.compile("\\.\\{"+patternName+"?}",Pattern.CASE_INSENSITIVE);
					Matcher m = p.matcher(strContent);
					
					while(m.find()){
						strContent = m.replaceAll(patternValue);
					}
				}
			}
		}catch(FileNotFoundException e){
			logger.error(e.getMessage());
		}catch(IOException e){
			logger.error(e.getMessage());
		}
		logger.info("strContent:  "+strContent);
		return strContent;
	}
	
	public String[] getCategoryEID(String ueid){
		int len = ueid.length();
		
		logger.info("validateEID:  "+ueid);
		
		int startIndex = 0;
		int endIndex = len-12;
		
		String category = ueid.substring(startIndex, endIndex);
		String enrollmentID = ueid.substring(endIndex, len);
		
		if(category.equalsIgnoreCase("V")){
			category = Constants.VENDOR_LINK_NAME;
		}else if(category.equalsIgnoreCase("SC")){
			category = Constants.SERVICE_CENTER_LINK_NAME;
		}else if(category.equalsIgnoreCase("SM")){
			category = Constants.SERVICE_MEN_LINK_NAME;
		}
		
		String[] strToReturn = new String[2];
		strToReturn[0] = category;
		strToReturn[1] = enrollmentID;
		
		logger.info("category:  "+category);
		logger.info("enrollmentID:  "+enrollmentID);
		
		return strToReturn;
	}
}
