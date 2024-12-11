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
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author T
 */
@WebServlet("/postdetail")
@MultipartConfig
public class postdetail extends HttpServlet {
    private static final String IMAGE_DIRECTORY = "C:\\Users\\T\\Desktop\\SWP-onlshop\\group02\\web\\imageBlog"; 
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
            out.println("<title>Servlet postdetail</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet postdetail at " + request.getContextPath() + "</h1>");
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
         String blogID = request.getParameter("blogId");
         BlogDAO dao = new BlogDAO();
         
         CategoryDAO cat = new CategoryDAO();
         List<Category> category = cat.getNameCategory();
         
        if (blogID != null) {
            Blogs blog = dao.getPostByID(Integer.parseInt(blogID));
            request.setAttribute("posts", blog);
            
        }
        request.setAttribute("categories", category);
        // Forward thông tin tới trang JSP
        request.getRequestDispatcher("Views/marketer/postdetail.jsp").forward(request, response);
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
       int blogID = Integer.parseInt(request.getParameter("post_id"));
        String title = request.getParameter("title");
        String detail = request.getParameter("detail");
        Date updateDate = Date.valueOf(request.getParameter("updateDate")); // yyyy-mm-dd
        int status = Integer.parseInt(request.getParameter("status"));
        int categoryId = Integer.parseInt(request.getParameter("category")); // Lấy categoryId

        BlogDAO dao = new BlogDAO();
        
         CategoryDAO cat = new CategoryDAO();
         List<Category> category = cat.getNameCategory();
         request.setAttribute("categories", category);
        // Xử lý ảnh upload
        Part filePart = request.getPart("image"); // Lấy đối tượng Part từ form upload
        String fileName = filePart.getSubmittedFileName(); // Sử dụng phương thức getSubmittedFileName() để lấy tên file

        String savePath = IMAGE_DIRECTORY + File.separator + fileName; // Đường dẫn để lưu file
        
        
        File directory = new File(IMAGE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs(); // Tạo thư mục nếu nó không tồn tại
        }
        
        
        
        
        // Lưu file ảnh
        filePart.write(savePath);

        // Cập nhật thông tin blog
        Blogs blog = new Blogs();
        blog.setCategoryId(categoryId); // Gán categoryId
        blog.setImage(fileName); // Gán tên file ảnh

        boolean isUpdated = dao.updateBlog(blogID, title, detail, updateDate, status, categoryId, fileName);

        // Xử lý kết quả
        if (isUpdated) {
            request.setAttribute("message", "Blog updated successfully!");
        } else {
            request.setAttribute("message", "Failed to update blog.");
        }

        // Lấy lại thông tin blog sau khi cập nhật
        Blogs updatedBlog = dao.getPostByID(blogID);
        request.setAttribute("posts", updatedBlog);

        // Forward thông tin tới trang JSP
        request.getRequestDispatcher("Views/marketer/postdetail.jsp").forward(request, response);
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
