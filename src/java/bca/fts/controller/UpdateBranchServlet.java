// this servlet gives the specific branch to the view which is needed to be updated
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import org.apache.log4j.*;

import bca.fts.model.*;

public class UpdateBranchServlet extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(UpdateBranchServlet.class);

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

        // illigal access
        if (id == null) {
            request.getRequestDispatcher("welcomeAdmin.jsp").forward(request, response);
        } else {
            BranchBean branch = null;
            int branchID = 0;

            ArrayList<BranchBean> branchRecords = (ArrayList<BranchBean>) session.getAttribute("branchRecords");

            try {
                branchID = Integer.valueOf(id.trim());

                // now get the particular branch record from the session
                Iterator i = branchRecords.iterator();

                while (i.hasNext()) {
                    branch = (BranchBean) i.next();

                    if (branchID == branch.getId()) {
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                logger.error(e);

                // error occured
                branch = null;
            }

            // check if branch record is found or not
            if (branch != null) {

                // set the branch in session and show it via view
            /* branch bean is set in session, instead of request,  so that if user enters wrong data in the showBranchRecord page 
                 the branch record still can be retrieved form the session 
                 */
                session.setAttribute("branchBean", branch);
                request.getRequestDispatcher("showBranchRecord.jsp").forward(request, response);

            } else {

                request.setAttribute("errorMessage", "Record couldn't be retrieved");
                request.getRequestDispatcher("branchList.jsp").forward(request, response);
            }

        }

    }
}
