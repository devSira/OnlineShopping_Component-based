/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.Enumeration;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Products;
import model.ProductsTable;
import model.Shoppingcart;
import model.ShoppingcartTable;

/**
 *
 * @author Siraphob.B
 */
public class CheckoutOrderController extends HttpServlet {

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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OnlineShoppingPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            Enumeration<String> parameterNames = request.getParameterNames();
            int lastCartId = ShoppingcartTable.findLastShoppingcartId();
            lastCartId += 1;
            System.out.println("last Id:" + lastCartId);

            if (parameterNames == null) {
                System.out.println("Parameter is null");
                return;
            }

            // Begin the transaction
            transaction.begin();

            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                if (paramName.startsWith("productId_")) {
                    String productIdText = request.getParameter(paramName);
                    String quantityParamName = "quantity_" + productIdText;
                    String quantityText = request.getParameter(quantityParamName);

                    int productId = Integer.parseInt(productIdText);
                    int quantity = Integer.parseInt(quantityText);
                    Products product = ProductsTable.findProductsById(productId);
                    if (product == null) {
                        return;
                    }
                    Shoppingcart cart = new Shoppingcart(lastCartId, productId);
                    cart.setQuantity(quantity);
                    cart.setProducts(product);

                    int rowInserted = ShoppingcartTable.insertShoppingcart(cart);
                    if (rowInserted == 1) {
                        System.out.println("Success Add to Database -> productId: " + productIdText + " quantity: " + quantityText);
                    }
                }
            }

            // Commit the transaction if everything is successful
            transaction.commit();

            int totalPrice = Integer.parseInt(request.getParameter("totalPrice"));
            request.setAttribute("totalPrice", totalPrice);
            request.getRequestDispatcher("show_confirmation.jsp").forward(request, response);
        } catch (Exception e) {
            // Rollback the transaction if an exception occurs
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            em.close();
            emf.close();
        }
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
