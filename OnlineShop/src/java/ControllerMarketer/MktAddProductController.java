package ControllerMarketer;

import DAL.DAO;
import Models.Category;
import Models.Customers;
import Models.Employees;
import Models.ProductColor;
import Models.ProductImage;
import Models.ProductSize;
import Models.Products;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

/**
 *
 * @author Hieu
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 50)
public class MktAddProductController extends HttpServlet {

    private static final long serialVersionUID = 1L;

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
        DAO dao = DAO.getInstance();

        List<Category> catList = dao.getAllCategory();
        request.setAttribute("categories", catList);
        request.getRequestDispatcher("Views/marketer/add-product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        try {
            String pName = request.getParameter("productName");
            if (pName == null || pName.trim().isEmpty()) {
                request.setAttribute("error", "Product name must be provided and cannot contain only spaces.");
                doGet(request, response);
                return;
            }

            // Check for leading or trailing spaces
            if (pName.startsWith(" ") || pName.endsWith(" ")) {
                request.setAttribute("error", "Product name cannot start or end with spaces.");
                doGet(request, response);
                return;
            }

            Products product = new Products();
            product.setProductName(pName);

            // Handle the default image upload
            Part filePartD = request.getPart("proImgDefault");
            InputStream fileContentD = filePartD.getInputStream();
            String getProImgDefault = encodeImageToBase64(fileContentD);
            String fmProImgDefault = "data:image/png;base64," + getProImgDefault;
            product.setImageUrl(fmProImgDefault);


            // Handle category
            String categoryParam = request.getParameter("getCateProductId");
            Category category = new Category();
            int categoryId = Integer.parseInt(categoryParam);
            category.setCategoryId(categoryId);
            product.setCategory(category);
            product.setDescription(request.getParameter("proDescription"));
            product.setBriefInformation(request.getParameter("briefInformation"));

            if (request.getParameter("proDescription") == null || request.getParameter("briefInformation") == null
                    || request.getParameter("proDescription").trim().isEmpty() || request.getParameter("briefInformation").trim().isEmpty()) {
                request.setAttribute("error", "Description or Brief information must be provided.");
                doGet(request, response);
                return;
            }

            // Validate product status
            product.setStatus(Integer.parseInt(request.getParameter("proStatus")));

            // Save product to database
            DAO dao = DAO.getInstance();
            boolean isAdded = dao.addProduct(product);

            if (isAdded) {
                int productId = dao.getInsertIdNewest(); // Assuming you have a method to get the last inserted ID
                // Handle additional images
                List<ProductImage> images = new ArrayList<>();
                for (int i = 1; i <= 3; i++) {
                    Part filePart = request.getPart("proImg" + i);
                    if (filePart != null && filePart.getSize() > 0) {
                        InputStream fileContent = filePart.getInputStream();
                        String encodedImage = encodeImageToBase64(fileContent);
                        String fullImage = "data:image/png;base64," + encodedImage;

                        // Create a ProductImage object and add it to the list
                        ProductImage productImage = new ProductImage();
                        productImage.setImageUrl(fullImage);
                        productImage.setProductId(productId);
                        images.add(productImage);
                    }
                }

                dao.addProductImages(productId, images);
                request.setAttribute("success", "Product added successfully.");
                doGet(request, response);
            } else {
                request.setAttribute("error", "Failed to add product. Please try again.");
                doGet(request, response);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            request.setAttribute("errorInputPrice", e.getMessage());
            doGet(request, response);
        }
    }

    private String encodeImageToBase64(InputStream imageInputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = imageInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        byte[] imageBytes = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    // Helper methods
    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    private boolean isValidInteger(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false; // Check for null or empty strings
        }
        try {
            int value = Integer.parseInt(str); // Try to parse the integer
            return value >= 0; // Ensure it's a non-negative integer
        } catch (NumberFormatException e) {
            return false; // Not a valid integer
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
