/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerMarketer;

import DAL.BlogDAO;
import DAL.CategoryDAO;
import Models.Blogs;
import Models.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author T
 */
@WebServlet("/postlist")
public class PostList extends HttpServlet {
private static final int PAGE_SIZE = 5; // Số lượng customer hiển thị trên mỗi trang
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
            out.println("<title>Servlet PostDetail</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PostDetail at " + request.getContextPath() + "</h1>");
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
        BlogDAO dao = new BlogDAO();
        
        //lay  so trang hien tai mac inh la 1
        String pageStr = request.getParameter("page");
        int page = pageStr == null ? 1 : Integer.parseInt(pageStr);
        
        // khoi tao DAo
        CategoryDAO categorydao = new CategoryDAO();
        
        String sort = request.getParameter("sort");
        String order = request.getParameter("orderby");
        // lay list tu dao
        List<Blogs> blogs;
        
        // tinh tong so trang 
        int totalFB = dao.getTotalPost();
        int totalPages = (int) Math.ceil((double)totalFB/PAGE_SIZE);  

        
        //lay danh sach sau khi sort
         if(sort != null && (sort.equals("desc") || sort.equals("asc")) && order != null){
            blogs = dao.getAllBlogsSort(sort, order, page, PAGE_SIZE);
        } else {
            blogs = dao.getAllBlogs(page, PAGE_SIZE);
        }
        
        List<Category> category = categorydao.getNameCategory();
        
        //day du lieu len jsp
        request.setAttribute("blogs", blogs);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("category", category);
        
        //chuyen huong den jsp de hien thi 
        request.getRequestDispatcher("/Views/marketer/postlist.jsp").forward(request, response);
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
        String categoryid = request.getParameter("category");
        String search = request.getParameter("searchTitle");
        //khoi tao ds blog
        List<Blogs> blogs = new ArrayList<>();
        BlogDAO dao = new BlogDAO();
        
        //dao
        CategoryDAO  categorydao = new CategoryDAO();
        List<Category> category  = categorydao.getNameCategory();
        
        if(search != null && !search.trim().isEmpty()) {
            blogs = dao.searchBlogsByTitle(search);
        }
        else if(categoryid != null & !categoryid.trim().isEmpty()){
             blogs = dao.getBlogsbyCategoryMar(categoryid);
        }
        request.setAttribute("blogs", blogs);
         request.setAttribute("category", category);
        request.getRequestDispatcher("/Views/marketer/postlist.jsp").forward(request, response);
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
