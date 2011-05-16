/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ritchie.backend.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class SessionFilter implements Filter {
	private static Logger logger = Logger.getLogger(SessionFilter.class);
	/**
	 * 
	 * @param req
	 *            ServletRequest
	 * @param res
	 *            ServletResponse
	 * @param chain
	 *            FilterChain
	 * @throws IOException .
	 * @throws ServletException .
	 */
	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res,
			final FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpServletResponse httpRes = (HttpServletResponse) res;
		Object username = httpReq.getSession().getAttribute("username");
		String reqURL = (String) httpReq.getRequestURI();
		logger.info("reqURL:  "+reqURL);

		if (reqURL.equals("/ritchie_backend/")
				|| reqURL.equals("/ritchie_backend")) {
			if ( username != null && !(username.toString().isEmpty())) {
				 ((HttpServletResponse) res).sendRedirect("dataimport.action");
			}
			chain.doFilter(req, res);
		}else if ((reqURL.equals("/ritchie_backend/welcome.action")
				//|| reqURL.equals("/ritchie_backend/login")
				|| reqURL.equals("/ritchie_backend/login.jsp")
				|| reqURL.equals("/ritchie_backend/css/login.css")
				|| reqURL.equals("/ritchie_backend/css/niceforms-default.css")
				|| reqURL.equals("/ritchie_backend/css/style.css")
				|| reqURL.equals("/ritchie_backend/images/logo.gif")
				|| reqURL.equals("/ritchie_backend/images/indeziner_logo.gif")
				|| reqURL.equals("/ritchie_backend/images/login_bg.png")
				|| reqURL.equals("/ritchie_backend/images/help.png")
				|| reqURL.equals("/ritchie_backend/images/bg.jpg")
				|| reqURL.equals("/ritchie_backend/img/input.gif")
				|| reqURL.equals("/ritchie_backend/img/input-left.gif")
				|| reqURL.equals("/ritchie_backend/img/input-right.gif")
				|| reqURL.equals("/ritchie_backend/img/checkbox.gif")
				|| reqURL.equals("/ritchie_backend/img/button.gif")
				|| reqURL.equals("/ritchie_backend/img/button-left.gif")
				|| reqURL.equals("/ritchie_backend/img/button-right.gif"))
				&& username == null){
			 chain.doFilter(req, res);  
		}else if (reqURL.contains("/ritchie_backend/") && username != null && !(username.toString().isEmpty())){
		 chain.doFilter(req, res);  
		}else if (username == null) {
			logger.info("Unauthorized access..");
			String toRedirect = httpReq.getContextPath() + "/login.jsp";
			((HttpServletResponse) res).sendRedirect(toRedirect);
		} else {
			chain.doFilter(req, res);
		}
	}
	
	/**
	 * 
	 * @param config
	 *            object of FilterConfig
	 * @throws ServletException .
	 */
	@Override
	public void init(final FilterConfig config) throws ServletException {
	}

	/**
	 * @ For destroing objects
	 */
	@Override
	public void destroy() {
	}
}
