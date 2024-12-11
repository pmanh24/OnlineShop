/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerMarketer;

import DAL.CustomerDAO;
import DAL.OrderDAO;
import Models.Customers;
import Models.Employees;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Hieu
 */
public class MktCustomerDetail extends HttpServlet {

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
        String customerId = request.getParameter("customerId");
        CustomerDAO dao = new CustomerDAO();
        Customers customer = dao.getCustomerById(Integer.parseInt(customerId));

        OrderDAO orderDAO = new OrderDAO();
        int totalPurchaseAmount = orderDAO.getTotalSpentByCustomer(Integer.parseInt(customerId));
        request.setAttribute("customer", customer);
        request.setAttribute("totalPurchaseAmount", totalPurchaseAmount);
        request.getRequestDispatcher("Views/marketer/customer-detail.jsp").forward(request, response);
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
        String note = request.getParameter("note");
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        CustomerDAO dao = new CustomerDAO();
        HttpSession session = request.getSession();
        Employees user = (Employees) session.getAttribute("employee");
        try {
            dao.updateCustomerInfo(note, user.getEmployeeId(), customerId);
            request.setAttribute("success", "Customer details updated successfully!");
            response.sendRedirect("mkt-customer-detail?customerId=" + customerId);
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred while updating customer information.");
            request.getRequestDispatcher("mkt-customer-detail").forward(request, response);
        }
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
