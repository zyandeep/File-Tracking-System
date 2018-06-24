package bca.fts.util;

import javax.servlet.*;
import org.apache.log4j.*;
import bca.fts.dao.AdminLoginCheckDao;

public class ApplicationtListener implements ServletContextListener {

    // the logger object
    private static final Logger logger = Logger.getLogger(ApplicationtListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        logger.info("FTS has been started.");

        ServletContext sc = event.getServletContext();

        // verify the admin account
        sc.setAttribute("adminAccount", AdminLoginCheckDao.checkAdminAcc());
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        logger.info("FTS has been stoped.");
    }
}
