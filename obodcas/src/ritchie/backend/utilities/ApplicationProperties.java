package ritchie.backend.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ApplicationProperties {
	public static Logger logger = Logger.getLogger(ApplicationProperties.class); 
	
	public static HashMap<String,String> hm = new HashMap<String,String>();
	
	static{
		logger.info("in ApplicationProperties static block");
		Properties prop = new Properties();
		
		try{
			prop.load(new FileInputStream(Constants.APPLICATION_CONFIG));
			
			hm.put(Constants.DB_NAME, prop.getProperty(Constants.DB_NAME));
			hm.put(Constants.RECORDS_PER_PAGE, prop.getProperty(Constants.RECORDS_PER_PAGE));
			hm.put(Constants.NO_OF_LINKS_VISIBLE, prop.getProperty(Constants.NO_OF_LINKS_VISIBLE));
			hm.put(Constants.PAGE_PATH, prop.getProperty(Constants.PAGE_PATH));
			
			hm.put(Constants.TE_TEMPLATE_UPLOAD_DEST, prop.getProperty(Constants.TE_TEMPLATE_UPLOAD_DEST));
			hm.put(Constants.TE_TEMPLATE_VALIDATE_PATH, prop.getProperty(Constants.TE_TEMPLATE_VALIDATE_PATH));
			hm.put(Constants.TE_TEMPLATE_GENERATE_PATH, prop.getProperty(Constants.TE_TEMPLATE_GENERATE_PATH));
			
			hm.put(Constants.VENDOR_XLS_TEMPLATE, prop.getProperty(Constants.VENDOR_XLS_TEMPLATE));
			hm.put(Constants.SERVICE_CENTER_XLS_TEMPLATE, prop.getProperty(Constants.SERVICE_CENTER_XLS_TEMPLATE));
			hm.put(Constants.SERVICE_MEN_XLS_TEMPLATE, prop.getProperty(Constants.SERVICE_MEN_XLS_TEMPLATE));
			
			
			hm.put(Constants.UPLOAD_XLS_VENDOR_CONFIG, prop.getProperty(Constants.UPLOAD_XLS_VENDOR_CONFIG));
			hm.put(Constants.UPLOAD_XLS_SERVICE_CENTER_CONFIG, prop.getProperty(Constants.UPLOAD_XLS_SERVICE_CENTER_CONFIG));
			hm.put(Constants.UPLOAD_XLS_SERVICE_MEN_CONFIG, prop.getProperty(Constants.UPLOAD_XLS_SERVICE_MEN_CONFIG));
			hm.put(Constants.APPLICATION_CONFIG, prop.getProperty(Constants.APPLICATION_CONFIG));
			
			logger.info("DB name:  "+hm.get(Constants.DB_NAME));
			
		}catch(FileNotFoundException fne){
			logger.error(fne.getMessage());
		}catch(IOException io){
			logger.error(io.getMessage());
		}
	}
}
