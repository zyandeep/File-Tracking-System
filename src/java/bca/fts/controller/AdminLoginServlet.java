// this servlet validate the ADMIN Login credentials
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.*;

import bca.fts.model.*;
import bca.fts.dao.*;
import java.util.ArrayList;

public class AdminLoginServlet extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(AdminLoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String errMsg = "";
        int result = 0;
        AdminBean admin = null;

        logger.info(username + " attempted to log in");

        if (username == null || password == null) {
            errMsg = " ";
            result = 0;
        } else if (username.isEmpty() || password.isEmpty()) {
            errMsg = "Username and password can't be empty";
            result = 0;
        } else {
            admin = new AdminBean();
            admin.setUserName(username.trim());

            // convert the text to md5 digest
            admin.setPassword(DigestUtils.md5Hex(password));

            result = AdminLoginCheckDao.authenticate(admin);

            // don't store the admin password in the session            
            admin.setPassword(null);
        }

        if (result == 1) {

            logger.info(username + " successfully logged in");

            // create the session and set the admin bean in the session
            HttpSession ses = request.getSession(true);
            ses.setAttribute("admin", admin);

            // get the branch names and set them in the session
            ArrayList<String> branchNames = BranchDao.giveBranchNames();

            if (branchNames != null) {
                ses.setAttribute("branchNames", branchNames);
            } else {
                request.setAttribute("errorMessage", "Error has occured the database operation!");
            }

            response.sendRedirect("/FTS/admin/pages/welcomeAdmin.jsp");
        } else if (result == 0) {

            logger.warn("invalid login attempt by user " + username);

            if (errMsg.isEmpty()) {
                errMsg = "Sorry, username and/or password might be incorrect";
            }

            request.setAttribute("errorMessage", errMsg);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Error has occured in the database operation!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }

    }

}
