package bca.fts.util;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AdminAccountFilter implements Filter {

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

        ServletContext sc = filterConfig.getServletContext();
        int adminAccount = (Integer) sc.getAttribute("adminAccount");

        if (adminAccount == -1) {               // database error occured

            // forward request to the app's home page
            res.sendRedirect("/FTS/index.jsp");
        } else if (adminAccount == 1 && req.getRequestURI().contains("install.jsp")) {

            // forward request to the install.jsp
            res.sendRedirect("/FTS/admin/index.jsp");
        } else if (adminAccount == 1) {

            // forward request to the index.jsp
            chain.doFilter(request, response);
        } else if (adminAccount == 0 && req.getRequestURI().contains("createAcc.do")) {

            // forward request to the index.jsp
            chain.doFilter(request, response);
        } else {
            // forward request to the install.jsp
            req.getRequestDispatcher("install.jsp").forward(req, res);
        }

    }

    @Override
    public void destroy() {

    }

}
