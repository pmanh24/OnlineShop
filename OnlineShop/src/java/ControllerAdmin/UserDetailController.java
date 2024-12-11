/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerAdmin;

import DAL.EmployeeDAO;
import Models.Employees;
import Models.Role;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Admin
 */
public class UserDetailController extends HttpServlet {

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
            out.println("<title>Servlet UserDetailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserDetailController at " + request.getContextPath() + "</h1>");
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
        String employeeId = request.getParameter("employeeId");
        String mode = request.getParameter("mode");
        if(mode.equals("1")){
            request.setAttribute("mode", 1);
        }else{
            request.setAttribute("mode", 2);
        }
        EmployeeDAO dao = new EmployeeDAO();
        List<Role> roles = dao.getAllRoles();
        Employees employee = null;
        if (employeeId != null) {
            employee = dao.getEmployeeById(Integer.parseInt(employeeId));
        }
        
        roles.remove(0);
        
        request.setAttribute("employee", employee);
        request.setAttribute("roles", roles);
        request.getRequestDispatcher("Views/admin/userdetail.jsp").forward(request, response);
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
        String roleStr = request.getParameter("role");
        String statusStr = request.getParameter("statusValue");
        String empIdStr = request.getParameter("employeeId");
        int empId = 0;
        if(empIdStr!=null && !empIdStr.isEmpty()){
            empId = Integer.parseInt(empIdStr);
        }
        int role = 0;
        boolean status = false;
        if(roleStr!= null && !roleStr.isEmpty()){
            role = Integer.parseInt(roleStr);
        }
        
        if(statusStr!= null && !statusStr.isEmpty()){
            status = Boolean.parseBoolean(statusStr);
        }
        EmployeeDAO dao = new EmployeeDAO();
        dao.updateEmployeeInfo(empId, status, role);
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
