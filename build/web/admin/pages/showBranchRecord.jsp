<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Branch Update Page</title>

        <link type="text/css" rel="stylesheet" href="/FTS/css/style.css" />

        <script type="text/javascript" src="/FTS/js/jquery.js"></script>
        <script type="text/javascript" src="/FTS/js/adminJS.js"></script>  
    </head>

    <body>

        <div class="container">

            <jsp:include page="../../header.html"/>

            <div align="right"> 
                |<a href="branchList.jsp"> Back to View Registered Branch </a>
                |<a href="welcomeAdmin.jsp"> Back to My Account  </a>
                <hr />
            </div>

            <div class="content">

                <div id="errorMesssage" align="center"><font color="red"> ${requestScope.errorMessage} </font></div>

                <div class="form" align="center">

                    <form method="post" action="modifyBranch.do" name="modifyBranchForm">

                        <table cellpadding="15" cellspacing="5" width="50%">
                            <caption> Update branch record for <c:out value=" ${sessionScope.branchBean.officeName}" /> </caption>

                            <tr>
                                <td>Branch ID:</td>                           
                                <td>&nbsp;&nbsp; <input type="text" name="id" value="${sessionScope.branchBean.id}" readonly="readonly" /></td>
                            </tr>                        
                            <tr>
                                <td><span class="point">*</span> Branch Name:</td>                           
                                <td>&nbsp;&nbsp; <input type="text" name="branchName" value=" <c:out value="${sessionScope.branchBean.officeName}" />" size="30"/></td>
                            </tr>
                            <tr>
                                <td><span class="point">*</span> Head/In-charge Name:</td>
                                <td>&nbsp;&nbsp; <input type="text" name="headName" value=" <c:out value="${sessionScope.branchBean.headName}" />" size="30"/></td>
                            </tr>
                            <tr>
                                <td><span class="point">*</span> Username: </td>
                                <td>&nbsp;&nbsp; <input type="text" name="username"  value=" <c:out value="${sessionScope.branchBean.username}" /> " size="30"/></td>
                            </tr>
                            <tr>
                                <td><span class="point">*</span> New Password: </td>
                                <td>&nbsp;&nbsp; <input type="password" name="newPass" size="30"/></td>
                            </tr>
                            <tr>
                                <td><span class="point">*</span> Confirm New Password: </td>
                                <td>&nbsp;&nbsp; <input type="password" name="confPass" size="30"/></td>
                            </tr>                            
                            <tr>
                                <td> </td>
                                <td>
                                    <input type="submit" value="Update Branch"/> &nbsp; &nbsp;
                                    <input type="reset" value="Reset Form"/>
                                </td>

                            </tr>

                        </table>

                    </form>

                </div>

                <jsp:include page="../../formNotice.html" />

                <p align="center">
                    :  Password must be of at least 8 characters, must contain at least one alphabet, one digit and one special character 
                </p>

            </div>

        </div>

    </body>

</html>
