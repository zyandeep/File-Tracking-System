// this servlet is responsible for displaying all the registered file-types
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;

import bca.fts.dao.*;
import bca.fts.model.*;

public class LoadFileType extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<String> fileTypes = null;

        fileTypes = AdminReportsDao.giveFileTypes();

        if (fileTypes == null) {

            // database-error occured
            request.setAttribute("errorMessage", "Error has occured in the database operation!");
            request.getRequestDispatcher("welcomeAdmin.jsp").forward(request, response);
        } else if (fileTypes.isEmpty()) {

            request.setAttribute("message", "You've not registered any file type yet");
            request.getRequestDispatcher("addFileType.jsp").forward(request, response);

        } else {

            // set the file-type in the session
            HttpSession session = request.getSession(false);
            session.setAttribute("fileType", fileTypes);

            request.getRequestDispatcher("addFileType.jsp").forward(request, response);
        }

    }

}
