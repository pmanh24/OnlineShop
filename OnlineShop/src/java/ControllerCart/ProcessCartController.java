/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerCart;

import DAL.DAO;
import DAL.OrderDAO;
import Models.Blogs;
import Models.Category;
import Models.Customers;
import Models.Item;
import Models.ProductSize;
import Models.Products;
import Models.Sliders;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author Admin
 */
public class ProcessCartController extends HttpServlet {

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
            out.println("<title>Servlet ProcessCartController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProcessCartController at " + request.getContextPath() + "</h1>");
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
        DAO dao = DAO.getInstance();
        OrderDAO orderDAO = new OrderDAO();
        List<Category> catList = dao.getAllCategory();
        List<Blogs> blogList = dao.getBlogsWithImages();

        List<Sliders> slideList = dao.getSlider();
        Cookie[] arr = request.getCookies();
        String txt = "";
        if (arr != null) {
            for (Cookie o : arr) {
                if (o.getName().equals("cart" + user.getCustomerId())) {
                    txt += o.getValue();
                    o.setMaxAge(0);
                    response.addCookie(o);
                }
            }
        }

        List<Item> items = new ArrayList<>();
        boolean checkProduct = false;

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
                }
            }
        } catch (Exception ex) {

        }
        if (request.getParameter("isDelete") != null) {
            int productSizeId = Integer.parseInt(request.getParameter("productSizeId"));
            if (!items.isEmpty()) {
                items.remove(findItemByProductSizeId(items, productSizeId));
            }
        } else {
            int id = 0;
            int quantity = 0;
            try {
                quantity = Integer.parseInt(request.getParameter("quantity"));
                id = Integer.parseInt(request.getParameter("id"));
                ProductSize p = orderDAO.getProductSizeByProductSizeId(id);
                if (quantity == -1 && findItemByProductSizeId(items, id).getQuantity() == 1) {
                    items.remove(findItemByProductSizeId(items, id));
                } else {
                    if (quantity == -1) {
                        Item it = findItemByProductSizeId(items, id);
                        it.setQuantity(it.getQuantity() - 1);
                    }
                    if (quantity == 1) {
                        Item it = findItemByProductSizeId(items, id);
                        it.setQuantity(1 + it.getQuantity());
                    }
                }
            } catch (Exception ex) {

            }
        }

        txt = "";

        if (!items.isEmpty()) {
            txt = items.get(0).getProductSize().getProductSizeId() + "=" + items.get(0).getQuantity();
            if (items.size() > 1) {
                for (int i = 1; i < items.size(); i++) {
                    txt += "-" + items.get(i).getProductSize().getProductSizeId() + "=" + items.get(i).getQuantity();
                }
            }
        }

        Cookie c = new Cookie("cart" + user.getCustomerId(), txt);
        c.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(c);

        // Get total price
        long total = 0;
        if (!items.isEmpty()) {
            for (Item i : items) {
                total += i.getQuantity() * i.getProductSize().getPrices();
            }
        }
        request.setAttribute("total", total);
        request.setAttribute("itemList", items);
        request.setAttribute("blogList", blogList);
        request.setAttribute("catList", catList);
        request.setAttribute("slideList", slideList);
        request.getRequestDispatcher("Views/CartDetail.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        if (session.getAttribute("customer") == null) {
            response.sendRedirect("login");
            return;
        }
        Customers user = (Customers) session.getAttribute("customer");

        String role = (String) session.getAttribute("role");
        OrderDAO orderDAO = new OrderDAO();
        Cookie[] arr = request.getCookies();
        String txt = "";
        if (arr != null) {
            for (Cookie o : arr) {
                if (o.getName().equals("cart" + user.getCustomerId())) {
                    txt += o.getValue();
                    o.setMaxAge(0);
                    response.addCookie(o);
                }
            }
        }

        List<Item> items = new ArrayList<>();
        boolean checkProduct = false;

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
                }
            }
        } catch (Exception ex) {

        }
        int productId = Integer.parseInt(request.getParameter("id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));




// Tính toán tổng giá trị giỏ hàng
        long total = 0;
        for (Item i : items) {
            total += i.getQuantity() * i.getProductSize().getProduct().getSalePrice();
        }

// Cập nhật giá trị Cookie
        txt = "";

        if (!items.isEmpty()) {
            txt = items.get(0).getProductSize().getProductSizeId() + "=" + items.get(0).getQuantity();
            if (items.size() > 1) {
                for (int i = 1; i < items.size(); i++) {
                    txt += "-" + items.get(i).getProductSize().getProductSizeId() + "=" + items.get(i).getQuantity();
                }
            }
        }

// Tạo Cookie mới
        Cookie c = new Cookie("cart" + user.getCustomerId(), txt);
        c.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(c);

        // Get total price
        total = 0;
        if (!items.isEmpty()) {
            for (Item i : items) {
                total += i.getQuantity() * i.getProductSize().getProduct().getSalePrice();
            }
        }

// Gửi phản hồi về tổng giá trị giỏ hàng
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("total", total);
        response.getWriter().write(jsonResponse.toString());
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
