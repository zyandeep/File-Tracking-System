// this servlet keeps track whether the user respond to the notification or not
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class NotificationServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getSession(false).setAttribute("inbox", true);

        request.getRequestDispatcher("loadIncomings.do").forward(request, response);
    }

}
