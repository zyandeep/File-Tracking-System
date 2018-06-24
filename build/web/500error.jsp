<jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
<jsp:directive.page isErrorPage="true" />

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> 500 Error </title>

        <link type="text/css" rel="stylesheet" href="/FTS/css/style.css" />
    </head>

    <body>
        <div class="container">

            <div align="center" class="errorPage">
                <h2> HTTP 500 Error: Internal Server Error! </h2>

                <h3>
                    The server encountered an error while fulfilling your request. Please contact the webmaster. <br />
                    We are really sorry for the inconvenience.
                </h3>

                <p>
                    <strong> Error message form the server: </strong> ${ pageContext.exception } <br />
                </p>
                <br />
                <br />

                <p>

                    <c:choose>

                        <c:when test="${ sessionScope.admin ne null }">
                            <a href="/FTS/admin/pages/welcomeAdmin.jsp"> Go Back to Home </a>
                        </c:when>

                        <c:when test="${ sessionScope.branch ne null }">
                            <a href="/FTS/branch/welcomeBranch.jsp"> Go Back to Home </a>
                        </c:when>

                        <c:otherwise>
                            <a href="/FTS/"> Go Back to Home page </a>
                        </c:otherwise>

                    </c:choose>

                </p>

            </div>
        </div>


    </body>
</html>
