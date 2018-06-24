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
        <script type="text/javascript" src="/FTS/js/branchJS.js"></script>
    </head>

    <body>

        <div class="container">

            <jsp:include page="../header.html"/>
            <jsp:include page="navGeneral.html"/>

            <div class="content">

                <%-- for success and error message --%>
                <div align="center"><font color="green"> ${requestScope.message} </font></div>
                <div id="errorMesssage" align="center"><font color="red"> ${requestScope.errorMessage} </font></div>

                <%-- Name of the loggin branch--%>
                <c:set var="logginBranch" value="${ sessionScope.branch.officeName }" />


                <display:table name="${sessionScope.registeredFiles}" pagesize="5"  uid="row" >

                    <display:caption> List of Registered Files... </display:caption>
                    
                    <display:setProperty name="paging.banner.placement" value="bottom" />

                    <display:column title="File ID" property="id" /> 
                    <display:column title="File No">  <c:out value="${row.no}"/> </display:column>  
                    <display:column title="File Name"> <c:out value="${row.name}"/> </display:column>
                    <display:column title="File Type"> <c:out value="${row.type}"/> </display:column>
                    <display:column title="Description"> <c:out value="${row.description}"/> </display:column>

                    <%-- current the status of the file --%>
                    <c:set var="status" value="${row.status}" />

                    <c:choose>

                        <%-- It's a NEW file--%>
                        <c:when test="${ fn:contains( row.status , 'NEW' ) }">

                            <%-- edit the file --%>
                            <display:column title="Edit File Recoed">
                                <form action="getFile.do" method="POST">
                                    <input type="hidden" name="id" value="${row.id}"/>
                                    <input type="submit" value="Edit File"/>
                                </form>
                            </display:column>

                            <%-- change file status--%>
                            <display:column title="Process File">
                                <form action="changeStatus.do" method="POST" class="changeStatusForm">

                                    <select name="fileStatus">

                                        <option disabled="disabled" selected="selected"> Change File Status </option>
                                        <option value="PROCESSING"> PROCESSING </option>
                                        <option value="CLOSED"> CLOSED </option>

                                    </select>
                                    <input type="hidden" name="id" value="${row.id}"/>
                                    <input type="submit" value="Change"/>

                                </form>
                            </display:column>

                            <%-- send the file --%>
                            <display:column title="Send File">
                                <form action="sendFile.do" method="POST" class="sendFileForm">

                                    <select name="branchToForward">

                                        <option disabled="disabled" selected="selected"> Select the branch </option>

                                        <c:forEach var="branch" items="${sessionScope.branchList}">

                                            <c:if test="${ not (fn:length(branch) eq fn:length(logginBranch) and 
                                                           fn:contains(branch, logginBranch)) }">

                                                  <option value="${ branch }"> ${branch} </option>
                                            </c:if>

                                        </c:forEach>

                                    </select>

                                    <input type="hidden" name="id" value="${row.id}"/>
                                    <input type="submit" value="Send File"/>

                                </form>

                            </display:column>

                        </c:when>

                        <%-- It's a PENDING file--%>
                        <c:when test="${ fn:contains( row.status , 'PENDING' ) }">
                            <display:column title="Edit File Record">  </display:column>
                            <display:column title="Process File"> The file is dispatched successfully </display:column>
                            <display:column title="Send File">  </display:column>
                        </c:when>

                        <%-- It's a REJECTED file--%>
                        <c:when test="${ fn:contains(status, 'REJECTED') }">
                            <display:column title="Edit File Record">  </display:column>
                            <display:column title="Process File"> The file is dispatched successfully </display:column>
                            <display:column title="Send File">  </display:column>
                        </c:when> 

                        <%-- It's a CLOSED file--%>    
                        <c:when test="${ fn:contains( row.status , 'CLOSED' ) }">

                            <display:column title="Edit File Record">  </display:column>

                            <c:choose>
                                <%-- file is at OWNER's branch --%>
                                <c:when test="${ empty row.curLoc}">    
                                    <display:column title="Process File"> The file is closed </display:column>

                                </c:when>

                                <c:otherwise>
                                    <display:column title="Process File"> The file is dispatched successfully </display:column>
                                </c:otherwise>

                            </c:choose>

                            <display:column title="Send File">  </display:column>

                        </c:when> 

                        <%-- It's a PROCESSING file--%>
                        <c:when test="${ fn:contains( row.status , 'PROCESSING' ) }">

                            <display:column title="Edit File Recoed"> </display:column>

                            <c:choose>

                                <%-- file is at OWNER's branch --%>
                                <c:when test="${ empty row.curLoc}">       

                                    <display:column title="Process File">
                                        <form action="changeStatus.do" method="POST" class="changeStatusForm">

                                            <select name="fileStatus">

                                                <option disabled="disabled" selected="selected"> Change File Status </option>
                                                <option value="CLOSED"> CLOSED </option>

                                            </select>
                                            <input type="hidden" name="id" value="${row.id}"/>
                                            <input type="submit" value="Change"/>

                                        </form>
                                    </display:column>

                                    <display:column title="Send File">
                                        <form action="sendFile.do" method="POST" class="sendFileForm">

                                            <select name="branchToForward">

                                                <option disabled="disabled" selected="selected"> Select the branch </option>

                                                <c:forEach var="branch" items="${sessionScope.branchList}">

                                                    <c:if test="${ not (fn:length(branch) eq fn:length(logginBranch) and 
                                                                   fn:contains(branch, logginBranch)) }">

                                                          <option value="${ branch }"> ${branch} </option>
                                                    </c:if>

                                                </c:forEach>

                                            </select>

                                            <input type="hidden" name="id" value="${row.id}"/>
                                            <input type="submit" value="Send File"/>

                                        </form>

                                    </display:column>

                                </c:when>

                                <c:otherwise>
                                    <display:column title=" "> The file is dispatched successfully </display:column>
                                </c:otherwise>

                            </c:choose>

                        </c:when>

                    </c:choose>

                </display:table>

            </div>

        </div>

    </body>

</html>
