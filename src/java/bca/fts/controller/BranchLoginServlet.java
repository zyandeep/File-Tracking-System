// this servlet checks the branch login credentials and also create notification
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.*;

import bca.fts.model.*;
import bca.fts.dao.*;
import java.util.ArrayList;

public class BranchLoginServlet extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(BranchLoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        BranchBean branch = null;
        String errMessage = "";

        int newFiles = 0;        // to give the branch notifications for newcoming files 

        logger.info(username + " attempted to log in");

        // check for null values, illigal access
        if (username == null || password == null) {
            errMessage = " ";
        } else if (username.isEmpty() || password.isEmpty()) {
            errMessage = "Username and password can't be empty";
        } else {

            // create the md5 digest form the given password
            branch = BranchLoginCheckDao.authenticate(username.trim(), DigestUtils.md5Hex(password));
        }

        // send the request to the appropiate page
        if (branch == null) {                      // either db error or empty data

            // database error
            if (errMessage.isEmpty()) {
                errMessage = "Error has occured in the database operation!";
            }

            request.setAttribute("errorMessage", errMessage);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else if (branch.getOfficeName() == null) {               // empty bean, invalid login

            logger.warn("invalid login attempt by user " + username);

            request.setAttribute("errorMessage", "Sorry, username and/or password might be incorrect");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            // successfull login
            logger.info(username + " successfully logged in");

            // create the session and set the admin bean in the session
            HttpSession session = request.getSession(true);
            session.setAttribute("branch", branch);

            // give notification to the branch if new files are comming 
            newFiles = NotificationDao.giveNotification(branch.getOfficeName());

            if (newFiles > 0) {

                if (newFiles == 1) {
                    session.setAttribute("notification", "You have " + newFiles + " file for processing");
                } else {
                    session.setAttribute("notification", "You have " + newFiles + " files for processing");
                }
            }

            // load the branch names and set them in the session
            ArrayList<String> branchNames = BranchDao.giveBranchNames();

            if (branchNames != null) {
                session.setAttribute("branchList", branchNames);
            } else {
                request.setAttribute("errorMessage", "Error has occured in the database operation!");
            }

            response.sendRedirect("/FTS/branch/welcomeBranch.jsp");
        }

    }

}
