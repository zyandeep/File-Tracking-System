// this servlet is responsible for tracking a file based on the "date-of-receive" provided by a branch 
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
import org.apache.log4j.*;

import bca.fts.dao.*;
import bca.fts.model.*;
import bca.fts.util.DateValidator;

public class TrackWithDor extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(TrackWithDor.class);

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

        String file_id = request.getParameter("file_id");
        ArrayList<TrackFileBean> files = null;

        // this block will execute only when a user selects a "DoR" from the list of file 
        if (file_id != null) {

            // get the user-selected file record from the session
            files = (ArrayList<TrackFileBean>) session.getAttribute("fileList");

            for (TrackFileBean f : files) {

                if (f.getId() == Integer.valueOf(file_id)) {

                    forwardData(f, request, response);

                    break;
                }
            }

        } else {

            String dor = request.getParameter("dor");
            String errMsg = "";

            // illigal access
            if (dor == null) {
                request.getRequestDispatcher("welcomeBranch.jsp").forward(request, response);
            } else {
                // validate the date
                if (DateValidator.validateDate(dor.trim()) == false) {

                    errMsg = "Please enter a valid date with the given form";

                    request.setAttribute("errorMessage", errMsg);
                    request.getRequestDispatcher("trackWithDoR.jsp").forward(request, response);
                } else {
                    logger.info(user + " searched for a file having date-of-receive " + dor);

                    files = BranchFileTrackDao.giveFilesDoR(dor.trim(), branch.getOfficeName());

                    if (files == null || files.isEmpty()) {

                        errMsg = "No File record found";

                        request.setAttribute("errorMessage", errMsg);
                        request.getRequestDispatcher("trackWithDoR.jsp").forward(request, response);
                    } else if (files.size() == 1) {

                        forwardData(files.get(0), request, response);          // just show the file-track data
                    } else {

                        // save the "files" in the session so that later when the user wanna track a paticular file
                        // the file record can be retrieved from the session, instead of hitting the DB again.
                        session.setAttribute("fileList", files);

                        // forward the file records to the VIEW 
                        request.setAttribute("fileList", files);
                        request.getRequestDispatcher("trackWithDoR.jsp").forward(request, response);

                    }
                }
            }

        }

    }

    // this method will generate file-track data and forward it to the appropriate view
    private void forwardData(TrackFileBean bean, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] path = null, dates = null, times = null;
        String[] rDates = null, rTimes = null;

        path = bean.getPath().split("[,]+");
        rDates = bean.getRecieveDates().split("[,]+");
        rTimes = bean.getRecieveTimes().split("[,]+");

        if (bean.getSendDates() != null) {

            dates = bean.getSendDates().split("[,]+");
            times = bean.getSendTimes().split("[,]+");

            // now concate "sendDates" with "sendTimes"
            // 'cause dates.length == times.length
            for (int i = 0; i < dates.length; i++) {
                dates[i] = dates[i].concat(" at ").concat(times[i]);
            }
        }

        // concate "recieveDates" with "recieveTimes"
        // 'cause rDates.length == rTimes.length        
        for (int i = 0; i < rDates.length; i++) {
            rDates[i] = rDates[i].concat(" at ").concat(rTimes[i]);
        }

        // forward the data to the VIEW as well as file-track data
        request.setAttribute("file", bean);
        request.setAttribute("path", path);
        request.setAttribute("dates", dates);
        request.setAttribute("rDates", rDates);

        request.getRequestDispatcher("trackWithDoR.jsp").forward(request, response);
    }

}
