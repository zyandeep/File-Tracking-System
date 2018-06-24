<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Successful File Registration </title>

        <link type="text/css" rel="stylesheet" href="/FTS/css/style.css" />

    </head>

    <body>

        <div class="container">

            <jsp:include page="../header.html"/>

            <div align="right"> 
                |<a href="loadFileType.do"> Add another file  </a>
                |<a href="welcomeBranch.jsp"> Back to My Account  </a>
                <hr />
            </div>

            <div class="content">

                <div align="center">
                    <br />
                    
                    <h3>
                        <font color="green">
                        File NO: &nbsp; ${ requestScope.fileBean.no} <br />
                        File Name: &nbsp; ${ requestScope.fileBean["name"] }<br /><br /><br />

                        -- has been registered successfully. <br /><br />
                        The corresponding file ID is: ${ requestScope.fileBean.id }
                        </font>                       
                    </h3>

                </div>

            </div>

        </div>

    </body>

</html>
