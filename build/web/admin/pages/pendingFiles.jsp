<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>

<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/> 

<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link type="text/css" rel="stylesheet" href="/FTS/css/style.css" />
        <link type="text/css" rel="stylesheet" href="/FTS/css/displaytag.css" />

        <script type="text/javascript" src="/FTS/js/jquery.js"></script>
        <script type="text/javascript" src="/FTS/js/adminJS.js"></script>

        <title>Pending Files Page</title>
    </head>

    <body>

        <div class="container">

            <jsp:include page="../../header.html"/>
            <jsp:include page="navReport.html"/>

            <div class="content">

                <div id="printData">

                    <display:table name="${sessionScope.pendingFiles}" pagesize="20" export="true" uid="row">
                        <display:caption> List of Pending Files... </display:caption>

                        <display:column title="ID" property="id" /> 
                        <display:column title="File No">  <c:out value="${row.no}"/> </display:column>  
                        <display:column title="File Name"> <c:out value="${row.name}"/> </display:column>
                        <display:column title="File Type"> <c:out value="${row.type}"/> </display:column>
                        <display:column title="Sender"> <c:out value="${row.sender}" default="NONE"/> </display:column>
                        <display:column title="Forward to Branch"> <c:out value="${row.curLoc}"/> </display:column>
                        <display:column title="Pending from"> <c:out value="${row.dateOfSend}" default="N/A"/> </display:column>

                        <display:setProperty name="export.excel.filename" value="pendingFiles.xls"/>
                        <display:setProperty name="export.pdf.filename" value="pendingFiles.pdf"/>
                        <display:setProperty name="export.rtf.filename" value="pendingFiles.rtf"/>
                        <display:setProperty name="export.csv.filename" value="pendingFiles.csv"/>
                        <display:setProperty name="export.xml.filename" value="pendingFiles.xml"/>

                    </display:table>

                </div>

            </div>

        </div>

    </body>

</html>
