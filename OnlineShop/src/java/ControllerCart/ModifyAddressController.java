/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerCart;

import DAL.OrderDAO;
import Models.Address;
import Models.Customers;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Admin
 */
public class ModifyAddressController extends HttpServlet {

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
            out.println("<title>Servlet ModifyAddressController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ModifyAddressController at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = null;
        JSONObject jsonResponse = new JSONObject();

        try {
            out = response.getWriter();
            OrderDAO orderDAO = new OrderDAO();

            // Lấy địa chỉ mới từ yêu cầu
            String newAddress = request.getParameter("address");
            HttpSession session = request.getSession(false);
            Customers user = (session != null) ? (Customers) session.getAttribute("customer") : null;

            // Kiểm tra người dùng đã đăng nhập hay chưa
            if (user == null) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "User not logged in.");
            } else if (newAddress == null || newAddress.trim().isEmpty()) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Invalid address.");
            } else {
                // Kiểm tra và thêm địa chỉ
                if (orderDAO.checkExistAddress(newAddress)) {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Address already exists.");
                } else {
                    // Thêm địa chỉ mới vào cơ sở dữ liệu
                    orderDAO.addNewAddress(newAddress, user.getCustomerId(), 0);

                    // Lấy danh sách địa chỉ đã cập nhật
                    List<Address> addressList = orderDAO.getAddressByCustomerId(user.getCustomerId());
                    JSONArray addressArray = new JSONArray();
                    for (Address address : addressList) {
                        JSONObject addressObj = new JSONObject();
                        addressObj.put("id", address.getId());
                        addressObj.put("address", address.getAddress());
                        addressObj.put("def", address.isDef());
                        addressArray.put(addressObj);
                    }

                    jsonResponse.put("addressList", addressArray);
                    jsonResponse.put("success", true);
                    jsonResponse.put("message", "Address added successfully!");
                }
            }

            // Gửi phản hồi JSON về client
            out.print(jsonResponse.toString());
        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "An error occurred: " + e.getMessage());
            if (out != null) {
                out.print(jsonResponse.toString());
            }
        } finally {
            if (out != null) {
                out.flush();
                out.close();
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
