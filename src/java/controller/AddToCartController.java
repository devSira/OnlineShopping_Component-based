/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CheckOut;
import model.Products;
import model.ProductsTable;

/**
 *
 * @author Siraphob.B
 */
public class AddToCartController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Products> productList = ProductsTable.findAllProducts();
        int totalPrice = 0;
        List<CheckOut> checkoutList = new ArrayList<>();

        for (Products product : productList) {
            int productId = product.getId();
            String selected = request.getParameter("selectedProduct_" + productId);
            int quantity, price;

            if ("ON".equals(selected)) {
                quantity = Integer.parseInt(request.getParameter("quantity_" + productId));
                // Now you have the selected product and its corresponding quantity
//                System.out.println("Product ID: " + productId);
//                System.out.println("Quantity: " + quantity);
                price = quantity * product.getPrice();
                totalPrice += price;
//                System.out.println("Price: " + price);
//                System.out.println("TotalPrice: " + totalPrice);
                checkoutList.add(new CheckOut(productId, quantity, price));
            }
        }
//        System.out.println("----Checkout----");
//        for (CheckOut element : checkoutList) {
//            System.out.println(element.getId() +" "+ element.getQuantity() + " "+ element.getPrice());
//        }
//        System.out.println(totalPrice);
        
        request.setAttribute("totalPrice", totalPrice);
        request.setAttribute("checkoutList", checkoutList);
        request.getRequestDispatcher("add_shoppingcart.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
