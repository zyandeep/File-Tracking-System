<jsp:directive.page  contentType="text/html" pageEncoding="UTF-8" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Branch Update Page</title>

        <link type="text/css" rel="stylesheet" href="/FTS/css/style.css" />

        <script type="text/javascript" src="/FTS/js/jquery.js"></script>
        <script type="text/javascript" src="/FTS/js/branchJS.js"></script>
    </head>

    <body>

        <div class="container">

            <jsp:include page="../header.html"/>

            <div align="right"> 
                |<a href="listRegFiles.do"> Back to View-registered-files  </a>
                |<a href="welcomeBranch.jsp"> Back to My Account  </a>
                <hr />
            </div>

            <div class="content">

                <div id="errorMesssage" align="center"><font color="red"> ${requestScope.errorMessage} </font> </div>

                <div class="form" align="center">

                    <form method="POST" action="updateFile.do" name="fileEditForm">

                        <table cellpadding="10" cellspacing="5" width="50%">
                            <caption> Edit file record with file ID- ${sessionScope.fileBean.id} </caption>

                            <tr>
                                <td>File ID:</td>                           
                                <td>
                                    &nbsp;&nbsp; <input type="text" name="id" value="${sessionScope.fileBean.id}" readonly="readonly" />

                                </td>
                            </tr>
                            <tr>
                                <td><span class="point">*</span>File No:</td>                           
                                <td>
                                    &nbsp;&nbsp; <input type="text" name="no" value=" <c:out value="${sessionScope.fileBean.no}"/> " size="30"/>
                                </td>
                            </tr>
                            <tr>
                                <td><span class="point">*</span>File Name:</td>
                                <td>
                                    &nbsp;&nbsp; <input type="text" name="name" value=" <c:out value="${sessionScope.fileBean.name}"/>  " size="30"/>
                                </td>
                            </tr>
                            <tr>
                                <td><span class="point">*</span>File Type:</td>
                                <td>
                                    <select name="type">
                                        <c:forEach var="type" items="${ sessionScope.fileType }">

                                            <c:choose>
                                                <c:when test="${fn:contains(type, sessionScope.fileBean.type) and 
                                                                fn:length(type) == fn:length(sessionScope.fileBean.type)}">

                                                        <option value="${ type }" selected="selected"> <c:out value="${ type }"/> </option>    

                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${ type }"> <c:out value="${ type }"/> </option>
                                                </c:otherwise>

                                            </c:choose>

                                        </c:forEach>

                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>Description:</td>
                                <td>
                                    &nbsp;&nbsp; <textarea name="description" rows="6" cols="30">

                                        <c:out value="${sessionScope.fileBean.description}"/> 

                                    </textarea>
                                </td>
                            </tr>
                            <tr>
                                <td> </td>
                                <td>
                                    <input type="submit" value="Update File"/>&nbsp; 
                                    <input type="reset" value="Reset Form"/>
                                </td>

                            </tr>

                        </table>

                    </form>
                </div>

                <jsp:include page="/formNotice.html" />

            </div>

        </div>


    </body>

</html>
