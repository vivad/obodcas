package ritchie.backend.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import ritchie.backend.dao.DIServiceMenDataEntryDAO;
import ritchie.backend.dao.DIVendorDataEntryDAO;
import ritchie.backend.dao.DIXLSDetailsDAO;
import ritchie.backend.utilities.Constants;
import ritchie.backend.utilities.DIServiceMenXLSConstants;
import ritchie.backend.utilities.DIVendorsUploadXLSConstants;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DIServiceMenDataEntryAction extends ActionSupport implements
		SessionAware{
	private static Logger logger = Logger.getLogger(DIServiceMenDataEntryAction.class);
	private String disubpage;
	private List<Map<String, Object>> fileDetailsList;
	private Map<String, String> vendorareamap;
	private Map map;
	private String actionName;

	private String vendorarea;
	private String areacode;
	
	private String mobile1;
	private String email1;
	private String servicementype;
	private String servicementypecode;
	private String mobile2;
	private String email2;
	private String name;
	private String website;
	private String description;
	private String coveragearea;
	private String address;
	private String mapdata;
	private String telephone;
	private String serviceenrolledstatus;
	private String activestatus;
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

	public String getServiceenrolledstatus() {
		return serviceenrolledstatus;
	}

	public void setServiceenrolledstatus(String serviceenrolledstatus) {
		this.serviceenrolledstatus = serviceenrolledstatus;
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

	public String getServicementype() {
		return servicementype;
	}

	public void setServicementype(String servicementype) {
		this.servicementype = servicementype;
	}

	public String getServicementypecode() {
		return servicementypecode;
	}

	public void setServicementypecode(String servicementypecode) {
		this.servicementypecode = servicementypecode;
	}

	public String getCoveragearea() {
		return coveragearea;
	}

	public void setCoveragearea(String coveragearea) {
		this.coveragearea = coveragearea;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String execute() throws SQLException{
		logger.info("in DIServiceMenDataEntryAction");
		setDisubpage(Constants.SERVICE_MEN_LINK_NAME);
		setActionName(ActionContext.getContext().getName());

		if (getSearchbutton()!= null && getSearchbutton().equalsIgnoreCase("Search")) {
			logger.info("in search");
			if (getIdforsearch().trim().length() == 0) {
				addActionError("Please enter a service men id to search");
				getUploadedFileDetails();
				return ERROR;
			}else if(getIdforsearch().trim().length() != 0) {
				try{
					Long.parseLong(getIdforsearch().trim());
				}catch(NumberFormatException e){
					logger.error(e.getMessage());
					addActionError("Please enter a valid service men id to search");
					getUploadedFileDetails();
					return ERROR;
				}
			}
			
			if(getIdforsearch().trim().length() != 12) {
				addActionError("Please enter a valid service men id to search");
				getUploadedFileDetails();
				return ERROR;
			}

			logger.info("idforsearch:  " +idforsearch);
			getSession().put("enrollmentno", idforsearch);
			DIServiceMenDataEntryDAO dao = new DIServiceMenDataEntryDAO();
			Map<String, String> serviceMenDetails = dao
					.getServiceMenDetails(idforsearch);
			
			if(serviceMenDetails.get(DIServiceMenXLSConstants.SERVICE_MEN_AREA_NAME) == null){
				addActionError("No record found for the enrollment id: "+idforsearch);
				getUploadedFileDetails();
				return ERROR;
			}
			setVendorarea(serviceMenDetails.get(DIServiceMenXLSConstants.SERVICE_MEN_AREA_NAME));
			setServicementype(serviceMenDetails
			.get(DIServiceMenXLSConstants.SERVICE_MEN_TYPE_NAME));
			setName(serviceMenDetails
					.get(DIServiceMenXLSConstants.SERVICE_MEN_NAME));
			setDescription(serviceMenDetails
					.get(DIServiceMenXLSConstants.SERVICE_DESCRIPTION));
			setCoveragearea(serviceMenDetails
					.get(DIServiceMenXLSConstants.AREA_OF_COVERAGE));
			setAddress(serviceMenDetails
					.get(DIServiceMenXLSConstants.VENDOR_ADDRESS));
			setMapdata(serviceMenDetails
					.get(DIServiceMenXLSConstants.MAP_DATA));
			setMobile1(serviceMenDetails
					.get(DIServiceMenXLSConstants.MOBILE_1));
			setMobile2(serviceMenDetails
					.get(DIServiceMenXLSConstants.MOBILE_2));
			setTelephone(serviceMenDetails
					.get(DIServiceMenXLSConstants.TELEPHONE_1));
			setEmail1(serviceMenDetails
					.get(DIServiceMenXLSConstants.EMAIL_ID_1));
			setEmail2(serviceMenDetails
					.get(DIServiceMenXLSConstants.EMAIL_ID_2));
			setWebsite(serviceMenDetails
					.get(DIServiceMenXLSConstants.WEBSITE));
			setServiceenrolledstatus(serviceMenDetails
					.get(DIServiceMenXLSConstants.ENROLLED));
			setActivestatus(serviceMenDetails
					.get(DIServiceMenXLSConstants.ACTIVE));
		} else {
			logger.info("not in search");
			
			if (getVendorarea().trim().length() == 0) {
				addActionError("Area is required");
				getUploadedFileDetails();
				return ERROR;
			}

			if (getServicementype().trim().length() == 0) {
				addActionError("Service men type is required");
				getUploadedFileDetails();
				return ERROR;
			}

			if (getName().trim().length() == 0) {
				addActionError("Name is required");
				getUploadedFileDetails();
				return ERROR;
			}
			
			if (getDescription().trim().length() == 0) {
				addActionError("Service description is required");
				getUploadedFileDetails();
				return ERROR;
			}
			
			if (getCoveragearea().trim().length() == 0) {
				addActionError("Area of coverage is required");
				getUploadedFileDetails();
				return ERROR;
			}

			if (getMobile1().trim().length() == 0) {
				addActionError("Mobile 1 is required");
				getUploadedFileDetails();
				return ERROR;
			}
			
			if (getTelephone().trim().length() == 0) {
				addActionError("Telephone is required");
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
			logger.info("servicementype:  " + servicementype);
			logger.info("servicementypecode:  " + servicementypecode);
			logger.info("mobile2:  " + mobile2);
			logger.info("name:  " + name);
			logger.info("website:  " + website);
			logger.info("description:  " + description);
			logger.info("area of coverage:  " + coveragearea);
			logger.info("address:  " + address);
			logger.info("mapdata:  " + mapdata);
			logger.info("telephone:  " + telephone);
			logger.info("serviceenrolledstatus:  " + serviceenrolledstatus);
			logger.info("activestatus:  " + activestatus);
			logger.info("idforsearch:  " + idforsearch);
			
			Map<String, String> map = new HashMap<String, String>();
			
			map.put(DIServiceMenXLSConstants.SERVICE_MEN_NAME, getName());
			map.put(DIServiceMenXLSConstants.SERVICE_DESCRIPTION,
					getDescription());
			map.put(DIServiceMenXLSConstants.AREA_OF_COVERAGE,
					getCoveragearea());
			map.put(DIServiceMenXLSConstants.VENDOR_ADDRESS,
					getAddress());
			map.put(DIServiceMenXLSConstants.MAP_DATA, getMapdata());
			map.put(DIServiceMenXLSConstants.MOBILE_1, getMobile1());
			map.put(DIServiceMenXLSConstants.MOBILE_2, getMobile2());
			map.put(DIServiceMenXLSConstants.TELEPHONE_1,
					getTelephone());
			map.put(DIServiceMenXLSConstants.EMAIL_ID_1, getEmail1());
			map.put(DIServiceMenXLSConstants.EMAIL_ID_2, getEmail2());
			map.put(DIServiceMenXLSConstants.WEBSITE, getWebsite());
			map.put(DIServiceMenXLSConstants.ENROLLED,getServiceenrolledstatus());
			map.put(DIServiceMenXLSConstants.ACTIVE,getActivestatus());
			

			DIServiceMenDataEntryDAO dao = new DIServiceMenDataEntryDAO();
			
			String enrollmentNo = null;
			if(getSession().get("enrollmentno") != null){
				enrollmentNo = getSession().get("enrollmentno").toString();
			}
			
			logger.info("enrollmentNo::   "+enrollmentNo);
			
			String areaCode1 = new DIVendorDataEntryDAO().getVendorArea(vendorarea).firstKey();
			logger.info("areaCode1:::  "+areaCode1);
			map.put(DIVendorsUploadXLSConstants.VENDOR_AREA, areaCode1);
			
			String serviceMenTypeCode = dao.getServiceMenType(getServicementype()).firstKey();
			logger.info("serviceMenTypeCode:::  "+serviceMenTypeCode);
			map.put(DIServiceMenXLSConstants.SERVICE_MEN_TYPE, serviceMenTypeCode);
			
			if(enrollmentNo == null){
				String status = dao.insertSingleServiceMenData(map, approved, draft,
						(String) getSession().get("username"));
				if (!status.equalsIgnoreCase("FAILURE")) {
					addActionMessage("Service men data successfully stored. Please note the enrollment id: "
							+ status);
				} else {
					addActionError("Service men data was not stored");
				}
			}else{
				String status = dao.updateSingleServiceMenData(map, approved, draft, 
						(String) getSession().get("username"),enrollmentNo);
				if (!status.equalsIgnoreCase("FAILURE")) {
					addActionMessage("Service men data successfully updated for the enrollment id: "
							+ status);
				} else {
					addActionError("Service men data was not updated");
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
