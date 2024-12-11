/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAL.CustomerDAO;
import DAL.DAO;
import DAL.FeedBackDAO;
import DAL.OrderDAO;
import Models.Customers;
import Models.FeedBacks;
import Models.Products;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Hieu
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 5, // 5 MB
        maxRequestSize = 1024 * 1024 * 10)  // 10 MB
public class FeedbackController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("customer") == null) {
            response.sendRedirect("login");
            return;
        }

        FeedBackDAO fbDAO = new FeedBackDAO();
        CustomerDAO cDao = new CustomerDAO();
        OrderDAO oDao = new OrderDAO();
        Customers user = (Customers) session.getAttribute("customer");

        int orderId = Integer.parseInt(request.getParameter("orderId"));
        int productId = Integer.parseInt(request.getParameter("productId"));
        Products orderedProducts = oDao.getOrderedProductByProductId(orderId, productId, user.getCustomerId());
        FeedBacks feedbacks = fbDAO.getFeedbackByOrderIdAndProductId(orderId, productId);
        boolean existedfb = fbDAO.existedFeedback(orderId, productId, user.getCustomerId());

        request.setAttribute("customer", cDao.getCustomerById(user.getCustomerId()));
        request.setAttribute("orderedProducts", orderedProducts);
        request.setAttribute("feedback", feedbacks);
        request.setAttribute("existedfeedback", existedfb);
        request.setAttribute("orderId", orderId);
        request.setAttribute("productId", productId);
        request.getRequestDispatcher("Views/cus-feedback.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customers user = (Customers) session.getAttribute("customer");

        // Ensure the user is logged in
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        // Collect form data
        String orderIdStr = request.getParameter("orderId");
        String productIdStr = request.getParameter("productId");

        // Validate orderId and productId
        if (orderIdStr == null || orderIdStr.isEmpty() || productIdStr == null || productIdStr.isEmpty()) {
            throw new ServletException("Order ID or Product ID is missing.");
        }

        int orderId = Integer.parseInt(orderIdStr);
        int productId = Integer.parseInt(productIdStr);

        // Handle rating and feedback content
        String rateParam = request.getParameter("rate_" + productId);
        String feedContent = request.getParameter("feedContent_" + productId);
        String selectedSizeIdStr = request.getParameter("size");

        // Parse rating and size ID
        int rating = (rateParam != null && !rateParam.isEmpty()) ? Integer.parseInt(rateParam) : 0;
        Integer selectedSizeId = (selectedSizeIdStr != null && !selectedSizeIdStr.isEmpty())
                ? Integer.valueOf(selectedSizeIdStr)
                : null;
        

        // Create DAO instance
        DAO feedbackDAO = new DAO();
        FeedBackDAO fbDAO = new FeedBackDAO();
        // Check if feedback exists for the given product and order
        FeedBacks existingFeedback = fbDAO.getFeedbackByOrderIdAndProductId(orderId, productId);
        List<String> imageFileNames = new ArrayList<>();

        // Handle file upload and Base64 conversion for the single product
        // Only encode new images if they are uploaded
        Part filePart = request.getPart("feedbackImage_" + productId);
        if (filePart.getSize() > 0) {
            imageFileNames = encodeImagesToBase64ForProduct(request, productId);
        } else if (existingFeedback != null) {
            // Use the existing image file names if no new images are uploaded
            imageFileNames = existingFeedback.getImageFileNames();
        }
        if (existingFeedback != null) {
            // Update existing feedback
            existingFeedback.setRated_star(rating);
            existingFeedback.setContent(feedContent);
            existingFeedback.setSize_id(selectedSizeId);
            existingFeedback.setImageFileNames(imageFileNames);
            existingFeedback.setStatus(1); // Assuming status remains the same

            feedbackDAO.updateFeedback(existingFeedback);

            request.setAttribute("successMessage", "Feedback updated successfully!");
        } else {
            // Create new feedback
            FeedBacks feedback = new FeedBacks();
            feedback.setProduct_id(productId);
            feedback.setCustomer_id(user.getCustomerId());
            feedback.setOrderId(orderId);
            feedback.setRated_star(rating);
            feedback.setContent(feedContent);
            feedback.setSize_id(selectedSizeId);
            feedback.setImageFileNames(imageFileNames);
            feedback.setStatus(1);

            feedbackDAO.insertFeedback(feedback);

            request.setAttribute("successMessage", "Thank you for your feedback!");
        }

        // Redirect to feedback page with success message and order ID
        request.setAttribute("orderId", orderId);
//        request.getRequestDispatcher("Views/cus-feedback.jsp").forward(request, response);
        doGet(request, response);
    }

// This method handles encoding images for the specific product
    private List<String> encodeImagesToBase64ForProduct(HttpServletRequest request, int productId) throws IOException, ServletException {
        List<String> imageFileNames = new ArrayList<>();

        // Retrieve the part that corresponds to the file uploads for this product
        Part[] fileParts = request.getParts().stream()
                .filter(part -> part.getName().equals("feedbackImage_" + productId))
                .toArray(Part[]::new);

        // Process each file part
        for (Part filePart : fileParts) {
            String fileName = filePart.getSubmittedFileName();
            if (fileName != null && !fileName.isEmpty()) {
                // Convert the file to a Base64 string
                try ( InputStream inputStream = filePart.getInputStream()) {
                    String base64Image = encodeImageToBase64(inputStream);
                    imageFileNames.add(base64Image); // Add the Base64 string to the list
                } catch (IOException e) {
                    e.printStackTrace(); // Handle the error appropriately
                }
            }
        }

        return imageFileNames; // Return the list of images for this product
    }

// This method encodes a single image input stream to a Base64 string
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
