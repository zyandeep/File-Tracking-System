// this servlet is responsible for handling AJAX request and send back filenames as response
package bca.fts.controller;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.util.ArrayList;

import com.google.gson.*;
import bca.fts.dao.FileDao;

public class BranchAutocomplete extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("term");
        ArrayList<String> fileNames = null;

        // check for null values, illigal access
        if (keyword == null) {
            request.getRequestDispatcher("welcomeBranch.jsp").forward(request, response);
        } else {
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
