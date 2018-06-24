<jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
<jsp:directive.page isErrorPage="true" />

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> 404 Error </title>
        
        <link type="text/css" rel="stylesheet" href="/FTS/css/style.css" />
    </head>

    <body>
        <div class="container">

            <div align="center" class="errorPage">
                <h2> HTTP 404 Error: Page Not Found! </h2>

                <h3>
                    We are really sorry, But the page or resource you are looking for cannot not be found.
                </h3>
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
