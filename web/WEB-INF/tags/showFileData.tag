<%@tag description="Displays file-track record" pageEncoding="UTF-8"%>
<%@tag import="java.util.Arrays" %>

<table border="1" width="70%" cellpadding="5">

    <thead>
        <tr>
            <th> SENDER BRANCH </th>
            <th> DATE-OF-SEND </th>
            <th> FORWARD-TO-BRANCH </th>
            <th> DATE-OF-RECEIVE </th>
        </tr>
    </thead>

    <tbody>
        
        <%

            String[] dates = (String[]) request.getAttribute("dates");
            String[] path = (String[]) request.getAttribute("path");
            String[] rDates = ( String[] ) request.getAttribute( "rDates");
            
            String recieveDates = "Not recieved yet";
           
            // While displaying "recieve dates", elements should be counted from index[1] 
 
            for ( int i = 0; i < dates.length; i++ ) {

        %>

        <tr>
            <td align="center"> <%= path[i] %></td>
            <td align="center"> <%= dates[i] %></td> 
            <td align="center"> <%= path[i + 1] %> </td>
            <td align="center"> 
                
                <jsp:scriptlet>
                    if( i + 1 == rDates.length ) {
                        recieveDates = "Not recieved yet";
                    }
                    else {
                        recieveDates = rDates[ i + 1 ];
                    }                          
                </jsp:scriptlet>
                
                <%= recieveDates %>
                    
            </td>
            
        </tr>

        <% 
            } 
        %>

    </tbody>

</table>