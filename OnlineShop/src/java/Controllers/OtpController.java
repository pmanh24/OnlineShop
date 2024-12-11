/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAL.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
@WebServlet(name = "OtpController", urlPatterns = {"/otp"})
public class OtpController extends HttpServlet {

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
            out.println("<title>Servlet OtpController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OtpController at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        DAO dao = DAO.getInstance();
        int value = 0;
        HttpSession session = request.getSession();
        int otp = 0;
        if(session.getAttribute("otp")!= null){
            otp = (int) session.getAttribute("otp");
        }
        boolean flgExpiredOtp = true;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cOtp")) {
                    if(cookie.getValue()!= null)
                    flgExpiredOtp = false;
                }
            }
        }
        if(flgExpiredOtp == true){
            request.setAttribute("message", "OTP Code is expired");
            request.getRequestDispatcher("Views/EnterOtp.jsp").forward(request, response);
            return;
        }
        
        try{
            value = Integer.parseInt(request.getParameter("otp"));
        }catch(Exception ex){
            request.setAttribute("message", "You have entered wrong OTP code");
            request.getRequestDispatcher("Views/EnterOtp.jsp").forward(request, response);
            return;
        }
        
        
        
        
        if (value == otp) {
            String email = (String)session.getAttribute("email");
            int role = dao.getRoleByEmail(email);
            request.setAttribute("role", role);
            request.setAttribute("email", request.getParameter("email"));
            request.getRequestDispatcher("Views/NewPassword.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "You have entered wrong OTP code");
            request.getRequestDispatcher("Views/EnterOtp.jsp").forward(request, response);
        }
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
