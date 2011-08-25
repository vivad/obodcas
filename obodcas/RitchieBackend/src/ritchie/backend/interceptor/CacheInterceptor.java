package ritchie.backend.interceptor;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CacheInterceptor extends AbstractInterceptor {
	private static Logger logger = Logger.getLogger(CacheInterceptor.class);
     public String intercept(final ActionInvocation invocation){
        String str = "";
        try{
            final ActionContext context = invocation.getInvocationContext();
            HttpServletResponse response = (HttpServletResponse) context.get(
                StrutsStatics.HTTP_RESPONSE);
            if(response != null){
                response.setHeader("Cache-Control",
                    "must-revalidate, no-cache,"
                    +" max-age=0, forua=true, no-store");
                response.setHeader("Pragma", "no-cache");
            }
            logger.info("Cache Cleared...");
            str = invocation.invoke();
         }catch(Exception e){
        	 logger.error(e);
         }
        return str;
    }
}
