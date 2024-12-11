/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerMarketer;

import DAL.CustomerDAO;
import Models.Customers;
import Models.Paging;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Hieu
 */
public class MktCustomerListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String statusStr = request.getParameter("status");
        String search = request.getParameter("search");
        String sortBy = request.getParameter("sortBy");
        String sortDirection = request.getParameter("sortDirection");
        Integer status = (statusStr != null && !statusStr.isEmpty()) ? Integer.parseInt(statusStr) : null;

        CustomerDAO dao = new CustomerDAO();
        int pageSize = 10; // Number of customers per page
        int pageIndex = 1; // Default to page 1

        String pageIndexStr = request.getParameter("index");
        if (pageIndexStr != null) {
            try {
                pageIndex = Integer.parseInt(pageIndexStr);
            } catch (NumberFormatException e) {
                // Handle invalid page index input
                pageIndex = 1;
            }
        }

        int totalCustomers = dao.getTotalCustomers(search, status);
        int totalPage = (int) Math.ceil((double) totalCustomers / pageSize);

        List<Customers> customerList = dao.getAllCustomers(search, status, pageIndex, pageSize, sortBy, sortDirection);

        // Set attributes for JSP
        request.setAttribute("customerList", customerList);
        request.setAttribute("totalCustomer", totalCustomers);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("page1", new Paging(pageSize, pageIndex, totalPage)); 

        request.getRequestDispatcher("Views/marketer/customer-list.jsp").forward(request, response);
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
