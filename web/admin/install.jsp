<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- Don't create session for this page, let the session to create after successful login --%>
<jsp:directive.page session="false" />

<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Registration Page</title>

        <link type="text/css" rel="stylesheet" href="/FTS/css/style.css" />

        <script type="text/javascript" src="/FTS/js/jquery.js"></script>
        <script type="text/javascript" src="/FTS/js/adminJS.js"></script>       
    </head>

    <body>

        <div class="container">

            <jsp:include page="../header.html"/>

            <div class="content">

                <p class="message" align="center">
                    Password must be of at least 8 characters, must contain at least one alphabet, one digit and one special character 
                </p>
                <br />

                <div id="errorMesssage" align="center"><font color="red"> ${requestScope.errorMessage} </font></div>

                <div align="center" class="loginForm"> 
                    <form method="post" action="createAcc.do" name="adminRegFrom">

                        <table  cellpadding="5" cellspacing="5">
                            <caption> Create The Administrator Account... </caption>

                            <tr>
                                <td>Username:  &nbsp;&nbsp;&nbsp; </td>
                                <td colspan="2"><input type="text" name="username" size="30"/> </td>
                            </tr>
                            <tr>
                                <td>Password:  &nbsp;&nbsp;&nbsp; </td>
                                <td colspan="2"><input type="password" name="password" size="30" /></td>
                            </tr>
                            <tr>
                                <td>Confirm Password:  &nbsp;&nbsp;&nbsp; </td>
                                <td colspan="2"><input type="password" name="confPass" size="30" /></td>
                            </tr>
                            <tr>
                                <td>  </td> 
                                <td>
                                    <input type="submit" value="Create Account" />
                                </td>
                                <td>
                                    <input type="reset" value="Reset Form" />
                                </td> 
                            </tr>

                        </table>

                    </form>

                </div>

            </div>

        </div>

    </body>

</html>
