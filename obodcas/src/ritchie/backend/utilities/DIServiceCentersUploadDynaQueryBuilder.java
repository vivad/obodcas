package ritchie.backend.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DIServiceCentersUploadDynaQueryBuilder {
	//private static Logger logger = Logger.getLogger(DIServiceCentersUploadDynaQueryBuilder.class);
	public static List getServiceCentersUploadUpdateQuery(List updateList, String user) {
		List queryList = new ArrayList();

		for (int i = 0; i < updateList.size(); i++) {
			Object obj = null;
			HashMap hm = (HashMap) updateList.get(i);
			StringBuilder updateQuery = new StringBuilder();
			updateQuery.append("UPDATE rit_servicecenter SET ");

			updateQuery.append("createdby ='" + user + "'");

			obj = hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.AREA_CODE));
			if (obj != null
					&& !((Long) obj).toString().isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("serviceCenterarea ="
						+ (Long) obj);
			}

			obj = hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.SERVICE_CENTER_TYPE));
			if (obj != null
					&& !((Long) obj).toString().isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("servicecentertype ="
						+ (Long) obj);
			}

			obj = hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.SERVICE_DESCRIPTION));
			if (obj != null
					&& !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("description ='"
						+ (String) obj + "'");
			}
			
			obj = hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.CONTACT_PERSON_1));
			if (obj != null
					&& !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("contactperson1 ='"
						+ (String) obj + "'");
			}

			obj = hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.CONTACT_PERSON_2));
			if (obj != null
					&& !((String)obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("contactperson2 ='"
						+ (String) obj + "'");
			}

			obj = hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.TELEPHONE_1));
			if (obj != null
					&& !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("telephone1 ='"
						+ (String) obj + "'");
			}

			obj = hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.TELEPHONE_2));
			if (obj != null
					&& !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("telephone2 ='"
						+ (String) obj + "'");
			}
			
			obj = hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.TELEPHONE_3));
			if (obj != null
					&& !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("telephone3 ='"
						+ (String) obj + "'");
			}
			
			obj = hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.MOBILE_1));
			if (obj != null
					&& !((Long) obj).toString().isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("mobile1 =" + (Long) obj);
			}

			obj = hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.MOBILE_2));
			if (obj != null
					&& !((Long) obj).toString().isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("mobile2 =" + (Long) obj);
			}

			obj = hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.MOBILE_3));
			if (obj != null
					&& !((Long) obj).toString().isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("mobile3 =" + (Long) obj);
			}
			
			obj = hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.ADDRESS));
			if (obj != null
					&& !((String)obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("address ='"
						+ (String)obj + "'");
			}

			obj = hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.EMAIL_ID_1));
			if (obj != null
					&& !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("email1 ='" + (String) obj
						+ "'");
			}

			obj = hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.EMAIL_ID_2));
			if (obj != null
					&& !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("email2 ='" + (String) obj
						+ "'");
			}

			obj = hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.WEBSITE));
			if (obj != null
					&& !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("website ='" + (String) obj
						+ "'");
			}

			obj = hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.ENROLLED));
			if (obj != null
					&& !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("enrolled ='" + (String) obj
						+ "'");
			}else{
				updateQuery.append(",");
				updateQuery.append("enrolled ='No'");
			}

			obj = hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.ACTIVE));
			if (obj != null
					&& !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("status ='" + (String)obj
						+ "'");
			}else{
				updateQuery.append(",");
				updateQuery.append("status ='Yes'");
			}
			
			updateQuery.append("where ");
			obj = hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.ENROLLMENT_ID));
			updateQuery.append("servicecenterid ="+ (Long)obj);
			updateQuery.append(" and ");
			obj = hm.get(DIServiceCentersXLSProperties.hm.get(DIServiceCentersXLSConstants.SERVICE_CENTER_NAME));
			updateQuery.append("servicecenter ='"+ (String)obj+ "'");

			queryList.add(updateQuery.toString());
		}
		return queryList;
	}
}