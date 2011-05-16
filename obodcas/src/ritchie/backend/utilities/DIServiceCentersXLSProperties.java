package ritchie.backend.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DIServiceCentersXLSProperties {
	public static Logger logger = Logger.getLogger(DIServiceCentersXLSProperties.class); 
	public static HashMap<String,String> hm = new HashMap<String,String>();
	static{
		logger.info("in ServiceCentersXLSProperties static block");
		Properties prop = new Properties();
		
		try{
			prop.load(new FileInputStream(Constants.UPLOAD_XLS_SERVICE_CENTER_CONFIG));
			
			hm.put(Constants.SEPARATOR, prop.getProperty(Constants.SEPARATOR));
			hm.put(Constants.FIELDS, prop.getProperty(Constants.FIELDS));
			hm.put(Constants.MANDATORYFIELDS, prop.getProperty(Constants.MANDATORYFIELDS));
			hm.put(DIServiceCentersXLSConstants.ENROLLMENT_ID, prop.getProperty(DIServiceCentersXLSConstants.ENROLLMENT_ID));
			hm.put(DIServiceCentersXLSConstants.AREA_CODE, prop.getProperty(DIServiceCentersXLSConstants.AREA_CODE));
			hm.put(DIServiceCentersXLSConstants.SERVICE_CENTER_TYPE, prop.getProperty(DIServiceCentersXLSConstants.SERVICE_CENTER_TYPE));
			hm.put(DIServiceCentersXLSConstants.SERVICE_CENTER_NAME, prop.getProperty(DIServiceCentersXLSConstants.SERVICE_CENTER_NAME));
			hm.put(DIServiceCentersXLSConstants.SERVICE_DESCRIPTION, prop.getProperty(DIServiceCentersXLSConstants.SERVICE_DESCRIPTION));
			hm.put(DIServiceCentersXLSConstants.ADDRESS, prop.getProperty(DIServiceCentersXLSConstants.ADDRESS));
			hm.put(DIServiceCentersXLSConstants.CONTACT_PERSON_1, prop.getProperty(DIServiceCentersXLSConstants.CONTACT_PERSON_1));
			hm.put(DIServiceCentersXLSConstants.CONTACT_PERSON_2, prop.getProperty(DIServiceCentersXLSConstants.CONTACT_PERSON_2));
			hm.put(DIServiceCentersXLSConstants.TELEPHONE_1, prop.getProperty(DIServiceCentersXLSConstants.TELEPHONE_1));
			hm.put(DIServiceCentersXLSConstants.TELEPHONE_2, prop.getProperty(DIServiceCentersXLSConstants.TELEPHONE_2));
			hm.put(DIServiceCentersXLSConstants.TELEPHONE_3, prop.getProperty(DIServiceCentersXLSConstants.TELEPHONE_3));
			hm.put(DIServiceCentersXLSConstants.MOBILE_1, prop.getProperty(DIServiceCentersXLSConstants.MOBILE_1));
			hm.put(DIServiceCentersXLSConstants.MOBILE_2, prop.getProperty(DIServiceCentersXLSConstants.MOBILE_2));
			hm.put(DIServiceCentersXLSConstants.MOBILE_3, prop.getProperty(DIServiceCentersXLSConstants.MOBILE_3));
			hm.put(DIServiceCentersXLSConstants.EMAIL_ID_1, prop.getProperty(DIServiceCentersXLSConstants.EMAIL_ID_1));
			hm.put(DIServiceCentersXLSConstants.EMAIL_ID_2, prop.getProperty(DIServiceCentersXLSConstants.EMAIL_ID_2));
			hm.put(DIServiceCentersXLSConstants.WEBSITE, prop.getProperty(DIServiceCentersXLSConstants.WEBSITE));
			hm.put(DIServiceCentersXLSConstants.ENROLLED, prop.getProperty(DIServiceCentersXLSConstants.ENROLLED));
			hm.put(DIServiceCentersXLSConstants.ACTIVE, prop.getProperty(DIServiceCentersXLSConstants.ACTIVE));
			
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
