<%-- 
    Document   : add_shoppingcart
    Created on : Oct 26, 2023, 4:36:32 PM
    Author     : Siraphob.B
--%>

<%@page import="model.ProductsTable"%>
<%@page import="model.Products"%>
<%@page import="model.CheckOut"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add to Shopping Cart</title>
    </head>


    <%

        int totalPrice = (Integer) request.getAttribute("totalPrice");
        List<CheckOut> checkoutList = (List<CheckOut>) request.getAttribute("checkoutList");
        List<Products> productList = ProductsTable.findAllProducts();

    %>
    <body>
    <center>
        <h1>Shopping Cart</h1>
        <form name="checkout" action="CheckoutOrderController" method="POST">
            <table border="1">
                <tr>
                    <th>Movie</th>
                    <th>Rating</th>
                    <th>Year Created</th>
                    <th>Price/Unit</th>
                    <th>Quantity</th>
                    <th>Price</th>
                </tr>
                <%                    
                    if (checkoutList != null && productList != null) {
                        for (CheckOut checkout : checkoutList) {
                            for (Products product : productList) {
                                if (product.getId() == checkout.getId()) {
                %>
                <tr>
                <input type="hidden" name="productId_<%= checkout.getId()%>" value="<%= checkout.getId()%>">
                <input type="hidden" name="quantity_<%= checkout.getId()%>" value="<%= checkout.getQuantity()%>">
                <input type="hidden" name="totalPrice" value="<%= totalPrice%>">
                <td><%= product.getMovie()%></td>
                <td><%= product.getRating()%></td>
                <td><%= product.getYearcreate()%></td>
                <td><%= product.getPrice()%></td>
                <td><%= checkout.getQuantity()%></td>
                <td><%= checkout.getPrice()%></td>
                </tr>
                <%
                                }
                            }
                        }
                    } else {
                        out.println("<h1> Data are not found </h1>");
                    }
                %>
                <tr>
                    <td colspan="5"><center>Total Price</center></td>
                <td><%= totalPrice%></td>
                </tr>
            </table>
            <input type="submit" value="Check Out" name="checkout" />
        </form>
    </center>
</body>
</html>
