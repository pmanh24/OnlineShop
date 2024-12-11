/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerEmployee;

import DAL.CustomerDAO;
import DAL.EmployeeDAO;
import Models.Customers;
import Models.Employees;
import Models.Employees;
import Models.Encode;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String previousURL = request.getHeader("Referer");

        // Check for a specific target URL parameter
        String targetURL = request.getParameter("target");
        if (targetURL != null) {
            session.setAttribute("previousURL", targetURL);
        } else if (previousURL != null) {
            session.setAttribute("previousURL", previousURL);
        }

        request.getRequestDispatcher("/Views/loginEmployee.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        String rem = request.getParameter("rememberMe");
        EmployeeDAO dao = new EmployeeDAO();

        boolean emailExisted = dao.isEmailExist(email);

        if (!emailExisted) {
            request.setAttribute("errorMessage", "Login Failed: Email does not exist");
            doGet(request, response);
            return;
        }

        String encodedPassword = Encode.toSHA1(pass);
        Employees u = dao.getEmployeeByEmailAndPassword(email, encodedPassword);

        if (u == null) {
            request.setAttribute("errorMessage", "Login Failed: Email or Password is Invalid");
            doGet(request, response);
            return;
        }

        // Invalidate existing session
        HttpSession session = request.getSession(false); // Get current session, do not create a new one
        if (session != null) {
            session.invalidate(); // Invalidate the session to remove existing attributes
        }

        // Create a new session
        session = request.getSession(); // Create a new session
        session.setAttribute("employee", u);
        session.setAttribute("role", "employee");
        session.setMaxInactiveInterval(9000); // Optional, depending on your requirement

        // Handle Remember Me cookies
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

        response.addCookie(cUser);
        response.addCookie(cPass);
        response.addCookie(cRem);

        // Get the previously stored URL from the session
        String previousURL = (String) session.getAttribute("previousURL");
        session.removeAttribute("previousURL"); // Clear the previous URL from session

        // Redirect to the stored URL or role-based page
        if (previousURL != null && !previousURL.contains("/login")) {
            response.sendRedirect(previousURL);
        } else {
            switch (u.getRoleId()) {
                case 1:
                    response.sendRedirect(request.getContextPath() + "/admin");
                    break;
                case 4:
                    response.sendRedirect(request.getContextPath() + "/mkt-dashboard");
                    break;
                case 5:
                    response.sendRedirect(request.getContextPath() + "/warehouser-order-list");
                    break;
                case 3:
                    response.sendRedirect(request.getContextPath() + "/sale-order-list");
                    break;
                case 2:
                    response.sendRedirect(request.getContextPath() + "/sale-employee-order-list");
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/home");
                    break;
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
