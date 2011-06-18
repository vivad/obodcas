package ritchie.backend.interceptor;

import java.io.File;
import java.util.Map;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class TemplateRenameInterceptor extends AbstractInterceptor {
	private static Logger logger = Logger.getLogger(TemplateRenameInterceptor.class);
     public String intercept(final ActionInvocation invocation){
        String str = "";
        try{
        	logger.info("before invocation.invoke()");
        	str = invocation.invoke();
        	logger.info("after invocation.invoke()");
            Map<String,String> map = invocation.getInvocationContext().getParameters();
            String src = map.get("oldfile");
            String dest = map.get("newfile");
            
            File oldfile = new File(src);

    		if (!oldfile.exists()) {
    			System.out.println(oldfile+" File or directory does not exist.");
    		}else{
    			File newfile = new File(dest);
        		logger.info("Old File or directory name : " + oldfile);
        		logger.info("New File or directory name : " + newfile);
        		logger.info("is file renamed:  "+oldfile.renameTo(newfile));
    		}
         }catch(Exception e){
        	 logger.error(e);
         }
        return str;
    }
}
