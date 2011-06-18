package ritchie.backend.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import ritchie.backend.dao.TETemplateDetailsDAO;
import ritchie.backend.exception.EmptyTemplateFoundException;
import ritchie.backend.utilities.Constants;
import ritchie.backend.utilities.DIXLSUploadUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class TETemplateUploadAction extends ActionSupport implements
		SessionAware {
	private static Logger logger = Logger
	.getLogger(TETemplateUploadAction.class);
	private File upload;
	private String uploadContentType;
	private String uploadFileName;
	private Map map;
	private List<Map<String, String>> fileDetailsList;
	private String disubpage;
	private String actionName;
	private boolean filedetailsexist = false;
	private String radio;
	private String category;

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

	public List<Map<String, String>> getFileDetailsList() {
		return fileDetailsList;
	}

	public void setFileDetailsList(List<Map<String, String>> fileDetailsList) {
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
	
	public boolean isFiledetailsexist() {
		return filedetailsexist;
	}

	public void setFiledetailsexist(boolean filedetailsexist) {
		this.filedetailsexist = filedetailsexist;
	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String execute() throws FileNotFoundException, IOException,
			SQLException {
		logger.info("in TETemplateUploadAction class");
		setDisubpage(Constants.TE_ENROLL_TEMPLATE_LINK_NAME);
		setActionName(ActionContext.getContext().getName());
		logger.info("Uploaded File Name:  " + getUploadFileName());
		if(getUploadFileName() == null){
			addActionError("File was not uploaded. Please click upload after uploading a file");
			getUploadedFileDetails();
			return ERROR;
		}
		
		if(getCategory().equalsIgnoreCase("-- select --")){
			addActionError(" Please select a template category");
			getUploadedFileDetails();
			return ERROR;
		}
		
		String srcFolder = Constants.UPLOAD_XLS_SRC;
		String targetFolder = Constants.TE_TEMPLATE_UPLOAD_DEST;
		DIXLSUploadUtil uploadUtil = new DIXLSUploadUtil();
		
		// Coping the uploaded file from the temp location to the permanent
		// location
		File f = new File(srcFolder);
		if (f.isDirectory()) {
			logger.info("inside if");
			File[] files = f.listFiles();
			logger.info("no of files: "+files.length);

			for (int i = 0; i < files.length; i++) {
				logger.info("inside for");
				logger.info("File Name:  "+files[i].getName());
				try {
					uploadUtil.copyFile(files[i], new File(targetFolder + "/"
							+ getUploadFileName()));
				} catch (IOException ioe) {
					logger.error(ioe);
				}
			}
		}
		
		try {
			String fileName = targetFolder + "/" + getUploadFileName();
			
			File templateFile = new File(fileName);
			
			logger.info("Check file size:  "+templateFile);
			
			if(templateFile.isFile()){
				logger.info("File is a file");
				FileInputStream fin = new FileInputStream(templateFile);
				if(fin.available()<=0){
					logger.info("File size <= 0");
					throw new EmptyTemplateFoundException("Uploaded Template is empty");
				}else{
					logger.info("File size > 0");
				}
				fin.close();
			}else{
				logger.info("File is not a file");
			}
				
			TETemplateDetailsDAO templateDetails = new TETemplateDetailsDAO();
			//String xlsSequenceNumber = templateDetails.getTemplateSequenceNumber(Constants.UPLOAD_TYPE_VENDOR);
			String xlsSequenceNumber = templateDetails.getTemplateSequenceNumber();
			logger.info("xlsSequenceNumber:  " + xlsSequenceNumber);

			String fileStatus = Constants.TE_TEMPLATE_UPLOADED_COMPLETED_STATUS;
			
			try {
				String uploadedFileNo = templateDetails.insertUploadedTemplateDetails(
						xlsSequenceNumber, getUploadFileName(), getCategory(),
						(String) getSession().get("username"), fileStatus,"Active");
				if(uploadedFileNo != null && uploadedFileNo.length() > 0){
					String newName = targetFolder + "/" + uploadedFileNo+".html";
					Map<String,String> fileNameMap = new HashMap<String,String>();
					fileNameMap.put("oldfile", fileName);
					fileNameMap.put("newfile", newName);
					ActionContext.getContext().setParameters(fileNameMap);
					
					addActionMessage(getUploadFileName()
							+ " successfully uploaded");
					
				}else{
					logger.info("***********************************");
					logger.info("Check what to do on this case. file details update problem");
					logger.info("***********************************");
					addActionMessage("There might be a problem in uploaded file processing. Plz check it");
				}
				getUploadedFileDetails();
			} catch (SQLException e) {
				throw e;
			}

		} catch (FileNotFoundException fnfe) {
			getUploadedFileDetails();
			throw fnfe;
		}catch (EmptyTemplateFoundException e) {
			getUploadedFileDetails();
			logger.error("EmptyTemplateFoundException: "
					+ e.getMessage());
			addActionError(e.getMessage());
		}catch (IOException ioe) {
			getUploadedFileDetails();
			throw ioe;
		} 
		
		return "SUCCESS";
	}

	public void getUploadedFileDetails() throws SQLException {
		try {
			setFileDetailsList(TETemplateDetailsDAO.getUploadedTemplateDetails(Constants.TE_ENROLL_TEMPLATE_LINK_NAME));
			if(getFileDetailsList().size() > 0){
				setFiledetailsexist(true);
			}
		} catch (SQLException e) {
			throw e;
		}
	}
}
