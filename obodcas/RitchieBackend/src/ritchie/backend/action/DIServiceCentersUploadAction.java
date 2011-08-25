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

import ritchie.backend.dao.DIServiceCentersUploadDAO;
import ritchie.backend.dao.DIXLSDetailsDAO;
import ritchie.backend.exception.InvalidXLSException;
import ritchie.backend.exception.NoRecordsFoundException;
import ritchie.backend.exception.XLSExceedsMaxRecordsException;
import ritchie.backend.utilities.Constants;
import ritchie.backend.utilities.DIServiceCentersUploadDynaQueryBuilder;
import ritchie.backend.utilities.DIServiceCentersXLSProperties;
import ritchie.backend.utilities.DIXLSUploadUtil;
import ritchie.backend.utilities.POIXLSReader;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DIServiceCentersUploadAction extends ActionSupport implements
		SessionAware {
	private static Logger logger = Logger.getLogger(DIServiceCentersUploadAction.class);
	private File upload;
	private String uploadContentType;
	private String uploadFileName;
	private Map map;
	private List<Map<String, Object>> fileDetailsList;
	private String disubpage;
	private String disubpagename;
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

	public String getDisubpagename() {
		return disubpagename;
	}

	public void setDisubpagename(String disubpagename) {
		this.disubpagename = disubpagename;
	}
	
	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String execute() throws FileNotFoundException, IOException,
			SQLException {
		logger.info("in DIServiceCentersUploadAction class");
		setDisubpage(Constants.SERVICE_CENTER_LINK_NAME);
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
					logger.error(ioe.getMessage());
					System.out.println("ioe:  " + ioe);
				}
			}
		}
		HashMap serviceCentersMap = null;
		POIXLSReader poiXLSReader = new POIXLSReader();
		List serviceCentersXLSList = null;
		try {
			String fileName = targetFolder + "/" + getUploadFileName();
			serviceCentersXLSList = poiXLSReader.processXLS(fileName, DIServiceCentersXLSProperties.hm);
			DIXLSDetailsDAO xlsDetails = new DIXLSDetailsDAO();
			DIServiceCentersUploadDAO dao = new DIServiceCentersUploadDAO();
			String xlsSequenceNumber = xlsDetails.getXLSSequenceNumber(Constants.UPLOAD_TYPE_SERVICE_CENTER);
			boolean updateStatus = false;
			logger.info("xlsSequenceNumber:  " + xlsSequenceNumber);

			String fileStatus = Constants.UPLOADED_XLS_INPROGRESS_STATUS;
			if (serviceCentersXLSList.size() == 0) {
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
					Map insertAndUpdateMap = uploadUtil.getUploadXLSInsertAndUpdateRecords(serviceCentersXLSList,Constants.SERVICE_CENTER_LINK_NAME);
					boolean recordInserionStatus = true;
					List insertList = (List)insertAndUpdateMap.get("InsertList");
					List updateList = (List)insertAndUpdateMap.get("UpdateList");
					if(insertList.size() > 0){
						recordInserionStatus = dao
						.insertUploadedXLSRecords(insertList, username);
					}
					updateList = dao.getUploadXLSValidUpdateRecords(updateList, username);
					
					List updateQueryList = DIServiceCentersUploadDynaQueryBuilder
							.getServiceCentersUploadUpdateQuery(updateList,
									username);
					boolean recordUpdateStatus = dao.updateUploadedXLSRecords(
							updateQueryList, username);
					if (recordInserionStatus && recordUpdateStatus) {
						updateStatus = xlsDetails.updateUploadedXLSDetails(
								uploadedFileNo, Constants.UPLOADED_XLS_COMPLETED_STATUS);
						if(updateStatus){
							addActionMessage(getUploadFileName()
									+ " successfully uploaded");
						}
					} else {
						logger.error("***********************************");
						logger.error("Check what to do on this case in DIServiceCentersUploadAction. file details update problem");
						logger.error("***********************************");
						addActionMessage("There might be a problem in uploaded file processing. Plz contact admin");
					}
				}
				getUploadedFileDetails();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw e;
			}
		} catch (FileNotFoundException fnfe) {
			logger.error(fnfe.getMessage());
			getUploadedFileDetails();
			throw fnfe;
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
			getUploadedFileDetails();
			throw ioe;
		} catch (XLSExceedsMaxRecordsException e) {
			logger.error(e.getMessage());
			getUploadedFileDetails();
			logger.error("XLSExceedsMaxRecordsException: "
					+ e.getMessage());
			addActionError(e.getMessage());
		} catch (InvalidXLSException e) {
			logger.error(e.getMessage());
			getUploadedFileDetails();
			logger.error("InvalidXLSException: " + e.getMessage());
			addActionError(e.getMessage());
		} catch (NoRecordsFoundException e) {
			logger.error(e.getMessage());
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
			logger.error(e.getMessage());
			throw e;
		}
	}
}
