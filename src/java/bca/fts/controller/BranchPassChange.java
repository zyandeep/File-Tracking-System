// this servlet is responsible for changing a branch account password
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.*;

import bca.fts.dao.*;
import bca.fts.model.*;
import bca.fts.util.PasswordValidator;

public class BranchPassChange extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(BranchPassChange.class);

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

        String oldPass = request.getParameter("oldPass");
        String newPass = request.getParameter("newPass");
        String confPass = request.getParameter("confPass");

        int result = 0;

        // check for null values, illigal access
        if (oldPass == null || newPass == null || confPass == null) {
            request.getRequestDispatcher("welcomeBranch.jsp").forward(request, response);
        } else {
            // just return to the "VIEW" if empty form
            if (oldPass.isEmpty() || newPass.isEmpty() || confPass.isEmpty()) {

                request.setAttribute("errorMessage", "Form fileds can't be empty");
                request.getRequestDispatcher("passChange.jsp").forward(request, response);
            } else {

                // query the database via "DAO"
                branch = BranchLoginCheckDao.authenticate(user, DigestUtils.md5Hex(oldPass));
                String passError = PasswordValidator.validatePassword(newPass);

                // verify the old password  
                if (branch == null || branch.getOfficeName() == null) {

                    if (branch == null) {
                        request.setAttribute("errorMessage", "Error has occured in the database operation!");
                    } else {
                        request.setAttribute("errorMessage", "Old password didn't match");
                    }

                    request.getRequestDispatcher("passChange.jsp").forward(request, response);
                } // verify the new password
                else if (passError.isEmpty() == false) {

                    request.setAttribute("errorMessage", passError);
                    request.getRequestDispatcher("passChange.jsp").forward(request, response);
                } // confirm the new password
                else if (newPass.equals(confPass) == false) {

                    request.setAttribute("errorMessage", "New and confirm passwords didn't match");
                    request.getRequestDispatcher("passChange.jsp").forward(request, response);
                } // every thing is fine, change the password
                else {
                    result = BranchLoginCheckDao.changePassword(user, DigestUtils.md5Hex(newPass));

                    if (result == 1) {
                        logger.info(user + " changed the password");

                        request.setAttribute("message", "Password has been changed succefully");
                        request.getRequestDispatcher("welcomeBranch.jsp").forward(request, response);
                    } else if (result == 0) {
                        request.setAttribute("errorMessage", "Password couldn't be changed");
                        request.getRequestDispatcher("passChange.jsp").forward(request, response);
                    } else {
                        request.setAttribute("errorMessage", "Error has occured in the database operation!");
                        request.getRequestDispatcher("passChange.jsp").forward(request, response);
                    }
                }

            }
        }

    }
}
