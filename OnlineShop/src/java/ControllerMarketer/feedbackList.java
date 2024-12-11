/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerMarketer;

import DAL.FeedBackDAO;
import Models.FeedBacks;
import jakarta.servlet.ServletException;
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
public class feedbackList extends HttpServlet {
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
            out.println("<title>Servlet feedbackList</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet feedbackList at " + request.getContextPath() + "</h1>");
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
        //lay  so trang hien tai mac inh la 1
        String pageStr = request.getParameter("page");
        int page = pageStr == null ? 1 : Integer.parseInt(pageStr);
        
        
        //khoi tao dao
        FeedBackDAO fbDAO = new FeedBackDAO();
        //lay list tu dao        
        List<FeedBacks> fb ;
        List<FeedBacks> star = fbDAO.getStar();
        List<FeedBacks> fbPage;
        // tinh tong so trang 
        int totalFB = fbDAO.getTotalFeedback();
        int totalPages = (int) Math.ceil((double)totalFB/PAGE_SIZE);  
        
        
        //lay sort tu url param
        String sort = request.getParameter("sort");
        String order = request.getParameter("orderby");
        //lay danh sach sau khi sort
         if(sort != null && (sort.equals("desc") || sort.equals("asc")) && order != null){
            fbPage = fbDAO.sortFB(sort, order, page, PAGE_SIZE);
        } else {
            fbPage = fbDAO.getFB(page, PAGE_SIZE);
        }
        //day du lieu 
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("feedback", fbPage);
        request.setAttribute("star", star);
        request.getRequestDispatcher("Views/marketer/feedbackList.jsp").forward(request, response);
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
        // lay star, search, status tu jsp
        String Star = request.getParameter("star");
        String action = request.getParameter("action");
        String search = request.getParameter("search");
        //khoi tao dao 
        FeedBackDAO fb = new FeedBackDAO();
        List<FeedBacks> feedback = new ArrayList<>();
        List<FeedBacks> star = fb.getStar();
        
        
        // lay fb tu star 
        if(Star != null && !Star.trim().isEmpty()){
            feedback = fb.getFeedbackbyStar(Star);
        }
        //lay fb tu status
        else if(action != null && !action.trim().isEmpty()){
            int active = Integer.parseInt(action);
            feedback = fb.getFBbyStatus(active);
        }
        else if(search != null && !search.trim().isEmpty()){
            feedback = fb.getFBbysearch(search);
        }
        
        
        
        request.setAttribute("feedback", feedback);
        request.setAttribute("star", star);
        request.getRequestDispatcher("Views/marketer/feedbackList.jsp").forward(request, response);
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
