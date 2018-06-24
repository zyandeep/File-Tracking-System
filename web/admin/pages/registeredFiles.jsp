<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>

<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/> 

<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registered Files Page</title>

        <link type="text/css" rel="stylesheet" href="/FTS/css/style.css" />
        <link type="text/css" rel="stylesheet" href="/FTS/css/displaytag.css" />

        <script type="text/javascript" src="/FTS/js/jquery.js"></script>
        <script type="text/javascript" src="/FTS/js/adminJS.js"></script>
    </head>

    <body>

        <div class="container">
            
            <jsp:include page="../../header.html"/>
            <jsp:include page="navReport.html"/>

            <div class="content">

                <div id="printData">

                    <display:table name="${sessionScope.registeredFiles}" pagesize="5" export="true" uid="row">
                        <display:caption> List of Registered Files... </display:caption>

                        <display:column title="ID" property="id" /> 
                        <display:column title="File No">  <c:out value="${row.no}"/> </display:column>  
                        <display:column title="File Name"> <c:out value="${row.name}"/> </display:column>
                        <display:column title="File Type"> <c:out value="${row.type}"/> </display:column>
                        <display:column title="Description"> <c:out value="${row.description}"/> </display:column>
                        <display:column title="Owner"> <c:out value="${ row.owner }"/> </display:column>              

                        <c:choose>

                            <c:when test="${ fn:contains( row.status , 'NEW' ) }">

                                <display:column title="Current File Status"> <c:out value="${ row.status }"/> </display:column>

                            </c:when>

                            <c:when test="${ fn:contains( row.status , 'REJECTED' ) }">

                                <display:column title="Current File Status"> 
                                    <c:out value="${ row.status }"/> at <c:out value="${ row.sender }"/>
                                </display:column>

                            </c:when>

                            <c:when test="${ fn:contains( row.status , 'PENDING' ) }">

                                <display:column title="Current File Status"> 
                                    <c:out value="${ row.status }"/> at <c:out value="${ row.curLoc }"/>
                                </display:column>

                            </c:when>

                            <c:when test="${ fn:contains( row.status , 'PROCESSING' ) }">

                                <display:column title="Current File Status"> 
                                    <c:out value="${ row.status }"/> at <c:out value="${ row.curLoc }" default="${row.owner}" />
                                </display:column>

                            </c:when>    

                            <c:when test="${ fn:contains( row.status , 'CLOSED' ) }">

                                <display:column title="Current File Status"> 
                                    <c:out value="${ row.status }"/> at <c:out value="${ row.curLoc }" default="${row.owner}" />
                                </display:column>

                            </c:when>    

                        </c:choose>

                        <display:setProperty name="export.excel.filename" value="registeredFiles.xls"/>
                        <display:setProperty name="export.pdf.filename" value="registeredFiles.pdf"/>
                        <display:setProperty name="export.rtf.filename" value="registeredFiles.rtf"/>
                        <display:setProperty name="export.csv.filename" value="registeredFiles.csv"/>
                        <display:setProperty name="export.xml.filename" value="registeredFiles.xml"/>

                    </display:table>

                </div>

            </div>

        </div>

    </body>

</html>
