<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>

<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/> 

<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Branch Inbox Page</title>

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


                <display:table name="${sessionScope.incomingFiles}" pagesize="2" uid="row">
                    <display:caption> Branch Inbox </display:caption>

                    <display:setProperty name="paging.banner.placement" value="bottom" />

                    <%-- get the file's current location and the name of sender branch--%>
                    <c:set var="curLoc" value="${ row.curLoc }" />
                    <c:set var="sender" value="${ row.sender}" />


                    <display:column title="File ID" property="id" /> 
                    <display:column title="File No">  <c:out value="${row.no}"/> </display:column>  
                    <display:column title="File Name"> <c:out value="${row.name}"/> </display:column>
                    <display:column title="File Type"> <c:out value="${row.type}"/> </display:column>
                    <display:column title="Sender"> <c:out value="${ row.sender }"/> </display:column>
                    <display:column title="Date Of Send"> <c:out value="${ row.dateOfSend }"/> </display:column>              

                    <c:choose>

                        <%-- Let the user to recieve the file first--%>
                        <c:when test="${ row.fileRecieved == false }">   

                            <display:column title="Cause of Rejection"> </display:column>
                            <display:column title="Receive File"> 

                                <form action="recieveFile.do" method="POST">
                                    <input type="hidden" name="id" value="${row.id}"/>
                                    <input type="submit" value="Recieve File"/>
                                </form>

                            </display:column>
                            <display:column title="Reject File"> </display:column>
                            <display:column title="Forward File"> </display:column>
                            <display:column title="Close File"> </display:column>

                        </c:when>


                        <%-- It's a PENDING file --%>
                        <c:when test="${ row.remark == null }">                          

                            <display:column title="Cause of Rejection"> </display:column>
                            <display:column title="Receive File"> </display:column>
                            <display:column title="Reject File">

                                <form action="rejectFile.do" method="POST" class="rejectForm">
                                    <textarea name="textArea" placeholder="Why to Reject?" cols="30" rows="2"></textarea><br />

                                    <input type="hidden" name="id" value="${row.id}"/>
                                    <input type="submit" value="Reject File"/>
                                </form>

                            </display:column>

                            <display:column title="Forward File">

                                <form action="sendFile.do?page=inbox" method="POST" class="sendFileForm">

                                    <select name="branchToForward">

                                        <option disabled="disabled" selected="selected"> Select the branch </option>

                                        <c:forEach var="branch" items="${ sessionScope.branchList }">

                                            <c:if test="${not ((fn:length(branch) eq fn:length(curLoc) and fn:contains(branch, curLoc)) or 
                                                          (fn:length(branch) eq fn:length(sender) and fn:contains(branch, sender)))}">

                                                  <option value="${ branch }"> ${branch} </option>
                                            </c:if>

                                        </c:forEach>

                                    </select>

                                    <input type="hidden" name="id" value="${row.id}" />
                                    <input type="submit" value="Forward File"/>

                                </form>

                            </display:column>

                            <display:column title="Close File">

                                <form action="changeStatus.do?page=inbox&fileStatus=CLOSED" method="POST">
                                    <input type="hidden" name="id" value="${row.id}"/>
                                    <input type="submit" value="Close File"/>
                                </form>

                            </display:column> 

                        </c:when>

                        <%-- It's a REJECTED file --%>
                        <c:otherwise>               

                            <display:column title="Cause of Rejection"> <c:out value="${ row.remark }"/> </display:column>
                            <display:column title="Receive File"> </display:column>
                            <display:column title="Reject File"> </display:column>
                            <display:column title="Forward File">

                                <form action="sendFile.do?page=inbox" method="POST" class="sendFileForm">

                                    <select name="branchToForward">

                                        <option disabled="disabled" selected="selected"> Select the branch </option>

                                        <c:forEach var="branch" items="${ sessionScope.branchList }">

                                            <c:if test="${ not (fn:length(branch) eq fn:length(curLoc) and fn:contains(branch, curLoc)) }">
                                                <option value="${ branch }"> ${branch} </option>
                                            </c:if>

                                        </c:forEach>

                                    </select>

                                    <input type="hidden" name="id" value="${row.id}" />
                                    <input type="submit" value="Forward File"/>
                                </form>

                            </display:column>

                            <display:column title="Close File">

                                <form action="changeStatus.do?page=inbox&fileStatus=CLOSED" method="POST" >
                                    <input type="hidden" name="id" value="${row.id}"/>
                                    <input type="submit" value="Close File"/>
                                </form>

                            </display:column>

                        </c:otherwise>

                    </c:choose>

                </display:table>



            </div>

        </div>

    </body>

</html>
