<%-- 
    Document   : add-product
    Created on : Sep 30, 2024, 2:23:02 PM
    Author     : Hieu
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sale Order Detail</title>
        <script src="https://kit.fontawesome.com/c203902cd2.js" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/assets/css/sb-admin-2.min.css" rel="stylesheet">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

        <style>
            .navbar1 {
                background-color: #808080; /* Change background color to grey */
                padding: 10px; /* Padding */
                position: fixed; /* Fixed position for navbar */
                top: 0; /* Stick to the top */
                left: 0; /* Align to the left */
                right: 0; /* Align to the right */
                z-index: 1000; /* Ensure it stays on top */
                display: flex; /* Use Flexbox */
                justify-content: flex-end; /* Align links to the right */
            }
            .navbar1 a {
                color: white; /* Text color */
                margin: 0 15px; /* Space between links */
                text-decoration: none; /* Remove underline */
            }
            .navbar1 a:hover {
                text-decoration: underline; /* Underline on hover */
            }
            /* Adjust the content margin to account for the fixed navbar */
            .content {
                margin-top: 60px; /* Add space for the navbar */
            }
            /* Styles for image previews */
            .image-preview {
                display: flex;
                flex-wrap: wrap;
                margin-top: 10px;
            }
            .image-preview img {
                width: 100px; /* Adjust as needed */
                height: 100px; /* Adjust as needed */
                margin-right: 10px;
                margin-bottom: 10px;
            }
            .sidebar a {
                color: white;
                text-decoration: none;
                display: block;
                padding: 15px;
            }

            .sidebar a:hover {
                background-color: #258391;
            }

            h1 {
                text-align: center;
            }



            .order-info, .receiver-info, .products-list {
                background-color: #ffffff;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                margin-bottom: 20px;
            }
            .products-list table {
                width: 100%;
            }
            .products-list img {
                max-width: 50px;
                max-height: 50px;
            }
            .total-cost {
                font-weight: bold;
                font-size: 1.2em;
                text-align: right;
            }
            .status-edit {
                margin-top: 10px;
            }

        </style>
        <script type="text/javascript">
            // Function to show the message box
            function showMessage() {
                var message = '<%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %>';
                if (message) {
                    alert(message);
                }
            }
        </script>

    </head>
    <body id="page-top" onload="showMessage()">
        <div id="wrapper">
            <!-- Sidebar -->
            <ul class="navbar-nav bg-gradient-info sidebar sidebar-dark accordion">
                <a class="sidebar-brand d-flex align-items-center justify-content-center" href="home">
                    <div class="sidebar-brand-icon rotate-n-15">
                        <i class="fas fa-laugh-wink"></i>
                    </div>
                    <div class="sidebar-brand-text mx-3">Sale Manager</div>
                </a>
                <hr class="sidebar-divider my-0">
                <li class="nav-item active">
                    <a class="nav-link" href="sale-dashboard-controller">
                        <i class="fas fa-fw fa-tachometer-alt"></i>
                        <span>Dashboard</span>
                    </a>
                </li>
                <hr class="sidebar-divider">
                <div class="sidebar-heading">MANAGEMENT</div>
                <li class="nav-item">
                    <a class="nav-link collapsed" href="sale-order-list">
                        <i class="fas fa-user fa-sm fa-fw"></i>
                        <span>Danh sách đơn hàng</span>
                    </a>
                </li>

            </ul>
            <!-- End of Sidebar -->

            <!-- Header Section -->
            <div class="navbar1 bg-gradient-info">
                <a href="logout">Logout</a>
            </div>


            <!-- Main Content -->
            <div class="content">
                <h1>Order Detail</h1>

                <div class="order-info">
                    <form action="sale-manager-order-detail" method="post">
                        <h3>Basic Order Information</h3>
                        <input type="hidden" name="orderId" value="${order.getOrderId()}" >
                        <p><strong>Order ID:</strong> ${order.getOrderId()}</p>
                        <p><strong>Customer Full Name:</strong> ${order.getCustomer().getFullName()}</p>
                        <p><strong>Email:</strong> ${order.getCustomer().getEmail()}</p>
                        <p><strong>Mobile:</strong> ${order.getCustomer().getPhoneNumber()}</p>
                        <p><strong>Order Date:</strong> ${order.getOrderDate()}</p>
                        <p><strong>Total Cost:</strong> ${order.getTotalCost()} VND</p>
                        <p><strong>Sale Name:</strong> 
                            <select id="saleNameSelect"  style="width: auto;" name="sale">
                                <c:forEach items="${employee}" var="e">
                                    <option value="${e.getEmployeeId()}" ${e.getEmployeeId()==order.getEmployee().getEmployeeId()?"selected":""}>${e.getFullName()} has ${e.getOrderCount()} orders</option>
                                </c:forEach>

                            </select>

                        </p>

                        <div class="status-edit">
                            <strong>Status:</strong>
                             <c:choose>
                                <c:when test="${isEdit == '0'}">
                                    <span> ${currentStatus}</span> 
                                </c:when>
                                <c:otherwise>
                                    <select id="statusSelect" name="status" >
                                        <c:forEach items="${statusList}" var="s">
                                             <c:if test="${fn:contains(isEdit, s.getStatusId())}">
                                                <option value="${s.getStatusId()}" ${order.getStatus() == s.getStatusId() ? "selected" : ""}>
                                                    ${s.getStatusName()}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </c:otherwise>
                            </c:choose>                            
                        </div>
                            <p></p>
                        <button class="btn btn-primary btn-sm" onclick="this.form.submit()">Update</button>
                    </form>

                </div>

                <div class="receiver-info">
                    <h3>Receiver Information</h3>
                    <p><strong>Full Name:</strong> ${order.getCustomer().getFullName()}</p>
                    <p><strong>Email:</strong> ${order.getCustomer().getEmail()}</p>
                    <p><strong>Mobile:</strong> ${order.getPhoneNumber()}</p>
                    <p><strong>Address:</strong> ${order.getAddress()}</p>
                </div>

                <div class="products-list">
                    <h3>Ordered Products</h3>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Thumbnail</th>
                                <th>Name</th>
                                <th>Category</th>
                                <th>Size</th>
                                <th>Color</th>
                                <th>Unit Price (VND)</th>
                                <th>Quantity </th>
                                <th>Total Cost</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${detail}" var="d">
                                <tr>
                                    <td><img src="${d.getProduct().getImageUrl()}" ></td>
                                    <td>${d.getProduct().getProductName()}</td>
                                    <td>${d.getProduct().getCategory().getName()}</td>
                                    <td>${d.getProductSize().getProductSizeName()}</td>
                                    <td>${d.getProductSize().getProductColor()}</td>
                                    <td>${d.getProductSize().getPrices()}</td>
                                    <td>${d.getQuantity()}</td>
                                    <td>${d.getProductSize().getPrices() * d.getQuantity()}</td>
                                </tr>
                            </c:forEach>                          
                            <!-- Add more products as needed -->
                        </tbody>
                    </table>
                    <p class="total-cost">Total Cost: ${order.getTotalCost()} VND</p>
                </div>

            </div>

        </div>
        <!-- End of Main Content -->

        <!-- Footer -->
        <footer class="sticky-footer bg-white">
            <div class="container my-auto">
                <div class="copyright text-center my-auto">
                    <span>Copyright &copy; Online Shop Website 2024</span>
                </div>
            </div>
        </footer>
        <!-- End of Footer -->

    </body>
</html>
