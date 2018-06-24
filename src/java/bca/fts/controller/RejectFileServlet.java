// this servlet is responsible for "REJECT FILE" action
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.log4j.*;

import bca.fts.dao.*;
import bca.fts.model.*;

public class RejectFileServlet extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(RejectFileServlet.class);

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

        String causeOfReject = request.getParameter("textArea");
        String id = request.getParameter("id");

        String errMsg = "";
        int result = 0, fileID = 0;

        // illigal access
        if (id == null || causeOfReject == null) {
            request.getRequestDispatcher("welcomeBranch.jsp").forward(request, response);
        } // when a branch rejects a file it must give its reason
        else if (causeOfReject.isEmpty()) {
            request.setAttribute("errorMessage", "You must give the cause of rejection");
            request.getRequestDispatcher("branchInbox.jsp").forward(request, response);
        } else {

            fileID = Integer.valueOf(id.trim());

            ArrayList<IncomingFileBean> files = (ArrayList<IncomingFileBean>) session.getAttribute("incomingFiles");
            IncomingFileBean f = null;

            // get the Itrator of "files"
            Iterator it = files.iterator();

            while (it.hasNext()) {
                f = (IncomingFileBean) it.next();

                if (f.getId() == fileID) {
                    break;
                }
            }

            // get the system's date
            Date today = new Date();

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String dateOfSend = sdf.format(today);

            // get the system's time
            sdf = new SimpleDateFormat("hh:mm:ss a");
            String timeOfSend = sdf.format(today);

            SendFileBean bean = new SendFileBean();
            bean.setFileId(fileID);
            bean.setDateOfSend(dateOfSend);
            bean.setSenderBranch(f.getCurLoc());
            bean.setForwardBranch(f.getSender());
            bean.setRemark(causeOfReject.trim());
            bean.setTimeOfSend(timeOfSend);

            result = FileDao.rejectFile(bean);

            if (result == 1) {
                logger.info(user + " rejected file having ID " + id);

                request.setAttribute("message", "File ID: " + id + " hase been rejected successfully");
                request.getRequestDispatcher("loadIncomings.do").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "File couldn't be rejected");
                request.getRequestDispatcher("branchInbox.jsp").forward(request, response);
            }
        }

    }

}
