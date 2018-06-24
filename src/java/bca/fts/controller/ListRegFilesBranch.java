// this servlet giver the list of registered files of a particular branch
package bca.fts.controller;

import java.util.ArrayList;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.*;

import bca.fts.dao.*;
import bca.fts.model.*;

public class ListRegFilesBranch extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(ListRegFilesBranch.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        BranchBean branch = (BranchBean) session.getAttribute("branch");
        String user = branch.getUsername();

        ArrayList<FileBean> files = null;

        logger.info(user + " viewed the registered file list");

        // call "dao" to give the list of files and branches where files can be dispatched 
        files = BranchReportDao.giveFiles(branch.getOfficeName());

        if (files != null) {

            session.setAttribute("registeredFiles", files);
            request.getRequestDispatcher("regFileList.jsp").forward(request, response);

        } else {
            request.setAttribute("errorMessage", "No file found");
            request.getRequestDispatcher("welcomeBranch.jsp").forward(request, response);
        }

    }

}
