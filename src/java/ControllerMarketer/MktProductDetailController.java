/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerMarketer;

import DAL.DAO;
import Models.Category;
import Models.Employees;
import Models.ProductColor;
import Models.ProductImage;
import Models.ProductSize;
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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Hieu
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 50)
public class MktProductDetailController extends HttpServlet {

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
        int productId = Integer.parseInt(request.getParameter("productId"));
        DAO dao = new DAO();

        // Fetch categories and product details
        List<Category> catList = dao.getAllCategory();
        Products product = dao.getProductByIdUpdate(productId);

        List<ProductSize> productSizes = dao.getProductSizesByProductId(productId);

        request.setAttribute("product", product);
        request.setAttribute("productSizes", productSizes);

        int salePercent = 0;
        int originalPrice = product.getOriginalPrice();
        int salePrice = product.getSalePrice();
        if (originalPrice != 0) {
            // Calculate exact percentage as an integer, without rounding to double during calculation
            salePercent = ((originalPrice - salePrice) * 100) / originalPrice;
        }

        request.setAttribute("salePercent", salePercent); // Cast to int if necessary

        request.setAttribute("catList", catList);

        // Fetch product images
        List<ProductImage> productImage = dao.getAllByProId(productId);
        request.setAttribute("productImage", productImage);

        // Forward to JSP
        request.getRequestDispatcher("/Views/marketer/product-detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String productIdParam = request.getParameter("productId");
        DAO dao = new DAO();
        if (productIdParam == null || productIdParam.trim().isEmpty()) {
            request.setAttribute("error", "Product ID must be provided.");
            request.getRequestDispatcher("/Views/marketer/product-detail.jsp").forward(request, response);
            return;
        }
        try {
            int productId = Integer.parseInt(productIdParam);

            // Retrieve product name
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

            // Fetch the product from the database using the ID
            Products product = dao.getProductByIdUpdate(productId);
            if (product == null) {
                request.setAttribute("error", "Product not found.");
                doGet(request, response);
                return;
            }

            // Update product fields
            product.setProductName(pName);

            String salePriceParam = request.getParameter("salePrice");
            String salePercentParam = request.getParameter("salePercent");
            int salePrice = 0;
            int originalPrice = 0;
            Map<String, Object> priceCalculationResult = calculateOriginalPrice(salePriceParam, salePercentParam);

            if (priceCalculationResult.containsKey("error")) {
                request.setAttribute("error", priceCalculationResult.get("error"));
                doGet(request, response);
                return;
            } else {
                originalPrice = (int) priceCalculationResult.get("originalPrice");
                product.setOriginalPrice(originalPrice);
            }

//            // Validate sale price
//            if (isValidInteger(salePriceParam) && isValidInteger(salePercentParam)) {
//                salePrice = Integer.parseInt(salePriceParam);
//                int salePercent = Integer.parseInt(salePercentParam);
//
//                // Validate salePercent
//                if (salePercent < 100) {
//                    // Calculate original price as integer from sale price and percentage with adjusted rounding
//                    originalPrice = (int) Math.round((double) salePrice * 100 / (100 - salePercent));
//                    product.setOriginalPrice(originalPrice);
//                } else {
//                    request.setAttribute("error", "Sale percent cannot be 100 or more.");
//                    doGet(request, response);
//                    return;
//                }
//
//            } else {
//                request.setAttribute("error", "Sale price and sale percent must be valid integers.");
//                doGet(request, response);
//                return;
//            }
            // Handle the default image upload
            Part filePartD = request.getPart("newProImgDefault");
            if (filePartD != null && filePartD.getSize() > 0) {
                InputStream fileContentD = filePartD.getInputStream();
                String getProImgDefault = encodeImageToBase64(fileContentD);
                String fmProImgDefault = "data:image/png;base64," + getProImgDefault;
                product.setImageUrl(fmProImgDefault); // Set the new image URL for the product
            } else {
                // Retain the old image if no new file is uploaded
                product.setImageUrl(request.getParameter("proImgDefault"));
            }

            // Handle additional images
            int imageCount = Integer.parseInt(request.getParameter("imageCount")); // Number of images uploaded
            for (int i = 1; i <= imageCount; i++) {
                Part sideImagePart = request.getPart("newProImage" + i);
                if (sideImagePart != null && sideImagePart.getSize() > 0) {
                    // Process the new image
                    InputStream sideImageContent = sideImagePart.getInputStream();
                    String sideImageBase64 = encodeImageToBase64(sideImageContent);
                    String sideImageUrl = "data:image/png;base64," + sideImageBase64;

                    // Update the corresponding product image in the database
                    int productImageId = Integer.parseInt(request.getParameter("productImageId" + i));
                    dao.updateProductImage(productImageId, sideImageUrl);
                }
            }
// Servlet logic for updating product prices
            String[] productSizes = request.getParameterValues("productSize[]");
            String[] prices = request.getParameterValues("prices[]");
            String[] importPrices = request.getParameterValues("importPrice[]");
            String[] productColors = request.getParameterValues("productColor[]"); // Assuming you pass colors

            int lowestPrice = Integer.MAX_VALUE;
            if (productSizes != null && prices != null && productSizes.length == prices.length) {
                for (int i = 0; i < productSizes.length; i++) {
                    int newPrice;
                    int importPrice;
                    try {
                        newPrice = Integer.parseInt(prices[i]);
                        importPrice = Integer.parseInt(importPrices[i]);
                    } catch (NumberFormatException e) {
                        request.setAttribute("error", "Price and import price must be valid integers.");
                        doGet(request, response);
                        return;
                    }

                    // Check if new price is less than import price
                    if (newPrice < importPrice) {
                        request.setAttribute("error", "Sale price cannot be less than import price for size: " + productSizes[i]);
                        doGet(request, response);
                        return;
                    }
                    if (newPrice < lowestPrice) {
                        lowestPrice = newPrice;
                    }

                    // Create a ProductSize object to retrieve existing size
                    ProductSize tempSize = new ProductSize();
                    tempSize.setProductSizeName(productSizes[i]);
                    tempSize.setProductColor(productColors[i]);
                    tempSize.setProductId(productId);

                    // After updating the price of the existing size
                    ProductSize existingSize = dao.getProductSizeByDetails(tempSize);
                    if (existingSize != null) {
                        existingSize.setPrices(newPrice); // Update the price only
                        boolean isUpdated = dao.updateProductSizeBool(existingSize); // Update in the database
                        if (isUpdated) {
                            existingSize.setPrices(newPrice);
                            // Check if lowestPrice is valid (greater than zero) before updating the product table
                            if (lowestPrice > 0) {
                                salePrice = lowestPrice;
                                boolean pricesUpdated = dao.updatePrices(productId, originalPrice, salePrice);
                                System.out.println("Price update status: " + pricesUpdated);
//                                request.setAttribute("info", "Price updated: " + salePrice);
                                if (!pricesUpdated) {
                                    request.setAttribute("error", "Failed to update product prices for product ID: " + productId);
                                }
                            }
                        } else {
                            request.setAttribute("error", "Failed to update product size price for: " + productSizes[i]);
                            doGet(request, response);
                            return;
                        }
                    } else {
                        request.setAttribute("error", "Product size not found: " + productSizes[i]);
                        doGet(request, response);
                        return;
                    }
                }
            }
            if (lowestPrice != Integer.MAX_VALUE) {
                salePrice = lowestPrice;  // Update sale price to the lowest price found
                product.setSalePrice(salePrice); // Set the sale price on product
                dao.updatePrices(productId, product.getOriginalPrice(), salePrice); // Update in database
            }

                originalPrice = (int) Math.round((double) salePrice * 100 / (100 - Integer.parseInt(salePercentParam)));
                product.setOriginalPrice(originalPrice);
            

            // Handle category
            String categoryParam = request.getParameter("getCateProductId");
            Category category = new Category();
            int categoryId = Integer.parseInt(categoryParam);
            category.setCategoryId(categoryId);
            product.setCategory(category);

            // Handle featured and status
            String featuredParam = request.getParameter("featured");
            int featured = (featuredParam != null && featuredParam.equals("1")) ? 1 : 0;
            product.setFeatured(featured);
            String statusParam = request.getParameter("status");
            product.setStatus(Integer.parseInt(statusParam));
            String briefInformation = request.getParameter("briefInformation");
            String description = request.getParameter("proDescription");

            int numberLeft = Integer.parseInt(request.getParameter("numberLeft"));

            // Validate brief information and description
            if (briefInformation == null || briefInformation.trim().isEmpty()) {
                request.setAttribute("error", "Brief information must be provided.");
                doGet(request, response);
                return;
            }
            if (description == null || description.trim().isEmpty()) {
                request.setAttribute("error", "Description must be provided.");
                doGet(request, response);
                return;
            }
            product.setNumberLeft(numberLeft);
            product.setBriefInformation(briefInformation);
            product.setDescription(description);
            product.setSalePrice(salePrice);
            // Save the updated product to the database
            boolean isUpdated = dao.updateProduct(product);
            if (isUpdated) {
                request.setAttribute("success", "Product updated successfully!");
            } else {
                request.setAttribute("error", "Failed to update the product.");
            }
            request.setAttribute("productId", productId);
//            HttpSession session = request.getSession();
//            session.setAttribute("productIdUpdate", productId);
            doGet(request, response);
//            response.sendRedirect("product-detail?productId=" + productId);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid input for numeric fields.");
            doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while updating the product.");
            doGet(request, response);
        }
    }

    private Map<String, Object> calculateOriginalPrice(String salePriceParam, String salePercentParam) {
        Map<String, Object> result = new HashMap<>();
        int salePrice;
        int originalPrice = 0;
        int salePercent;

        // Validate that salePriceParam and salePercentParam are valid integers
        if (isValidInteger(salePriceParam) && isValidInteger(salePercentParam)) {
            salePrice = Integer.parseInt(salePriceParam);
            salePercent = Integer.parseInt(salePercentParam);

            // Ensure salePercent is less than 100
            if (salePercent < 100) {
                // Calculate the original price based on sale price and sale percentage
                originalPrice = (int) Math.round((double) salePrice * 100 / (100 - salePercent));
                result.put("originalPrice", originalPrice);
            } else {
                // Error: Sale percent cannot be 100 or more
                result.put("error", "Sale percent cannot be 100 or more.");
            }
        } else {
            // Error: Sale price and sale percent must be valid integers
            result.put("error", "Sale price and sale percent must be valid integers.");
        }

        return result;
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

    private boolean isValidInteger(String param) {
        try {
            return param != null && !param.trim().isEmpty() && Integer.parseInt(param) >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
