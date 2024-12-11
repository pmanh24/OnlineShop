/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAL.DAO;
import Models.Blogs;
import Models.Category;
import Models.Customers;
import Models.Products;
import Models.Sliders;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Check if session contains a user
        Customers user = (Customers) session.getAttribute("customer");
        String role = (String) session.getAttribute("role");
        DAO dao = new DAO();

        List<Category> catList = dao.getAllCategory();
        List<Blogs> blogList = dao.getBlogsWithImages();
        List<Blogs> blogListRecent = dao.getBlogListRecent();
        List<Products> productList = dao.getProductsFeatured();
        List<Products> productListCurrent = dao.getProductsCurrent();
        List<Sliders> slideList = dao.getSlider();
        request.setAttribute("productList", productList);
        request.setAttribute("productListCurrent", productListCurrent);
        request.setAttribute("catList", catList);
        request.setAttribute("blogList", blogList);
        request.setAttribute("blogListRecent", blogListRecent);
        request.setAttribute("slideList", slideList);

        request.getRequestDispatcher("Views/home.jsp").forward(request, response);
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
