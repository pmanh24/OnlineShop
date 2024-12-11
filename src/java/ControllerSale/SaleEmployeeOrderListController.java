/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerSale;

import DAL.OrderDetailDAO;
import DAL.OrderListDAO;
import Models.Employees;
import Models.Order;
import Models.Status;
import Models.StatusCount;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
@WebServlet(name = "SaleEmployeeOrderListController", urlPatterns = {"/sale-employee-order-list"})
public class SaleEmployeeOrderListController extends HttpServlet {

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
            out.println("<title>Servlet SaleEmployeeOrderListController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SaleEmployeeOrderListController at " + request.getContextPath() + "</h1>");
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
        OrderListDAO dao = new OrderListDAO();
        System.out.println("user id: " + user.getEmployeeId());
        List<Order> orders = dao.getAllOrderByEmployeeID(user.getEmployeeId());
        List<StatusCount> statusList = dao.getAllStatusCountByEmployeeID(2, user.getEmployeeId());
        String path = "";
        String orderIdQuery = null;
        String customerNameQuery = null;
        String saleNameQuery = null;
        String dateFromQuery = null;
        String dateToQuery = null;
        String statusQuery = null;

        String orderId = request.getParameter("orderId");
        if (orderId != null && !orderId.isEmpty()) {
            if (!orderId.endsWith("null")) {
                System.out.println("vao order id " + orderId);
                orderIdQuery = orderId;
                request.setAttribute("orderIdValue", orderIdQuery);
            }
        }

        String customerName = request.getParameter("customerName");
        if (customerName != null && !customerName.isEmpty()) {
            if (!customerName.endsWith("null")) {
                System.out.println("vao customer name " + customerName);
                customerNameQuery = customerName;
                request.setAttribute("customerNameValue", customerNameQuery);
            }
        }

        String saleName = request.getParameter("saleName");
        if (saleName != null && !saleName.isEmpty()) {
            if (!saleName.endsWith("null")) {
                System.out.println("vao sale name " + saleName);
                saleNameQuery = saleName;
                request.setAttribute("saleNameValue", saleNameQuery);
            }
        }

        String dateFrom = request.getParameter("dateFrom");
        if (dateFrom != null && !dateFrom.isEmpty()) {
            if (!dateFrom.endsWith("null")) {
                System.out.println("vao date from " + dateFrom);
                dateFromQuery = dateFrom;
                request.setAttribute("dateFromValue", dateFromQuery);
            }
        }
        
        String dateTo = request.getParameter("dateTo");
        if (dateTo != null && !dateTo.isEmpty()) {
            if (!dateTo.endsWith("null")) {
                System.out.println("vao date to " + dateTo);
                dateToQuery = dateTo;
                request.setAttribute("dateToValue", dateToQuery);
            }
        }
        
        String status = request.getParameter("status");
        if (status != null) {
            if (!status.contains("0")) {
                System.out.println("vao status");
                statusQuery = status;
                request.setAttribute("statusValue", statusQuery);
            }

        } else {
            request.setAttribute("statusValue", 0);
        }
        if (orderIdQuery == null && customerNameQuery == null && saleNameQuery == null && dateFromQuery == null && dateToQuery == null && statusQuery == null) {
            orders = dao.getAllOrderByEmployeeID(user.getEmployeeId());
        } else {
            orders = dao.SearchAllOrderByKey(orderIdQuery, customerNameQuery, dateFromQuery, dateToQuery, statusQuery, user.getFullName());
            path = "orderId=" + orderId + "&customerName=" + customerName + "&saleName=" + user.getFullName() + "&dateFrom=" + dateFrom + "&dateTo=" + dateTo + "&status=" + status;
        }

        int size = orders.size();
        int page, numperpage = 10;
        int numPage = (size % numperpage == 0 ? (size / numperpage) : (size / numperpage + 1));
        String xpage = request.getParameter("page");
        if (xpage == null) {
            page = 1;
        } else {
            page = Integer.parseInt(xpage);
        }
        int start, end;
        start = (page - 1) * numperpage;
        end = Math.min(page * numperpage, size);
        List<Order> orderByPage = new ArrayList<>();
        orderByPage = dao.getAllOrderByPage(orders, start, end);

        ArrayList<String> listOfStatus = new ArrayList<>();
        for (int i = 0; i < orderByPage.size(); i++) {
            String isEdit = "";
            Status statusI = dao.getStatusByStatusId(orderByPage.get(i).getStatusO().getStatusId());
            if (statusI.getRoleId().contains(String.valueOf(user.getRoleId()))) {
                switch (statusI.getStatusId()) {
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
            listOfStatus.add(isEdit);
        }

        request.setAttribute("statusListTable", listOfStatus);
        request.setAttribute("statusList", statusList);
        request.setAttribute("orderList", orderByPage);
        request.setAttribute("page", page);
        request.setAttribute("numpage", numPage);
        request.setAttribute("path", path);
        request.getRequestDispatcher("Views/sale/sale-employee-order-list.jsp").forward(request, response);
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
        // Get the parameters from the form
        String orderIdParam = request.getParameter("orderIdUpdate");
        String statusParam = request.getParameter("statusIdUpdate");
        System.out.println("ststus: " + orderIdParam);
        // Update order status logic here
        OrderDetailDAO DAO = new OrderDetailDAO();
        int orderId = Integer.parseInt(orderIdParam);
        int status = Integer.parseInt(statusParam);

        DAO.updateStatusForOrder(orderId, status);
        if (status == 6) {
            String email = DAO.getOrderByOrderID(orderId).getCustomer().getEmail();
            sendConfirmationEmail(email, orderId);
        } else if (status == 7) {
            System.out.println("vao truong hop update so luong return");
            DAO.UpdateQuantityReturn(orderId);
        } else if (status == 8) {
            System.out.println("vao truong hop update so luong cancel");
            DAO.UpdateQuantityCancel(orderId);
        }
        response.sendRedirect(request.getContextPath() + "/sale-employee-order-list");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
