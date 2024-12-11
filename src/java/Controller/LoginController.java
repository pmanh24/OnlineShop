/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAL.CustomerDAO;
import DAL.DAO;
import Models.Customers;
import Models.Encode;
import Models.Users;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Hieu
 */
public class LoginController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */     // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        String referer = request.getHeader("Referer");
    if (referer != null && !referer.contains("login")) { // Avoid storing the login page URL
        request.getSession().setAttribute("previousPage", referer);
    }
    request.getRequestDispatcher("Views/login.jsp").forward(request, response);
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
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        String rem = request.getParameter("rememberMe");
        CustomerDAO dao = new CustomerDAO();

        boolean emailExisted = dao.isEmailExist(email);

        if (!emailExisted) {
            request.setAttribute("errorMessage", "Login Failed: Email does not exist");
            request.getRequestDispatcher("Views/login.jsp").forward(request, response);
            return;
        }
        String encodedPassword = Encode.toSHA1(pass);
        Customers u = dao.getCustomerByEmailAndPassword(email, encodedPassword);

        Cookie cUser = new Cookie("cEmail", email);
        Cookie cPass = new Cookie("cPass", pass);
        Cookie cRem = new Cookie("cRem", rem);

        if (rem != null) {
            cUser.setMaxAge(60 * 60 * 24 * 7); // 7 days
            cPass.setMaxAge(60 * 60 * 24 * 7); // 7 days
            cRem.setMaxAge(60 * 60 * 24 * 7); // 7 days
        } else {
            cUser.setMaxAge(0);
            cPass.setMaxAge(0);
            cRem.setMaxAge(0);
        }

        // Add cookies to the response
        response.addCookie(cUser);
        response.addCookie(cPass);
        response.addCookie(cRem);

        HttpSession session = request.getSession();

        if (u == null) {
            request.setAttribute("errorMessage", "Login Failed: Email or Password is Invalid");
            request.getRequestDispatcher("Views/login.jsp").forward(request, response);
        } else {
            session.setAttribute("customer", u);
            session.setAttribute("role", "customer");
            session.setMaxInactiveInterval(9000);
        // Retrieve the stored URL and redirect to it
        String previousPage = (String) session.getAttribute("previousPage");
        if (previousPage != null) {
            if(previousPage.contains("register") || previousPage.contains("forgotpass")){
                 response.sendRedirect("home"); 
            }else{
                 session.removeAttribute("previousPage"); 
            response.sendRedirect(previousPage);
            }   
        }
        else {
            response.sendRedirect("home"); 
        }
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
