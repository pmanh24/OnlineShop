package Controller;

import DAL.DAO;
import Models.Category;
import Models.FeedBacks;
import Models.ProductColor;
import Models.ProductImage;
import Models.ProductSize;
import Models.Products;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ProductDetailController", urlPatterns = {"/productdetail"})
public class ProductDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int productId = Integer.parseInt(request.getParameter("productId"));
        DAO dao = new DAO();
        List<Category> catList = dao.getAllCategory();
        Products product = dao.getProductById(productId);
        List<ProductSize> productSize = dao.getProductbySize(productId);

        List<FeedBacks> feedbacks = dao.getAllFeedbackByProductId(productId);
        request.setAttribute("size", productSize);
        request.setAttribute("detail", product);
        request.setAttribute("catList", catList);
        request.setAttribute("feedbackList", feedbacks);

        List<ProductImage> image = dao.getImagebyID(productId);
        request.setAttribute("images", image);
        request.getRequestDispatcher("Views/productDetails.jsp").forward(request, response);
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
        String quantity = request.getParameter("pquantity");
        int quantityP = Integer.parseInt(quantity);

        int productId = Integer.parseInt(request.getParameter("pid"));
        DAO dao = new DAO();
        List<Category> catList = dao.getAllCategory();
        Products product = dao.getProductById(productId);
        List<ProductSize> productSize = dao.getProductbySize(productId);
        List<ProductImage> image = dao.getImagebyID(productId);
        request.setAttribute("size", productSize);
        request.setAttribute("detail", product);
        request.setAttribute("catList", catList);
        request.setAttribute("images", image);

        String id = request.getParameter("productSize");

        String quantityKey = "quantity_" + id; // Tạo key cho quantity
        String quantityValue = request.getParameter(quantityKey); // Lấy giá trị quantity từ key

        // Lấy giá trị `selectedPrice` từ form
        String selectedPrice = request.getParameter("selectedPrice");
        int price = Integer.parseInt(selectedPrice);
        // Kiểm tra nếu `selectedPrice` có giá trị hợp lệ
        if (selectedPrice != null && !selectedPrice.isEmpty()) {
            // Chuyển đổi `selectedPrice` thành số nguyên và cập nhật `salePrice`
            product.setSalePrice(price);
            // Đặt `detail` làm thuộc tính cho request để chuyển tiếp sang JSP
            request.setAttribute("detail", product);

        }

        int quantitySP = 0;
        if (quantityValue != null) {
            quantitySP = Integer.parseInt(quantityValue);
        }
        if (quantityP > quantitySP || quantityP < 0) {
            request.setAttribute("mess", "*Insufficient quantity");
            request.getRequestDispatcher("Views/productDetails.jsp").forward(request, response);
        } else {
            response.sendRedirect("addcart?productSize=" + id + "&pquantity=" + quantity);
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
