// this servlet updates a file record with newly entered data
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.*;

import bca.fts.dao.*;
import bca.fts.model.*;

public class UpdateFileRecord extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(UpdateFileRecord.class);

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

        String id = request.getParameter("id");
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String desc = request.getParameter("description");

        String errMessage = " ";
        int row = 0;

        // illigal access
        if (id == null || no == null || name == null || type == null || desc == null) {
            request.getRequestDispatcher("welcomeBranch.jsp").forward(request, response);
        } else {
            FileBean newFile = null;
            FileBean oldFile = (FileBean) session.getAttribute("fileBean");

            // trim the data
            id = id.trim();
            no = no.trim();
            name = name.trim();
            type = type.trim();
            desc = desc.trim();

            if (no.isEmpty() || name.isEmpty() || type.isEmpty()) {
                errMessage = "Empty file-record data";

                row = 0;
            } // check if no data was modified
            else if (oldFile.getNo().equals(no) && oldFile.getName().equals(name) && oldFile.getType().equals(type)
                    && oldFile.getDescription().equals(desc)) {

                errMessage = "You didn't modify any data";

                row = 0;
            } else {
                // create the branch bean
                newFile = new FileBean();

                newFile.setId(Integer.valueOf(id));        // "id" can't be edit. RIGHT??? 
                newFile.setNo(no);
                newFile.setName(name);
                newFile.setType(type);
                newFile.setDescription(desc);

                row = FileDao.updateFile(newFile);

                // set the error message if update fails
                if (row != 1) {
                    errMessage = "File having " + id + " could not be updated";
                }
            }

            if (row == 1) {

                logger.info(user + " updated file record having ID " + id);

                request.setAttribute("message", "File ID- " + id + " has been updated successfully");
                request.getRequestDispatcher("listRegFiles.do").forward(request, response);
            } else {
                request.setAttribute("errorMessage", errMessage);
                request.getRequestDispatcher("fileEdit.jsp").forward(request, response);
            }

        }

    }

}
