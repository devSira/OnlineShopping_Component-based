<%-- 
    Document   : show_confirmation
    Created on : Oct 27, 2023, 11:21:36 AM
    Author     : Siraphob.B
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm Order</title>
    </head>
    <%

        int totalPrice = (Integer) request.getAttribute("totalPrice");

    %>
    <body>
        <h1>Your Order is Confirmed!</h1>
        <h1>The Total amount is $<%=totalPrice%></h1>
        <a href="index.jsp"><input type="button" value="Back to Home" name="Home" /></a>
    </body>
</html>
