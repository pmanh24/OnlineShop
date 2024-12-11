/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAL.ProductListDAO;
import Models.Category;
import Models.Products;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Admin
 */
@WebServlet(name = "ProductListUpdateController", urlPatterns = {"/productListUpdate"})
public class ProductListUpdateController extends HttpServlet {

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
            out.println("<title>Servlet ProductListUpdateController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductListUpdateController at " + request.getContextPath() + "</h1>");
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
        int cid=0;
        int[] cidd = null;
        ProductListDAO d = new ProductListDAO();
        List<Category> list = d.getAllCategory();
        boolean[] child = new boolean[list.size() + 1];
        List<Products> products = new ArrayList<>();
        List<Category> catList = d.getAllCategory();
        String xpage=request.getParameter("page");
        String path = request.getQueryString();
        
        
        String[] cidd_raw = request.getParameterValues("cidd");
        if(cidd_raw!=null){
            cidd = new int[cidd_raw.length];
            for(int i=0;i<cidd.length;i++){
                cidd[i] = Integer.parseInt(cidd_raw[i]);
            }
            System.out.println("vao getAllProductsSearchByArrayCateID");
            products=d.getAllProductsSearchByArrayCateID(cidd);
        }else {
            System.out.println("vao getAllProducts");
            products=d.getAllProducts();
        }
        
        String key=request.getParameter("key");
        if(key!=null){
            System.out.println("vao getAllProductsSearchByKey");
            products=d.getAllProductsSearchByKey(key);
        }
        
        String cid_raw=request.getParameter("cid");
        if(cid_raw!=null){
            cid = Integer.parseInt(cid_raw);
            if(cid ==1){
                System.out.println("vao getAllProductsOrderByDate");
                products=d.getAllProductsOrderByDate();
            }
            if(cid ==2){
                System.out.println("vao getAllProductsOrderByPrice");
                products=d.getAllProductsOrderByPrice();
            }
        }
        
        
        
        if(cidd_raw == null){
            child[0]=true;
        }else if(cidd[0]==0  && cid_raw==null){
            child[0]=true;
        }
        
        if((cidd_raw!=null)&& (cidd[0]!=0)){
            child[0]=false;
            for (int i=1; i< child.length;i++){
                if(ischeck(list.get(i-1).getCategoryId(), cidd)){
                    child[i]=true;
                }else {
                    child[i]=false;
                }
            }
        }
        
        int size = products.size();
        int page, numperpage=6;
        int numPage= (size%numperpage==0?(size/numperpage):(size/numperpage+1));
        
        if (xpage==null){
            page=1;
        }else{
            page= Integer.parseInt(xpage);
        }
        int start, end;
        start =(page-1)*numperpage;
        end=Math.min(page*numperpage, size);
        List<Products> productsByPage = new ArrayList<>();
         
                
        productsByPage= d.getAllProductByPage(products, start, end);
        
        request.setAttribute("data", list);
        request.setAttribute("catList", catList);
        request.setAttribute("products", productsByPage);
        request.setAttribute("child", child);
        request.setAttribute("cid", cid);
        request.setAttribute("key", key);
        request.setAttribute("page", page);
        request.setAttribute("numpage", numPage);
        request.setAttribute("cidd", cidd);
        request.setAttribute("path", path);
        request.getRequestDispatcher("Views/productList.jsp").forward(request, response);
    }

    private boolean ischeck(int d, int[] id){
        if (id == null){
            return false;
        }else {          
            for(int i=0; i<id.length;i++){
                if (id[i]==d){
                    return true;
                }
            }
            return false;
        }
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
