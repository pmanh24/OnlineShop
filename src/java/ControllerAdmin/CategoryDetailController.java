/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerAdmin;

import DAL.CategoryListDAO;
import Models.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Admin
 */
@WebServlet(name = "CategoryDetailController", urlPatterns = {"/category-detail"})
public class CategoryDetailController extends HttpServlet {

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
            out.println("<title>Servlet CategoryDetailController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CategoryDetailController at " + request.getContextPath() + "</h1>");
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
        String categoryID = request.getParameter("categoryId");
        String mode = request.getParameter("mode");
        if(mode.equals("1")){
            request.setAttribute("mode", 1);
        }else{
            request.setAttribute("mode", 2);
        }
        CategoryListDAO dao = new CategoryListDAO();
        Category category = null;
        if (categoryID != null) {
            category = dao.getCategoryByID(Integer.parseInt(categoryID));
        }
        
        System.out.println("status: "+category.getStatus());
        request.setAttribute("category", category);
        request.getRequestDispatcher("Views/admin/categorydetail.jsp").forward(request, response);
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
        String statusStr = request.getParameter("statusValue");
        String categoryName = request.getParameter("categoryName");
        String categoryID = request.getParameter("categoryId");
        int cateId = 0;
        int status=0;
        if(categoryID!=null && !categoryID.isEmpty()){
            cateId = Integer.parseInt(categoryID);
        }       
        if(statusStr!= null && !statusStr.isEmpty()){
            status = Integer.parseInt(statusStr);
        }
        
        CategoryListDAO dao = new CategoryListDAO();
        dao.updateCategory(cateId, status, categoryName);
        request.setAttribute("success", "Update category success!");
       response.sendRedirect("category-list");
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
