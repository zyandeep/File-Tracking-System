// this class implements Java's Conncetion Pulling
// to SPEED UP Database OPERATIONS
// DATE: 18-02-2015
package bca.fts.util;

import java.sql.*;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import org.apache.log4j.*;

public class DatabaseConnector {

    private static InitialContext ic = null;
    private static DataSource ds = null;

    // the logger object
    private static Logger logger = Logger.getLogger(DatabaseConnector.class);

    // this method will be called only once
    private static void initMethod() {

        try {
            ic = new InitialContext();
            ds = (DataSource) ic.lookup("java:/comp/env/jdbc/file_tracking");
        } catch (Exception e) {
            logger.fatal(e, e);
        }
    }

    public static Connection getConnection() {

        Connection con = null;

        if (ic == null || ds == null) {
            initMethod();
        }

        try {
            con = ds.getConnection();
        } catch (Exception e) {
            logger.fatal(e, e);
        }

        return con;
    }

    public static void freeConncetion(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            logger.fatal(e, e);
        }
    }

    public static void closeResultSet(ResultSet res) {
        try {
            if (res != null) {
                res.close();
            }
        } catch (Exception e) {
            logger.fatal(e, e);
        }
    }

    public static void closeStatement(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (Exception e) {
            logger.fatal(e, e);
        }
    }

    public static void closePreStatement(PreparedStatement pst) {
        try {
            if (pst != null) {
                pst.close();
            }
        } catch (Exception e) {
            logger.fatal(e, e);
        }
    }
}
