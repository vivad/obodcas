package ritchie.backend.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ritchie.backend.dao.UserDAO;

public class LoginServlet extends HttpServlet {
	public static Logger logger = Logger.getLogger(LoginServlet.class);

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		logger.info("in LoginServlet");
		String userName = req.getParameter("username");
		String password = req.getParameter("password");

		if (userName != null && userName.trim().length() > 0
				&& password != null && password.trim().length() > 0) {
			Map<String, String> userMap = null;

			try {
				userMap = new UserDAO().authenticateUser(userName, password);

				logger.info("Login Status::  " + userMap.get("status"));
				if (userMap.get("status").equalsIgnoreCase("SUCCESS")) {
					req.getSession().setAttribute("username", userName);
					req.getSession().setAttribute("firstname",
							userMap.get("firstname"));
					req.getSession().setAttribute("lastname",
							userMap.get("lastname"));
					req.getSession().setAttribute("usertype",
							userMap.get("usertype"));
					req.getRequestDispatcher("/pages/dataimport.jsp").forward(
							req, res);
				} else {
					logger.info("in else");
					res.sendRedirect(req.getContextPath() + "/login.jsp?r=2");
				}

			} catch (SQLException sqle) {
				logger.error(sqle);
			}
		} else {
			res.sendRedirect(req.getContextPath() + "/login.jsp?r=1");
		}
	}
}