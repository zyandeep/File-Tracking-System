<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Branch Registration Page</title>

        <link type="text/css" rel="stylesheet" href="/FTS/css/style.css" />

        <script type="text/javascript" src="/FTS/js/jquery.js"></script>
        <script type="text/javascript" src="/FTS/js/adminJS.js"></script>  
    </head>

    <body>

        <div class="container">

            <jsp:include page="../../header.html"/>
            <jsp:include page="navGeneral.html"/>

            <div class="content">

                <div id="errorMesssage" align="center"><font color="red"> ${requestScope.errMessage} </font></div>

                <div class="form" align="center">

                    <form method="post" action="addBranch.do" name="branchRegFrom">

                        <table cellpadding="15" cellspacing="5" width="50%">
                            <caption> Register New Branch </caption>

                            <tr>
                                <td>Branch ID:</td>                           
                                <td>&nbsp;&nbsp; <input type="text" name="id" value="${requestScope.branchID}" readonly="readonly" /></td>
                            </tr>                        
                            <tr>
                                <td><span class="point">*</span> Branch Name:</td>                           
                                <td>&nbsp;&nbsp; <input type="text" name="branchName" size="30"/></td>
                            </tr>
                            <tr>
                                <td><span class="point">*</span> Head/In-charge Name:</td>
                                <td>&nbsp;&nbsp; <input type="text" name="headName" size="30"/></td>
                            </tr>
                            <tr>
                                <td><span class="point">*</span> Username: </td>
                                <td>&nbsp;&nbsp; <input type="text" name="username" size="30"/></td>
                            </tr>
                            <tr>
                                <td><span class="point">*</span> Password: </td>
                                <td>&nbsp;&nbsp; <input type="password" name="password" size="30"/></td>
                            </tr>
                            <tr>
                                <td><span class="point">*</span> Confirm Password: </td>
                                <td>&nbsp;&nbsp; <input type="password" name="confPass" size="30"/></td>
                            </tr>                            
                            <tr>
                                <td> </td>
                                <td>
                                    <input type="submit" value="Register Branch"/> &nbsp; &nbsp;
                                    <input type="reset" value="Reset Form"/>
                                </td>

                            </tr>

                        </table>

                    </form>

                </div>

                <jsp:include page="../../formNotice.html" />

                <p align="center">
                   :  Password must be of at least 8 characters, must contain at least one alphabet, one digit and one special character 
                </p>

            </div>

        </div>

    </body>

</html>
