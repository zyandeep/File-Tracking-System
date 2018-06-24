// this servlet gives the list of sent files of a branch with there current status 
package bca.fts.controller;

import java.util.ArrayList;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.*;

import bca.fts.dao.*;
import bca.fts.model.*;
import java.util.Collections;
import java.util.Comparator;

public class ViewSentFiles extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(ViewSentFiles.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        BranchBean branch = (BranchBean) session.getAttribute("branch");
        String user = branch.getUsername();

        ArrayList<FileBean> files = null;

        logger.info(user + " viewed the sent file list");

        // get the lits of sent files from "dao"
        files = BranchReportDao.giveSentFiles(branch.getOfficeName());

        if (files != null) {

            // sort the list first in Ascending order based on the date of date-of-send;
            Collections.sort(files, new Comparator<FileBean>() {

                @Override
                public int compare(FileBean o1, FileBean o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });

            // sort the list first in Ascending order;
            Collections.reverse(files);

            session.setAttribute("sentFiles", files);
            request.getRequestDispatcher("sentFiles.jsp").forward(request, response);

        } else {
            request.setAttribute("errorMessage", "No file found");
            request.getRequestDispatcher("welcomeBranch.jsp").forward(request, response);
        }

    }

}
