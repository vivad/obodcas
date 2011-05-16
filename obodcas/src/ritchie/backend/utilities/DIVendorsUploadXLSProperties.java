package ritchie.backend.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DIVendorsUploadXLSProperties {
	public static Logger logger = Logger.getLogger(DIVendorsUploadXLSProperties.class); 
	public static HashMap<String,String> hm = new HashMap<String,String>();
	static{
		logger.info("in DIVendorsUploadXLSProperties static block");
		Properties prop = new Properties();
		
		try{
			prop.load(new FileInputStream(Constants.UPLOAD_XLS_VENDOR_CONFIG));
			
			hm.put(Constants.SEPARATOR, prop.getProperty(Constants.SEPARATOR));
			hm.put(Constants.FIELDS, prop.getProperty(Constants.FIELDS));
			hm.put(Constants.MANDATORYFIELDS, prop.getProperty(Constants.MANDATORYFIELDS));
			
			hm.put(DIVendorsUploadXLSConstants.ENROLLMENT_ID, prop.getProperty(DIVendorsUploadXLSConstants.ENROLLMENT_ID));
			hm.put(DIVendorsUploadXLSConstants.VENDOR_AREA, prop.getProperty(DIVendorsUploadXLSConstants.VENDOR_AREA));
			hm.put(DIVendorsUploadXLSConstants.VENDOR_TYPE, prop.getProperty(DIVendorsUploadXLSConstants.VENDOR_TYPE));
			hm.put(DIVendorsUploadXLSConstants.VENDOR_NAME, prop.getProperty(DIVendorsUploadXLSConstants.VENDOR_NAME));
			hm.put(DIVendorsUploadXLSConstants.VENDOR_DESCRIPTION, prop.getProperty(DIVendorsUploadXLSConstants.VENDOR_DESCRIPTION));
			hm.put(DIVendorsUploadXLSConstants.VENDOR_ADDRESS, prop.getProperty(DIVendorsUploadXLSConstants.VENDOR_ADDRESS));
			hm.put(DIVendorsUploadXLSConstants.CONTACT_PERSON_1, prop.getProperty(DIVendorsUploadXLSConstants.CONTACT_PERSON_1));
			hm.put(DIVendorsUploadXLSConstants.CONTACT_PERSON_2, prop.getProperty(DIVendorsUploadXLSConstants.CONTACT_PERSON_2));
			hm.put(DIVendorsUploadXLSConstants.TELEPHONE_1, prop.getProperty(DIVendorsUploadXLSConstants.TELEPHONE_1));
			hm.put(DIVendorsUploadXLSConstants.TELEPHONE_2, prop.getProperty(DIVendorsUploadXLSConstants.TELEPHONE_2));
			hm.put(DIVendorsUploadXLSConstants.TELEPHONE_3, prop.getProperty(DIVendorsUploadXLSConstants.TELEPHONE_3));
			hm.put(DIVendorsUploadXLSConstants.MOBILE_1, prop.getProperty(DIVendorsUploadXLSConstants.MOBILE_1));
			hm.put(DIVendorsUploadXLSConstants.MOBILE_2, prop.getProperty(DIVendorsUploadXLSConstants.MOBILE_2));
			hm.put(DIVendorsUploadXLSConstants.MOBILE_3, prop.getProperty(DIVendorsUploadXLSConstants.MOBILE_3));
			hm.put(DIVendorsUploadXLSConstants.EMAIL_ID_1, prop.getProperty(DIVendorsUploadXLSConstants.EMAIL_ID_1));
			hm.put(DIVendorsUploadXLSConstants.EMAIL_ID_2, prop.getProperty(DIVendorsUploadXLSConstants.EMAIL_ID_2));
			hm.put(DIVendorsUploadXLSConstants.WEBSITE, prop.getProperty(DIVendorsUploadXLSConstants.WEBSITE));
			hm.put(DIVendorsUploadXLSConstants.ENROLLED, prop.getProperty(DIVendorsUploadXLSConstants.ENROLLED));
			hm.put(DIVendorsUploadXLSConstants.ACTIVE, prop.getProperty(DIVendorsUploadXLSConstants.ACTIVE));
			
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
