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
import java.util.List;

/**
 *
 * @author T
 */
public class feedbackDetail extends HttpServlet {

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
            out.println("<title>Servlet feedbackDetail</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet feedbackDetail at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</htmccl>");
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
        int id = Integer.parseInt(request.getParameter("feedback_id"));
        FeedBackDAO fb = new FeedBackDAO();
        FeedBacks feedback = fb.getFBbyID(id);

        request.setAttribute("feedback", feedback);
        request.getRequestDispatcher("Views/marketer/feedbackDetail.jsp").forward(request, response);

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
        int feedbackId = Integer.parseInt(request.getParameter("feedback_id"));
        int status = Integer.parseInt(request.getParameter("status"));

        FeedBackDAO fbDAO = new FeedBackDAO();
        boolean fbd = fbDAO.updateFeedbackStatus(feedbackId, status);
        if (fbd) {
            request.setAttribute("mess", "update success!");
            FeedBacks feedback = fbDAO.getFBbyID(feedbackId);
        request.setAttribute("feedback", feedback);
        request.getRequestDispatcher("Views/marketer/feedbackDetail.jsp").forward(request, response);
        } else {
            request.setAttribute("mess", "failed");
            FeedBacks feedback = fbDAO.getFBbyID(feedbackId);
        request.setAttribute("feedback", feedback);
        request.getRequestDispatcher("Views/marketer/feedbackDetail.jsp").forward(request, response);
        }
        response.sendRedirect("feedbackDetail?feedback_id=" + feedbackId); // Chuyển hướng về trang chi tiết phản hồi

        
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
