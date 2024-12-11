package ControllerMarketer;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAL.DAO;
import Models.Employees;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * Controller for handling dashboard requests for the marketer. It processes
 * date inputs, fetches product sales data, and forwards the data to the JSP.
 *
 * Author: Hieu
 */
public class MktDashboardController extends HttpServlet {

    private static final String ERROR_INVALID_DATE_FORMAT = "Invalid date format. Use YYYY-MM-DD.";
    private static final String ERROR_END_DATE_BEFORE_START = "End date must be on or after start date.";
    private static final String SUCCESS_NO_SALES_DATA = "Product sales data is unavailable for the selected period.";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Employees user = (Employees) session.getAttribute("employee");

        // Redirect to login if user is not logged in
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/employee/login");
            return;
        } else if (user.getRoleId() == 4) {
            DAO dao = new DAO();

            // Set default date range (last 7 days)
            LocalDate endDate = LocalDate.now();
            LocalDate startDate = endDate.minusDays(7);

            // Retrieve date parameters from the request
            String startDateParam = request.getParameter("startDate");
            String endDateParam = request.getParameter("endDate");

            // Validate and parse date parameters
            String errorMessage = validateDates(startDateParam, endDateParam);
            if (errorMessage != null) {
                request.setAttribute("error", errorMessage);
                // Use either provided or default date range
                request.setAttribute("startDate", (startDateParam != null) ? startDateParam : startDate.toString());
                request.setAttribute("endDate", (endDateParam != null) ? endDateParam : endDate.toString());
            } else {
                // Parse the date values if no validation errors
                if (startDateParam != null) {
                    startDate = LocalDate.parse(startDateParam, DATE_FORMATTER);
                }
                if (endDateParam != null) {
                    endDate = LocalDate.parse(endDateParam, DATE_FORMATTER);
                }
                
                // Fetch statistics
                int postCount = dao.getPostCount(startDate.toString(), endDate.toString());
                int productCount = dao.getProductCount(startDate.toString(), endDate.toString());
                int customerCount = dao.getCustomerCount(startDate.toString(), endDate.toString());
                int fbCount = dao.getFeedbackCount(startDate.toString(), endDate.toString());
                Map<LocalDate, Integer> customerTrends = dao.getCustomerTrends(endDate.toString());
                Map<String, Integer> productSales = dao.getProductSales(startDate, endDate);

                // Create a new map to hold modified product names
                Map<String, Integer> modifiedProductSales = new HashMap<>();

                for (Map.Entry<String, Integer> entry : productSales.entrySet()) {
                    String modifiedProductName = entry.getKey()
                            //                        .replace("/", "_") // Replace slashes with underscores
                            .replace("'", "_"); // Replace apostrophes with underscores

//                        .replace(" ", "_");
                    modifiedProductSales.put(modifiedProductName, entry.getValue());
                }

                // Set statistics in request attributes
                request.setAttribute("postCount", postCount);
                request.setAttribute("productCount", productCount);
                request.setAttribute("customerCount", customerCount);
                request.setAttribute("fbCount", fbCount);
                request.setAttribute("customerTrends", customerTrends);

                int totalSales = modifiedProductSales.values().stream().mapToInt(Integer::intValue).sum();
                request.setAttribute("productSales", modifiedProductSales); // Use the new map
                request.setAttribute("totalSales", totalSales);

                // Check if customer trends are empty
                if (customerTrends == null || customerTrends.isEmpty()) {
                    request.setAttribute("info", SUCCESS_NO_SALES_DATA);
                } else {
                    // Convert customer trends to JSON
                    Gson gson = new Gson();
                    String customerTrendsJson = gson.toJson(customerTrends);
                    request.setAttribute("customerTrends", customerTrendsJson);
                }
                
                // Set other attributes
                request.setAttribute("startDate", startDate.toString());
                request.setAttribute("endDate", endDate.toString());
            }

            // Forward to the dashboard JSP
            request.getRequestDispatcher("Views/marketer/dashboard.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("Views/unauthorized.jsp").forward(request, response);
        }
    }

    private String validateDates(String startDateParam, String endDateParam) {
        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now();

        try {
            if (startDateParam != null && !startDateParam.isEmpty()) {
                startDate = LocalDate.parse(startDateParam, DATE_FORMATTER);
            }
            if (endDateParam != null && !endDateParam.isEmpty()) {
                endDate = LocalDate.parse(endDateParam, DATE_FORMATTER);
            }

            if (endDate.isBefore(startDate)) {
                return ERROR_END_DATE_BEFORE_START;
            }
        } catch (DateTimeParseException e) {
            return ERROR_INVALID_DATE_FORMAT;
        }

        return null; // No errors
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "MktDashboardController - Handles dashboard functionalities for marketers.";
    }
}
