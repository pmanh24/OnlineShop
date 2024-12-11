/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerSale;

import DAL.SaleDashboardDAO;
import Models.Employees;
import Models.OrderCount;
import Models.Status;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Admin
 */
@WebServlet(name = "SaleDashboardController", urlPatterns = {"/sale-dashboard-controller"})
public class SaleDashboardController extends HttpServlet {

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
            out.println("<title>Servlet SaleDashboardController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SaleDashboardController at " + request.getContextPath() + "</h1>");
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
        SaleDashboardDAO dao = new SaleDashboardDAO();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String saleNameQuery = null;
        String dateFromQuery = null;
        String dateToQuery = null;
        String statusQuery = null;
        List<OrderCount> orderCounts = dao.getDailyOrderCounts();
        List<OrderCount> costCounts;
        List<Status> statusList = dao.getAllStatus();
        String saleName = request.getParameter("saleName");
        if (saleName != null && saleName != "") {
            System.out.println("vao sale name ");
            saleNameQuery = saleName;
            request.setAttribute("saleNameValue", saleNameQuery);
        }
        String dateFrom = request.getParameter("dateFrom");
        if (dateFrom != null && dateFrom != "") {
            System.out.println("vao date from ");
            dateFromQuery = dateFrom;
            request.setAttribute("dateFromValue", dateFromQuery);
        }else{
            LocalDate dateStart = LocalDate.now().minusDays(6);
            dateFromQuery = dateStart.format(formatter);
        }
        String dateTo = request.getParameter("dateTo");
        if (dateTo != null && dateTo != "") {
            System.out.println("vao date to");
            dateToQuery = dateTo;
            request.setAttribute("dateToValue", dateToQuery);
        } else {
            LocalDate dateEnd = LocalDate.now().plusDays(1);
            dateToQuery = dateEnd.format(formatter);
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

        if (saleNameQuery == null && dateFromQuery == null && dateToQuery == null && statusQuery == null) {         
            orderCounts = dao.getDailyOrderCountsByKey(dateFromQuery, dateToQuery, statusQuery, saleNameQuery);
            costCounts = dao.getDailyRevenueCountsByKey(dateFromQuery, dateToQuery, statusQuery, saleNameQuery);
        } else {
            orderCounts = dao.getDailyOrderCountsByKey(dateFromQuery, dateToQuery, statusQuery, saleNameQuery);
            costCounts = dao.getDailyRevenueCountsByKey(dateFromQuery, dateToQuery, statusQuery, saleNameQuery);

        }

        Gson gson = new Gson();
        String jsonOrderCounts = gson.toJson(orderCounts); 
        String jsonCostCounts = gson.toJson(costCounts); 
        request.setAttribute("orderCounts", jsonOrderCounts);
        request.setAttribute("costCounts", jsonCostCounts);
        request.setAttribute("statusList", statusList);
        request.getRequestDispatcher("Views/sale/sale-dashboard.jsp").forward(request, response);
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
