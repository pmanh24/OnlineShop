/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAL.DAO;
import Models.Blogs;
import Models.Category;
import Models.Customers;
import Models.Employees;
import Models.Encode;
import Models.Sliders;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ChangePasswordController", urlPatterns = {"/changePassword"})
public class ChangePasswordController extends HttpServlet {

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
            out.println("<title>Servlet ChangePasswordController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangePasswordController at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        if(session.getAttribute("customer") == null){
            response.sendRedirect("login");
            return;
        }
        
        DAO dao = DAO.getInstance();

        List<Category> catList = dao.getAllCategory();
        request.setAttribute("catList", catList);

 
        request.getRequestDispatcher("Views/ChangePassword.jsp").forward(request, response);
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
        HttpSession ses = request.getSession();
        String role = (String)ses.getAttribute("role");
        String oldPass = request.getParameter("oldPassword");
        String newPass = request.getParameter("password");
        String confNewPass = request.getParameter("confPassword");
        String passwordRegex = "^(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
	Pattern patternPassword = Pattern.compile(passwordRegex);
	Matcher matcherPassword = patternPassword.matcher(newPass);
	if (matcherPassword.matches() == false) {
            request.setAttribute("errMsg", "Password at least 8 character , 1 uppercase , "
                    + "1 special character and should not contain any whitespace characters.  !!!");
            request.getRequestDispatcher("Views/ChangePassword.jsp").forward(request, response);
        }
        if(role == null){
            request.setAttribute("errMsg", "session null");
                    doGet(request, response);
        }
        if(role.equals("customer")){
            Customers cus = (Customers) ses.getAttribute("customer");
            if (cus.getPassword().equals(Encode.toSHA1(oldPass))) {
                if (newPass != null && confNewPass != null && newPass.equals(confNewPass)) {
                    dao.changePasswordCus(Encode.toSHA1(newPass), cus.getEmail());
                    request.setAttribute("status", "Change password successfully!");
                    doGet(request, response);
                    System.out.println("update pass thanh cong");
                }
                if (!(newPass.equals(confNewPass)) && newPass!=null && confNewPass!= null) {
                    request.setAttribute("errMsg", "Confirm password not match");
                    doGet(request, response);
                }
            } else {
                request.setAttribute("errMsg", "Old password incorrect!!");
                doGet(request, response);
            }
        }
        else if(role.equals("employee")){
            Employees emp = (Employees) ses.getAttribute("employee");
            if (emp.getPassword().equals(Encode.toSHA1(oldPass))) {
                if (newPass != null && confNewPass != null && newPass.equals(confNewPass)) {
                    dao.changePasswordEmp(Encode.toSHA1(newPass), emp.getEmail());
                    request.setAttribute("status", "Change password successfully!");
                    doGet(request, response);
                }
                if (!(newPass.equals(confNewPass)) && newPass!=null && confNewPass!= null) {
                    request.setAttribute("errMsg", "Confirm password not match");
                    doGet(request, response);
                }
            } else {
                request.setAttribute("errMsg", "Old password incorrect!!");
                doGet(request, response);
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
