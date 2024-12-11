/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerWarehouser;

import DAL.DAO;
import DAL.OrderDetailDAO;
import DAL.OrderListDAO;
import Models.Category;
import Models.Employees;
import Models.Order;
import Models.OrderDetail;
import Models.Paging;
import Models.ProductSize;
import Models.Products;
import Models.Status;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author OS
 */
public class WarehouserProductList extends HttpServlet {

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

        // Get all categories
        List<Category> catList = dao.getAllCategory();
        request.setAttribute("catList", catList);

        int pageSize = 10; // Number of products per page
        int pageIndex = 1; // Default to the first page
        String indexStr = request.getParameter("index");

        // Parse page index from request
        if (indexStr != null) {
            try {
                pageIndex = Integer.parseInt(indexStr);
                if (pageIndex < 1) {
                    pageIndex = 1; // Reset to 1 if invalid
                }
            } catch (NumberFormatException e) {
                pageIndex = 1; // Reset to 1 if parsing fails
            }
        }

        // Get filter parameters
        String search = request.getParameter("search");
        String categoryIdStr = request.getParameter("category");
        String productId = request.getParameter("productId");
        Integer categoryId = (categoryIdStr != null && !categoryIdStr.isEmpty()) ? Integer.parseInt(categoryIdStr) : null;

        // Get sorting parameters
        String sortBy = request.getParameter("sortBy");
        String sortDirection = request.getParameter("sortDirection");

        // Ensure default sorting if not specified
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "create_date"; // Default sort field
        }
        if (sortDirection == null || sortDirection.isEmpty()) {
            sortDirection = "desc"; // Default sort direction
        }

        // Get filtered products
        List<Products> filteredProducts = dao.getFilteredProductsWarehouser(pageIndex, pageSize, search, categoryId, null, sortBy, sortDirection);
        request.setAttribute("productList", filteredProducts);

        // Calculate total products and pages
        int totalProducts = dao.getTotalFilteredProducts(search, categoryId);
        int totalPage = (int) Math.ceil((double) totalProducts / pageSize);

        Paging paging = new Paging(pageSize, pageIndex, totalProducts);
        paging.setTotalPage(totalPage); // Ensure this is set correctly

        // Initialize overall total for sizes
        int overallTotal = 0;

        // Retrieve sizes for the current products and accumulate totals
        for (Products product : filteredProducts) {
            List<ProductSize> sizes = dao.getProductSizesByProductId(product.getProductId());
            request.setAttribute("sizes_" + product.getProductId(), sizes);
            overallTotal += sizes.size(); // Accumulate the total number of sizes
        }

        // Set overall total in the request scope for display in JSP
        request.setAttribute("overallTotal", overallTotal);
        request.setAttribute("totalProduct", totalProducts);
        request.setAttribute("page1", paging);
        request.setAttribute("search", search);
        request.setAttribute("category", categoryIdStr);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortDirection", sortDirection);

        request.getRequestDispatcher("/Views/warehouser/product-list.jsp").forward(request, response);
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
        String productId = request.getParameter("productId");

        DAO dao = new DAO();
        boolean success = true; // Track if all operations are successful
        for (int i = 0; i < productSizes.length; i++) {
            ProductSize productSize = new ProductSize();
            productSize.setProductSizeName(productSizes[i]);
            productSize.setProductId(Integer.parseInt(productId));
            productSize.setQuantity(Integer.parseInt(productQuantities[i]));
            productSize.setProductColor(productColors[i]);
            productSize.setImportPrice(Integer.parseInt(importPrices[i]));
            productSize.setPrices(0); // Set to appropriate value if available
            productSize.setHold(0); // Set to appropriate value if applicable

            try {
                // Check if the product size already exists
                ProductSize existingSize = dao.getProductSizeByDetails(productSize);
                if (existingSize != null) {
                    // Update the existing product size
                    existingSize.setQuantity(productSize.getQuantity());
                    existingSize.setImportPrice(productSize.getImportPrice());
                    // Update any other fields as necessary
                    dao.updateProductSize(existingSize);
                } else {
                    // Insert the new product size
                    dao.insertProductSize(productSize);
                }
            } catch (SQLException ex) {
                Logger.getLogger(WarehouserProductList.class.getName()).log(Level.SEVERE, null, ex);
                success = false; // Set to false if any operation fails
            }
        }

        // Set a success message
        if (success) {
            request.setAttribute("message", "Product sizes successfully processed.");
        } else {
            request.setAttribute("message", "Some product sizes could not be processed.");
        }
        doGet(request, response); // Forward to doGet after processing
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
