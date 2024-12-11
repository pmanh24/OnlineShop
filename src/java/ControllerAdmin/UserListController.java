/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerAdmin;

import DAL.CustomerDAO;
import DAL.EmployeeDAO;
import Models.Customers;
import Models.Employees;
import Models.Paging;
import Models.Role;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Admin
 */

public class UserListController extends HttpServlet {

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
            out.println("<title>Servlet UserListController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserListController at " + request.getContextPath() + "</h1>");
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
        String statusStr = request.getParameter("status");
        String search = request.getParameter("search");
        String sortBy = request.getParameter("sortBy");
        String sortDirection = request.getParameter("sortDirection");
        String genderStr = request.getParameter("gender");
        String roleStr = request.getParameter("role");
//        Integer status = (statusStr != null && !statusStr.isEmpty()) ? Integer.parseInt(statusStr) : null;
        Boolean status = null;
        if (statusStr != null && !statusStr.isEmpty()) {
            status = statusStr.equals("1");
        }
        Boolean gender = null;
        if (genderStr != null && !genderStr.isEmpty()) {
            gender = genderStr.equals("1");
        }

        Integer role = null;
        if (roleStr != null && !roleStr.isEmpty()) {
            role = Integer.parseInt(roleStr);
        }
        EmployeeDAO dao = new EmployeeDAO();

        List<Role> roles = dao.getAllRoles();
        int pageSize = 4; // Number of customers per page
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
        List<Employees> employeeList = dao.getAllEmployees(search, status, gender, role, pageIndex, pageSize, sortBy, sortDirection);

        int totalEmployees = dao.getTotalEmployees(search, status, gender, role);
        int totalPage = (int) Math.ceil((double) totalEmployees / pageSize);

        // popup
        Cookie[] cookies = request.getCookies();
        boolean createSuccess = false;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("createSuccess".equals(cookie.getName())) {
                    createSuccess = true;
                    // Xóa cookie sau khi đã kiểm tra
                    cookie.setMaxAge(0); // Hoặc sử dụng response.addCookie(cookie) để xóa
                    response.addCookie(cookie);
                    break;
                }
            }
        }


        
        // Set attributes for JSP
        request.setAttribute("createSuccess", createSuccess);
        request.setAttribute("roles", roles);
        request.setAttribute("employeeList", employeeList);
        request.setAttribute("totalEmployee", totalEmployees);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("page1", new Paging(pageSize, pageIndex, totalPage));

        request.getRequestDispatcher("Views/admin/userlist.jsp").forward(request, response);
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
