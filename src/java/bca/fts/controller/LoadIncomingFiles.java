// this servlet is responsible for displaying new incoming files to a branch
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
import org.apache.log4j.*;
import java.util.*;

import bca.fts.dao.*;
import bca.fts.model.*;

public class LoadIncomingFiles extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(LoadIncomingFiles.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        BranchBean branch = (BranchBean) session.getAttribute("branch");
        String user = branch.getUsername();

        logger.info(user + " checked the inbox");

        ArrayList<IncomingFileBean> files = null;
        files = BranchReportDao.giveIncomingFiles(branch.getOfficeName());

        if (files == null) {

            // database-error occured
            request.setAttribute("errorMessage", "Error has occured in the database operation!");
            request.getRequestDispatcher("welcomeBranch.jsp").forward(request, response);
        } else if (files.isEmpty()) {

            // so that the "Inbox view" knows there is no files to display in the inbox
            session.setAttribute("incomingFiles", null);

            // so that, the "action message" of the last file is shown in the Inbox
            if (request.getAttribute("message") != null || request.getAttribute("errorMessage") != null) {
                request.getRequestDispatcher("branchInbox.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "No incoming file found");
                request.getRequestDispatcher("welcomeBranch.jsp").forward(request, response);
            }

        } else {

            // get the list of branches and set them in session scope, instead of request scope, so that data 
            // should be available during various "Recieve file" requests.
            // sort the list first in Ascending order based on the date of date-of-send;
            Collections.sort(files, new Comparator<IncomingFileBean>() {

                @Override
                public int compare(IncomingFileBean o1, IncomingFileBean o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });

            // sort the list first in Ascending order;
            Collections.reverse(files);

            session.setAttribute("incomingFiles", files);
            request.getRequestDispatcher("branchInbox.jsp").forward(request, response);
        }

    }

}
