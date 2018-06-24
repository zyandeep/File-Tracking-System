// this servlet is responsible for loading all rejected files
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
import org.apache.log4j.*;

import bca.fts.model.*;
import bca.fts.dao.*;
import java.util.Collections;
import java.util.Comparator;

public class AdminRejectFiles extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(AdminRejectFiles.class);

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

        logger.info(user + " viewed the rejected file report");

        ArrayList<FileBean> files = AdminReportsDao.giveRejectedFiles();

        if (files == null) {

            request.setAttribute("errorMessage", "Error has occured in the database operation!");
            request.getRequestDispatcher("reportsAdmin.jsp").forward(request, response);
        } else if (files.isEmpty()) {

            request.setAttribute("errorMessage", "No file found");
            request.getRequestDispatcher("reportsAdmin.jsp").forward(request, response);
        } else {

            // sort the list first in Ascending order based on the date of date-of-send;
            Collections.sort(files, new Comparator<FileBean>() {

                @Override
                public int compare(FileBean o1, FileBean o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });

            // sort the list first in Ascending order;
            Collections.reverse(files);

            session.setAttribute("rejectedFiles", files);
            request.getRequestDispatcher("rejectedFiles.jsp").forward(request, response);
        }
    }
}
