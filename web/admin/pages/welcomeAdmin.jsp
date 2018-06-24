<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 

<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Welcome Page</title>

        <link type="text/css" rel="stylesheet" href="/FTS/css/style.css"/>
    </head>

    <body>
        <div class="container">

            <jsp:include page="/header.html"/>

            <div class="navigation">
                <p> &nbsp;&nbsp; <strong> Navigation </strong> </p>
                <ul>
                    <li> <a href="giveID.do"> REGISTER BRANCH </a> </li>
                    <li> <a href="loadFileTypes.do"> REGISTER FILE TYPE </a> </li>
                    <li> <a href="trackFile.jsp"> SEARCH A FILE </a> </li>
                    <li> <a href="changePassword.jsp"> CHANGE PASSWORD </a> </li>
                    <li> <a href="loadBranchRecords.do"> VIEW REGISTERED BRANCH  </a> </li>
                    <li> <a href="reportsAdmin.jsp"> GENERATE REPORT  </a> </li>
                    <li> <a href="logMeOut.do"> LOG OUT </a> </li>
                </ul>
            </div>

            <div class="content" >                
                <h2> Welcome Admin- &nbsp; <font color="green"><i> <c:out value="${ sessionScope.admin.userName }"/> </i></font></h2>  
                <br />
                
                <p>
                    <font color="green"> ${requestScope.message} </font>  
                    <font color="red"> ${requestScope.errorMessage} </font> 
                </p>                
            </div>

        </div>
    </body>
    
</html>
