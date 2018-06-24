<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 

<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>File Registration Page</title>

        <link type="text/css" rel="stylesheet" href="/FTS/css/style.css" />

        <script type="text/javascript" src="/FTS/js/jquery.js"></script>
        <script type="text/javascript" src="/FTS/js/branchJS.js"></script>
    </head>

    <body>
        <div class="container">
            <jsp:include page="../header.html"/>
            <jsp:include page="navGeneral.html"/>

            <div class="content">
             
                <div id="errorMesssage" align="center"><font color="red"> ${requestScope.errorMessage} </font></div>

                <div class="form" align="center" >

                    <form method="POST" action="addFile.do" name="fileRegForm">

                        <table cellpadding="15" cellspacing="5" width="50%">
                            <caption> Register New File </caption>
                            <tr>
                                <td><span class="point">*</span>File No:</td>                           
                                <td>&nbsp;&nbsp;<input type="text" name="fileNo" size="30"/></td>
                            </tr>
                            <tr>
                                <td><span class="point">*</span>File Name:</td>
                                <td>&nbsp;&nbsp;<input type="text" name="fileName" size="30"/></td>
                            </tr>
                            <tr>
                                <td><span class="point">*</span>File Type:</td>
                                <td>
                                    <select name="fileType"> 
                                        <c:forEach var="type" items="${ sessionScope.fileType }">

                                            <option value="${ type }"> <c:out value="${ type }"/> </option>

                                        </c:forEach>

                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>Description:</td>
                                <td>&nbsp;&nbsp; <textarea name="description" rows="6" cols="30"></textarea></td>
                            </tr>
                            <tr>
                                <td> </td>
                                <td>
                                    <input type="submit" value="Register File"/> &nbsp; &nbsp;
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
