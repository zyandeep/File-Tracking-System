//this servlet delete a file-type to the database
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.*;

import bca.fts.dao.*;
import bca.fts.model.AdminBean;

public class DeleteFileType extends HttpServlet {

    private static final Logger logger = Logger.getLogger(DeleteFileType.class);

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
        int row = 0;

        // illigal access
        if (fileType == null) {
            request.getRequestDispatcher("welcomeAdmin.jsp").forward(request, response);
        } else {

            row = FileDao.deleteFileType(fileType.trim());

            // redirect the requset to the appropiate page
            if (row == 1) {
                logger.info(user + " deleted file type " + fileType);

                request.setAttribute("message", "File-type " + fileType + " has been successfully deleted");
                request.getRequestDispatcher("loadFileTypes.do").forward(request, response);
            } else if (row == 0) {

                request.setAttribute("errorMessage", "File type " + fileType + " couldn't be deleted");
                request.getRequestDispatcher("addFileType.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Error has occured in the databse operation!");
                request.getRequestDispatcher("addFileType.jsp").forward(request, response);
            }

        }

    }

}
