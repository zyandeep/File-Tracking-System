<jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />

<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link type="text/css" rel="stylesheet" href="/FTS/css/style.css" />

        <title>Track File Page</title>
    </head>

    <body>

        <div class="container">

            <jsp:include page="../../header.html"/>
            <jsp:include page="navGeneral.html"/>

            <div class="content">

                <div class="form" align="center" >
                    <table cellpadding="10" cellspacing="5">
                        <caption> 
                            <span>
                                <img src="/FTS/img/find.png" alt="search file" width="35" height="35" /> Choose your option..
                            </span>
                        </caption>

                        <tr>
                            <td><a href="trackIdAdmin.jsp"> Track file by file ID </a></td>
                        </tr>
                        <tr>
                            <td> <a href="trackNoAdmin.jsp"> Track file by file no </a></td>
                        </tr>
                        <tr>
                            <td><a href="trackNameAdmin.jsp"> Track file by file name </a></td>
                        </tr>
                        <tr>
                            <td><a href="trackByStatus.jsp"> Track file by file-status </a></td>
                        </tr>
                        <tr>
                            <td><a href="trackByBranch.jsp"> Track file by branch </a></td>
                        </tr>
                        <tr>
                            <td><a href="trackDoSAdmin.jsp"> Track file by date of send </a></td>
                        </tr>
                        <tr>
                            <td><a href="trackDoRAdmin.jsp"> Track file by date of receive </a></td>
                        </tr>
                    </table>

                </div>

            </div>

        </div>

    </body>

</html>
