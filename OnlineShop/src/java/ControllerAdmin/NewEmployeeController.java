/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerAdmin;

import DAL.DAO;
import DAL.EmployeeDAO;
import Models.Employees;
import Models.Encode;
import Models.Role;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Admin
 */
public class NewEmployeeController extends HttpServlet {

    static final String from = "onlineshopsystem95@gmail.com";
    static final String password = "bgmhithlxyctqepb";

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
            out.println("<title>Servlet NewEmployeeController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewEmployeeController at " + request.getContextPath() + "</h1>");
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
        EmployeeDAO dao = new EmployeeDAO();
        List<Role> roles = dao.getAllRoles();
        roles.remove(0);
        request.setAttribute("roles", roles);
        request.getRequestDispatcher("Views/admin/newemployee.jsp").forward(request, response);
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
        String fullname = request.getParameter("employeeName");
        String email = request.getParameter("employeeEmail");
        String phoneNumber = request.getParameter("employeePhone");
        String genderStr = request.getParameter("employeeGender");
        String roleStr = request.getParameter("employeeRole");

        boolean gender = false;
        int role = 0;
        if (genderStr != null && !genderStr.isEmpty()) {
            gender = Boolean.parseBoolean(genderStr);
        }
        if (roleStr != null && !roleStr.isEmpty()) {
            role = Integer.parseInt(roleStr);
        }

        String password = generatePassword(10);
        String encodePass = Encode.toSHA1(password);
        EmployeeDAO dao = EmployeeDAO.getInstance();
        DAO d = DAO.getInstance();
        if (d.checkEmailExist(email)) {
            request.setAttribute("errMsg", "Email is already exist!!");
            List<Role> roles = dao.getAllRoles();
            roles.remove(0);
            request.setAttribute("roles", roles);
            request.getRequestDispatcher("Views/admin/newemployee.jsp").forward(request, response);
            return;
        }

        
        Employees newEmp = new Employees();
        newEmp.setFullName(fullname);
        newEmp.setEmail(email);
        newEmp.setGender(gender);
        newEmp.setStatus(true);
        newEmp.setRoleId(role);
        newEmp.setPhoneNumber(phoneNumber);
        newEmp.setPassword(encodePass);

        String to = email;
        boolean isSend = this.sendEmail(to, "New Employee Password",
                "This is your new employee password, please change it early!<br>"
                + "Password: <b>" + password + "</b>");

        dao.createEmployee(newEmp);
        
        Cookie c = new Cookie("createSuccess", "1");
        c.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(c);

        response.sendRedirect("userlist");
    }

    public static String generatePassword(int length) {
        // Các tập ký tự cần thiết
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String specialCharacters = "@#$%^&+=!";
        String allAllowedChars = upperCaseLetters + specialCharacters + "abcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();

        // Đảm bảo mật khẩu có ít nhất 1 ký tự viết hoa và 1 ký tự đặc biệt
        StringBuilder password = new StringBuilder();
        password.append(upperCaseLetters.charAt(random.nextInt(upperCaseLetters.length())));
        password.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));

        // Tạo các ký tự còn lại để đạt được độ dài mong muốn
        for (int i = 2; i < length; i++) {
            password.append(allAllowedChars.charAt(random.nextInt(allAllowedChars.length())));
        }

        // Trộn các ký tự để không theo thứ tự cố định
        return shuffleString(password.toString());
    }

    // Hàm trộn chuỗi để ngẫu nhiên hóa vị trí các ký tự
    private static String shuffleString(String input) {
        StringBuilder result = new StringBuilder(input.length());
        SecureRandom random = new SecureRandom();
        char[] characters = input.toCharArray();
        for (int i = input.length(); i > 0; i--) {
            int index = random.nextInt(i);
            result.append(characters[index]);
            characters[index] = characters[i - 1];
        }
        return result.toString();
    }

    public static boolean sendEmail(String to, String tieuDe, String noiDung) {
        // Properties : khai báo các thuộc tính
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.starttls.enable", "true");

        // create Authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // TODO Auto-generated method stub
                return new PasswordAuthentication(from, password);
            }
        };

        // Phiên làm việc
        Session session = Session.getInstance(props, auth);

        // Tạo một tin nhắn
        MimeMessage msg = new MimeMessage(session);

        try {
            // Kiểu nội dung
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");

            // Người gửi
            msg.setFrom(new InternetAddress(from));

            // Người nhận
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

            // Tiêu đề email
            msg.setSubject(tieuDe);

            // Quy đinh ngày gửi
            msg.setSentDate(new Date());

            // Quy định email nhận phản hồi
            // msg.setReplyTo(InternetAddress.parse(from, false))
            // Nội dung
            msg.setContent(noiDung, "text/HTML; charset=UTF-8");
//               msg.setText(to);
            // Gửi email
            Transport.send(msg);
            System.out.println("Gửi email thành công");
            return true;
        } catch (Exception e) {
            System.out.println("Gặp lỗi trong quá trình gửi email");
            e.printStackTrace();
            return false;
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
