<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- Don't create session for this page, let the session to create after successful login --%>
<jsp:directive.page session="false" />

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Login Page</title>

        <link type="text/css" rel="stylesheet" href="/FTS/css/style.css" />

        <script type="text/javascript" src="/FTS/js/jquery.js"></script>
        <script type="text/javascript" src="/FTS/js/adminJS.js"></script>   
    </head>
    
    <body>
        <div class="container">

            <jsp:include page="../header.html"/>
            <br />

            <div align="center"><font color="green"> ${requestScope.message} </font></div>
            <div id="errorMesssage" align="center"><font color="red"> ${requestScope.errorMessage} </font></div>
            
            <div align="center" class="loginForm"> 
                <form method="post" action="adminLogin.do" name="adminLoginFrom">

                    <table  cellpadding="5" cellspacing="5">
                        <caption> Administrator Login Panel </caption>

                        <tr>
                            <td>Username:  &nbsp;&nbsp;&nbsp; </td>
                            <td colspan="2"><input type="text" name="username" size="30"/> </td>
                        </tr>
                         <tr>
                            <td>&nbsp;&nbsp;</td>
                        </tr>
                        <tr>
                            <td>Password:  &nbsp;&nbsp;&nbsp; </td>
                            <td colspan="2"><input type="password" name="password" size="30" /></td>
                        </tr>
                        <tr>
                            <td>  <img src="/FTS/img/lock_open.png" alt="Login" width="50" height="50"/>  </td> 
                            <td>
                                <input type="submit" value="Login" />
                            </td>
                            <td>  <img src="/FTS/img/keys.png" alt="Login" width="100" height="80"/>  </td> 
                        </tr>

                    </table>
                </form>

            </div>

        </div>
    </body>
</html>
