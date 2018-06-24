<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>

<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <link type="text/css" rel="stylesheet" href="/FTS/css/style.css" />
        <link type="text/css" rel="stylesheet" href="/FTS/css/displaytag.css" />

        <script type="text/javascript" src="/FTS/js/jquery.js"></script>
        <script type="text/javascript" src="/FTS/js/adminJS.js"></script>

        <title>Branch Report Page</title>
    </head>

    <body>

        <div class="container">

            <jsp:include page="../../header.html"/>
            <jsp:include page="navGeneral.html"/>

            <div align="center">
                <font color="green"> <c:out value="${requestScope.message}"/> </font>  
                <font color="red"> <c:out value="${requestScope.errorMessage}"/> </font>
            </div>

            <div class="content">

                <display:table name="${sessionScope.branchRecords}" pagesize="20" uid="row" >

                    <display:caption> List of Registered Branch... </display:caption>

                    <display:column title="ID" property="id" /> 
                    <display:column title="Branch Name">  <c:out value="${row.officeName}"/> </display:column>  
                    <display:column title="Incharge Name"> <c:out value="${row.headName}"/> </display:column>
                    <display:column title="Username"> <c:out value="${row.username}"/> </display:column>
                    <display:column title="Edit Branch Record"> 

                        <form action="branchUpdate.do" method="POST">
                            <input type="hidden" name="id" value="${row.id}"/>
                            <input type="submit" value="Edit"/>
                        </form>

                    </display:column>
                    <display:column title="Delete Branch Record"> 

                        <form action="deleteBranch.do" method="POST" class="deleteBranch">
                            <input type="hidden" name="id" value="${row.id}"/>
                            <input type="submit" value="Delete"/>
                        </form>

                    </display:column>

                </display:table>

            </div>

        </div>




    </body>

</html>
