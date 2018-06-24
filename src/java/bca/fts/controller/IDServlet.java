// this servlet is responsible for retriving the auto-increment ID for a new branch
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import bca.fts.dao.IDDao;

public class IDServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = IDDao.getLastID();

        // check the database-related error
        if (id == -1) {
            request.setAttribute("errorMessage", "Error has occured in the database operation!");
            request.getRequestDispatcher("welcomeAdmin.jsp").forward(request, response);
        } else {
            request.setAttribute("branchID", ++id);
            request.getRequestDispatcher("branchRegister.jsp").forward(request, response);
        }

    }

}
