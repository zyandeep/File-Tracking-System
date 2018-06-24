<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>

<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Sent File Page</title>

        <link type="text/css" rel="stylesheet" href="/FTS/css/style.css" />
        <link type="text/css" rel="stylesheet" href="/FTS/css/displaytag.css" />

    </head>

    <body>

        <div class="container">

            <jsp:include page="../header.html"/>
            <jsp:include page="navGeneral.html"/>

            <div class="content">

                <display:table name="${sessionScope.sentFiles}" pagesize="20" uid="row">
                    <display:caption> List of Sent Files... </display:caption>
                    
                    <display:setProperty name="paging.banner.placement" value="bottom" />

                    <display:column title="File ID" property="id" /> 
                    <display:column title="File No">  <c:out value="${row.no}"/> </display:column>  
                    <display:column title="File Name"> <c:out value="${row.name}"/> </display:column>
                    <display:column title="Sent to Branch"> <c:out value="${row.forwardBranch}"/> </display:column>
                    <display:column title="Date of Send"> <c:out value="${row.dateOfSend}"/> </display:column>

                    <c:choose>

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
                                <c:out value="${ row.status }"/> at <c:out value="${ row.curLoc }" />
                            </display:column>

                        </c:when>    

                        <c:otherwise>

                            <display:column title="Current File Status"> 
                                <c:out value="${ row.status }"/> at <c:out value="${ row.curLoc }" />
                            </display:column>

                        </c:otherwise>    

                    </c:choose>

                </display:table>
                                
            </div>

        </div>



    </body>

</html>
