<%-- 
    Document   : index
    Created on : Oct 25, 2023, 10:36:48 PM
    Author     : Siraphob.B
--%>

<%@page import="model.ProductsTable"%>
<%@page import="model.Products"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show Items</title>
    </head>
    <jsp:useBean id="product" class="model.Products" scope="request"/>
    <%

        List<Products> productList = ProductsTable.findAllProducts();
        Iterator<Products> itr = productList.iterator();

    %>
    <body>
    <center>
        <h1>Products List</h1>
        <form name="addToCart" action="AddToCart" method="POST">
            <table border="1">
                <tr>
                    <th>Movie</th>
                    <th>Rating</th>
                    <th>Year Created</th>
                    <th>Price</th>
                    <th>Quantity</th>
                </tr>
                <% while (itr.hasNext()) {
                        product = itr.next();
                %>
                <tr>
                    <td>
                        <input type="checkbox" name="selectedProduct_<%= product.getId()%>" value="ON" />
                        <%= product.getMovie()%>
                    </td>
                    <td> <%= product.getRating()%></td>
                    <td> <%= product.getYearcreate()%></td>
                    <td> <%= product.getPrice()%></td>
                    <td>
                        <input type="text" name="quantity_<%= product.getId()%>" value="0" size="5" />
                    </td>
                </tr>
                <%
                    }
                %>
            </table>
            <input type="submit" value="AddToCart" name="addToCart" />
        </form>
    </center>
</body>
</html>
