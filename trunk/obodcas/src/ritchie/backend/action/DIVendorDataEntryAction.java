package ritchie.backend.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import ritchie.backend.dao.DIVendorDataEntryDAO;
import ritchie.backend.dao.DIXLSDetailsDAO;
import ritchie.backend.utilities.Constants;
import ritchie.backend.utilities.DIVendorsUploadXLSConstants;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DIVendorDataEntryAction extends ActionSupport implements
		SessionAware{
	private static Logger logger = Logger.getLogger(DIVendorDataEntryAction.class);
	private String disubpage;
	private List<Map<String, Object>> fileDetailsList;
	private Map<String, String> vendorareamap;
	private Map map;
	private String actionName;

	private String vendorarea;
	private String areacode;
	private String vendormobile1;
	private String vendoremail1;
	private String vendortype;
	private String vendortypecode;
	private String vendormobile2;
	private String vendoremail2;
	private String vendorname;
	private String vendormobile3;
	private String vendorwebsite;
	private String vendordescription;
	private String vendoraddress;
	private String vendormapdata;
	private String vendorcontactperson1;
	private String vendorcontactperson2;
	private String vendortelephone1;
	private String vendortelephone2;
	private String vendortelephone3;
	private String vendoractivestatus;
	private String catalogueenrolledstatus;
	private String vendoridforsearch;

	private String dataentrybutton;
	private String searchbutton;

	public String getDisubpage() {
		return disubpage;
	}

	public void setDisubpage(String disubpage) {
		this.disubpage = disubpage;
	}

	public List<Map<String, Object>> getFileDetailsList() {
		return fileDetailsList;
	}

	public void setFileDetailsList(List<Map<String, Object>> fileDetailsList) {
		this.fileDetailsList = fileDetailsList;
	}
	
	public Map<String, String> getVendorareamap() {
		return vendorareamap;
	}

	public void setVendorareamap(Map<String, String> vendorareamap) {
		this.vendorareamap = vendorareamap;
	}

	public void setSession(Map map) {
		this.map = map;
	}

	public Map getSession() {
		return map;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getVendormobile1() {
		return vendormobile1;
	}

	public void setVendormobile1(String vendormobile1) {
		this.vendormobile1 = vendormobile1;
	}

	public String getVendoremail1() {
		return vendoremail1;
	}

	public void setVendoremail1(String vendoremail1) {
		this.vendoremail1 = vendoremail1;
	}

	public String getVendortype() {
		return vendortype;
	}

	public void setVendortype(String vendortype) {
		this.vendortype = vendortype;
	}

	public String getVendortypecode() {
		return vendortypecode;
	}

	public void setVendortypecode(String vendortypecode) {
		this.vendortypecode = vendortypecode;
	}

	public String getVendormobile2() {
		return vendormobile2;
	}

	public void setVendormobile2(String vendormobile2) {
		this.vendormobile2 = vendormobile2;
	}

	public String getVendoremail2() {
		return vendoremail2;
	}

	public void setVendoremail2(String vendoremail2) {
		this.vendoremail2 = vendoremail2;
	}

	public String getVendorname() {
		return vendorname;
	}

	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}

	public String getVendormobile3() {
		return vendormobile3;
	}

	public void setVendormobile3(String vendormobile3) {
		this.vendormobile3 = vendormobile3;
	}

	public String getVendorwebsite() {
		return vendorwebsite;
	}

	public void setVendorwebsite(String vendorwebsite) {
		this.vendorwebsite = vendorwebsite;
	}

	public String getVendordescription() {
		return vendordescription;
	}

	public void setVendordescription(String vendordescription) {
		this.vendordescription = vendordescription;
	}

	public String getVendoraddress() {
		return vendoraddress;
	}

	public void setVendoraddress(String vendoraddress) {
		this.vendoraddress = vendoraddress;
	}

	public String getVendormapdata() {
		return vendormapdata;
	}

	public void setVendormapdata(String vendormapdata) {
		this.vendormapdata = vendormapdata;
	}

	public String getVendorcontactperson1() {
		return vendorcontactperson1;
	}

	public void setVendorcontactperson1(String vendorcontactperson1) {
		this.vendorcontactperson1 = vendorcontactperson1;
	}

	public String getVendorcontactperson2() {
		return vendorcontactperson2;
	}

	public void setVendorcontactperson2(String vendorcontactperson2) {
		this.vendorcontactperson2 = vendorcontactperson2;
	}

	public String getVendortelephone1() {
		return vendortelephone1;
	}

	public void setVendortelephone1(String vendortelephone1) {
		this.vendortelephone1 = vendortelephone1;
	}

	public String getVendortelephone2() {
		return vendortelephone2;
	}

	public void setVendortelephone2(String vendortelephone2) {
		this.vendortelephone2 = vendortelephone2;
	}

	public String getVendortelephone3() {
		return vendortelephone3;
	}

	public void setVendortelephone3(String vendortelephone3) {
		this.vendortelephone3 = vendortelephone3;
	}

	public String getVendoractivestatus() {
		return vendoractivestatus;
	}

	public void setVendoractivestatus(String vendoractivestatus) {
		this.vendoractivestatus = vendoractivestatus;
	}

	public String getCatalogueenrolledstatus() {
		return catalogueenrolledstatus;
	}

	public void setCatalogueenrolledstatus(String catalogueenrolledstatus) {
		this.catalogueenrolledstatus = catalogueenrolledstatus;
	}

	public String getVendoridforsearch() {
		return vendoridforsearch;
	}

	public void setVendoridforsearch(String vendoridforsearch) {
		this.vendoridforsearch = vendoridforsearch;
	}

	public String getVendorarea() {
		return vendorarea;
	}

	public void setVendorarea(String vendorarea) {
		this.vendorarea = vendorarea;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public void setDataentrybutton(String dataentrybutton) {
		this.dataentrybutton = dataentrybutton;
	}

	public String getDataentrybutton() {
		return dataentrybutton;
	}

	public void setSearchbutton(String searchbutton) {
		this.searchbutton = searchbutton;
	}

	public String getSearchbutton() {
		return searchbutton;
	}

	public String execute() throws SQLException{
		logger.info("in DIVendorDataEntryAction");
		setDisubpage(Constants.VENDOR_LINK_NAME);
		setActionName(ActionContext.getContext().getName());

		if (getSearchbutton()!= null && getSearchbutton().equalsIgnoreCase("Search")) {
			logger.info("in search");
			if (getVendoridforsearch().trim().length() == 0) {
				addActionError("Please enter a vendor id to search");
				getUploadedFileDetails();
				return ERROR;
			}else if(getVendoridforsearch().trim().length() != 0) {
				try{
					Long.parseLong(getVendoridforsearch().trim());
				}catch(NumberFormatException e){
					logger.error(e.getMessage());
					addActionError("Please enter a valid vendor id to search");
					getUploadedFileDetails();
					return ERROR;
				}
			}
			
			if(getVendoridforsearch().trim().length() != 12) {
				addActionError("Please enter a valid vendor id to search");
				getUploadedFileDetails();
				return ERROR;
			}

			logger.info("vendoridforsearch:  " + vendoridforsearch);
			getSession().put("vendorenrollmentno", vendoridforsearch);
			DIVendorDataEntryDAO dao = new DIVendorDataEntryDAO();
			Map<String, String> vendorDetails = dao
					.getVendorDetails(vendoridforsearch);
			
			if(vendorDetails.get(DIVendorsUploadXLSConstants.VENDOR_AREA) == null){
				addActionError("No record found for the enrollment id: "+vendoridforsearch);
				getUploadedFileDetails();
				return ERROR;
			}
			setVendorarea(vendorDetails.get(DIVendorsUploadXLSConstants.VENDOR_AREA));
			setVendortype(vendorDetails
			.get(DIVendorsUploadXLSConstants.VENDOR_TYPE));
			setVendorname(vendorDetails
					.get(DIVendorsUploadXLSConstants.VENDOR_NAME));
			setVendordescription(vendorDetails
					.get(DIVendorsUploadXLSConstants.VENDOR_DESCRIPTION));
			setVendoraddress(vendorDetails
					.get(DIVendorsUploadXLSConstants.VENDOR_ADDRESS));
			setVendormapdata(vendorDetails
					.get(DIVendorsUploadXLSConstants.MAP_DATA));
			setVendormobile1(vendorDetails
					.get(DIVendorsUploadXLSConstants.MOBILE_1));
			setVendormobile2(vendorDetails
					.get(DIVendorsUploadXLSConstants.MOBILE_2));
			setVendormobile3(vendorDetails
					.get(DIVendorsUploadXLSConstants.MOBILE_3));
			setVendorcontactperson1(vendorDetails
					.get(DIVendorsUploadXLSConstants.CONTACT_PERSON_1));
			setVendorcontactperson2(vendorDetails
					.get(DIVendorsUploadXLSConstants.CONTACT_PERSON_2));
			setVendortelephone1(vendorDetails
					.get(DIVendorsUploadXLSConstants.TELEPHONE_1));
			setVendortelephone2(vendorDetails
					.get(DIVendorsUploadXLSConstants.TELEPHONE_2));
			setVendortelephone3(vendorDetails
					.get(DIVendorsUploadXLSConstants.TELEPHONE_3));
			setVendoremail1(vendorDetails
					.get(DIVendorsUploadXLSConstants.EMAIL_ID_1));
			setVendoremail2(vendorDetails
					.get(DIVendorsUploadXLSConstants.EMAIL_ID_2));
			setVendorwebsite(vendorDetails
					.get(DIVendorsUploadXLSConstants.WEBSITE));
			setCatalogueenrolledstatus(vendorDetails
					.get(DIVendorsUploadXLSConstants.ENROLLED));
			setVendoractivestatus(vendorDetails
					.get(DIVendorsUploadXLSConstants.ACTIVE));
		} else {
			logger.info("not in search");
			if (getVendorarea().trim().length() == 0) {
				addActionError("Vendor area is required");
				getUploadedFileDetails();
				return ERROR;
			}

			if (getVendortype().trim().length() == 0) {
				addActionError("Vendor type is required");
				getUploadedFileDetails();
				return ERROR;
			}

			if (getVendorname().trim().length() == 0) {
				addActionError("Vendor name is required");
				getUploadedFileDetails();
				return ERROR;
			}

			if (getVendortelephone1().trim().length() == 0) {
				addActionError("Vendor telephone 1 is required");
				getUploadedFileDetails();
				return ERROR;
			}

			if (getVendormobile1().trim().length() == 0) {
				addActionError("Vendor mobile 1 is required");
				getUploadedFileDetails();
				return ERROR;
			}

			if (getVendoraddress().trim().length() == 0) {
				addActionError("Vendor address is required");
				getUploadedFileDetails();
				return ERROR;
			}

			int approved = 0;
			int draft = 0;
			if (getDataentrybutton().equalsIgnoreCase("Save")) {
				approved = 1;
				draft = 0;
			} else if (getDataentrybutton().equalsIgnoreCase("Draft")) {
				approved = 0;
				draft = 1;
			} else if (getDataentrybutton().equalsIgnoreCase(
					"Save & Send for Approval")) {
				approved = 0;
				draft = 0;
			}

			logger.info("vendorareaname:  " + vendorarea);
			logger.info("areacode:  " + areacode);
			logger.info("vendormobile1:  " + vendormobile1);
			logger.info("vendoremail1:  " + vendoremail1);
			logger.info("vendoremail2:  " + vendoremail2);
			logger.info("vendortype:  " + vendortype);
			logger.info("vendortypecode:  " + vendortypecode);
			logger.info("vendormobile2:  " + vendormobile2);
			logger.info("vendorname:  " + vendorname);
			logger.info("vendormobile3:  " + vendormobile3);
			logger.info("vendorwebsite:  " + vendorwebsite);
			logger.info("vendordescription:  " + vendordescription);
			logger.info("vendoraddress:  " + vendoraddress);
			logger.info("vendormapdata:  " + vendormapdata);
			logger.info("vendorcontactperson1:  " + vendorcontactperson1);
			logger.info("vendorcontactperson2:  " + vendorcontactperson2);
			logger.info("vendortelephone1:  " + vendortelephone1);
			logger.info("vendortelephone2:  " + vendortelephone2);
			logger.info("vendortelephone3:  " + vendortelephone3);
			logger.info("vendoractivestatus:  " + vendoractivestatus);
			logger.info("catalogueenrolledstatus:  "
					+ catalogueenrolledstatus);
			logger.info("vendoridforsearch:  " + vendoridforsearch);
			

			Map<String, String> map = new HashMap<String, String>();
			map.put(DIVendorsUploadXLSConstants.VENDOR_NAME, getVendorname());
			map.put(DIVendorsUploadXLSConstants.VENDOR_DESCRIPTION,
					getVendordescription());
			map.put(DIVendorsUploadXLSConstants.VENDOR_ADDRESS,
					getVendoraddress());
			map.put(DIVendorsUploadXLSConstants.MAP_DATA, getVendormapdata());
			map.put(DIVendorsUploadXLSConstants.MOBILE_1, getVendormobile1());
			map.put(DIVendorsUploadXLSConstants.MOBILE_2, getVendormobile2());
			map.put(DIVendorsUploadXLSConstants.MOBILE_3, getVendormobile3());
			map.put(DIVendorsUploadXLSConstants.CONTACT_PERSON_1,
					getVendorcontactperson1());
			map.put(DIVendorsUploadXLSConstants.CONTACT_PERSON_2,
					getVendorcontactperson2());
			map.put(DIVendorsUploadXLSConstants.TELEPHONE_1,
					getVendortelephone1());
			map.put(DIVendorsUploadXLSConstants.TELEPHONE_2,
					getVendortelephone2());
			map.put(DIVendorsUploadXLSConstants.TELEPHONE_3,
					getVendortelephone3());
			map.put(DIVendorsUploadXLSConstants.EMAIL_ID_1, getVendoremail1());
			map.put(DIVendorsUploadXLSConstants.EMAIL_ID_2, getVendoremail2());
			map.put(DIVendorsUploadXLSConstants.WEBSITE, getVendorwebsite());
			map.put(DIVendorsUploadXLSConstants.ENROLLED,
					getCatalogueenrolledstatus());
			map
					.put(DIVendorsUploadXLSConstants.ACTIVE,
							getVendoractivestatus());

			DIVendorDataEntryDAO dao = new DIVendorDataEntryDAO();
			
			String vendorEnrollmentNo = null;
			if(getSession().get("vendorenrollmentno") != null){
				vendorEnrollmentNo = getSession().get("vendorenrollmentno").toString();
			}
			
			logger.info("vendorEnrollmentNo::   "+vendorEnrollmentNo);
			
			String areaCode1 = dao.getVendorArea(vendorarea).firstKey();
			logger.info("areaCode1:::  "+areaCode1);
			map.put(DIVendorsUploadXLSConstants.VENDOR_AREA, areaCode1);
			
			String vendorTypeCode1 = dao.getVendorType(vendortype).firstKey();
			logger.info("vendorTypeCode1:::  "+vendorTypeCode1);
			map.put(DIVendorsUploadXLSConstants.VENDOR_TYPE, vendorTypeCode1);
			
			if(vendorEnrollmentNo == null){
				String status = dao.insertSingleVendorData(map, approved, draft,
						(String) getSession().get("username"));
				if (!status.equalsIgnoreCase("FAILURE")) {
					addActionMessage("Vendor data successfully stored. Please note the enrollment id: "
							+ status);
				} else {
					addActionError("Vendor data was not stored");
				}
			}else{
				String status = dao.updateSingleVendorData(map, approved, draft, 
						(String) getSession().get("username"),vendorEnrollmentNo);
				if (!status.equalsIgnoreCase("FAILURE")) {
					addActionMessage("Vendor data successfully updated for the enrollment id: "
							+ status);
				} else {
					addActionError("Vendor data was not updated");
				}
				getSession().remove("vendorenrollmentno");
			}
		}

		getUploadedFileDetails();

		return SUCCESS;
	}

	public void getUploadedFileDetails() throws SQLException {
		try {
			setFileDetailsList(DIXLSDetailsDAO.getUploadedXLSDetails());
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw e;
		}
	}
}
