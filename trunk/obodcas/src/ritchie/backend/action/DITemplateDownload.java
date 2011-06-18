package ritchie.backend.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import ritchie.backend.utilities.ApplicationProperties;
import ritchie.backend.utilities.Constants;

public class DITemplateDownload {
	private static Logger logger = Logger.getLogger(DITemplateDownload.class);
	private InputStream inputStream;
	private String filename;
	private String pagename;

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

	public String getPagename() {
		return pagename;
	}

	public void setPagename(String pagename) {
		this.pagename = pagename;
	}
	
	public String execute() throws FileNotFoundException{
		logger.info("inside DITemplateDownload class");
		try{
			String fileName = null;
			String filePathName = null;
			if(getPagename().equals("vendors")){
				fileName = "Vendor_Template.xls";
				filePathName = ApplicationProperties.hm.get(Constants.VENDOR_XLS_TEMPLATE);
			}else if(getPagename().equals("servicecenters")){
				fileName = "Service_Center_Template.xls";
				filePathName = ApplicationProperties.hm.get(Constants.SERVICE_CENTER_XLS_TEMPLATE);
			}else if(getPagename().equals("servicemen")){
				fileName = "Service_Men_Template.xls";
				filePathName = ApplicationProperties.hm.get(Constants.SERVICE_MEN_XLS_TEMPLATE);
			}
			
			logger.info("fileName:  "+fileName);
			logger.info("filePathName:  "+filePathName);
			
			setFilename(fileName);
			setInputStream(new FileInputStream(new File(filePathName)));
		}catch(FileNotFoundException e){
			logger.error(e.getMessage());
			throw e;
		}
		return "SUCCESS";
	}
}
