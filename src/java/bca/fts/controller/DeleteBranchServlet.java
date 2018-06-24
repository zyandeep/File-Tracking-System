// this servlet takes the ID of the branch and delete it throug BranchDao
package bca.fts.controller;

import bca.fts.dao.AdminReportsDao;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
import org.apache.log4j.*;

import bca.fts.dao.BranchDao;
import bca.fts.model.AdminBean;
import bca.fts.model.BranchBean;

public class DeleteBranchServlet extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(DeleteBranchServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        AdminBean bean = (AdminBean) session.getAttribute("admin");
        String user = bean.getUserName();

        String id = request.getParameter("id");
        int row = 0;
        String errMessage = "";

        if (id == null) {
            request.getRequestDispatcher("welcomeAdmin.jsp").forward(request, response);
        } else {
            row = BranchDao.deleteBranch(Integer.valueOf(id));

            // check if branch record is deleted or not
            if (row == 1) {

                logger.info(user + " deleted the branch with ID " + id);

                String message = "Branch ID: " + id + " has been deleted successfully";

                // load the new branch records from the database
                ArrayList<BranchBean> branchRecords = AdminReportsDao.giveBranches();
                ArrayList<String> branchNames = new ArrayList<String>();

                if (branchRecords != null) {

                    // iterate over the branch-records to collect the branch names
                    for (BranchBean b : branchRecords) {
                        branchNames.add(b.getOfficeName());
                    }

                    session.setAttribute("branchRecords", branchRecords);
                    session.setAttribute("branchNames", branchNames);

                    request.setAttribute("message", message);
                    request.getRequestDispatcher("branchList.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Error has occured the database operation!");
                    request.getRequestDispatcher("branchList.jsp").forward(request, response);
                }

            } else {
                // record not found, so error meassage 

                if (errMessage.isEmpty()) {
                    errMessage = "Branch couldn't be deleted";
                }

                request.setAttribute("errorMessage", errMessage);
                request.getRequestDispatcher("branchList.jsp").forward(request, response);
            }
        }

    }
}
