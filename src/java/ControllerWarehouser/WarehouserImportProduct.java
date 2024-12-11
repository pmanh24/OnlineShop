/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerWarehouser;

import DAL.DAO;
import DAL.OrderDetailDAO;
import DAL.OrderListDAO;
import Models.Employees;
import Models.Order;
import Models.OrderDetail;
import Models.ProductSize;
import Models.Products;
import Models.Status;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author OS
 */
public class WarehouserImportProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Employees user = (Employees) session.getAttribute("employee");

        // Redirect to login if user is not logged in
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/employee/login");
            return;
        }
        DAO dao = new DAO();
        String productId = request.getParameter("productId");
        try {
            Products product = dao.getProductCreateDateById(Integer.parseInt(productId));
            List<ProductSize> sizes = dao.getProductSizesByProductId(Integer.parseInt(productId));

            request.setAttribute("sizes", sizes);
            request.setAttribute("product", product);
            request.getRequestDispatcher("/Views/warehouser/import-product.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid Product ID format.");
            request.getRequestDispatcher("/Views/warehouser/import-product.jsp").forward(request, response);
        }
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
        String[] productSizes = request.getParameterValues("productSize[]");
        String[] productColors = request.getParameterValues("productColor[]");
        String[] productQuantities = request.getParameterValues("productQuantity[]");
        String[] importPrices = request.getParameterValues("importPrice[]");
        String[] prices = request.getParameterValues("prices[]");
        String productId = request.getParameter("productId");

        if (productSizes == null || productColors == null || productQuantities == null || importPrices == null || productId == null || prices == null) {
            request.setAttribute("error", "Input data for product sizes, colors, quantities, or prices is missing.");
            request.getRequestDispatcher("/Views/warehouser/import-product.jsp").forward(request, response);
            return;
        }

        DAO dao = new DAO();
        boolean success = true;
        StringBuilder messages = new StringBuilder();
        int totalQuantity = 0;

        for (int i = 0; i < productSizes.length; i++) {
            ProductSize productSize = new ProductSize();
            productSize.setProductSizeName(productSizes[i]);
            productSize.setProductId(Integer.parseInt(productId));
            productSize.setProductColor(productColors[i]);

            try {
                int quantity = Integer.parseInt(productQuantities[i]);
                productSize.setQuantity(Integer.parseInt(productQuantities[i]));
                productSize.setImportPrice(Integer.parseInt(importPrices[i]));
                totalQuantity += quantity; // Sum the quantities
                productSize.setPrices(Integer.parseInt(prices[i]));
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid input for size " + productSizes[i]);
                request.getRequestDispatcher("/Views/warehouser/import-product.jsp").forward(request, response);
                return;
            }

            try {
                ProductSize existingSize = dao.getProductSizeByDetails(productSize);
                if (existingSize != null) {
                    // Update existing size
                    existingSize.setQuantity(productSize.getQuantity());
                    existingSize.setImportPrice(productSize.getImportPrice());
                    existingSize.setPrices(productSize.getPrices());
                    dao.updateProductSize(existingSize);

                    messages.append("Updated size ").append(productSizes[i]).append(" successfully. ");
                } else {
                    // Insert new size
                    dao.insertProductSize(productSize);

                    messages.append("Added size ").append(productSizes[i]).append(" successfully. ");
                }
            } catch (SQLException ex) {
                Logger.getLogger(WarehouserImportProduct.class.getName()).log(Level.SEVERE, null, ex);
                success = false;
                messages.append("Failed to process size ").append(productSizes[i]).append(". ");
            }
        }

        try {
            // Update the total quantity in the products table
            dao.updateProductQuantity(Integer.parseInt(productId), totalQuantity);
        } catch (SQLException ex) {
               messages.append("Failed to update product quantity. ");
        }
        // Set success message
        request.setAttribute("success", messages.toString());
        doGet(request, response);
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
