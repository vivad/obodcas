package ritchie.backend.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import ritchie.backend.dao.DIVendorsUploadDAO;
import ritchie.backend.dao.DIXLSDetailsDAO;
import ritchie.backend.exception.InvalidXLSException;
import ritchie.backend.exception.NoRecordsFoundException;
import ritchie.backend.exception.XLSExceedsMaxRecordsException;
import ritchie.backend.utilities.Constants;
import ritchie.backend.utilities.DIVendorsUploadDynaQueryBuilder;
import ritchie.backend.utilities.DIVendorsUploadXLSProperties;
import ritchie.backend.utilities.DIXLSUploadUtil;
import ritchie.backend.utilities.POIXLSReader;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DIVendorsUploadAction extends ActionSupport implements
		SessionAware {
	private static Logger logger = Logger
	.getLogger(DIVendorsUploadAction.class);
	private File upload;
	private String uploadContentType;
	private String uploadFileName;
	private Map map;
	private List<Map<String, Object>> fileDetailsList;
	private String disubpage;
	private String actionName;

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setSession(Map map) {
		this.map = map;
	}

	public Map getSession() {
		return map;
	}

	public List<Map<String, Object>> getFileDetailsList() {
		return fileDetailsList;
	}

	public void setFileDetailsList(List<Map<String, Object>> fileDetailsList) {
		this.fileDetailsList = fileDetailsList;
	}

	public String getDisubpage() {
		return disubpage;
	}

	public void setDisubpage(String disubpage) {
		this.disubpage = disubpage;
	}
	
	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String execute() throws FileNotFoundException, IOException,
			SQLException {
		setDisubpage(Constants.VENDOR_LINK_NAME);
		setActionName(ActionContext.getContext().getName());
		logger.info("Uploaded File Name:  " + getUploadFileName());
		if(getUploadFileName() == null){
			addActionError("File was not uploaded. Please click upload after uploading a file");
			getUploadedFileDetails();
			return "ERROR";
		}
		String srcFolder = Constants.UPLOAD_XLS_SRC;
		String targetFolder = Constants.UPLOAD_XLS_DEST;
		DIXLSUploadUtil uploadUtil = new DIXLSUploadUtil();
		
		// Coping the uploaded file from the temp location to the permanent
		// location
		File f = new File(srcFolder);
		if (f.isDirectory()) {
			File[] files = f.listFiles();

			for (int i = 0; i < files.length; i++) {
				try {
					uploadUtil.copyFile(files[i], new File(targetFolder + "/"
							+ getUploadFileName()));
				} catch (IOException ioe) {
					System.out.println("ioe:  " + ioe);
				}
			}
		}
		
		HashMap vendorMap = null;
		POIXLSReader poiXLSReader = new POIXLSReader();
		List vendorXLSList = null;

		
		try {
			String fileName = targetFolder + "/" + getUploadFileName();
			vendorXLSList = poiXLSReader.processXLS(fileName, DIVendorsUploadXLSProperties.hm);
			DIXLSDetailsDAO xlsDetails = new DIXLSDetailsDAO();
			DIVendorsUploadDAO dao = new DIVendorsUploadDAO();
			String xlsSequenceNumber = xlsDetails.getXLSSequenceNumber(Constants.UPLOAD_TYPE_VENDOR);
			boolean updateStatus = false;
			logger.info("xlsSequenceNumber:  " + xlsSequenceNumber);

			String fileStatus = Constants.UPLOADED_XLS_INPROGRESS_STATUS;
			if (vendorXLSList.size() == 0) {
				fileStatus = Constants.UPLOADED_XLS_REJECTED_STATUS;
			}
			try {
				String username = (String) getSession().get("username");
				String uploadedFileNo = xlsDetails.insertUploadedXLSDetails(
						xlsSequenceNumber, getUploadFileName(), "Backoffice",
						(String) getSession().get("username"), fileStatus);
				if (fileStatus.equalsIgnoreCase(Constants.UPLOADED_XLS_REJECTED_STATUS)) {
					addActionMessage("No Valid record found in the uploaded xls: "
							+ getUploadFileName());
				} else if (fileStatus.equalsIgnoreCase(Constants.UPLOADED_XLS_INPROGRESS_STATUS)) {
					logger.info("uploadedFileNo:  " + uploadedFileNo);
					Map insertAndUpdateMap = uploadUtil.getUploadXLSInsertAndUpdateRecords(vendorXLSList,Constants.VENDOR_LINK_NAME);
					boolean recordInserionStatus = true;
					List insertList = (List)insertAndUpdateMap.get("InsertList");
					List updateList = (List)insertAndUpdateMap.get("UpdateList");
					if(insertList.size() > 0){
						recordInserionStatus = dao
						.insertUploadedXLSRecords(insertList, username);
					}
					updateList = dao.getUploadXLSValidUpdateRecords(updateList, username);
					
					List updateQueryList = DIVendorsUploadDynaQueryBuilder
							.getVendorsUploadUpdateQuery(updateList,
									username);
					boolean recordUpdateStatus = dao.updateUploadedXLSRecords(
							updateQueryList, username);

					if (recordInserionStatus && recordUpdateStatus) {
						updateStatus = xlsDetails.updateUploadedXLSDetails(
								uploadedFileNo,Constants.UPLOADED_XLS_COMPLETED_STATUS);
						if(updateStatus){
							addActionMessage(getUploadFileName()
									+ " successfully uploaded");
						}
					} else {
						logger.info("***********************************");
						logger.info("Check what to do on this case. file details update problem");
						logger.info("***********************************");
						addActionMessage("There might be a problem in uploaded file processing. Plz check it");
					}
				}
				getUploadedFileDetails();
			} catch (SQLException e) {
				throw e;
			}

		} catch (FileNotFoundException fnfe) {
			getUploadedFileDetails();
			throw fnfe;
		} catch (IOException ioe) {
			getUploadedFileDetails();
			throw ioe;
		} catch (XLSExceedsMaxRecordsException e) {
			getUploadedFileDetails();
			logger.error("XLSExceedsMaxRecordsException: "
					+ e.getMessage());
			addActionError(e.getMessage());
		} catch (InvalidXLSException e) {
			getUploadedFileDetails();
			logger.error("InvalidXLSException: " + e.getMessage());
			addActionError(e.getMessage());
		} catch (NoRecordsFoundException e) {
			getUploadedFileDetails();
			logger.error("NoRecordsFoundException: " + e.getMessage());
			addActionError(e.getMessage());
		}
		return "SUCCESS";
	}

	public void getUploadedFileDetails() throws SQLException {
		try {
			setFileDetailsList(DIXLSDetailsDAO.getUploadedXLSDetails());
		} catch (SQLException e) {
			throw e;
		}
	}
}
