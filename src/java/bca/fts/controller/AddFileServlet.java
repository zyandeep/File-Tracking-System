// this servlet adds a file record to the database
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.*;

import bca.fts.model.*;
import bca.fts.dao.*;

public class AddFileServlet extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(AddFileServlet.class);

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

        String fileNo = request.getParameter("fileNo");
        String fileName = request.getParameter("fileName");
        String fileType = request.getParameter("fileType");
        String des = request.getParameter("description");

        int row = 0;        // row != 1 means error
        String errMessage = "";
        FileBean file = null;

        // check for null values, illigal access
        if (fileNo == null || fileName == null || fileType == null || des == null) {
            request.getRequestDispatcher("welcomeBranch.jsp").forward(request, response);
        } else {
            if (fileNo.isEmpty() || fileName.isEmpty() || fileType.isEmpty()) {

                errMessage = "Form fileds can't be empty";
            } else {
                file = new FileBean();

                file.setNo(fileNo.trim());
                file.setName(fileName.trim());
                file.setType(fileType.trim());
                file.setDescription(des.trim());
                file.setOwner(branch.getOfficeName());

                // get recieve date
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date today = new Date();
                file.setDateOfRecieve(sdf.format(today));

                // get recieve time
                sdf = new SimpleDateFormat("hh:mm:ss a");
                file.setTimeOfRecieve(sdf.format(today));

                row = FileDao.addFile(file);
            }

            // redirect the requset to the appropiate page
            if (row == 1) {

                logger.info(user + " registered file " + fileName + " no " + fileNo);

                request.setAttribute("fileBean", file);
                request.getRequestDispatcher("messageToBranch.jsp").forward(request, response);
            } else if (row == 0) {

                if (errMessage.isEmpty()) {
                    errMessage = "File couldn't be registered";
                }

                request.setAttribute("errorMessage", errMessage);
                request.getRequestDispatcher("fileRegister.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Error has occured in the databse operation!");
                request.getRequestDispatcher("fileRegister.jsp").forward(request, response);
            }
        }

    }

}
