/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAL.CategoryDAO;
import DAL.CustomerrDAO;
import Models.Category;
import Models.Customerr;
import Models.Customers;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author T
 */
@WebServlet("/userprofile")
public class UserProfile extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserProfile</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserProfile at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        HttpSession session = request.getSession();
        Customers user = (Customers) session.getAttribute("customer");
        CustomerrDAO dao = new CustomerrDAO();
        CategoryDAO category = new CategoryDAO();
        // Lấy thông tin customer từ DAO
        Customerr customer = dao.getAllprofile(user.getCustomerId());
        List<Category> listcate = category.getNameCategory();

        request.setAttribute("customer", customer);
        request.setAttribute("catList", listcate);

        // Forward đến trang JSP
        request.getRequestDispatcher("Views/userprofile.jsp").forward(request, response);

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
        Customers user = (Customers) session.getAttribute("customer");
        String fullname = request.getParameter("fullname");
        int gender = Integer.parseInt(request.getParameter("gender"));
        String phone = request.getParameter("numberphone");
        String address = request.getParameter("address");
        CategoryDAO category = new CategoryDAO();

        // Cập nhật dữ liệu vào cơ sở dữ liệu
        CustomerrDAO dao = new CustomerrDAO();
        dao.updateCustomerr(user.getCustomerId(), fullname, gender, phone, address);

        // Lấy lại thông tin khách hàng đã cập nhật
        Customerr updatedCustomer = dao.getAllprofile(user.getCustomerId());
        List<Category> listcate = category.getNameCategory();

        // Đặt thuộc tính customer vào request và forward lại trang JSP để hiển thị
        request.setAttribute("mess","updated successfully!");
        request.setAttribute("customer", updatedCustomer);
        request.getRequestDispatcher("Views/userprofile.jsp").forward(request, response);

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
