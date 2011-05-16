package ritchie.backend.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DIVendorsUploadDynaQueryBuilder {
	public static List getVendorsUploadUpdateQuery(List updateList, String user) {
		List queryList = new ArrayList();

		for (int i = 0; i < updateList.size(); i++) {
			Object obj = null;
			HashMap hm = (HashMap) updateList.get(i);
			StringBuilder updateQuery = new StringBuilder();
			updateQuery.append("UPDATE rit_vendors SET ");

			updateQuery.append("createdby ='" + user + "'");

			obj = hm.get(DIVendorsUploadXLSProperties.hm
					.get(DIVendorsUploadXLSConstants.VENDOR_AREA));
			if (obj != null && !((Long) obj).toString().isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("vendorarea =" + (Long) obj);
			}

			obj = hm.get(DIVendorsUploadXLSProperties.hm
					.get(DIVendorsUploadXLSConstants.VENDOR_TYPE));
			if (obj != null && !((Long) obj).toString().isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("vendortype =" + (Long) obj);
			}

			obj = hm.get(DIVendorsUploadXLSProperties.hm
					.get(DIVendorsUploadXLSConstants.VENDOR_DESCRIPTION));
			if (obj != null && !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("vendordescription ='" + (String) obj + "'");
			}

			obj = hm.get(DIVendorsUploadXLSProperties.hm
					.get(DIVendorsUploadXLSConstants.CONTACT_PERSON_1));
			if (obj != null && !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("contactperson1 ='" + (String) obj + "'");
			}

			obj = hm.get(DIVendorsUploadXLSProperties.hm
					.get(DIVendorsUploadXLSConstants.CONTACT_PERSON_2));
			if (obj != null && !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("contactperson2 ='" + (String) obj + "'");
			}

			obj = hm.get(DIVendorsUploadXLSProperties.hm
					.get(DIVendorsUploadXLSConstants.TELEPHONE_1));
			if (obj != null && !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("telephone1 ='" + (String) obj + "'");
			}

			obj = hm.get(DIVendorsUploadXLSProperties.hm
					.get(DIVendorsUploadXLSConstants.TELEPHONE_2));
			if (hm.get("telephone 2") != null
					&& !((String) hm.get("telephone 2")).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("telephone2 ='"
						+ (String) hm.get("telephone 2") + "'");
			}

			obj = hm.get(DIVendorsUploadXLSProperties.hm
					.get(DIVendorsUploadXLSConstants.TELEPHONE_3));
			if (obj != null && !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("telephone3 ='" + (String) obj + "'");
			}

			obj = hm.get(DIVendorsUploadXLSProperties.hm
					.get(DIVendorsUploadXLSConstants.MOBILE_1));
			if (obj != null && !((Long) obj).toString().isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("mobile1 =" + (Long) obj);
			}

			obj = hm.get(DIVendorsUploadXLSProperties.hm
					.get(DIVendorsUploadXLSConstants.MOBILE_2));
			if (obj != null && !((Long) obj).toString().isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("mobile2 =" + (Long) obj);
			}

			obj = hm.get(DIVendorsUploadXLSProperties.hm
					.get(DIVendorsUploadXLSConstants.MOBILE_3));
			if (obj != null && !((Long) obj).toString().isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("mobile3 =" + (Long) obj);
			}

			obj = hm.get(DIVendorsUploadXLSProperties.hm
					.get(DIVendorsUploadXLSConstants.VENDOR_ADDRESS));
			if (obj != null && !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("address ='" + (String) obj + "'");
			}

			obj = hm.get(DIVendorsUploadXLSProperties.hm
					.get(DIVendorsUploadXLSConstants.EMAIL_ID_1));
			if (obj != null && !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("email1 ='" + (String) obj + "'");
			}

			obj = hm.get(DIVendorsUploadXLSProperties.hm
					.get(DIVendorsUploadXLSConstants.EMAIL_ID_2));
			if (obj != null && !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("email2 ='" + (String) obj + "'");
			}

			obj = hm.get(DIVendorsUploadXLSProperties.hm
					.get(DIVendorsUploadXLSConstants.WEBSITE));
			if (obj != null && !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("website ='" + (String) obj + "'");
			}

			obj = hm.get(DIVendorsUploadXLSProperties.hm
					.get(DIVendorsUploadXLSConstants.ENROLLED));
			if (obj != null && !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("enrolled ='" + (String) obj + "'");
			}else{
				updateQuery.append(",");
				updateQuery.append("enrolled ='No'");
			}

			obj = hm.get(DIVendorsUploadXLSProperties.hm
					.get(DIVendorsUploadXLSConstants.ACTIVE));
			if (obj != null && !((String) obj).isEmpty()) {
				updateQuery.append(",");
				updateQuery.append("status ='" + (String) obj + "'");
			}else{
				updateQuery.append(",");
				updateQuery.append("status ='Yes'");
			}

			updateQuery.append("where ");

			obj = hm.get(DIVendorsUploadXLSProperties.hm
					.get(DIVendorsUploadXLSConstants.ENROLLMENT_ID));
			updateQuery.append("vendorid =" + (Long) obj);
			updateQuery.append(" and ");
			obj = hm.get(DIVendorsUploadXLSProperties.hm
					.get(DIVendorsUploadXLSConstants.VENDOR_NAME));
			updateQuery.append("vendorname ='" + (String) obj + "'");

			queryList.add(updateQuery.toString());
		}
		return queryList;
	}
}