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

        <title>Closed Files Page</title>
    </head>

    <body>

        <div class="container">

            <jsp:include page="../../header.html"/>
            <jsp:include page="navReport.html"/>

            <div class="content">

                <div id="printData">

                    <display:table name="${sessionScope.closeFiles}" pagesize="20" export="true" uid="row">
                        <display:caption> List of Closed Files... </display:caption>

                        <display:column title="ID" property="id" /> 
                        <display:column title="File No">  <c:out value="${row.no}"/> </display:column>  
                        <display:column title="File Name"> <c:out value="${row.name}"/> </display:column>
                        <display:column title="File Type"> <c:out value="${row.type}"/> </display:column>
                        <display:column title="Owner"> <c:out value="${row.owner}" /> </display:column>
                        <display:column title="Date of Send"> <c:out value="${row.dateOfSend}" default="N/A"/> </display:column>
                        <display:column title="Closed by Branch"> 
                            <c:out value="${row.curLoc}" default="${row.owner}"/> 
                        </display:column>
                        <display:column title="Closed Date"> <c:out value="${row.dateOfClose}"/> </display:column> 

                        <display:setProperty name="export.excel.filename" value="closedFiles.xls"/>
                        <display:setProperty name="export.pdf.filename" value="closedFiles.pdf"/>
                        <display:setProperty name="export.rtf.filename" value="closedFiles.rtf"/>
                        <display:setProperty name="export.csv.filename" value="closedFiles.csv"/>
                        <display:setProperty name="export.xml.filename" value="closedFiles.xml"/>

                    </display:table>

                </div>

            </div>

        </div>

    </body>

</html>
