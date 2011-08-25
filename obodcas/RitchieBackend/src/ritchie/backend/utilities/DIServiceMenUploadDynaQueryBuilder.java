package ritchie.backend.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DIServiceMenUploadDynaQueryBuilder {
	public static List getServiceMenUploadUpdateQuery(List updateList, String user) {
		List queryList = new ArrayList();

		for (int i = 0; i < updateList.size(); i++) {
			Object obj = null;
			HashMap hm = (HashMap) updateList.get(i);
			StringBuilder updateQuery = new StringBuilder();
			updateQuery.append("UPDATE rit_servicemen SET ");

			updateQuery.append("createdby ='" + user + "'");

			obj = hm.get(DIServiceMenXLSProperties.hm.get(DIServiceMenXLSConstants.SERVICE_MEN_AREA));
			if (obj != null
					&& !((Long) obj).toString().isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("servicemenarea ="
						+ (Long) obj);
			}

			obj = hm.get(DIServiceMenXLSProperties.hm.get(DIServiceMenXLSConstants.SERVICE_MEN_TYPE));
			if (obj != null
					&& !((Long) obj).toString().isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("servicementypeid ="
						+ (Long) obj);
			}

			obj = hm.get(DIServiceMenXLSProperties.hm.get(DIServiceMenXLSConstants.SERVICE_DESCRIPTION));
			if (obj != null
					&& !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("servicedescription ='"
						+ (String) obj + "'");
			}
			
			obj = hm.get(DIServiceMenXLSProperties.hm.get(DIServiceMenXLSConstants.AREA_OF_COVERAGE));
			if (obj != null
					&& !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("areaofcoverage ='"
						+ (String) obj + "'");
			}
			
			obj = hm.get(DIServiceMenXLSProperties.hm.get(DIServiceMenXLSConstants.MOBILE_1));
			if (obj != null
					&& !((Long) obj).toString().isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("mobile1 =" + (Long) obj);
			}

			obj = hm.get(DIServiceMenXLSProperties.hm.get(DIServiceMenXLSConstants.MOBILE_2));
			if (obj != null
					&& !((Long) obj).toString().isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("mobile2 =" + (Long) obj);
			}
			
			obj = hm.get(DIServiceMenXLSProperties.hm.get(DIServiceMenXLSConstants.TELEPHONE_1));
			if (obj != null
					&& !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("telephone ='"
						+ (String) obj + "'");
			}

			
			
			obj = hm.get(DIServiceMenXLSProperties.hm.get(DIServiceMenXLSConstants.VENDOR_ADDRESS));
			if (obj != null
					&& !((String)obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("address ='"
						+ (String)obj + "'");
			}

			obj = hm.get(DIServiceMenXLSProperties.hm.get(DIServiceMenXLSConstants.EMAIL_ID_1));
			if (obj != null
					&& !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("email1 ='" + (String) obj
						+ "'");
			}

			obj = hm.get(DIServiceMenXLSProperties.hm.get(DIServiceMenXLSConstants.EMAIL_ID_2));
			if (obj != null
					&& !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("email2 ='" + (String) obj
						+ "'");
			}

			obj = hm.get(DIServiceMenXLSProperties.hm.get(DIServiceMenXLSConstants.WEBSITE));
			if (obj != null
					&& !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("website ='" + (String) obj
						+ "'");
			}

			obj = hm.get(DIServiceMenXLSProperties.hm.get(DIServiceMenXLSConstants.ENROLLED));
			if (obj != null
					&& !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("enrolled ='" + (String) obj
						+ "'");
			}else{
				updateQuery.append(",");
				updateQuery.append("enrolled ='No'");
			}

			obj = hm.get(DIServiceMenXLSProperties.hm.get(DIServiceMenXLSConstants.ACTIVE));
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
			obj = hm.get(DIServiceMenXLSProperties.hm.get(DIServiceMenXLSConstants.ENROLLMENT_ID));
			updateQuery.append("servicemenid ="+ (Long)obj);
			updateQuery.append(" and ");
			obj = hm.get(DIServiceMenXLSProperties.hm.get(DIServiceMenXLSConstants.SERVICE_MEN_NAME));
			updateQuery.append("servicemen ='"+ (String)obj+ "'");

			queryList.add(updateQuery.toString());
		}
		return queryList;
	}
}