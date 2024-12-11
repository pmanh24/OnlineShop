/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package ControllerEmployee;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Hieu
 */
public class EmployeeController extends HttpServlet {
   
 @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();
        
        if (path == null || path.equals("/")) {
            // Handle /employee or /employee/
            request.getRequestDispatcher("/Views/employee/home.jsp").forward(request, response);
        } else if (path.equals("/employee/login")) {
            // Handle /employee/login
            request.getRequestDispatcher("/Views/loginEmployee.jsp").forward(request, response);
        } else if (path.equals("/profile")) {
            // Handle /employee/profile
            request.getRequestDispatcher("/Views/employee/profile.jsp").forward(request, response);
        } else {
            // Handle 404 or other paths
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
        }
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
