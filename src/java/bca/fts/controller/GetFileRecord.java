// this servlet fetch the data of the file to be updated and display it via view-fileEdit.jsp
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import bca.fts.model.*;
import bca.fts.dao.*;

public class GetFileRecord extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        FileBean file = null;
        String errMessage = "";
        int fileID = 0;

        if (id == null || id.isEmpty()) {
            file = null;
        } else {

            fileID = Integer.valueOf(id.trim());

            file = FileDao.giveFileRecord(fileID);
        }

        //check if file record is found or not
        if (file != null) {

            // set the fileBean in session, instead of request scope, so that if user submits wrong data, data can still
            // be retrieved form the session
            HttpSession session = request.getSession(false);
            session.setAttribute("fileBean", file);

            request.getRequestDispatcher("loadFileType.do?page=fileEdit.jsp").forward(request, response);

        } else {

            request.setAttribute("errorMessage", "Record couldn't be retrieved");
            request.getRequestDispatcher("listRegFiles.do").forward(request, response);
        }

    }

}
