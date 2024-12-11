/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package ControllerSale;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author GIGABYTE
 */
public class SaleController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String path = request.getPathInfo();
        
        if (path == null || path.equals("/sale")) {
            request.getRequestDispatcher("/Views/sale/home.jsp").forward(request, response);
        } else if (path.equals("/employee/login")) {
            request.getRequestDispatcher("/Views/loginEmployee.jsp").forward(request, response);
        } else if (path.equals("/sale/order-list")) {
            response.sendRedirect(request.getContextPath() + "/order-list");
        } else if (path.equals("/sale/order-detail")) {
            response.sendRedirect(request.getContextPath() + "/order-detail");
        }else {
            // Handle 404 or other paths
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
        }
    } 

}
