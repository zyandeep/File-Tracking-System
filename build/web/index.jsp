<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--- Don't create session for this page, let the session to create after successful login ---%>
<jsp:directive.page session="false" />

<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> File Tracking System </title>

        <link type="text/css" rel="stylesheet" href="css/style.css" />

        <script type="text/javascript" src="/FTS/js/jquery.js"></script>
        <script type="text/javascript" src="/FTS/js/branchJS.js"></script>
    </head>

    <body>

        <div class="container">
            <jsp:include page="header.html"/>
            <br />

            <div id="errorMesssage" align="center"> <font color="red"> ${ requestScope.errorMessage} </font> </div>

            <div align="center" class="loginForm">
                <form method="POST" action="loginCheck.do" name="branchLoginFrom">
                    
                    <table cellpadding="5" cellspacing="5">
                        <caption> Branch Login Panel </caption>
                        <tr>
                            <td>Username: &nbsp;&nbsp; </td> 
                            <td><input type="text" name="username" size="30"/> </td>
                        </tr>
                        <tr>
                            <td>&nbsp;&nbsp;</td>
                        </tr>
                        <tr>
                            <td>Password: &nbsp;&nbsp; </td> 
                            <td><input type="password" name="password" size="30" /></td>
                        </tr>
                        <tr>
                            <td>&nbsp;&nbsp;</td>
                        </tr>
                        <tr>
                            <td>   </td> 
                            <td>
                                 
                                  &nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="submit" value="Login" />                              
                            </td>
                        </tr>
                    </table>
                    
                </form>
            </div>

        </div>

    </body>

</html>
