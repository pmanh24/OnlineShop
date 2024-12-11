/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerAdmin;

import DAL.CategoryListDAO;
import DAL.EmployeeDAO;
import Models.Category;
import Models.Employees;
import Models.Order;
import Models.Paging;
import Models.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
@WebServlet(name = "CategoryListController", urlPatterns = {"/category-list"})
public class CategoryListController extends HttpServlet {

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
            out.println("<title>Servlet CategoryListController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CategoryListController at " + request.getContextPath() + "</h1>");
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
        String statusStr = request.getParameter("status");
        if (statusStr==""){
            statusStr=null;
        }
        String cateID = request.getParameter("cateID");
        if (cateID==""){
            cateID=null;
        }
       
        String cateName = request.getParameter("cateName");
        if(cateName==""){
            cateName=null;
        }
        String path = "";

        CategoryListDAO dao = new CategoryListDAO();
        List<Category> categoryList = dao.SearchAllCategoryByKey(cateID, cateName, statusStr);

        int size = categoryList.size();
        int page, numperpage = 10;
        int numPage = (size % numperpage == 0 ? (size / numperpage) : (size / numperpage + 1));
        String xpage = request.getParameter("page");
        if (xpage == null) {
            page = 1;
        } else {
            page = Integer.parseInt(xpage);
        }
        int start, end;
        start = (page - 1) * numperpage;
        end = Math.min(page * numperpage, size);
        List<Category> categoryByPage = new ArrayList<>();
        categoryByPage = dao.getAllCategoryByPage(categoryList, start, end);
        if (statusStr == null && cateID == null && cateName == null) {

        } else {
            path = "status=" + statusStr + "&cateID=" + cateID + "&cateName=" + cateName;
            request.setAttribute("status", statusStr);
            request.setAttribute("cateID", cateID);
            request.setAttribute("cateName", cateName);
        }
        System.out.println(categoryByPage.size());
        int totalCategory = categoryList.size();
        request.setAttribute("categoryList", categoryByPage);
        request.setAttribute("totalCategory", totalCategory);
        request.setAttribute("page", page);
        request.setAttribute("numpage", numPage);
        request.setAttribute("path", path);
        request.getRequestDispatcher("Views/admin/categorylist.jsp").forward(request, response);
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
