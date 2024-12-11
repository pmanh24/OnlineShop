/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerMarketer;

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
public class MarketerController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();

        if (path == null || path.equals("/marketer")) {
          response.sendRedirect(request.getContextPath() + "/mkt-dashboard");
        } else if (path.equals("/employee/login")) {
            request.getRequestDispatcher("/Views/loginEmployee.jsp").forward(request, response);
        } else if (path.equals("/product") || path.equals("/marketer/product")) {
            response.sendRedirect(request.getContextPath() + "/mkt-product-list");
        } else if (path.equals("/add-product")) {
            request.getRequestDispatcher("/Views/marketer/add-product.jsp").forward(request, response);
        } else if (path.equals("/marketer/slider")) {
            response.sendRedirect(request.getContextPath() + "/mkt-slider-list");
        } else if (path.equals("/marketer/product-detail")) {
            response.sendRedirect(request.getContextPath() + "/product-detail");
//            request.getRequestDispatcher("Views/marketer/product-detail.jsp").forward(request, response);
        } else {
            // Handle 404 or other paths
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
        }
    }

}
