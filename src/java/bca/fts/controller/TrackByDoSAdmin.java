// this servlet is responsible for getting the file-track data from DAO based on DOS and show it via VIEW
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import org.apache.log4j.*;

import bca.fts.model.*;
import bca.fts.dao.*;
import bca.fts.util.DateValidator;

public class TrackByDoSAdmin extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(TrackByDoSAdmin.class);

    // this method will generate file-track data and forward it to "VIEW"    
    private void forwardData(TrackFileBean file, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] path = null, dates = null, times = null, rDates = null, rTimes = null;

        path = file.getPath().split("[,]+");
        rDates = file.getRecieveDates().split("[,]+");
        rTimes = file.getRecieveTimes().split("[,]+");

        if (file.getSendDates() != null) {
            dates = file.getSendDates().split("[,]+");
            times = file.getSendTimes().split("[,]+");

            // now concate "sendDates" with "sendTimes"
            // 'cause dates.length == times.length
            for (int i = 0; i < dates.length; i++) {
                dates[i] = dates[i].concat(" at ").concat(times[i]);
            }
        }

        // now concate "recieveDates" with "recieveTimes"
        // 'cause rDates.length == rTimes.length        
        for (int i = 0; i < rDates.length; i++) {
            rDates[i] = rDates[i].concat(" at ").concat(rTimes[i]);
        }

        // forward the data to the VIEW as well as file-track data
        request.setAttribute("file", file);
        request.setAttribute("path", path);
        request.setAttribute("dates", dates);
        request.setAttribute("rDates", rDates);

        request.getRequestDispatcher("trackDoSAdmin.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        AdminBean bean = (AdminBean) session.getAttribute("admin");
        String user = bean.getUserName();

        String file_id = request.getParameter("file_id");

        // this will hold the list of file records
        ArrayList<TrackFileBean> files = null;

        // this block will execute only when a user selects a file_no from the list of file 
        if (file_id != null) {

            // get the user-selected file record from the session
            files = (ArrayList<TrackFileBean>) request.getSession(false).getAttribute("fileList");

            for (TrackFileBean f : files) {

                if (f.getId() == Integer.valueOf(file_id)) {

                    forwardData(f, request, response);

                    break;
                }

            }

        } else {

            String dos = request.getParameter("dos");
            String sender = request.getParameter("sender");
            String reciever = request.getParameter("reciever");
            String status = request.getParameter("status");

            // illigal access
            if (dos == null || reciever == null || sender == null || status == null) {
                request.getRequestDispatcher("welcomeAdmin.jsp").forward(request, response);
            } else {
                if (sender.equalsIgnoreCase("---- Select the branch ----")) {
                    sender = null;
                }
                if (reciever.equalsIgnoreCase("---- Select the branch ----")) {
                    reciever = null;
                }
                if (status.equalsIgnoreCase("---- Select file status ----")) {
                    status = null;
                }

                // validate the date first
                if (DateValidator.validateDate(dos.trim()) == false) {

                    request.setAttribute("errorMessage", "Please enter a Valid date with the given form");
                    request.getRequestDispatcher("trackDoSAdmin.jsp").forward(request, response);
                } else {

                    logger.info(user + " searched a file by Date-of-Sent " + dos);

                    // retrieve the files from the DB
                    files = AdminFileTrackDao.giveFilesViaDoS(dos.trim(), sender, reciever, status);

                    // now check whether the file can be traceable or not
                    if (files == null || files.isEmpty()) {

                        request.setAttribute("errorMessage", "No File record found");
                        request.getRequestDispatcher("trackDoSAdmin.jsp").forward(request, response);

                    } else if (files.size() == 1) {

                        // if only one record found then display it's file-track data
                        forwardData(files.get(0), request, response);

                    } else {

                        // save the "files" in the session so that later when the user wanna track a paticular file
                        // the file record can be retrieved from the session, instead of hitting the DB again.
                        request.getSession(false).setAttribute("fileList", files);

                        // forward the file records to the VIEW 
                        request.setAttribute("fileList", files);
                        request.getRequestDispatcher("trackDoSAdmin.jsp").forward(request, response);
                    }

                }
            }

        }

    }

}
