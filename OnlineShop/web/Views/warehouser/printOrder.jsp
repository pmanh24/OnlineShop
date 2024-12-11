<%-- 
    Document   : printOrder
    Created on : Oct 14, 2024, 11:58:28 AM
    Author     : Hieu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Print Order</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
                padding: 0;
                background-color: #f9f9f9;
                color: #333;
            }
            h1 {
                text-align: center;
                color: #007BFF;
                margin-bottom: 20px;
            }
            .order-details {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
                background-color: #fff;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }
            .order-details th, .order-details td {
                border: 1px solid #ccc;
                padding: 12px;
                text-align: left;
            }
            .order-details th {
                background-color: #f2f2f2;
                font-weight: bold;
            }
            .order-details td {
                font-size: 14px;
            }
            .footer {
                text-align: center;
                margin-top: 30px;
                font-size: 12px;
                color: #666;
            }
            @media print {
                @page {
                    size: auto;
                    margin: 0;
                }
                body {
                    margin: 20mm;
                }
            }
        </style>
    </head>
    <body>
        <h1>Order Details</h1>
        <c:set var="order" value="${sessionScope.order}" />
        <c:choose>
            <c:when test="${not empty order}">
                <table class="order-details">
                    <tr>
                        <th>Order ID</th>
                        <td>${order.orderId}</td>
                    </tr>
                    <tr>
                        <th>Customer Name</th>
                        <td>${order.customer.fullName}</td>
                    </tr>
                    <tr>
                        <th>Order Date</th>
                        <td><fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd" /></td>
                    </tr>
                    <tr>
                        <th>Total Amount</th>
                        <td><fmt:formatNumber value="${order.totalCost}" pattern="#,###"/> VND</td>
                    </tr>
                    <tr>
                        <th>Address</th>
                        <td>${order.address}</td>
                    </tr>
                    <tr>
                        <th>Phone Number</th>
                        <td>${order.phoneNumber}</td>
                    </tr>
                    <tr>
                        <th>Products</th>
                        <td>
                            <c:forEach items="${order.orderDetails}" var="orderDetail">
                                ${order.productNames[orderDetail.productId]} (Quantity: ${orderDetail.quantity})
                                <c:if test="${not empty orderDetail.productSize}">
                                    <br/>Size: ${orderDetail.productSize.productSizeName}
                                </c:if>
                                <br/>
                            </c:forEach>
                        </td>
                    </tr>
                </table>

                <c:set var="currentDate" value="<%= new SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\").format(new Date()) %>" />
                <div class="footer">
                    <p>Print Time: ${currentDate}</p>
                </div>
            </c:when>
            <c:otherwise>
                <p>No order found.</p>
            </c:otherwise>
        </c:choose>

        <script>
            window.onload = function () {
                window.print();
            }
        </script>
    </body>
</html>
