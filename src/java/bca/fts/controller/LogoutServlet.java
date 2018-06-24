// this servlet logs a user out of his/her account
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.*;

import bca.fts.model.*;

public class LogoutServlet extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(LogoutServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        AdminBean admin = (AdminBean) session.getAttribute("admin");
        BranchBean branch = (BranchBean) session.getAttribute("branch");

        String user = null;

        if (admin == null) {

            // this session is for branch
            user = branch.getOfficeName();
        } else {

            // this session is for admin
            user = admin.getUserName();
        }

        // destroy it
        session.invalidate();

        logger.info(user + " successfully logged out");

        response.sendRedirect("/FTS/");
    }

}
