/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller_Blog;

import DAL.BlogDAO;
import DAL.CategoryDAO;
import DAL.DAO;
import Models.Blog;
import Models.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author T
 */
@WebServlet("/blogdetail")
public class Blogdetail extends HttpServlet {

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
            out.println("<title>Servlet Blogdetail</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Blogdetail at " + request.getContextPath() + "</h1>");
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
        String blogID = request.getParameter("blogID");
        String search = request.getParameter("Search");
        if (blogID != null && !blogID.trim().isEmpty()) {
            // Lấy thông tin chi tiết của blog từ DAO
            BlogDAO blogDAO = new BlogDAO();
            Blog blog = blogDAO.getBlogbyID(blogID);
            CategoryDAO category = new CategoryDAO();
            List<Category> listcate = category.getNameCategory();
            request.setAttribute("catList", listcate);
            // Gửi thông tin blog đến trang blogDetail.jsp
            request.setAttribute("blog", blog);
            request.getRequestDispatcher("Views/blogdetail.jsp").forward(request, response);
        } else if (search != null && !search.trim().isEmpty()) {
            BlogDAO blogDAO = new BlogDAO();
            Blog blog = blogDAO.getBlogbySearch(search);
            CategoryDAO category = new CategoryDAO();
            List<Category> listcate = category.getNameCategory();
            request.setAttribute("catList", listcate);
            // Gửi thông tin blog đến trang blogDetail.jsp
            request.setAttribute("blog", blog);
            request.getRequestDispatcher("Views/blogdetail.jsp").forward(request, response);
        }
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
        processRequest(request, response);
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
