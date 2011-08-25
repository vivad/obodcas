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

import ritchie.backend.dao.DIServiceMenUploadDAO;
import ritchie.backend.dao.DIXLSDetailsDAO;
import ritchie.backend.exception.InvalidXLSException;
import ritchie.backend.exception.NoRecordsFoundException;
import ritchie.backend.exception.XLSExceedsMaxRecordsException;
import ritchie.backend.utilities.Constants;
import ritchie.backend.utilities.DIServiceMenUploadDynaQueryBuilder;
import ritchie.backend.utilities.DIServiceMenXLSProperties;
import ritchie.backend.utilities.DIXLSUploadUtil;
import ritchie.backend.utilities.POIXLSReader;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DIServiceMenUploadAction extends ActionSupport implements
		SessionAware {
	private static Logger logger = Logger.getLogger(DIServiceMenUploadAction.class);
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
		logger.info("in DIServiceMenUploadAction class");
		setDisubpage(Constants.SERVICE_MEN_LINK_NAME);
		setActionName(ActionContext.getContext().getName());
		logger.info("Uploaded File Name:  " + getUploadFileName());
		if(getUploadFileName() == null){
			addActionError("File was not uploaded. Please submit the form after uploading a file");
			getUploadedFileDetails();
			return "ERROR";
		}
		File deleteFileName = null;
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
					deleteFileName = files[i];
					uploadUtil.copyFile(files[i], new File(targetFolder + "/"
							+ getUploadFileName()));
				} catch (IOException ioe) {
					logger.error(ioe.getMessage());
					System.out.println("ioe:  " + ioe);
				}
			}
		}
		HashMap serviceMenMap = null;
		POIXLSReader poiXLSReader = new POIXLSReader();
		List serviceMenXLSList = null;
		try {
			String fileName = targetFolder + "/" + getUploadFileName();
			serviceMenXLSList = poiXLSReader.processXLS(fileName, DIServiceMenXLSProperties.hm);
			DIXLSDetailsDAO xlsDetails = new DIXLSDetailsDAO();
			DIServiceMenUploadDAO dao = new DIServiceMenUploadDAO();
			String xlsSequenceNumber = xlsDetails.getXLSSequenceNumber(Constants.UPLOAD_TYPE_SERVICE_MEN);
			boolean updateStatus = false;
			logger.info("xlsSequenceNumber:  " + xlsSequenceNumber);

			String fileStatus = Constants.UPLOADED_XLS_INPROGRESS_STATUS;
			if (serviceMenXLSList.size() == 0) {
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
					Map insertAndUpdateMap = uploadUtil.getUploadXLSInsertAndUpdateRecords(serviceMenXLSList,Constants.SERVICE_MEN_LINK_NAME);
					boolean recordInserionStatus = true;
					List insertList = (List)insertAndUpdateMap.get("InsertList");
					List updateList = (List)insertAndUpdateMap.get("UpdateList");
					if(insertList.size() > 0){
						recordInserionStatus = dao
						.insertUploadedXLSRecords(insertList, username);
					}
					updateList = dao.getUploadXLSValidUpdateRecords(updateList, username);
					
					List updateQueryList = DIServiceMenUploadDynaQueryBuilder
							.getServiceMenUploadUpdateQuery(updateList,
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
						logger.info("***********************************");
						logger.info("Check what to do on this case in DIServiceMenUploadAction. file details update problem");
						logger.info("***********************************");
						addActionMessage("There might be a problem in uploaded file processing. Plz contact admin");
					}
				}
				getUploadedFileDetails();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				//uploadUtil.deleteFile(deleteFileName);
				throw e;
			}
		} catch (FileNotFoundException fnfe) {
			logger.error(fnfe.getMessage());
			getUploadedFileDetails();
			//uploadUtil.deleteFile(deleteFileName);
			throw fnfe;
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
			getUploadedFileDetails();
			//uploadUtil.deleteFile(deleteFileName);
			throw ioe;
		} catch (XLSExceedsMaxRecordsException e) {
			getUploadedFileDetails();
			//uploadUtil.deleteFile(deleteFileName);
			logger.error("XLSExceedsMaxRecordsException: "
					+ e.getMessage());
			addActionError(e.getMessage());
		} catch (InvalidXLSException e) {
			getUploadedFileDetails();
			//uploadUtil.deleteFile(deleteFileName);
			logger.error("InvalidXLSException: " + e.getMessage());
			addActionError(e.getMessage());
		} catch (NoRecordsFoundException e) {
			getUploadedFileDetails();
			//uploadUtil.deleteFile(deleteFileName);
			logger.error("NoRecordsFoundException: " + e.getMessage());
			addActionError(e.getMessage());
		}catch (Exception e) {
			logger.error(e.getMessage());
			getUploadedFileDetails();
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
