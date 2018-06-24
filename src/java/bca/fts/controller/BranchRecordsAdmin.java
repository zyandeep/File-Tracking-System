// this servlet loads a list of registered branch records and sends it to the appropiate VIEW 
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
import org.apache.log4j.*;

import bca.fts.dao.*;
import bca.fts.model.*;

public class BranchRecordsAdmin extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(BranchRecordsAdmin.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        AdminBean bean = (AdminBean) session.getAttribute("admin");
        String user = bean.getUserName();

        String bName = request.getParameter("bName");

        logger.info(user + " viewed registered branch records");

        // get the branch records and set it to the session
        ArrayList<BranchBean> branchRecords = AdminReportsDao.giveBranches();

        if (branchRecords == null) {

            request.setAttribute("errorMessage", "Error has occured in the database operation!");
            request.getRequestDispatcher("welcomeAdmin.jsp").forward(request, response);
        }
        if (branchRecords.isEmpty()) {

            request.setAttribute("errorMessage", "No branch has been registered yet");
            request.getRequestDispatcher("welcomeAdmin.jsp").forward(request, response);
        } else {
            session.setAttribute("branchRecords", branchRecords);

            // verify if "branchNames" need to be update
            if (bName != null) {

                ArrayList<String> branchNames = new ArrayList<String>();

                // iterate over the branch-records to collect the branch names
                for (BranchBean b : branchRecords) {
                    branchNames.add(b.getOfficeName());
                }

                session.setAttribute("branchNames", branchNames);
            }

            request.getRequestDispatcher("branchList.jsp").forward(request, response);
        }

    }

}
