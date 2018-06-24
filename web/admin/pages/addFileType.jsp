<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 

<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add New File Type</title>

        <link type="text/css" rel="stylesheet" href="/FTS/css/style.css" />

        <script type="text/javascript" src="/FTS/js/jquery.js"></script>
        <script type="text/javascript" src="/FTS/js/adminJS.js"></script>  

    </head>

    <body>

        <div class="container">

            <jsp:include page="../../header.html"/>
            <jsp:include page="navGeneral.html"/>

            <div class="content">

                <div id="errorMesssage" align="center"><font color="red"> ${requestScope.errorMessage} </font> </div>
                <div align="center"><font color="green"> ${requestScope.message} </font> </div>

                <div class="form" align="center">
                    <form method="GET" action="addFileType.do" name="addFileType">
                        <table cellpadding="10" cellspacing="5" width="50%" >
                            <caption> Register File type</caption>

                            <tr>
                                <td> Enter a File-type: </td>
                                <td><input type="text" name="fileType" size="20"/> </td>
                            </tr>
                            <tr>
                                <td> </td><td> </td>
                            </tr> 
                            <tr>
                                <td colspan="2" align="center"> <input type="submit" value=" Add File-type "/> </td>
                                <td> </td>
                            </tr> 
                        </table>           
                    </form>
                </div>
                <br />
                <br />

                <div class="form" align="center">

                    <c:if test="${ sessionScope.fileType != null }">

                        <form method="GET" action="delFileType.do" name="deleteFileType">

                            <table cellpadding="10" cellspacing="5" width="50%"  >
                                
                                <tr>
                                    <td> Registered File-Type(s): </td>
                                    <td>
                                        <select name="fileType">
                                            <c:forEach var="type" items="${ sessionScope.fileType }">

                                                <option> <c:out value="${ type }"/> </option>

                                            </c:forEach>

                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td> </td><td> </td>
                                </tr> 
                                <tr>
                                    <td colspan="2" align="center"> <input type="submit" value=" Delete File-type "/> </td>
                                    <td> </td>
                                </tr>

                            </table>

                        </form>

                    </c:if>

                </div>

            </div>

        </div>





    </body>
</html>
