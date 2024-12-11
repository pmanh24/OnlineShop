/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerSale;

import DAL.OrderDetailDAO;
import Models.Employees;
import Models.Item;
import Models.Order;
import Models.OrderDetail;
import Models.Status;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "SaleOrderDetailController_new", urlPatterns = {"/sale-order-detail"})
public class SaleOrderDetailController_new extends HttpServlet {

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
            out.println("<title>Servlet SaleOrderDetailController_new</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SaleOrderDetailController_new at " + request.getContextPath() + "</h1>");
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
        Employees user = (Employees) session.getAttribute("employee");

        // Redirect to login if user is not logged in
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/employee/login");
            return;
        }
        OrderDetailDAO dao = new OrderDetailDAO();
        String orderId = request.getParameter("orderId");
        Order o = dao.getOrderByOrderID(Integer.parseInt(orderId));
        List<OrderDetail> detail = dao.getOrderDetailByOrderID(Integer.parseInt(orderId));
        List<Status> statusListForSale = dao.getAllStatusForSale();
        Status s = dao.getStatusByStatusId(o.getStatus());
        String isEdit = "";
        if (s.getRoleId().contains(String.valueOf(user.getRoleId()))) {
            switch (s.getStatusId()) {
                case 1:
                    isEdit = "1,2,8";
                    break;
                case 2:
                    isEdit = "2,8";
                    break;
                case 5:
                    isEdit = "5,6,7";
                    break;
                case 6:
                    isEdit = "0";
                    break;
                case 7:
                    isEdit = "0";
                    break;
                case 8:
                    isEdit = "0";
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("ko edit");
            isEdit = "0";
        }
        request.setAttribute("isEdit", isEdit);
        request.setAttribute("currentStatus", s.getStatusName());
        request.setAttribute("statusList", statusListForSale);
        request.setAttribute("order", o);
        request.setAttribute("detail", detail);
        request.getRequestDispatcher("Views/sale/sale-order-detail.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        Employees user = (Employees) session.getAttribute("employee");

        // Redirect to login if user is not logged in
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/employee/login");
            return;
        }
        OrderDetailDAO dao = new OrderDetailDAO();
        String orderId = request.getParameter("orderId");
        String status = request.getParameter("status");
        Order o = dao.getOrderByOrderID(Integer.parseInt(orderId));
        List<OrderDetail> detail = dao.getOrderDetailByOrderID(Integer.parseInt(orderId));
        List<Status> statusListForSale = dao.getAllStatusForSale();
        String email = o.getCustomer().getEmail();
        if (status != null) {
            dao.updateStatusForOrder(Integer.parseInt(orderId), Integer.parseInt(status));
            request.setAttribute("message", "Status order update successfully!");
            if (Integer.parseInt(status) == 6) {
                sendConfirmationEmail(email, o.getOrderId());
            } else if (Integer.parseInt(status) == 7) {
                System.out.println("vao truong hop update so luong return");
                dao.UpdateQuantityReturn(o.getOrderId());
            } else if (Integer.parseInt(status) == 8) {
                System.out.println("vao truong hop update so luong cancel");
                dao.UpdateQuantityCancel(o.getOrderId());
            }
        }
        o = dao.getOrderByOrderID(Integer.parseInt(orderId));
        Status s = dao.getStatusByStatusId(o.getStatus());
        String isEdit = "";
        if (s.getRoleId().contains(String.valueOf(user.getRoleId()))) {
            switch (s.getStatusId()) {
                case 1:
                    isEdit = "1,2,8";
                    break;
                case 2:
                    isEdit = "2,8";
                    break;
                case 5:
                    isEdit = "5,6,7";
                    break;
                case 6:
                    isEdit = "0";
                    break;
                case 7:
                    isEdit = "0";
                    break;
                case 8:
                    isEdit = "0";
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("ko edit");
            isEdit = "0";
        }
        request.setAttribute("isEdit", isEdit);
        request.setAttribute("currentStatus", s.getStatusName());
        request.setAttribute("statusList", statusListForSale);
        request.setAttribute("order", o);
        request.setAttribute("detail", detail);
        request.getRequestDispatcher("Views/sale/sale-order-detail.jsp").forward(request, response);
    }

    static final String fromEmail = "onlineshopsystem95@gmail.com";
    static final String password = "bgmhithlxyctqepb";

    private void sendConfirmationEmail(String toEmail, int orderId) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Your order has been delivered successfully - Order #" + orderId);

            StringBuilder emailContent = new StringBuilder();
            emailContent.append("Dear Customer,\n\n");
            emailContent.append("Your order has been delivered successfully! Your order number is ").append(orderId).append(".\n");

            emailContent.append("\nWe appreciate your business!\n");
            emailContent.append("\nPlease share your feedback with us at http://localhost:9999/OnlineShop/orderdetail?orderId=" + orderId);
            emailContent.append("\nBest Regards,\nOnline Shop System");

            message.setText(emailContent.toString());

            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace(); // Log error for debugging
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
