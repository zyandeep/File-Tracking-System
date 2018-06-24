// this servlet is responsible for changing admin password
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.*;

import bca.fts.dao.*;
import bca.fts.model.*;
import bca.fts.util.PasswordValidator;

public class AdminPasswdChng extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(AdminPasswdChng.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        AdminBean admin = (AdminBean) session.getAttribute("admin");
        String user = admin.getUserName();

        String oldPass = request.getParameter("oldPass");
        String newPass = request.getParameter("newPass");
        String confPass = request.getParameter("confPass");

        int result = 0;

        // check for null values, illigal access
        if (oldPass == null || newPass == null || confPass == null) {
            request.getRequestDispatcher("welcomeAdmin.jsp").forward(request, response);
        } else {
            // just return to the "VIEW" if empty form
            if (oldPass.isEmpty() || newPass.isEmpty() || confPass.isEmpty()) {

                request.setAttribute("errorMessage", "Empty form data");
                request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            } else {

                // temporaly store the md5 digest of the password in the admin bean 
                admin.setPassword(DigestUtils.md5Hex(oldPass));

                // query the database via "DAO"
                result = AdminLoginCheckDao.authenticate(admin);

                // now remove the password from the admin session
                admin.setPassword(null);

                // validate the new password
                String message = PasswordValidator.validatePassword(newPass);

                // verify the old password  
                if (result != 1) {

                    if (result == 0) {
                        request.setAttribute("errorMessage", "Old password didn't match");
                    } else {
                        request.setAttribute("errorMessage", "Error has occured in the database operation!!");
                    }

                    request.getRequestDispatcher("changePassword.jsp").forward(request, response);

                } // verify the new password
                else if (message.isEmpty() == false) {

                    request.setAttribute("errorMessage", message);
                    request.getRequestDispatcher("changePassword.jsp").forward(request, response);
                } else if (newPass.equals(confPass) == false) {

                    request.setAttribute("errorMessage", "New and Confirm password must be matched ");
                    request.getRequestDispatcher("changePassword.jsp").forward(request, response);
                } // every thing is fine, change the password
                else {
                    result = AdminLoginCheckDao.changePassword(DigestUtils.md5Hex(newPass));

                    if (result == 1) {
                        logger.info(user + " changed the old password");

                        request.setAttribute("message", "Password has been changed succefully");
                        request.getRequestDispatcher("welcomeAdmin.jsp").forward(request, response);
                    } else if (result == 0) {

                        request.setAttribute("errorMessage", "Password couldn't be change");
                        request.getRequestDispatcher("changePassword.jsp").forward(request, response);
                    } else {

                        request.setAttribute("errorMessage", "Error has occured in the database operation!");
                        request.getRequestDispatcher("changePassword.jsp").forward(request, response);
                    }
                }

            }

        }

    }
}
