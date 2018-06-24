// this Servlet takes the NEW data and update the particular branch
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.*;

import bca.fts.dao.*;
import bca.fts.model.*;
import bca.fts.util.PasswordValidator;

public class ModifyBranchData extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(ModifyBranchData.class);

    // this method redirect a user to the appropiate page
    private void redirectRequest(String message, boolean isError, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (isError) {
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("showBranchRecord.jsp").forward(request, response);
        } else {
            request.setAttribute("message", message);
            request.getRequestDispatcher("branchList.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
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
        String newPass = request.getParameter("newPass");
        String confPass = request.getParameter("confPass");

        boolean isUpdatable = false;
        int row = 0;
        BranchBean oldBean = (BranchBean) session.getAttribute("branchBean");   // branch to be updated
        BranchBean newBean = null;

        // illigal access
        if (id == null || bn == null || hn == null || un == null || newPass == null || confPass == null) {
            request.getRequestDispatcher("welcomeAdmin.jsp").forward(request, response);
        } else if (bn.isEmpty() || hn.isEmpty() || un.isEmpty()) {

            redirectRequest("Empty data", true, request, response);
        } else {

            // trim the data
            id = id.trim();
            bn = bn.trim();
            hn = hn.trim();
            un = un.trim();

            // validate the password
            String passMsg = PasswordValidator.validatePassword(newPass);

            if (bn.equals(oldBean.getOfficeName()) && hn.equals(oldBean.getHeadName()) && un.equals(oldBean.getUsername())) {

                if (newPass.isEmpty() && confPass.isEmpty()) {

                    redirectRequest("You didn't modify any data", true, request, response);
                } else if (newPass.isEmpty() == false && passMsg.isEmpty() == false) {

                    redirectRequest(passMsg, true, request, response);
                } else if (confPass.isEmpty() || newPass.isEmpty() || newPass.equals(confPass) == false) {

                    redirectRequest("New and Confirm password must be matched", true, request, response);
                } else {
                    isUpdatable = true;
                }

            } else {

                // check if the username is already registered
                if (un.equalsIgnoreCase(oldBean.getUsername()) == false && BranchDao.checkUniqueUsername(un)) {

                    redirectRequest("Username " + un + " is already registered", true, request, response);
                } // check if the branch name is already registered
                else if (bn.equalsIgnoreCase(oldBean.getOfficeName()) == false && BranchDao.checkUniqueBranchName(bn)) {

                    redirectRequest("Branch " + bn + " is already registered", true, request, response);
                } else if (newPass.isEmpty() && confPass.isEmpty()) {
                    isUpdatable = true;
                } else if (newPass.isEmpty() == false && passMsg.isEmpty() == false) {

                    redirectRequest(passMsg, true, request, response);
                } else if (confPass.isEmpty() || newPass.isEmpty() || newPass.equals(confPass) == false) {
                    redirectRequest("New and confirm password need to be matched", true, request, response);
                } else {
                    isUpdatable = true;
                }
            }

            // update the branch record
            if (isUpdatable) {

                newBean = new BranchBean();

                newBean.setId(Integer.valueOf(id));      // redundant(??)
                newBean.setOfficeName(bn);
                newBean.setHeadName(hn);
                newBean.setUsername(un);
                newBean.setPassword((newPass.isEmpty()) ? newPass : DigestUtils.md5Hex(newPass));

                row = BranchDao.updateBranch(newBean);

                if (row == 1) {

                    logger.info(user + " updated branch record having ID " + id);

                    // verify if branch name, head name and username is changed or not 
                    if (oldBean.getOfficeName().equals(bn) && oldBean.getHeadName().equals(hn) && oldBean.getUsername().equals(un)) {

                        // not chnaged
                        redirectRequest("Branch with ID- " + id + " has been updated successfully", false, request, response);
                    } // if the branch name is changed then 
                    else if (oldBean.getOfficeName().equals(bn) == false) {

                        // the new "branchNames" and "branchRecords"should be loaded in session
                        request.setAttribute("message", "Branch with ID- " + id + " has been updated successfully");
                        request.getRequestDispatcher("loadBranchRecords.do?bName=true").forward(request, response);
                    } else {

                        // only load the "branchRecords"
                        request.setAttribute("message", "Branch with ID- " + id + " has been updated successfully");
                        request.getRequestDispatcher("loadBranchRecords.do").forward(request, response);
                    }
                } else if (row == 0) {

                    redirectRequest("Branch record could not be updated", true, request, response);
                } else {

                    redirectRequest("Error has occured in the database operation!", true, request, response);
                }
            }

        }

    }
}
