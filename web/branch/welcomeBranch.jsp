<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Branch Welcome Page</title>

        <link type="text/css" rel="stylesheet" href="/FTS/css/style.css"/>
    </head>

    <body>
        <div class="container">

            <jsp:include page="/header.html"/>

            <div class="navigation">
                <p> &nbsp;&nbsp; <strong> Navigation </strong> </p>
                <ul>
                    <li> <a href="loadFileType.do"> REGISTER FILE </a> </li>
                    <li> <a href="notification.do"> CHECK INBOX </a> </li>
                    <li> <a href="trackFile.jsp"> SEARCH A FILE </a> </li>
                    <li> <a href="passChange.jsp"> CHANGE PASSWORD </a> </li>
                    <li> <a href="listRegFiles.do"> VIEW REGISTERED FILES </a> </li>
                    <li> <a href="giveSentFiles.do"> VIEW SENT FILES </a> </li>
                    <li> <a href="logMeOut.do"> LOG OUT </a> </li>
                </ul>
            </div>

            <div class="content">
                <h3> Welcome User- &nbsp; <font color="green"><i> <c:out value="${sessionScope.branch.username}"/> </i> </font> </h3>  

                <h3> Login from Branch- &nbsp; <font color="green"><i> <c:out value="${sessionScope.branch.officeName}"/> </i></font></h3>
                
                <h3> 
                    <br />
                    <%-- for displaying notification --%>
                    <c:if test="${ empty sessionScope.inbox || sessionScope.inbox ne true }">
                        <a href="notification.do"> ${ sessionScope.notification } </a> 
                    </c:if>
                </h3>
                
                <p>
                    <%-- for success and error message --%>
                    <font color="green">${requestScope.message}</font>
                    <font color="red">${requestScope.errorMessage}</font>
                </p>
            </div>

        </div>

    </body>
</html>
