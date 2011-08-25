package ritchie.backend.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import ritchie.backend.dao.TEGeneratePageDAO;
import ritchie.backend.utilities.Constants;
import ritchie.backend.utilities.TEGeneratePageUtil;

public class TEValidatePageAction implements ServletRequestAware{
	private static Logger logger = Logger.getLogger(TEValidatePageAction.class);
	
	private HttpServletRequest servletRequest;
	private InputStream inputStream;
	private String filename;
	private String uniqueenrollmentid;

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
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

	public String getUniqueenrollmentid() {
		return uniqueenrollmentid;
	}

	public void setUniqueenrollmentid(String uniqueenrollmentid) {
		this.uniqueenrollmentid = uniqueenrollmentid;
	}

	public String execute() throws FileNotFoundException,SQLException,IOException{
		logger.info("inside TEValidatePageAction class");
		String uniqueEnrollmentID = getServletRequest().getParameter("uniqueenrollmentid");
		int len = uniqueEnrollmentID.length();
		
		logger.info("uniqueEnrollmentID:  "+uniqueEnrollmentID);
		
		int beginIndex = 0;
		int endIndex = len-12;
		
		String category = uniqueEnrollmentID.substring(beginIndex, endIndex);
		String enrollmentID = uniqueEnrollmentID.substring(endIndex, len);
		
		logger.info("category:  "+category);
		logger.info("enrollmentID:  "+enrollmentID);
		
		if(category.equalsIgnoreCase("V")){
			category = Constants.VENDOR_LINK_NAME;
		}else if(category.equalsIgnoreCase("SC")){
			category = Constants.SERVICE_CENTER_LINK_NAME;
		}else if(category.equalsIgnoreCase("SM")){
			category = Constants.SERVICE_MEN_LINK_NAME;
		}
		
		try{
			TEGeneratePageDAO dao = new TEGeneratePageDAO();
			String templateNo = dao.getTemplateNo(category,"Active");
			List<Map<String,Object>> list = dao.getUserPatternAssignedValue(templateNo, enrollmentID, category);
			
			String fileName = Constants.TE_TEMPLATE_UPLOAD_DEST + "/" + templateNo+".html";
			String destFileName = Constants.TE_TEMPLATE_VALIDATE_PATH + "/" + enrollmentID+".html";
			
			TEGeneratePageUtil.writeFile(assignDataPattern(fileName,list),destFileName);
			
			logger.info("fileName:  "+enrollmentID+".html");
			logger.info("filePathName:  "+destFileName);
			
			setFilename(enrollmentID+".html");
			setInputStream(new FileInputStream(new File(destFileName)));
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
		return "SUCCESS";
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
}
