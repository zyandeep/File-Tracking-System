// this servlet is responsible for creating the admin account
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.*;
import org.apache.commons.codec.digest.DigestUtils;

import bca.fts.util.PasswordValidator;
import bca.fts.dao.AdminLoginCheckDao;

public class AdminRegistration extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(AdminRegistration.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confPass = request.getParameter("confPass");

        int row = 0;

        // check for null values, illigal access
        if (username == null || password == null || confPass == null) {
            response.sendRedirect("/FTS/admin/");
        } else {
            if (username.isEmpty() || password.isEmpty() || confPass.isEmpty()) {

                request.setAttribute("errorMessage", "You need to fill up all the form data");
                request.getRequestDispatcher("install.jsp").forward(request, response);
            } else {

                // verify the given password
                String result = PasswordValidator.validatePassword(password);

                if (result.isEmpty() && password.equals(confPass)) {

                    // register the admin
                    row = AdminLoginCheckDao.regisAdmin(username.trim(), DigestUtils.md5Hex(password));

                    if (row == 1) {

                        logger.info("admin account has been created by user " + username);

                        // tell the app context about admin registration
                        getServletContext().setAttribute("adminAccount", 1);

                        request.setAttribute("message", "Admin account has been created. You can now log in");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    } else {
                        String errMsg = " ";

                        if (row == -1) {
                            errMsg = "Error has occured in the database operation!";
                        } else {
                            errMsg = "Admin account couldn't be created";
                        }

                        request.setAttribute("errorMessage", errMsg);
                        request.getRequestDispatcher("install.jsp").forward(request, response);

                    }
                } else if (result.isEmpty() == false) {

                    request.setAttribute("errorMessage", result);
                    request.getRequestDispatcher("install.jsp").forward(request, response);
                } else {

                    request.setAttribute("errorMessage", "Password and confirm password must be matched");
                    request.getRequestDispatcher("install.jsp").forward(request, response);
                }

            }
        }

    }

}
