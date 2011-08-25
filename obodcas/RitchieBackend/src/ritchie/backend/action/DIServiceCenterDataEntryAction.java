package ritchie.backend.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import ritchie.backend.dao.DIServiceCenterDataEntryDAO;
import ritchie.backend.dao.DIVendorDataEntryDAO;
import ritchie.backend.dao.DIXLSDetailsDAO;
import ritchie.backend.utilities.Constants;
import ritchie.backend.utilities.DIServiceCentersXLSConstants;
import ritchie.backend.utilities.DIVendorsUploadXLSConstants;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DIServiceCenterDataEntryAction extends ActionSupport implements
		SessionAware{
	private static Logger logger = Logger.getLogger(DIServiceCenterDataEntryAction.class);
	private String disubpage;
	private List<Map<String, Object>> fileDetailsList;
	private Map<String, String> vendorareamap;
	private Map map;
	private String actionName;

	private String vendorarea;
	private String areacode;
	
	private String mobile1;
	private String email1;
	private String servicecentertype;
	private String servicecentertypecode;
	private String mobile2;
	private String email2;
	private String name;
	private String mobile3;
	private String website;
	private String description;
	private String address;
	private String mapdata;
	private String contactperson1;
	private String contactperson2;
	private String telephone1;
	private String telephone2;
	private String telephone3;
	private String activestatus;
	private String catalogueenrolledstatus;
	private String idforsearch;

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

	public String getMobile1() {
		return mobile1;
	}

	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getServicecentertype() {
		return servicecentertype;
	}

	public void setServicecentertype(String servicecentertype) {
		this.servicecentertype = servicecentertype;
	}

	public String getServicecentertypecode() {
		return servicecentertypecode;
	}

	public void setServicecentertypecode(String servicecentertypecode) {
		this.servicecentertypecode = servicecentertypecode;
	}

	public String getMobile2() {
		return mobile2;
	}

	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile3() {
		return mobile3;
	}

	public void setMobile3(String mobile3) {
		this.mobile3 = mobile3;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMapdata() {
		return mapdata;
	}

	public void setMapdata(String mapdata) {
		this.mapdata = mapdata;
	}

	public String getContactperson1() {
		return contactperson1;
	}

	public void setContactperson1(String contactperson1) {
		this.contactperson1 = contactperson1;
	}

	public String getContactperson2() {
		return contactperson2;
	}

	public void setContactperson2(String contactperson2) {
		this.contactperson2 = contactperson2;
	}

	public String getTelephone1() {
		return telephone1;
	}

	public void setTelephone1(String telephone1) {
		this.telephone1 = telephone1;
	}

	public String getTelephone2() {
		return telephone2;
	}

	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
	}

	public String getTelephone3() {
		return telephone3;
	}

	public void setTelephone3(String telephone3) {
		this.telephone3 = telephone3;
	}

	public String getActivestatus() {
		return activestatus;
	}

	public void setActivestatus(String activestatus) {
		this.activestatus = activestatus;
	}

	public String getIdforsearch() {
		return idforsearch;
	}

	public void setIdforsearch(String idforsearch) {
		this.idforsearch = idforsearch;
	}

	public String getCatalogueenrolledstatus() {
		return catalogueenrolledstatus;
	}

	public void setCatalogueenrolledstatus(String catalogueenrolledstatus) {
		this.catalogueenrolledstatus = catalogueenrolledstatus;
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
		logger.info("in DIServiceCenterDataEntryAction");
		setDisubpage(Constants.SERVICE_CENTER_LINK_NAME);
		setActionName(ActionContext.getContext().getName());

		if (getSearchbutton()!= null && getSearchbutton().equalsIgnoreCase("Search")) {
			logger.info("in search");
			if (getIdforsearch().trim().length() == 0) {
				addActionError("Please enter a service center id to search");
				getUploadedFileDetails();
				return ERROR;
			}else if(getIdforsearch().trim().length() != 0) {
				try{
					Long.parseLong(getIdforsearch().trim());
				}catch(NumberFormatException e){
					logger.error(e.getMessage());
					addActionError("Please enter a valid service center id to search");
					getUploadedFileDetails();
					return ERROR;
				}
			}
			
			if(getIdforsearch().trim().length() != 12) {
				addActionError("Please enter a valid service center id to search");
				getUploadedFileDetails();
				return ERROR;
			}

			logger.info("idforsearch:  " +idforsearch);
			getSession().put("enrollmentno", idforsearch);
			DIServiceCenterDataEntryDAO dao = new DIServiceCenterDataEntryDAO();
			Map<String, String> serviceCenterDetails = dao
					.getServiceCenterDetails(idforsearch);
			
			if(serviceCenterDetails.get(DIServiceCentersXLSConstants.AREA_NAME) == null){
				addActionError("No record found for the enrollment id: "+idforsearch);
				getUploadedFileDetails();
				return ERROR;
			}
			setVendorarea(serviceCenterDetails.get(DIServiceCentersXLSConstants.AREA_NAME));
			setServicecentertype(serviceCenterDetails
			.get(DIServiceCentersXLSConstants.SERVICE_CENTER_TYPE_NAME));
			setName(serviceCenterDetails
					.get(DIServiceCentersXLSConstants.SERVICE_CENTER_NAME));
			setDescription(serviceCenterDetails
					.get(DIServiceCentersXLSConstants.SERVICE_DESCRIPTION));
			setAddress(serviceCenterDetails
					.get(DIServiceCentersXLSConstants.ADDRESS));
			setMapdata(serviceCenterDetails
					.get(DIServiceCentersXLSConstants.MAP_DATA));
			setMobile1(serviceCenterDetails
					.get(DIServiceCentersXLSConstants.MOBILE_1));
			setMobile2(serviceCenterDetails
					.get(DIServiceCentersXLSConstants.MOBILE_2));
			setMobile3(serviceCenterDetails
					.get(DIServiceCentersXLSConstants.MOBILE_3));
			setContactperson1(serviceCenterDetails
					.get(DIServiceCentersXLSConstants.CONTACT_PERSON_1));
			setContactperson2(serviceCenterDetails
					.get(DIServiceCentersXLSConstants.CONTACT_PERSON_2));
			setTelephone1(serviceCenterDetails
					.get(DIServiceCentersXLSConstants.TELEPHONE_1));
			setTelephone2(serviceCenterDetails
					.get(DIServiceCentersXLSConstants.TELEPHONE_2));
			setTelephone3(serviceCenterDetails
					.get(DIServiceCentersXLSConstants.TELEPHONE_3));
			setEmail1(serviceCenterDetails
					.get(DIServiceCentersXLSConstants.EMAIL_ID_1));
			setEmail2(serviceCenterDetails
					.get(DIServiceCentersXLSConstants.EMAIL_ID_2));
			setWebsite(serviceCenterDetails
					.get(DIServiceCentersXLSConstants.WEBSITE));
			setCatalogueenrolledstatus(serviceCenterDetails
					.get(DIServiceCentersXLSConstants.ENROLLED));
			setActivestatus(serviceCenterDetails
					.get(DIServiceCentersXLSConstants.ACTIVE));
		} else {
			logger.info("not in search");
			
			if (getVendorarea().trim().length() == 0) {
				addActionError("Area is required");
				getUploadedFileDetails();
				return ERROR;
			}

			if (getServicecentertype().trim().length() == 0) {
				addActionError("Service type is required");
				getUploadedFileDetails();
				return ERROR;
			}

			if (getName().trim().length() == 0) {
				addActionError("Name is required");
				getUploadedFileDetails();
				return ERROR;
			}

			if (getTelephone1().trim().length() == 0) {
				addActionError("Telephone 1 is required");
				getUploadedFileDetails();
				return ERROR;
			}

			if (getMobile1().trim().length() == 0) {
				addActionError("Mobile 1 is required");
				getUploadedFileDetails();
				return ERROR;
			}

			if (getAddress().trim().length() == 0) {
				addActionError("Address is required");
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
			logger.info("mobile1:  " + mobile1);
			logger.info("email1:  " + email1);
			logger.info("email2:  " + email2);
			logger.info("servicecentertype:  " + servicecentertype);
			logger.info("servicecentertypecode:  " + servicecentertypecode);
			logger.info("mobile2:  " + mobile2);
			logger.info("name:  " + name);
			logger.info("mobile3:  " + mobile3);
			logger.info("website:  " + website);
			logger.info("description:  " + description);
			logger.info("address:  " + address);
			logger.info("mapdata:  " + mapdata);
			logger.info("contactperson1:  " + contactperson1);
			logger.info("contactperson2:  " + contactperson2);
			logger.info("telephone1:  " + telephone1);
			logger.info("telephone2:  " + telephone2);
			logger.info("telephone3:  " + telephone3);
			logger.info("activestatus:  " + activestatus);
			logger.info("catalogueenrolledstatus:  "
					+ catalogueenrolledstatus);
			logger.info("idforsearch:  " + idforsearch);
			

			Map<String, String> map = new HashMap<String, String>();
			map.put(DIServiceCentersXLSConstants.SERVICE_CENTER_NAME, getName());
			map.put(DIServiceCentersXLSConstants.SERVICE_DESCRIPTION,
					getDescription());
			map.put(DIServiceCentersXLSConstants.ADDRESS,
					getAddress());
			map.put(DIServiceCentersXLSConstants.MAP_DATA, getMapdata());
			map.put(DIServiceCentersXLSConstants.MOBILE_1, getMobile1());
			map.put(DIServiceCentersXLSConstants.MOBILE_2, getMobile2());
			map.put(DIServiceCentersXLSConstants.MOBILE_3, getMobile3());
			map.put(DIServiceCentersXLSConstants.CONTACT_PERSON_1,
					getContactperson1());
			map.put(DIServiceCentersXLSConstants.CONTACT_PERSON_2,
					getContactperson2());
			map.put(DIServiceCentersXLSConstants.TELEPHONE_1,
					getTelephone1());
			map.put(DIServiceCentersXLSConstants.TELEPHONE_2,
					getTelephone2());
			map.put(DIServiceCentersXLSConstants.TELEPHONE_3,
					getTelephone3());
			map.put(DIServiceCentersXLSConstants.EMAIL_ID_1, getEmail1());
			map.put(DIServiceCentersXLSConstants.EMAIL_ID_2, getEmail2());
			map.put(DIServiceCentersXLSConstants.WEBSITE, getWebsite());
			map.put(DIServiceCentersXLSConstants.ENROLLED,
					getCatalogueenrolledstatus());
			map
					.put(DIServiceCentersXLSConstants.ACTIVE,
							getActivestatus());

			DIServiceCenterDataEntryDAO dao = new DIServiceCenterDataEntryDAO();
			
			String enrollmentNo = null;
			if(getSession().get("enrollmentno") != null){
				enrollmentNo = getSession().get("enrollmentno").toString();
			}
			
			logger.info("enrollmentNo::   "+enrollmentNo);
			
			String areaCode1 = new DIVendorDataEntryDAO().getVendorArea(vendorarea).firstKey();
			logger.info("areaCode1:::  "+areaCode1);
			map.put(DIVendorsUploadXLSConstants.VENDOR_AREA, areaCode1);
			
			String serviceCenterTypeCode = dao.getServiceCenterType(getServicecentertype()).firstKey();
			logger.info("serviceCenterTypeCode:::  "+serviceCenterTypeCode);
			map.put(DIServiceCentersXLSConstants.SERVICE_CENTER_TYPE, serviceCenterTypeCode);
			
			if(enrollmentNo == null){
				String status = dao.insertSingleServiceCenterData(map, approved, draft,
						(String) getSession().get("username"));
				if (!status.equalsIgnoreCase("FAILURE")) {
					addActionMessage("Service center data successfully stored. Please note the enrollment id: "
							+ status);
				} else {
					addActionError("Service center data was not stored");
				}
				//getSession().remove("enrollmentno");
			}else{
				String status = dao.updateSingleServiceCenterData(map, approved, draft, 
						(String) getSession().get("username"),enrollmentNo);
				if (!status.equalsIgnoreCase("FAILURE")) {
					addActionMessage("Service center data successfully updated for the enrollment id: "
							+ status);
				} else {
					addActionError("Service center data was not updated");
				}
				logger.info("Before removing enrollmentno from session");
				getSession().remove("enrollmentno");
				logger.info("After removing enrollmentno from session");
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
