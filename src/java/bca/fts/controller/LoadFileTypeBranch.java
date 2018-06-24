// this servlet is responsible for displaying all the registered file-types
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;

import bca.fts.dao.*;

public class LoadFileTypeBranch extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String page = request.getParameter("page");

        ArrayList<String> fileTypes = null;

        fileTypes = AdminReportsDao.giveFileTypes();

        if (fileTypes == null) {

            // database-error occured
            request.setAttribute("errorMessage", "Error has occured in the database operation!");

            if (page == null) {
                request.getRequestDispatcher("welcomeBranch.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("regFileList.jsp").forward(request, response);
            }
        } else if (fileTypes.isEmpty()) {

            request.setAttribute("message", "No file type has been registered yet. Contact the Admin");
            request.getRequestDispatcher("welcomeBranch.jsp").forward(request, response);

        } else {

            // give the existing session, don't create one
            HttpSession session = request.getSession(false);
            session.setAttribute("fileType", fileTypes);

            if (page == null) {
                request.getRequestDispatcher("fileRegister.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher(page).forward(request, response);
            }

        }

    }

}
