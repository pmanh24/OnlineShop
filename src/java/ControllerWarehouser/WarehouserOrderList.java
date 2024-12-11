/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerWarehouser;

import DAL.DAO;
import DAL.OrderDAO;
import DAL.OrderDetailDAO;
import DAL.OrderListDAO;
import Information.Email;
import Models.Employees;
import Models.Item;
import Models.Order;
import Models.Paging;
import Models.ProductSize;
import Models.Products;
import Models.Status;
import Models.StatusCount;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Hieu
 */
public class WarehouserOrderList extends HttpServlet {

    private static final int DEFAULT_PAGE_SIZE = 10;

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

        String search = request.getParameter("search");
        Integer status = parseStatus(request.getParameter("status"));

        // Parse page index with a default of 1
        int index = parsePageIndex(request.getParameter("index")); // This method should be adjusted if needed
        if (index < 1) {
            index = 1; // Ensure index is at least 1
        }

        String sort = request.getParameter("sort");
        String sortOrder = request.getParameter("sortOrder");
        if (sort == null || sort.isEmpty()) {
            sort = "order_date"; // Default sort column
        }
        if (sortOrder == null || (!sortOrder.equalsIgnoreCase("asc") && !sortOrder.equalsIgnoreCase("desc"))) {
            sortOrder = "desc"; // Default sort order
        }

        // Set a default page size if not defined
        final int pageSize = DEFAULT_PAGE_SIZE;

        OrderDAO orderDAO = new OrderDAO();
        OrderListDAO dao = new OrderListDAO();
        List<StatusCount> statusList = dao.getAllStatusCountWarehouse(5);

        // Get total orders and calculate pagination details
        int totalOrders = orderDAO.getTotalOrders(search, status, user.getRoleId());
        int totalPages = (int) Math.ceil((double) totalOrders / pageSize);

        // Clamp index within valid page range
        if (index > totalPages) {
            index = totalPages; // Ensure index does not exceed total pages
        }

        // Get orders for the current page
        List<Order> orderList = orderDAO.getAllOrders(search, status, index, pageSize, sort, sortOrder, user.getRoleId());

        // Set attributes for the JSP
        request.setAttribute("orderList", orderList);
        request.setAttribute("totalOrders", totalOrders); // Total orders available, not the current list size
        request.setAttribute("currentPage", index);
        request.setAttribute("index", index);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("updateSuccess", true);
        request.setAttribute("currentSort", sort);
        request.setAttribute("currentSortOrder", sortOrder);
        request.setAttribute("statusList", statusList);
        request.setAttribute("statusValue", status);
        request.setAttribute("role_id", user.getRoleId());

        // Forward to JSP
        request.getRequestDispatcher("/Views/warehouser/order-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Employees user = (Employees) session.getAttribute("employee");

        // Redirect to login if user is not logged in
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/employee/login");
            return;
        }
        final int STATUS_DELIVERING = 5;

        // Get the parameters from the form
        String orderIdParam = request.getParameter("orderId");
        String statusParam = request.getParameter("status");

        // Update order status logic here
        OrderDAO orderDAO = new OrderDAO();
        int orderId = Integer.parseInt(orderIdParam);
        int status = Integer.parseInt(statusParam);
        boolean updateSuccess = orderDAO.updateOrderStatus(orderId, status);

        // Prepare message for redirect
        String message;
        if (updateSuccess) {
            // Update quantity only if status is 5
            if (status == STATUS_DELIVERING) {
                OrderDetailDAO od = new OrderDetailDAO();
                od.UpdateQuantityDelivering(orderId);
            }
            message = "Order " + orderId + " status updated successfully!";
        } else {
            message = "Failed to update order status.";
        }

        // Redirect with message
        response.sendRedirect(request.getContextPath() + "/warehouser-order-list?message=" + URLEncoder.encode(message, "UTF-8"));
    }

    private Integer parseStatus(String statusParam) {
        if (statusParam == null || statusParam.isEmpty()) {
            return null; // Return null for empty status
        }
        try {
            return Integer.parseInt(statusParam);
        } catch (NumberFormatException e) {
            return null; // Return null if parsing fails
        }
    }

    private int parsePageIndex(String indexStr) {
        try {
            int index = Integer.parseInt(indexStr);
            return index > 0 ? index : 1;
        } catch (NumberFormatException e) {
            return 1;
        }
    }
}
