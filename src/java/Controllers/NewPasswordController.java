/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controllers;

import DAL.DAO;
import Models.Encode;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Admin
 */
@WebServlet(name="NewPasswordController", urlPatterns={"/newpass"})
public class NewPasswordController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewPasswordController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewPasswordController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 
    
    DAO dao = DAO.getInstance();

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("Views/NewPassword.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        String newPassword = request.getParameter("password");
        String confPassword = request.getParameter("confPassword");
        String passwordRegex = "^(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
	Pattern patternPassword = Pattern.compile(passwordRegex);
	Matcher matcherPassword = patternPassword.matcher(newPassword);
        String email = (String) session.getAttribute("email");
        int role = dao.getRoleByEmail(email);
        request.setAttribute("role", role);
	if (matcherPassword.matches() == false) {
            request.setAttribute("errMsg", "Password at least 8 character , 1 uppercase , "
                    + "1 special character and should not contain any whitespace characters.  !!!");
            request.getRequestDispatcher("Views/NewPassword.jsp").forward(request, response);
        }

        if (newPassword != null && confPassword != null && newPassword.equals(confPassword)) {
            try {
                String encodePass = Encode.toSHA1(newPassword);
                switch(role){
                    case 1: 
                        boolean rowCount1 = dao.UpdatePasswordEmployee(encodePass, email);
                        if (rowCount1) {
                            request.setAttribute("status", "Reset password successfully");
                            request.getRequestDispatcher("Views/NewPassword.jsp").forward(request, response);
                        } else {
                            request.setAttribute("errMsg", "Reset password failed");
                            request.getRequestDispatcher("Views/NewPassword.jsp").forward(request, response);
                        }
                        break;
                    case 2: 
                        boolean rowCount2 = dao.UpdatePasswordCustomer(encodePass, email);
                        if (rowCount2) {
                            request.setAttribute("status", "Reset password successfully");
                            request.getRequestDispatcher("Views/NewPassword.jsp").forward(request, response);
                        } else {
                            request.setAttribute("errMsg", "Reset password failed");
                            request.getRequestDispatcher("Views/NewPassword.jsp").forward(request, response);
                        }
                        break;
                    default: 
                        request.setAttribute("errMsg", "Email is not existed");
                        request.getRequestDispatcher("Views/NewPassword.jsp").forward(request, response);
                        break;
                    }    
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            request.setAttribute("errMsg", "New password failed");
            request.getRequestDispatcher("Views/NewPassword.jsp").forward(request, response);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
