package ControllerMarketer;

import DAL.DAO;
import Models.Category;
import Models.Employees;
import Models.Paging;
import Models.Products;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;

public class MktProductListController extends HttpServlet {

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

        List<Category> catList = dao.getAllCategory();
        request.setAttribute("catList", catList);

        int pageSize = 4; // Number of products per page
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
        String statusStr = request.getParameter("status");

        Integer categoryId = (categoryIdStr != null && !categoryIdStr.isEmpty()) ? Integer.parseInt(categoryIdStr) : null;
        Integer status = (statusStr != null && !statusStr.isEmpty()) ? Integer.parseInt(statusStr) : null;

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
        List<Products> filteredProducts = dao.getFilteredProducts(pageIndex, pageSize, search, categoryId, status,  sortBy, sortDirection);
        request.setAttribute("productList", filteredProducts);

        // Calculate total products and pages
        int totalProducts = dao.getTotalProducts(search, categoryId, status);
        int totalPage = (int) Math.ceil((double) totalProducts / pageSize);

        Paging paging = new Paging(pageSize, pageIndex, totalProducts);
        paging.setTotalPage(totalPage); // Ensure this is set correctly

        // Set request attributes for the JSP
        request.setAttribute("totalProduct", dao.getTotalProducts());
        request.setAttribute("page1", paging);
        request.setAttribute("search", search);
        request.setAttribute("category", categoryIdStr);
        request.setAttribute("status", statusStr);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortDirection", sortDirection);
        request.setAttribute("error", "");
        request.setAttribute("success", "");

        request.getRequestDispatcher("Views/marketer/product-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            int newStatus = "show".equals(action) ? 1 : 0; // Assuming 1 = show, 0 = hide
            int productId = Integer.parseInt(request.getParameter("productId"));

            DAO dao = new DAO();
            boolean updated = dao.updateProductStatus(productId, newStatus);

            // Log update status
            System.out.println("Update Status: " + updated + ", Product ID: " + productId + ", New Status: " + newStatus);

            // Retrieve all required parameters to maintain the filter state
            String search = request.getParameter("search");
            String categoryIdStr = request.getParameter("category");
            String statusStr = request.getParameter("status");
            String sortBy = request.getParameter("sortBy");
            String sortDirection = request.getParameter("sortDirection");
            String indexStr = request.getParameter("index");

            // Use the index from the request to maintain the current page
            int pageIndex = (indexStr != null && !indexStr.isEmpty()) ? Integer.parseInt(indexStr) : 1;

            // Ensure pageIndex is at least 1
            if (pageIndex < 1) {
                pageIndex = 1; // Reset to 1 if invalid
            }

            // Create a redirect URL with all parameters
            String redirectUrl = String.format("%s/mkt-product-list?search=%s&category=%s&status=%s&index=%d&sortBy=%s&sortDirection=%s",
                    request.getContextPath(),
                    URLEncoder.encode(search != null ? search : "", "UTF-8"),
                    URLEncoder.encode(categoryIdStr != null ? categoryIdStr : "", "UTF-8"),
                    URLEncoder.encode(statusStr != null ? statusStr : "", "UTF-8"),
                    pageIndex,
                    URLEncoder.encode(sortBy != null ? sortBy : "", "UTF-8"),
                    URLEncoder.encode(sortDirection != null ? sortDirection : "", "UTF-8"));

            // Set success or error message
            if (updated) {
                request.setAttribute("success", "Show/Hide successfully");
            } else {
                request.setAttribute("error", "Product not found or update failed.");
            }

            // Perform the redirect
            response.sendRedirect(redirectUrl);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid product ID or index.");
            e.printStackTrace(); // Log the error
            doGet(request, response); // Forward back to doGet in case of error
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred.");
            e.printStackTrace(); // Log the error
            doGet(request, response); // Forward back to doGet in case of error
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
