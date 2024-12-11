/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package ControllerCart;

import DAL.CategoryDAO;
import DAL.CustomerrDAO;
import DAL.OrderDAO;
import DAL.OrderDetailDAO;
import Models.Category;
import Models.Customerr;
import Models.Customers;
import Models.Order;
import Models.OrderDetail;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author GIGABYTE
 */
@WebServlet(name="OrderDetailController", urlPatterns={"/orderdetail"})
public class OrderDetailController extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customers user = (Customers) session.getAttribute("customer");
        
        
        
        CustomerrDAO dao = new CustomerrDAO();
        Customerr customer = dao.getAllprofile(user.getCustomerId());
        request.setAttribute("customer", customer);
        
        CategoryDAO category = new CategoryDAO();
        List<Category> listcate = category.getNameCategory();
        request.setAttribute("catList", listcate);
        
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        OrderDAO odao = new OrderDAO();
        Order order = odao.getOrderByID(orderId);
        List<OrderDetail> orderL = (List<OrderDetail>) odao.getProductOrderInfo(orderId);
        
        request.setAttribute("order", order);
        request.setAttribute("orderL", orderL);
        request.getRequestDispatcher("Views/orderDetails.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       String Idparam = request.getParameter("orderId");
       int orderId = Integer.parseInt(Idparam);
       
       OrderDAO dao = new OrderDAO();
       dao.updateOrderStatus(orderId, 8);
       
       OrderDetailDAO DAO = new OrderDetailDAO();
       DAO.UpdateQuantityCancel(orderId);
               
       response.sendRedirect("orderdetail?orderId="+ orderId);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
