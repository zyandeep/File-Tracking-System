// this servlet is responsible for registering a new branch
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.*;
import java.util.ArrayList;

import bca.fts.model.*;
import bca.fts.dao.*;
import bca.fts.util.PasswordValidator;

public class AddBranchServlet extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(AddBranchServlet.class);

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
        String bn = request.getParameter("branchName");
        String hn = request.getParameter("headName");
        String un = request.getParameter("username");
        String pass = request.getParameter("password");
        String confPass = request.getParameter("confPass");

        String errMessage = "";
        String digest = null;
        int row = 0;

        // check for null values, illigal access
        if (id == null || bn == null || hn == null || un == null || pass == null || confPass == null) {
            request.getRequestDispatcher("welcomeAdmin.jsp").forward(request, response);
        } else {
            if (bn.isEmpty() || hn.isEmpty() || un.isEmpty() || pass.isEmpty() || confPass.isEmpty()) {

                errMessage = "Form fileds can't be empty";
            } else {

                String passMsg = PasswordValidator.validatePassword(pass);

                // check for the unique branch name
                if (BranchDao.checkUniqueBranchName(bn.trim())) {
                    errMessage = "Branch " + bn + " is already registered";
                } // check for the unique branch name
                else if (BranchDao.checkUniqueUsername(un.trim())) {
                    errMessage = "Username " + un + " is already registered";
                } // validate the password
                else if (passMsg.isEmpty() == false) {
                    errMessage = passMsg;
                } // validate the passwords
                else if (pass.equals(confPass) == false) {
                    errMessage = "Password and Confirm password must be matched";
                } // add the branch record to DB
                else {
                    BranchBean branch = new BranchBean();

                    // Calculates the MD5 digest for the password and returns the value as a 32 character hex string
                    digest = DigestUtils.md5Hex(pass);

                    branch.setId(Integer.valueOf(id.trim()));           // shouldn't throw exception. Right????
                    branch.setOfficeName(bn.trim());
                    branch.setHeadName(hn.trim());
                    branch.setUsername(un.trim());
                    branch.setPassword(digest);

                    row = BranchDao.addBranch(branch);
                }

            }

            if (errMessage.isEmpty()) {

                logger.info(user + " registered branch " + bn);

                request.setAttribute("message", "Branch " + bn + " has been successfully registered ");
                request.getRequestDispatcher("welcomeAdmin.jsp").forward(request, response);

                // load the new branch name list to the session
                ArrayList<String> branchNames = BranchDao.giveBranchNames();

                if (branchNames != null) {
                    request.getSession(false).setAttribute("branchNames", branchNames);
                } else {
                    request.setAttribute("errorMessage", "Error has occured the database operation!");
                }
            } else {

                int branchID = IDDao.getLastID();

                // check the database-related error
                if (branchID == -1) {

                    if (!errMessage.equalsIgnoreCase("Error has occured in the database operation!")) {
                        errMessage += "<br />Error has occured in the database operation!";
                    }
                }

                request.setAttribute("branchID", ++branchID);
                request.setAttribute("errMessage", errMessage);

                request.getRequestDispatcher("branchRegister.jsp").forward(request, response);
            }
        }

    }

}
