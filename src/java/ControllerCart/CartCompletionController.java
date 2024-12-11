/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerCart;

import DAL.CartCompletionDAO;
import Models.Blogs;
import Models.Category;
import Models.Sliders;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import DAL.DAO;
import DAL.OrderDAO;
import Models.Customers;
import Models.Item;
import Models.Order;
import Models.OrderDetail;
import Models.PaymentHistory;
import Models.Products;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
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
@WebServlet(name = "CartCompletionController", urlPatterns = {"/cartcompletion"})
public class CartCompletionController extends HttpServlet {

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
            out.println("<title>Servlet CartCompletionController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartCompletionController at " + request.getContextPath() + "</h1>");
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

        // Header
        DAO dao = new DAO();
        CartCompletionDAO cartCompletionDAO = new CartCompletionDAO();
        List<Category> catList = dao.getAllCategory();
        List<Blogs> blogList = dao.getBlogsWithImages();
        List<Sliders> slideList = dao.getSlider();
        Order createdOrder;
        Date currentOrderDate = new java.util.Date();
        Customers user = (Customers) session.getAttribute("customer");
        String payment = (String) session.getAttribute("payment");
        List<Item> items = (List<Item>) session.getAttribute("items");
        long totalPrice = (Long) session.getAttribute("totalPrice");
        String email = (String) session.getAttribute("email");
        String address = (String) session.getAttribute("address");
        if (payment.equalsIgnoreCase("COD")) {
            int orderId = (int) session.getAttribute("orderID");
            System.out.println(orderId);
            createdOrder = cartCompletionDAO.getOrderByOrderID(orderId);

        } else {
            address = (String) session.getAttribute("address");
            String phoneNumber = (String) session.getAttribute("phoneNumber");
            Order newOrder = new Order(currentOrderDate, totalPrice, 1, user.getCustomerId(), address, phoneNumber);
            OrderDAO orderDAO = new OrderDAO();
            createdOrder = orderDAO.addOrder(newOrder);
            orderDAO.addOrderDetail(items, createdOrder.getOrderId());
            sendConfirmationEmail(email, createdOrder.getOrderId(), totalPrice, items, payment);
        }

        String paymentMethod = (String) request.getSession().getAttribute("payment");
        Order orderDetails = cartCompletionDAO.getOrderByOrderID(createdOrder.getOrderId());
        cartCompletionDAO.UpdateQuantity(createdOrder.getOrderId());
        cartCompletionDAO.updateStatusAndEmployeeIDForOrder(createdOrder.getOrderId());
        List<OrderDetail> orderDetail = cartCompletionDAO.getOrderDetailWithProductByOrderID(createdOrder.getOrderId());

        // Add new payment history
        DAO paymentDAO = DAO.getInstance();
        java.sql.Date sqlOrderDate = new java.sql.Date(currentOrderDate.getTime());
        PaymentHistory paymentHistory = new PaymentHistory(0, sqlOrderDate, paymentMethod, createdOrder.getOrderId(), createdOrder.getCustomerId());
        paymentDAO.addPayHistory(paymentHistory);
        
        // Delete cart
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cart" + user.getCustomerId())) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        request.setAttribute("address", address);
        request.setAttribute("orderDetail", orderDetail);
        request.setAttribute("blogList", blogList);
        request.setAttribute("catList", catList);
        request.setAttribute("slideList", slideList);
        request.setAttribute("order", orderDetails);
        request.setAttribute("payment", paymentMethod);

        request.getRequestDispatcher("Views/cartCompletion.jsp").forward(request, response);
    }
    DAO dao = new DAO();
    static final String fromEmail = "onlineshopsystem95@gmail.com";
    static final String password1 = "bgmhithlxyctqepb";

    private void sendConfirmationEmail(String toEmail, int orderId, long totalPrice, List<Item> items, String payment) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password1);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Order Confirmation - Order #" + orderId);

            // Build the email content with HTML
            StringBuilder emailContent = new StringBuilder();
            emailContent.append("<html>")
                    .append("<body>")
                    .append("<p>Dear Customer,</p>")
                    .append("<p>Thank you for your order! Your order number is <strong>").append(orderId).append("</strong>.</p>")
                    .append("<p>Total Price: <strong>").append(totalPrice).append(" VND</strong></p>")
                    .append("<p>Payment Method: <strong>").append(payment).append("</strong></p>")
                    .append("<h3>Order Details:</h3>")
                    .append("<table style='border-collapse: collapse; width: 100%;'>")
                    .append("<tr><th style='border: 1px solid black; padding: 8px;'>Product Name</th>")
                    .append("<th style='border: 1px solid black; padding: 8px;'>Quantity</th>")
                    .append("<th style='border: 1px solid black; padding: 8px;'>Total (VND)</th></tr>");

            for (Item item : items) {
                emailContent.append("<tr>")
                        .append("<td style='border: 1px solid black; padding: 8px;'>").append(item.getProductSize().getProduct().getProductName()).append("</td>")
                        .append("<td style='border: 1px solid black; padding: 8px; text-align: center'>").append(item.getQuantity()).append("</td>")
                        .append("<td style='border: 1px solid black; padding: 8px;'>").append(item.getProductSize().getPrices() * item.getQuantity()).append(" VND</td>")
                        .append("</tr>");
            }

            emailContent.append("</table>")
                    .append("<p>We appreciate your business!</p>")
                    .append("<p>Best Regards,<br>Online Shop System</p>")
                    .append("</body>")
                    .append("</html>");

            // Set the content of the email to HTML
            message.setContent(emailContent.toString(), "text/html");

            // Send the email
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace(); // Log error for debugging
        }
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
