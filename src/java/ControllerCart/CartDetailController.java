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

/**
 *
 * @author Admin
 */
public class CartDetailController extends HttpServlet {

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
            out.println("<title>Servlet CartDetailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartDetailController at " + request.getContextPath() + "</h1>");
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
        if(session.getAttribute("customer") == null){
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
// Get total price
        long total = 0;
        if (!items.isEmpty()) {
            for (Item i : items) {
                total += i.getQuantity() * i.getProductSize().getPrices();
            }
        }
        request.setAttribute("total", total);

        request.setAttribute("txtCookie", txt);

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
