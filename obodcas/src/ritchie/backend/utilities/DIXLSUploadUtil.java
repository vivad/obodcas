package ritchie.backend.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class DIXLSUploadUtil {
	private static Logger logger = Logger.getLogger(DIXLSUploadUtil.class);
	public void copyFile(File src, File dst) throws IOException {
		logger.info("in copyFile method");
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dst);

		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}
	
	public boolean deleteFile(File file) throws IOException {
		logger.info("in deleteFile method");
		boolean isFileDeleted = false;
		if(file.isFile()){
			isFileDeleted = file.delete();
		}
		logger.info("isFileDeleted:  "+isFileDeleted);
		return isFileDeleted;
	}

	public Map getUploadXLSInsertAndUpdateRecords(List list,String from) {
		logger.info("in getUploadXLSInsertAndUpdateRecords method");
		Map map = new HashMap();
		List insertList = new ArrayList();
		List updateList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Object enIDObjFromXLS = null;
			if(from.equalsIgnoreCase(Constants.VENDOR_LINK_NAME)){
				enIDObjFromXLS = ((HashMap) list.get(i))
				.get(DIVendorsUploadXLSProperties.hm
						.get(DIVendorsUploadXLSConstants.ENROLLMENT_ID));
			}else if(from.equalsIgnoreCase(Constants.SERVICE_CENTER_LINK_NAME)){
				enIDObjFromXLS = ((HashMap) list.get(i))
				.get(DIServiceCentersXLSProperties.hm
						.get(DIServiceCentersXLSConstants.ENROLLMENT_ID));
			}else if(from.equalsIgnoreCase(Constants.SERVICE_MEN_LINK_NAME)){
				enIDObjFromXLS = ((HashMap) list.get(i))
				.get(DIServiceMenXLSProperties.hm
						.get(DIServiceMenXLSConstants.ENROLLMENT_ID));
			}
			
			if (enIDObjFromXLS == null) {
				logger.info("Enrollment id is null");
				insertList.add(list.get(i));
			}else{
				updateList.add(list.get(i));
			}
		}
		map.put("InsertList", insertList);
		map.put("UpdateList", updateList);
		return map;
	}
}
