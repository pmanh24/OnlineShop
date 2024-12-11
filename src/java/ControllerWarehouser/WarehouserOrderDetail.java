/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerWarehouser;

import DAL.OrderDAO;
import DAL.OrderDetailDAO;
import DAL.OrderListDAO;
import Models.Employees;
import Models.Order;
import Models.OrderDetail;
import Models.Status;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author Dr. Dao Duc Tung
 */
public class WarehouserOrderDetail extends HttpServlet {

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
        OrderDetailDAO dao = new OrderDetailDAO();
        String orderId = request.getParameter("orderId");
        Order o = dao.getOrderByOrderID(Integer.parseInt(orderId));
        List<OrderDetail> detail = dao.getOrderDetailByOrderID(Integer.parseInt(orderId));
//        Status s = dao.getStatusByStatusId(o.getStatus());
        OrderListDAO orDAO = new OrderListDAO();
        List<Status> statusList = orDAO.getAllStatus();
        request.setAttribute("statusList", statusList);
        request.setAttribute("role_id", user.getRoleId());
        request.setAttribute("order", o);
        request.setAttribute("detail", detail);
        request.getRequestDispatcher("/Views/warehouser/order-detail.jsp").forward(request, response);
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

        // Set message attribute for the redirect
        request.setAttribute("message", message);

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
