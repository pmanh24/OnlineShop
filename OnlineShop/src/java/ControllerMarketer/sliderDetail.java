/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerMarketer;

import DAL.BlogDAO;
import Models.Sliders;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author T
 */
@MultipartConfig
public class sliderDetail extends HttpServlet {
private static final String IMAGE_DIRECTORY = "C:\\Users\\T\\Desktop\\SWP-onlshop\\group02\\web\\sliderImage"; // Đường dẫn lưu ảnh slider

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
            out.println("<title>Servlet sliderList</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet sliderList at " + request.getContextPath() + "</h1>");
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
         String sliderID = request.getParameter("sliderId");
        BlogDAO dao = new BlogDAO();
        
        if (sliderID != null) {
            Sliders slider = dao.getSliderByID(Integer.parseInt(sliderID));
            request.setAttribute("slider", slider);
        }

        // Forward thông tin tới trang JSP
        request.getRequestDispatcher("Views/marketer/sliderdetail.jsp").forward(request, response);
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
       int sliderID = Integer.parseInt(request.getParameter("sliderId"));
        String title = request.getParameter("title");
        String backLink = request.getParameter("back_link");
        String note = request.getParameter("note");
        
        int status = Integer.parseInt(request.getParameter("status"));

        BlogDAO dao = new BlogDAO();

        // Xử lý ảnh upload
        Part filePart = request.getPart("image"); // Lấy đối tượng Part từ form upload
        String fileName = filePart.getSubmittedFileName(); // Lấy tên file

        String savePath = IMAGE_DIRECTORY + File.separator + fileName; // Đường dẫn để lưu file
        
        
       File uploadDir = new File(IMAGE_DIRECTORY);
        if (!uploadDir.exists()) {
    uploadDir.mkdirs();  // Tạo thư mục nếu nó không tồn tại
}


        // Lưu file ảnh
        filePart.write(savePath);

 

        boolean isUpdated = dao.updateSlider(sliderID, title, backLink, note, status, fileName);

        // Xử lý kết quả
        if (isUpdated) {
            request.setAttribute("message", "Slider updated successfully!");
        } else {
            request.setAttribute("message", "Failed to update slider.");
        }

        // Lấy lại thông tin slider sau khi cập nhật
        Sliders updatedSlider = dao.getSliderByID(sliderID);
        request.setAttribute("slider", updatedSlider);

        // Forward thông tin tới trang JSP
        request.getRequestDispatcher("Views/marketer/sliderdetail.jsp").forward(request, response);
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
