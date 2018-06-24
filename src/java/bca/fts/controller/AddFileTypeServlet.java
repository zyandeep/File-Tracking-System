// this servlet adds a file-type to the database
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.*;

import bca.fts.dao.*;
import bca.fts.model.AdminBean;

public class AddFileTypeServlet extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(AddFileTypeServlet.class);

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

        String fileType = request.getParameter("fileType");

        int row = 0;        // row != 1 means error

        // check for null values, illigal access
        if (fileType == null) {
            request.getRequestDispatcher("welcomeAdmin.jsp").forward(request, response);
        } else {
            if (fileType.isEmpty()) {

                request.setAttribute("errorMessage", "Empty file type");
                request.getRequestDispatcher("addFileType.jsp").forward(request, response);
            } // check if the file type is already registered
            else if (FileDao.checkUniqueFiletype(fileType.trim())) {

                request.setAttribute("errorMessage", "File type " + fileType + " is already registered");
                request.getRequestDispatcher("addFileType.jsp").forward(request, response);
            } else {

                row = FileDao.addFileType(fileType.trim());

                // redirect the requset to the appropiate page
                if (row == 1) {
                    logger.info(user + " registered file type " + fileType);

                    request.setAttribute("message", "File-type " + fileType + " has been successfully registered");
                    request.getRequestDispatcher("loadFileTypes.do").forward(request, response);
                } else if (row == 0) {

                    request.setAttribute("errorMessage", "File-type couldn't be registered");
                    request.getRequestDispatcher("addFileType.jsp").forward(request, response);
                } else {

                    request.setAttribute("errorMessage", "Error has occured in the databse operation!");
                    request.getRequestDispatcher("addFileType.jsp").forward(request, response);
                }

            }
        }

    }

}
