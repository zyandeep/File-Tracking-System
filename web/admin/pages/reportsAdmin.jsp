<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
        <title>Admin Reports Page</title>

        <link type="text/css" rel="stylesheet" href="/FTS/css/style.css" />
    </head>

    <body>

        <div class="container">

            <jsp:include page="../../header.html"/>
            <jsp:include page="navGeneral.html"/>

            <div class="content">

                <div id="errorMesssage" align="center"><font color="red">  ${ requestScope.errorMessage } </font> </div>
                <br />
                
                <div align="center"> <h3> Generating Reports for Admin... </h3> </div>
                
                <div align="center" class="loginForm">

                    <table cellpadding="10" cellspacing="5">                        
                        <thead>
                            <tr>
                                <th> View The Report Of </th>
                            </tr>
                            <tr>
                                
                            </tr>
                        </thead>

                        <tbody>

                            <tr>
                                <td><a href="regFiles.do"> List of Registered Files </a></td>
                            </tr>
                            <tr>
                                <td><a href="pendFiles.do"> List of Pending Files </a></td>
                            </tr>
                            <tr>
                                <td><a href= "rejectFiles.do"> List of Rejected Files </a></td>
                            </tr>
                            <tr>
                                <td> <a href= "processFile.do"> List of Processing Files </a></td>
                            </tr>
                            <tr>
                                <td> <a href= "closeFiles.do"> List of Closed Files </a></td>
                            </tr>

                        </tbody>
                    </table>


                </div>



            </div>

        </div>

    </body>

</html>
