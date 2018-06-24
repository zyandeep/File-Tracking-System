<jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@taglib prefix="zTag" tagdir="/WEB-INF/tags/" %>

<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Track File By Date-of-Receive Page</title>

        <link type="text/css" rel="stylesheet" href="/FTS/css/style.css" />

        <script type="text/javascript" src="/FTS/js/jquery.js"></script>
        <script type="text/javascript" src="/FTS/js/branchJS.js"></script>
    </head>

    <body>

        <div class="container">

            <jsp:include page="../header.html"/>
            <jsp:include page="navSearch.html"/>

            <div class="content">

                <div align="center" class="loginForm">

                    <form method="GET" action="trackDoR.do" name="trackDoRForm">

                        <table cellpadding="5" cellspacing="5">
                            <caption> Track file by Date-of-receive </caption>

                            <tr>
                                <td> Enter the date of receive: &nbsp;&nbsp; </td> 
                                <td> <input type="text" name="dor" placeholder="dd-mm-yyyy" /> </td>
                            </tr>

                            <tr>
                                <td>   </td> 
                                <td>
                                    <input type="submit" value="Track File" />                           
                                </td>
                            </tr>
                        </table>

                    </form>
                </div>

                <%-- for displaying error message --%>
                <div id="errorMesssage" align="center"><font color="red"> ${requestScope.errorMessage} </font></div>

                <div class="fileTrackData" align="center">

                    <%-- for displaying file record list --%>
                    <c:if test="${ not empty requestScope.fileList }">

                        <div align="center">

                            <h4>
                                Select the "Date-of-receive" of the file to track it down...
                            </h4>

                            <table border="1" cellpadding="6" width="60%">
                                <tr>
                                    <th>File ID</th><th>File No</th><th>File Name</th><th>Owner</th><th>DATE-OF-RECEIVE</th><th>CURRENT FILE STATUS</th>
                                </tr>

                                <c:forEach var="file" items="${requestScope.fileList}">

                                    <tr>
                                        <td align="center">${ file.id }</td>
                                        <td align="center"> <c:out value="${ file.no }"/> </td>
                                        <td align="center">  <c:out value="${ file.name }"/> </td>
                                        <td align="center"> <c:out value="${ file.owner }"/> </td>
                                        <td align="center"><a href="trackDoR.do?file_id=${ file.id }"> <c:out value="${ file.dateOfReceive }"/> </a></td>

                                        <c:choose>

                                            <c:when test="${ fn:contains( file.status, 'REJECTED') }">
                                                <td align="center"><font color="red"> ${ file.status } </font></td>
                                                </c:when>

                                            <c:when test="${ fn:contains( file.status, 'PENDING') }">
                                                <td align="center"><font color="orange"> ${ file.status } </font></td>
                                                </c:when>

                                            <c:when test="${ fn:contains( file.status, 'PROCESSING') }">
                                                <td align="center"><font color="green"> ${ file.status } </font></td>
                                                </c:when>    

                                            <c:when test="${ fn:contains( file.status, 'CLOSED') }">
                                                <td align="center"><font color="blue"> ${ file.status } </font></td>
                                                </c:when> 

                                            <c:otherwise>
                                                <td align="center"> ${ file.status } </td>
                                            </c:otherwise>

                                        </c:choose>

                                    </tr>
                                </c:forEach>

                            </table>

                        </div>

                    </c:if>



                    <%-- Below this File-Track data will be displayed --%>
                    <c:if test="${ not empty requestScope.file }">

                        File ID: &nbsp; ${file.id} <br /> 
                        File NO: &nbsp; <c:out value="${file.no}" /><br />
                        File Name: &nbsp; <c:out value="${file.name}" /><br />
                        File Owner: &nbsp; <c:out value="${file.owner}" /><br />
                        Date Of File-Creation: &nbsp; <c:out value="${ file.dateOfCreation }" /><br />
                        Current Status: &nbsp; <c:out value="${file.status}" /> @

                        <c:choose>

                            <c:when test="${ fn:contains( file.status, 'NEW') }">
                                <c:out value="${ file.owner }" /><br />        
                            </c:when>

                            <c:when test="${ fn:contains( file.status, 'REJECTED') }">
                                <c:out value="${file.sender}" /><br />
                            </c:when>

                            <c:when test="${ fn:contains( file.status, 'CLOSED') }">

                                <c:out value="${file.curLoc}" />

                                <c:if test="${ empty file.curLoc }">
                                    <c:out value="${file.owner}" />
                                </c:if>
                                <br />

                                Date-Of-Close: &nbsp; <c:out value="${file.dateOfClose}" /><br />        
                            </c:when>

                            <c:otherwise>

                                <c:out value="${file.curLoc}" />

                                <c:if test="${ empty file.curLoc }">
                                    <c:out value="${file.owner}" />
                                </c:if>
                                <br />

                            </c:otherwise>

                        </c:choose>
                        <br />        


                        <%-- Here the path of the file's movement is displayed--%>        
                        <div align="center">

                            <strong> PATH: </strong>
                            <c:forEach var="node" items="${ requestScope.path }" varStatus="status">

                                <i>
                                    <c:choose>

                                        <c:when test="${ status.count == 1 }">
                                            <c:out value="${ node }" />
                                        </c:when>

                                        <c:otherwise>
                                            ---><c:out value="${ node }" />
                                        </c:otherwise>

                                    </c:choose>

                                </i>

                            </c:forEach>

                        </div>
                        <br />


                        <%-- Here the File-Track data is displayed if "dates" is not null --%>        
                        <c:if test="${ not empty requestScope.dates }">

                            <div align="center">

                                <zTag:showFileData />

                            </div>

                        </c:if>

                    </c:if>

                </div>

            </div>

        </div>



    </body>

</html>
