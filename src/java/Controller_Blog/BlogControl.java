/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller_Blog;

import DAL.BlogDAO;
import DAL.CategoryDAO;
import DAL.Filter;
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
@WebServlet("/blog")
public class BlogControl extends HttpServlet {
     private static final int PAGE_SIZE = 3; // Số lượng blog hiển thị trên mỗi trang
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
            out.println("<title>Servlet blogControl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet blogControl at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        BlogDAO dao = new BlogDAO();
        //lay  so trang hien tai mac inh la 1
        String pageStr = request.getParameter("page");
        int page = pageStr == null ? 1 : Integer.parseInt(pageStr);
        int totalBlog = dao.getTotalBlog();
        int totalPages = (int) Math.ceil((double)totalBlog/PAGE_SIZE);  
        
        
        //b1: lay data tu DAO
        
        CategoryDAO category = new CategoryDAO();
        List<Blog> list = dao.getAllBlogss(page,PAGE_SIZE);
        List<Category> listcate = category.getNameCategory();
        //b2: gui data len jsp
        request.setAttribute("catList", listcate);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("blog", list);
        request.setAttribute("cate", listcate);
        request.getRequestDispatcher("Views/blog.jsp").forward(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        String categoryid = request.getParameter("category");
        String search = request.getParameter("search");
        String sort = request.getParameter("sortBy");
        
        Filter dao = new Filter();
        BlogDAO blogdao = new BlogDAO();
        List<Blog> blogs = null;
        
        CategoryDAO category = new CategoryDAO();
        List<Category> listcate = category.getNameCategory();

        if (categoryid != null && !categoryid.trim().isEmpty()) {
            blogs = dao.getBlogbyCategory(categoryid);
            
        }else if(search != null && !search.trim().isEmpty()){
            blogs = blogdao.searchBytitle(search);
            
        }else if(sort != null && !sort.trim().isEmpty()){
            blogs = blogdao.sortBydate();
        }
        
        request.setAttribute("blog", blogs);
        request.setAttribute("catList", listcate);
        request.setAttribute("cate", listcate);
        request.getRequestDispatcher("Views/blog.jsp").forward(request, response);
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
