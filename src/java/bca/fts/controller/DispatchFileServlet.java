// this servlet takes care of dispatchning a file from one branch to another
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.apache.log4j.*;

import bca.fts.dao.*;
import bca.fts.model.*;

public class DispatchFileServlet extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(DispatchFileServlet.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        BranchBean branch = (BranchBean) session.getAttribute("branch");
        String user = branch.getUsername();

        String id = request.getParameter("id");
        String forwardBranch = request.getParameter("branchToForward");
        String page = request.getParameter("page");

        int row = 0, fileID = 0;
        String errMessage = "";

        // illigal access
        if (id == null && forwardBranch == null) {
            request.getRequestDispatcher("welcomeBranch.jsp").forward(request, response);
        } else {

            if (forwardBranch == null) {
                errMessage = "Please select a branch";

                row = 0;
            } else {
                fileID = Integer.valueOf(id.trim());               //shouldn't throw an exception. Right??

                // get the system's date
                Date today = new Date();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String dateOfSend = sdf.format(today);

                // get the system's time
                sdf = new SimpleDateFormat("hh:mm:ss a");
                String timeOfSend = sdf.format(today);

                SendFileBean bean = new SendFileBean();
                bean.setFileId(fileID);
                bean.setSenderBranch(branch.getOfficeName());
                bean.setForwardBranch(forwardBranch.trim());
                bean.setDateOfSend(dateOfSend);
                bean.setTimeOfSend(timeOfSend);

                row = FileDao.sendFile(bean);
            }

            // check whether file successfully forwarded or not
            if (row == 1) {

                logger.info(user + " sent file having ID " + id + " to branch " + forwardBranch);

                request.setAttribute("message", "File with ID- " + id + " has been successfully forwarded to " + forwardBranch);

                // determine to which the request is need to forward
                if (page == null) {
                    request.getRequestDispatcher("listRegFiles.do").forward(request, response);
                } else {
                    request.getRequestDispatcher("loadIncomings.do").forward(request, response);
                }

            } else {

                if (errMessage.isEmpty()) {
                    errMessage = "File couldn't be forwarded";
                }

                request.setAttribute("errorMessage", errMessage);

                // determine to which VIEW the request is need to forward
                if (page == null) {
                    request.getRequestDispatcher("listRegFiles.do").forward(request, response);
                } else {
                    request.getRequestDispatcher("branchInbox.jsp").forward(request, response);
                }

            }
        }
    }
}
