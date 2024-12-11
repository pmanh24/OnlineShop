<%-- 
    Document   : orderInfo
    Created on : Oct 4, 2024, 9:41:21 PM
    Author     : GIGABYTE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Order Details</title>
        <script src="https://kit.fontawesome.com/c203902cd2.js" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/assets/css/sb-admin-2.min.css" rel="stylesheet">
        <!-- Font Awesome CDN -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
        <!--<link href="Resource/css/bootstrap.min.css" rel="stylesheet">-->
        <link href="Resource/css/main.css" rel="stylesheet">
        <!-- Bootstrap CDN -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" 
              integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" 
              crossorigin="anonymous">
        <script src="https://kit.fontawesome.com/332a215f17.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" 
                integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" 
        crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <style>
            .home{
                padding-top: 2%;
            }

            body {
                font-family: Arial, sans-serif;
                background-color: #f9f9f9;
                margin: 0;
                padding: 0;
                overflow-x: hidden;
            }

            .content {
                margin-left: 220px; /* Offset the sidebar width */
                padding: 20px;
            }

            .thumbnail img {
                width: 50px;
                height: 50px;
            }

            .receiver-info, .items-table {
                padding: 15px;
                background-color: #f9f9f9;
            }

            .receiver-info {
                border: 1px solid #ccc;
                border-radius: 5px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            .items-table {
                margin-top: 20px;
            }

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
        </style>
    </head>
    <body id="page-top">  
        <div id="wrapper">
            <!-- Sidebar -->
            <ul class="navbar-nav bg-gradient-info sidebar sidebar-dark accordion">
                <a class="sidebar-brand d-flex align-items-center justify-content-center" href="home">
                    <div class="sidebar-brand-icon rotate-n-15">
                        <i class="fas fa-laugh-wink"></i>
                    </div>
                    <div class="sidebar-brand-text mx-3">Sale</div>
                </a>
                <hr class="sidebar-divider my-0">
                <hr class="sidebar-divider">
                <div class="sidebar-heading">MANAGEMENT</div>
                <li class="nav-item">
                    <a class="nav-link collapsed" href="order-list">
                        <i class="fas fa-user fa-sm fa-fw"></i>
                        <span>Order List</span>
                    </a>
                </li> 
            </ul>

            <div class="navbar1 bg-gradient-info">
                <a href="logout">Logout</a>
            </div>

            <div class="content">
                <div class="container-fluid">
                    <!-- Order Details Table -->
                    <div class="order-info mb-4">
                        <h4>Order Details</h4>
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>Order ID</th>
                                    <th>Customer Name</th>
                                    <th>Email</th>
                                    <th>Mobile</th>
                                    <th>Order Date</th>
                                    <th>Total Cost</th>
                                    <th>Sale Name</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>${ord.orderId}</td>
                                    <td><%= request.getParameter("customerName") %></td>
                                    <td><%= request.getParameter("email") %></td>
                                    <td><%= request.getParameter("mobile") %></td>
                                    <td>${ord.orderDate}</td>
                                    <td>${ord.totalCost}</td>
                                    <td><%= request.getParameter("saleName") %></td>
                                    <td>${ord.status}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <div class="row">
                        <!-- Receiver Info -->
                        <div class="col-md-3 receiver-info">
                            <h4>Receiver Info</h4>
                            <p><strong>Full Name:</strong> <%= request.getParameter("receiverName") %></p>
                            <p><strong>Gender:</strong> <%= request.getParameter("gender") %></p>
                            <p><strong>Email:</strong> <%= request.getParameter("receiverEmail") %></p>
                            <p><strong>Mobile:</strong> <%= request.getParameter("receiverMobile") %></p>
                            <p><strong>Address:</strong> <%= request.getParameter("receiverAddress") %></p>
                        </div>

                        <!-- Order Items -->
                        <div class="col-md-9 items-table">
                            <h4>Order Items</h4>
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th>Number</th>
                                        <th>Thumbnail</th>
                                        <th>Name</th>
                                        <th>Category</th>
                                        <th>Price</th>
                                        <th>Quantity</th>
                                        <th>Total Cost</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>1</td>
                                        <td class="thumbnail">
                                            <img src="<%= request.getParameter("itemThumbnail") %>" alt="Item Image">
                                        </td>
                                        <td><%= request.getParameter("itemName") %></td>
                                        <td><%= request.getParameter("itemCategory") %></td>
                                        <td><%= request.getParameter("itemPrice") %></td>
                                        <td><%= request.getParameter("itemQuantity") %></td>
                                        <td><%= request.getParameter("itemTotalCost") %></td>
                                    </tr>

                                </tbody>
                            </table>
                            <c:if test="${order.status == 'submitted'}">
                                <button type="button" onclick="location.href = 'UpdateOrderServlet?orderId=${order.id}'">Update</button>
                                <button type="button" onclick="location.href = 'CancelOrderServlet?orderId=${order.id}'">Cancel</button>
                            </c:if>
                        </div>
                    </div>
                </div>  
            </div>
        </div>
        <!-- Footer -->
        <footer class="sticky-footer bg-white" style="z-index: 1000">
            <div class="container my-auto">
                <div class="copyright text-center my-auto">
                    <span>Copyright &copy; Online Shop Website 2024</span>
                </div>
            </div>
        </footer>
    </body>
</html>