package ritchie.backend.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ritchie.backend.dao.DBConnection;
import ritchie.backend.utilities.DIServiceCentersXLSProperties;
import ritchie.backend.utilities.DIServiceMenXLSProperties;
import ritchie.backend.utilities.DIVendorsUploadXLSProperties;

public class LoadInitialConfiguration extends HttpServlet{
	public static Logger logger = Logger.getLogger(LoadInitialConfiguration.class); 
	public void init(){
		logger.info("Start loading initial configurations");
		new DBConnection();
		new DIVendorsUploadXLSProperties();
		new DIServiceCentersXLSProperties();
		new DIServiceMenXLSProperties();
		logger.info("Initial configurations have been loaded");
	}
	public void doPost(HttpServletRequest req,HttpServletResponse res){
		/*System.out.println("Before invalidating the session");
		req.getSession().invalidate();
		System.out.println("Session has been invalidated");*/
	}
}
