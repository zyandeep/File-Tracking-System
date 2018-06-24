// this servlet is responsible for getting the file-track data from DAO and show it via VIEW
package bca.fts.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.*;

import bca.fts.model.*;
import bca.fts.dao.*;

public class TrackWithIDBranch extends HttpServlet {

    // the logger object
    private static final Logger logger = Logger.getLogger(TrackWithIDBranch.class);

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
        String errMsg = "";
        boolean isIDOk = true;
        int fileId = 0;

        // illigal access
        if (id == null) {
            request.getRequestDispatcher("welcomeBranch.jsp").forward(request, response);
        } else {

            // check if the "id" is empty or a Non-Integer
            try {

                if (id.isEmpty() == false) {

                    fileId = Integer.valueOf(id.trim());

                    // check if the file ID is negative or zero
                    if (fileId > 0 == false) {
                        errMsg = "File id must be a positive number";

                        isIDOk = false;
                    }

                } else {
                    errMsg = "Please enter a file ID";

                    isIDOk = false;
                }

            } catch (NumberFormatException e) {
                logger.error(e);

                // error occured
                isIDOk = false;
            }

            if (isIDOk) {

                logger.info(user + " searched for a file having ID " + id);

                TrackFileBean file = BranchFileTrackDao.isFileTracable(fileId, branch.getOfficeName());

                if (file != null && file.getId() > 0) {

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

                    request.getRequestDispatcher("trackWithID.jsp").forward(request, response);

                } else if (file == null) {
                    errMsg = "No record found";

                    request.setAttribute("errorMessage", errMsg);
                    request.getRequestDispatcher("trackWithID.jsp").forward(request, response);
                } else {
                    errMsg = "Request is denied. You've not processed the file yet";

                    request.setAttribute("errorMessage", errMsg);
                    request.getRequestDispatcher("trackWithID.jsp").forward(request, response);
                }
            } else {

                request.setAttribute("errorMessage", errMsg);
                request.getRequestDispatcher("trackWithID.jsp").forward(request, response);
            }

        }

    }

}
