// this servlet is responsible for changing the status of a file
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.*;

import bca.fts.dao.*;
import bca.fts.model.BranchBean;

public class ChangeFileStatus extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(ChangeFileStatus.class);

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
        String status = request.getParameter("fileStatus");
        String page = request.getParameter("page");

        int result = 0;
        String errMeesage = "";

        // check for null values, illigal access
        if (id == null) {
            request.getRequestDispatcher("welcomeBranch.jsp").forward(request, response);
        } else {
            if (status == null) {
                errMeesage = "Select the file status!";

                //for the own sake of satisfaction!!!!
                result = 0;
            } else {
                result = FileDao.changeFileStatus(Integer.valueOf(id), status);
            }

            // check whether the file status is chamged or not
            if (result == 1) {

                logger.info(user + " changed file status to " + status + " of file ID " + id);

                // determine to which the request is need to forward
                if (page == null) {

                    request.setAttribute("message", "File status has been successfully chanded to " + status);
                    request.getRequestDispatcher("listRegFiles.do").forward(request, response);
                } else {

                    request.setAttribute("message", "File ID " + id + " has been successfully CLOSED");
                    request.getRequestDispatcher("loadIncomings.do").forward(request, response);
                }

            } else {

                if (page == null) {

                    if (errMeesage.isEmpty()) {
                        errMeesage = "File status could't be changed to " + status;
                    }

                    request.setAttribute("errorMessage", errMeesage);
                    request.getRequestDispatcher("listRegFiles.do").forward(request, response);
                } else {

                    request.setAttribute("message", "File couldn't be closed");
                    request.getRequestDispatcher("loadIncomings.do").forward(request, response);
                }

            }
        }

    }

}
