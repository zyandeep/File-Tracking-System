<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">      
        <title>Admin Password Change Page</title>

        <link type="text/css" rel="stylesheet" href="/FTS/css/style.css" />

        <script type="text/javascript" src="/FTS/js/jquery.js"></script>
        <script type="text/javascript" src="/FTS/js/adminJS.js"></script>  
    </head>

    <body>

        <div class="container">

            <jsp:include page="../../header.html"/>
            <jsp:include page="navGeneral.html"/>

            <div class="content">

                <p class="message" align="center">
                    Password must be of at least 8 characters, must contain at least one alphabet, one digit and one special character 
                </p>
                <br />

                <div id="errorMesssage" align="center"><font color="red"> ${requestScope.errorMessage} </font> </div>

                <div class="form" align="center">

                    <form method="post" action="changeMyPasswd.do" name="adminPwdChForm">

                        <table cellpadding="10" cellspacing="5">
                            <caption> Change Admin Password </caption>

                            <tr>
                                <td> Enter Old Password: </td>
                                <td>
                                    &nbsp;&nbsp; <input type="password" name="oldPass" size="30"/> 
                                </td>
                            </tr>
                            <tr>
                                <td> Enter New Password: </td>
                                <td>
                                    &nbsp;&nbsp; <input type="password" name="newPass" size="30"/> 
                                </td>
                            </tr>
                            <tr>
                                <td> Confirm New Password: </td>
                                <td>
                                    &nbsp;&nbsp; <input type="password" name="confPass" size="30"/> 
                                </td>
                            </tr>
                            <tr>
                                <td> </td>
                                <td>
                                    <input type="submit" value="Change Password"/>&nbsp; 
                                    <input type="reset" value="Reset Form"/>
                                </td>

                            </tr>                  
                        </table>

                    </form>

                </div>

            </div>

        </div>

    </body>
</html>
