/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerCart;

import DAL.DAO;
import DAL.OrderDAO;
import DAL.OrderListDAO;
import Models.Blogs;
import Models.Category;
import Models.Customers;
import Models.Order;
import Models.OrderNumbProduct;
import Models.Sliders;
import Models.Status;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class MyOrderController extends HttpServlet {

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
            out.println("<title>Servlet MyOrderController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MyOrderController at " + request.getContextPath() + "</h1>");
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
        Customers user = (Customers) session.getAttribute("customer");
        String role = (String) session.getAttribute("role");
        DAO dao = new DAO();
        OrderDAO orderDAO = OrderDAO.getInstance();
        List<Order> orders = orderDAO.getAllOrderByCustomerId(user.getCustomerId());

        List<OrderNumbProduct> ods = new ArrayList<OrderNumbProduct>();

        for (Order o : orders) {
            OrderNumbProduct od = new OrderNumbProduct();
            od.setNumber(orderDAO.getNumberProductByOrder(o.getOrderId()) - 1);
            od.setOrder(o);
            od.setFirstProduct(dao.getProductById(orderDAO.getFirstProductIdByOrderId(o.getOrderId())));
            ods.add(od);
        }

        int size = ods.size();
        //paging
        int page, numperpage = 3;
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
        List<OrderNumbProduct> ordersByPage = new ArrayList<>();
        ordersByPage = orderDAO.getAllOrderByPage(ods, start, end);
//        int orderId = Integer.parseInt(request.getParameter("orderId"));
//        boolean hasFeedback = dao.hasUserFeedback(orderId, user.getCustomerId());
        request.setAttribute("orders", ordersByPage);
        request.setAttribute("page", page);
        request.setAttribute("numpage", numPage);
//        request.setAttribute("hasFeedback", hasFeedback);
        request.setAttribute("orderList", ods);
        OrderListDAO daoOR = new OrderListDAO();
        List<Category> catList = dao.getAllCategory();
        List<Blogs> blogList = dao.getBlogsWithImages();
        List<Status> statusList = daoOR.getAllStatus();
        List<Sliders> slideList = dao.getSlider();
        request.setAttribute("statusList", statusList);
        request.setAttribute("blogList", blogList);
        request.setAttribute("catList", catList);
        request.setAttribute("slideList", slideList);
        request.getRequestDispatcher("Views/MyOrders.jsp").forward(request, response);
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
