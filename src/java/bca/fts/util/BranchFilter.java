// this filter is responsible for branch Authorisation
package bca.fts.util;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.*;

public class BranchFilter implements Filter {

    // the logger object
    private static final Logger logger = Logger.getLogger(BranchFilter.class);

    private FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        HttpSession session = req.getSession(false);          // give exsisting session, don't create one

        if (session == null) {

            logger.warn("unauthorised attempt to access the secure branch resource has been denied");

            // for the RequestDispatcher, "/" means context path of the web app
            res.sendRedirect("/FTS/");

        } else if (session.getAttribute("branch") == null) {

            logger.warn("unauthorised attempt to access the secure branch resource has been denied");

            session.invalidate();
            res.sendRedirect("/FTS/");
        } else {

            // tell the browser not to cache the secure pages
            res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // for HTTP 1.1.
            res.setHeader("Pragma", "no-cache"); // for old HTTP 1.0.
            res.setDateHeader("Expires", 0); // Proxies.

            // forward request to the next resource( html/jsp/servlet )
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
    }

}
