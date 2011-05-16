package ritchie.backend.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DIServiceMenXLSProperties {
	public static Logger logger = Logger.getLogger(DIServiceMenXLSProperties.class); 
	public static HashMap<String,String> hm = new HashMap<String,String>();
	static{
		logger.info("in DIServiceMenXLSProperties static block");
		Properties prop = new Properties();
		
		try{
			prop.load(new FileInputStream(Constants.UPLOAD_XLS_SERVICE_MEN_CONFIG));
			
			hm.put(Constants.SEPARATOR, prop.getProperty(Constants.SEPARATOR));
			hm.put(Constants.FIELDS, prop.getProperty(Constants.FIELDS));
			hm.put(Constants.MANDATORYFIELDS, prop.getProperty(Constants.MANDATORYFIELDS));
			hm.put(DIServiceMenXLSConstants.ENROLLMENT_ID, prop.getProperty(DIServiceMenXLSConstants.ENROLLMENT_ID));
			hm.put(DIServiceMenXLSConstants.SERVICE_MEN_AREA, prop.getProperty(DIServiceMenXLSConstants.SERVICE_MEN_AREA));
			hm.put(DIServiceMenXLSConstants.SERVICE_MEN_TYPE, prop.getProperty(DIServiceMenXLSConstants.SERVICE_MEN_TYPE));
			hm.put(DIServiceMenXLSConstants.SERVICE_MEN_NAME, prop.getProperty(DIServiceMenXLSConstants.SERVICE_MEN_NAME));
			hm.put(DIServiceMenXLSConstants.SERVICE_DESCRIPTION, prop.getProperty(DIServiceMenXLSConstants.SERVICE_DESCRIPTION));
			hm.put(DIServiceMenXLSConstants.AREA_OF_COVERAGE, prop.getProperty(DIServiceMenXLSConstants.AREA_OF_COVERAGE));
			hm.put(DIServiceMenXLSConstants.VENDOR_ADDRESS, prop.getProperty(DIServiceMenXLSConstants.VENDOR_ADDRESS));
			hm.put(DIServiceMenXLSConstants.TELEPHONE_1, prop.getProperty(DIServiceMenXLSConstants.TELEPHONE_1));
			hm.put(DIServiceMenXLSConstants.MOBILE_1, prop.getProperty(DIServiceMenXLSConstants.MOBILE_1));
			hm.put(DIServiceMenXLSConstants.MOBILE_2, prop.getProperty(DIServiceMenXLSConstants.MOBILE_2));
			hm.put(DIServiceMenXLSConstants.EMAIL_ID_1, prop.getProperty(DIServiceMenXLSConstants.EMAIL_ID_1));
			hm.put(DIServiceMenXLSConstants.EMAIL_ID_2, prop.getProperty(DIServiceMenXLSConstants.EMAIL_ID_2));
			hm.put(DIServiceMenXLSConstants.WEBSITE, prop.getProperty(DIServiceMenXLSConstants.WEBSITE));
			hm.put(DIServiceMenXLSConstants.ENROLLED, prop.getProperty(DIServiceMenXLSConstants.ENROLLED));
			hm.put(DIServiceMenXLSConstants.ACTIVE, prop.getProperty(DIServiceMenXLSConstants.ACTIVE));
			
			logger.info("separator:  "+hm.get(Constants.SEPARATOR));
			logger.info("fields:  "+hm.get(Constants.FIELDS));
			logger.info("mandatoryfields:  "+hm.get(Constants.MANDATORYFIELDS));
		}catch(FileNotFoundException fne){
			logger.error(fne.getMessage());
		}catch(IOException io){
			logger.error(io.getMessage());
		}
	}
}
