// this servlet is responsible for handling AJAX request and send back filenames as response 
package bca.fts.controller;

import bca.fts.dao.FileDao;
import com.google.gson.Gson;
import java.io.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class AdminAutocomplete extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("term");

        // check for null values, illigal access
        if (keyword == null) {
            request.getRequestDispatcher("welcomeAdmin.jsp").forward(request, response);
        } else {
            ArrayList<String> fileNames = null;

            if (keyword.equals("%") || keyword.equals("_")) {

                // just return null
                String searchList = new Gson().toJson(fileNames);

                response.setContentType("application/json");

                PrintWriter out = response.getWriter();
                out.write(searchList);
            } else {
                // get the filenames from DAO
                fileNames = FileDao.autocompleteFileName(keyword);

                // database related error so don't send json data and let the browser to show the alert box
                if (fileNames == null) {
                    return;
                }

                // convert the String array to JSON format
                String searchList = new Gson().toJson(fileNames);

                response.setContentType("application/json");

                PrintWriter out = response.getWriter();
                out.write(searchList);
            }
        }

    }

}
