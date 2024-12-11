/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerCart;

import DAL.CartCompletionDAO;
import DAL.DAO;
import DAL.OrderDAO;
import Models.Blogs;
import Models.Category;
import Models.Customers;
import Models.Item;
import Models.Order;
import Models.Products;
import Models.Sliders;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
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
import Models.Address;
import Models.ProductSize;

/**
 *
 * @author Admin
 */
public class CartContactController extends HttpServlet {

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
            out.println("<title>Servlet CartContactController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartContactController at " + request.getContextPath() + "</h1>");
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
        if (session.getAttribute("customer") == null) {
            response.sendRedirect("login");
            return;
        }
        if ("handlePaymentResponse".equals(request.getParameter("method"))) {
            handlePaymentResponse(request, response);
        }
        Customers user = (Customers) session.getAttribute("customer");
        String role = (String) session.getAttribute("role");
        DAO dao = DAO.getInstance();
        OrderDAO orderDAO = OrderDAO.getInstance();
        List<Category> catList = dao.getAllCategory();
        List<Blogs> blogList = dao.getBlogsWithImages();

        List<Sliders> slideList = dao.getSlider();
        List<Item> items = new ArrayList<>();
        Cookie[] arr = request.getCookies();
        boolean checkProduct = false;
        String txt = "";
        if (arr != null) {
            for (Cookie o : arr) {
                if (o.getName().equals("cart" + user.getCustomerId())) {
                    txt += o.getValue();
                    try {
                        if (txt != null && txt.length() != 0) {
                            String[] s = txt.split("-");
                            for (String i : s) {
                                String[] n = i.split("=");
                                int id = Integer.parseInt(n[0]);
                                int quantity = Integer.parseInt(n[1]);
                                ProductSize p = orderDAO.getProductSizeByProductSizeId(id);

                                checkProduct = checkExistProduct(items, p);
                                if (checkProduct == true) {
                                    Item it = findItemByProductSizeId(items, id);
                                    it.setQuantity(it.getQuantity() + quantity);
                                } else {
                                    Item item = new Item(p, quantity);
                                    items.add(item);
                                }
//                                request.setAttribute("value1", id);
//                                request.setAttribute("value2", quantity);
                            }
                        }
                    } catch (Exception ex) {

                    }
                }
            }
        }

//        if (!checkProduct) {
//            request.setAttribute("errMsg", "Your cart do not have any products");
//            request.getRequestDispatcher("Views/CartDetail.jsp").forward(request, response);
//        }
// Get total price
        long total = 0;
        if (!items.isEmpty()) {
            for (Item i : items) {
                total += i.getQuantity() * i.getProductSize().getPrices();
            }
        }
        List<Address> addrs = new ArrayList<>();
        // fill form
        if (user != null) {
            String fullName = user.getFullName();
            String email = user.getEmail();
            String phoneNumber = user.getPhoneNumber();
            boolean gender = user.isGender();
            addrs = orderDAO.getAddressByCustomerId(user.getCustomerId());

            String address = user.getAddress();
            request.setAttribute("fullname", fullName);
            request.setAttribute("email", email);
            request.setAttribute("phone", phoneNumber);
            request.setAttribute("gender", gender);
            request.setAttribute("addressList", addrs);
        }
        request.setAttribute("total", total);

        request.setAttribute("txtCookie", txt);

        request.setAttribute("itemList", items);

        request.setAttribute("blogList", blogList);
        request.setAttribute("catList", catList);
        request.setAttribute("slideList", slideList);
        // check empty
        if (items.isEmpty()) {
            request.setAttribute("errMsg", "Your cart do not have any products");
            request.getRequestDispatcher("Views/CartDetail.jsp").forward(request, response);
        }
        // check number remain
        for (Item it : items) {
            if (!orderDAO.checkRemainProductSize(it.getProductSize().getProductSizeId(), it.getQuantity())) {
                request.setAttribute("errMsg", "Out of stock, please choose the lower quantity or other products");
                request.getRequestDispatcher("Views/CartDetail.jsp").forward(request, response);
            }
        }

        request.getRequestDispatcher("Views/CartContact.jsp").forward(request, response);
    }
  private void handlePaymentResponse(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");

        if (vnp_ResponseCode != null && vnp_ResponseCode.equals("00")) {
            // Redirect to cart completion page
            response.sendRedirect("http://localhost:9999/OnlineShop/cartcompletion");
        } else {
            // Redirect to failed payment page
            response.sendRedirect("http://localhost:9999/OnlineShop/Views/payment_failed.jsp");
        }
    }

    private boolean checkExistProduct(List<Item> items, ProductSize p) {
        for (Item i : items) {
            if (i.getProductSize().getProductSizeId() == p.getProductSizeId()) {
                return true;
            }
        }
        return false;
    }

    private Item findItemByProductSizeId(List<Item> items, int productSizeId) {
        for (Item i : items) {
            if (i.getProductSize().getProductSizeId() == productSizeId) {
                return i;
            }
        }
        return null;
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
        OrderDAO orderDAO = new OrderDAO();
        long totalPrice = 0;
        double totalPriceDouble = Double.parseDouble(request.getParameter("txtTotal"));
        try {
            totalPrice = Long.parseLong(request.getParameter("txtTotal"));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        HttpSession session = request.getSession();
        Customers user = (Customers) session.getAttribute("customer");
        DAO dao = DAO.getInstance();
        List<Category> catList = dao.getAllCategory();
        List<Blogs> blogList = dao.getBlogsWithImages();

        List<Sliders> slideList = dao.getSlider();
        List<Item> items = new ArrayList<>();

        Cookie[] arr = request.getCookies();
        boolean checkProduct = false;
        String txt = "";
        if (arr != null) {
            for (Cookie o : arr) {
                if (o.getName().equals("cart" + user.getCustomerId())) {
                    txt += o.getValue();
                    try {
                        if (txt != null && txt.length() != 0) {
                            String[] s = txt.split("-");
                            for (String i : s) {
                                String[] n = i.split("=");
                                int id = Integer.parseInt(n[0]);
                                int quantity = Integer.parseInt(n[1]);
                                ProductSize p = orderDAO.getProductSizeByProductSizeId(id);

                                checkProduct = checkExistProduct(items, p);
                                if (checkProduct == true) {
                                    Item it = findItemByProductSizeId(items, id);
                                    it.setQuantity(it.getQuantity() + quantity);
                                } else {
                                    Item item = new Item(p, quantity);
                                    items.add(item);
                                }
//                                request.setAttribute("value1", id);
//                                request.setAttribute("value2", quantity);
                            }
                        }
                    } catch (Exception ex) {

                    }
                }
            }
        }

        String newAddress1 = request.getParameter("newAddress1");
        if (newAddress1 != null && !newAddress1.trim().isEmpty()) {
            orderDAO.addNewAddress(newAddress1, user.getCustomerId(), 1);
        }

        String newAddress = request.getParameter("newAddress");
        if (newAddress != null && !newAddress.trim().isEmpty() && orderDAO.checkExistAddress(newAddress) == false) {
            orderDAO.addNewAddress(newAddress, user.getCustomerId(), 0);
        }
        String setDefault = request.getParameter("setDefault");

        Address a = new Address();
        int addressId = 0;
        if (request.getParameter("address") != null) {
            addressId = Integer.parseInt(request.getParameter("address"));
            a = orderDAO.getAddressById(addressId);
        }

        if (a != null) {
            if (setDefault != null && a.isDef() == false) {
                orderDAO.updateAddressDefault(a.getId(), a.getCustomerId());
            }
            request.getSession().setAttribute("address", a.getAddress());
        }
        Address a1 = new Address();
        if (newAddress != null && !newAddress.trim().isEmpty()) {

            a1 = orderDAO.getAddressByName(newAddress);
            if (setDefault != null) {
                orderDAO.updateAddressDefault(a1.getId(), a1.getCustomerId());
            }
            request.getSession().setAttribute("address", a1.getAddress());
        }

        String fullName = request.getParameter("name");

        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phone");

        String payment = request.getParameter("payment");
        if (payment.equals("COD") && totalPriceDouble >= 1000000) {
            request.setAttribute("txtCookie", txt);

            request.setAttribute("itemList", items);

            request.setAttribute("blogList", blogList);
            request.setAttribute("catList", catList);
            request.setAttribute("slideList", slideList);
            request.setAttribute("errMsg", "Cannot select COD payment method for orders greater than 1,000,000 VND");
            request.getRequestDispatcher("Views/CartDetail.jsp").forward(request, response);
            return;
        }

        request.getSession().setAttribute("payment", payment);
        request.getSession().setAttribute("items", items);
        request.getSession().setAttribute("totalPrice", totalPrice);
        request.getSession().setAttribute("customerId", user.getCustomerId());
        request.getSession().setAttribute("email", email);

        request.getSession().setAttribute("phoneNumber", phoneNumber);

        Order newOrder = new Order();
        // Redirect to completion view
        if (payment.equalsIgnoreCase("COD")) {
            Date currentOrderDate = new java.util.Date(); // Renamed variable
            if (newAddress != null) {
                newOrder = new Order(currentOrderDate, totalPrice, 1, user.getCustomerId(), a1.getAddress(), phoneNumber);
            } else {
                newOrder = new Order(currentOrderDate, totalPrice, 1, user.getCustomerId(), a.getAddress(), phoneNumber);
            }

            Order createdOrder = orderDAO.addOrder(newOrder); // Renamed variable
            orderDAO.addOrderDetail(items, createdOrder.getOrderId());
            CartCompletionDAO cartCompletionDAO = new CartCompletionDAO();
            Order orderDetails = cartCompletionDAO.getOrderByOrderID(createdOrder.getOrderId()); // Renamed variable
            request.getSession().setAttribute("orderID", createdOrder.getOrderId());
            request.setAttribute("orderDetail", orderDetails);
            request.setAttribute("payment", payment);
            sendConfirmationEmail(email, createdOrder.getOrderId(), totalPrice, items, payment);
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("cart" + user.getCustomerId())) {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
            }
            System.out.println("chuyen sang man cart completion");
            response.sendRedirect("cartcompletion");
            //request.getRequestDispatcher("/cartcompletion").forward(request, response);
        } else {
            PaymentController payController = new PaymentController();
            String result = payController.createPayment(request, response, totalPriceDouble, payment);
            response.sendRedirect(result);
        }
    }
    static final String fromEmail = "onlineshopsystem95@gmail.com";
    static final String password = "bgmhithlxyctqepb";

    private void sendConfirmationEmail(String toEmail, int orderId, long totalPrice, List<Item> items, String payment) {
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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
