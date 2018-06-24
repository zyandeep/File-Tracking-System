// this servlet is responsible for loading all registered files
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
import org.apache.log4j.*;

import bca.fts.model.*;
import bca.fts.dao.*;

public class AdminRegisFiles extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(AdminRegisFiles.class);

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

        logger.info(user + " viewed the registered file report");

        ArrayList<FileBean> files = AdminReportsDao.giveRegisteredFiles();

        if (files == null) {

            request.setAttribute("errorMessage", "Error has occured in the database operation!");
            request.getRequestDispatcher("reportsAdmin.jsp").forward(request, response);
        } else if (files.isEmpty()) {

            request.setAttribute("errorMessage", "No file found");
            request.getRequestDispatcher("reportsAdmin.jsp").forward(request, response);
        } else {

            session.setAttribute("registeredFiles", files);
            request.getRequestDispatcher("registeredFiles.jsp").forward(request, response);
        }

    }
}
