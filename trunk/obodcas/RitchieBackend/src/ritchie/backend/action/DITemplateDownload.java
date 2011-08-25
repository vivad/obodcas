package ritchie.backend.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.log4j.Logger;

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
				filePathName = "E:/delightpeople/ritchie/sample files/vendor/template/Vendor_Template.xls";
			}else if(getPagename().equals("servicecenters")){
				fileName = "Service_Center_Template.xls";
				filePathName = "E:/delightpeople/ritchie/sample files/service center/template/Service_Center_Template.xls";
			}else if(getPagename().equals("servicemen")){
				fileName = "Service_Men_Template.xls";
				filePathName = "E:/delightpeople/ritchie/sample files/service men/template/Service_Men_Upload_Template.xls";
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
