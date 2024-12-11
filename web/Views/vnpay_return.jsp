<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="Information.Config" %>
<%@ page import="Models.Order" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Payment Result</title>
        <link href="./assets/bootstrap.min.css" rel="stylesheet"/>
        <link href="./assets/jumbotron-narrow.css" rel="stylesheet">
        <script src="./assets/jquery-1.11.3.min.js"></script>
    </head>
    <body>
        <%
            // Begin process return from VNPAY
            Map<String, String> fields = new HashMap<>();
            for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
                String fieldName = URLEncoder.encode(params.nextElement(), StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
                if (fieldValue != null && fieldValue.length() > 0) {
                    fields.put(fieldName, fieldValue);
                }
            }

            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            fields.remove("vnp_SecureHashType");
            fields.remove("vnp_SecureHash");
            String signValue = Config.hashAllFields(fields);

            boolean isVnpaySuccess = signValue.equals(vnp_SecureHash) && "00".equals(request.getParameter("vnp_TransactionStatus"));
        %>

        <div class="container">
            <div class="header clearfix">
                <h3 class="text-muted">Payment Result</h3>
            </div>

            <form action="finalpay" method="post">
                <div class="table-responsive">
                    <div class="form-group">
                        <input name="uID" value="${sessionScope.user.userID}" style="display: none"/>
                        <label>Transaction ID:</label>
                        <label><%= request.getParameter("vnp_TxnRef") %></label>
                    </div>
                    <div class="form-group">
                        <label>Amount:</label>
                        <%
                            int amount = Integer.parseInt(request.getParameter("vnp_Amount"));
                            int dividedAmount = amount / 100;
                        %>
                        <input name="money" value="<%= dividedAmount %>" style="display: none"/>
                        <label><%= dividedAmount %>đ</label>
                    </div>
                    <div class="form-group">
                        <label>Description:</label>
                        <label><%= request.getParameter("vnp_OrderInfo") %></label>
                    </div>
                    <div class="form-group">
                        <label>Response Code:</label>
                        <label><%= request.getParameter("vnp_ResponseCode") %></label>
                    </div>
                    <div class="form-group">
                        <label>VNPAY-QR Transaction No.:</label>
                        <label><%= request.getParameter("vnp_TransactionNo") %></label>
                    </div>
                    <div class="form-group">
                        <label>Bank Code:</label>
                        <label><%= request.getParameter("vnp_BankCode") %></label>
                    </div>

                    <%
                          // Lấy thời gian thanh toán từ tham số yêu cầu
    String payDateStr = request.getParameter("vnp_PayDate");
    // Định dạng thời gian từ chuỗi thành đối tượng Date
    Date payDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(payDateStr);
    // Định dạng lại thời gian thành chuỗi theo định dạng mong muốn
    String formattedPayDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(payDate);

                      Order order = (Order) request.getSession().getAttribute("order");
                    %>

                    <div class="form-group">
                        <label>Payment Time:</label>
                        <label><%= formattedPayDate %></label>
                        <input name="date" value="<%= formattedPayDate %>" style="display:none"/>
                    </div>

                    <div class="form-group">
                        <label>Transaction Status:</label>
                        <label>
                            <% if (isVnpaySuccess) { %>
                            Successful
                            <% } else { %>
                            Unsuccessful
                            <% } %>
                        </label>
                    </div>
                </div>

                <div>
                    <% if (isVnpaySuccess && order != null) { %>
                    <!-- Success: Display order completion details -->
                    <section>
                        <h3 class="text-center pb-3" style="color: #FF9A00; font-weight: bold">Order placed successfully</h3>
                        <h2 class="text-center pb-3" style="font-size: 20px;">Please check your email to ensure that the order has been placed correctly</h2>
                        <h2 class="text-center pb-3" style="font-size: 18px;">Order date: ${order.orderDate}</h2>
                        <h2 class="text-center pb-3" style="font-size: 18px;">Total cost: ${order.totalCost} VND</h2>
                        <h2 class="text-center pb-3" style="font-size: 18px;">Address: ${order.address} </h2>
                        <h2 class="text-center pb-3" style="font-size: 18px;">Phone number: ${order.phoneNumber}</h2>
                        <button style="margin-top:30px" type="button" class="btn btn-primary" onclick="window.location.href = '<%= request.getContextPath() %>/home'">Back To Homepage</button>
                        <button style="margin-top:30px" type="button" class="btn btn-primary" onclick="window.location.href = '<%= request.getContextPath() %>/cartdetail'">Back To Cart</button>
                    </section>
                    <% } else { %>
                    <section>
                        <h3 class="text-center pb-3" style="color: red; font-weight: bold">Order placement failed</h3>
                        <button style="margin-top:30px" type="button" class="btn btn-primary" onclick="window.location.href = '<%= request.getContextPath() %>/home'">Back To Homepage</button>
                    </section>
                    <% } %>

                    <section class="mt-5">
                        <div class="row">
                            <h3 class="text-center pb-3" style="color: #FF9A00;font-weight: bold">Hot Posts</h3>
                            <c:forEach var="blog" items="${blogList}">
                                <div class="col-sm-4 blog-wrap">
                                    <a href="blogdetail?blogID=${blog.blogId}" style="text-decoration: none">
                                        <div class="blog-content-wrapper">
                                            <c:forEach var="image" items="${blog.images}">
                                                <img src="${image.imageUrl}" alt="${blog.title}" class="img-fluid" style="max-height: 200px; object-fit: cover; width: 100%; border-radius: 8px;"/>
                                            </c:forEach>
                                            <h4>${blog.title}</h4>
                                            <p>${fn:substring(blog.detail, 0, 100)}...</p>
                                            <small>Updated on: ${blog.updateDate}</small>
                                        </div>
                                    </a>
                                </div>
                            </c:forEach>
                        </div>
                    </section>
                </div>
            </form>

            <footer class="footer">
                <p>&copy; VNPAY 2020</p>
            </footer>
        </div>  
    </body>
</html>
