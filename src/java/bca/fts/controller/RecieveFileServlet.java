// this servlet is responsible for recieving a file and changing its status to "PROCESSING"
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
import org.apache.log4j.*;

import bca.fts.dao.*;
import bca.fts.model.BranchBean;
import bca.fts.model.IncomingFileBean;

public class RecieveFileServlet extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(RecieveFileServlet.class);

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

        String id = request.getParameter("id");

        // illigal access
        if (id == null) {
            request.getRequestDispatcher("welcomeBranch.jsp").forward(request, response);
        } else {
            int fileID = Integer.valueOf(id.trim());

            int result = 0;

            // get the incoming files list from session and make that receive_file_status = true
            ArrayList<IncomingFileBean> files = (ArrayList<IncomingFileBean>) session.getAttribute("incomingFiles");

            for (IncomingFileBean file : files) {

                if (file.getId() == fileID) {
                    file.setFileRecieved(true);

                    break;
                }

            }

            // change the file's status in Database
            result = FileDao.changeFileStatus(fileID, "PROCESSING");

            // check whether the file is recieved successfully or not
            if (result == 1) {
                logger.info(user + " recieved file having ID " + id);

                request.getRequestDispatcher("branchInbox.jsp").forward(request, response);
            } else {

                request.setAttribute("errorMessage", "File couldn't be recieved successfully");
                request.getRequestDispatcher("branchInbox.jsp").forward(request, response);
            }
        }

    }

}
