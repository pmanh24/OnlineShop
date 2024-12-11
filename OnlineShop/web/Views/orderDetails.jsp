<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Order Infomation</title>
        <!-- Font Awesome CDN -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
        <link rel="stylesheet" href="Resource/css/menu.css"/>
        <link rel="stylesheet" href="Resource/css/cart.css"/> 
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
                padding: 20px;
            }
            .thumbnail img {
                width: 50px;
                height: 50px;
            }
            body {
                font-family: Arial, sans-serif;
                background-color: #f9f9f9;
                margin: 0;
                padding: 0;
                overflow-x: hidden;
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
            .btn-custom {
                display: inline-block;
                padding: 7px 17px;
                font-size: 16px;
                font-weight: bold;
                color: #fff;
                background-color: #007bff;
                border: none;
                border-radius: 5px;
                text-align: center;
                text-decoration: none;
                transition: background-color 0.3s ease;
            }

            .btn-custom:hover {
                background-color: #0056b3;
                color: #fff;
            }

            /* CSS để tạo modal */
            .modal {
                display: none; /* Ẩn modal mặc định */
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.5); /* Màu nền tối mờ */
                justify-content: center;
                align-items: center;
            }
            .modal-content {
                background-color: #fff;
                padding: 20px;
                border-radius: 5px;
                width: 300px;
                text-align: center;
            }
            .modal button {
                margin: 10px;

            </style>
        </head>
        <body>
            <header id="header">
                <!-- Header Container -->
                <div id="header-container">
                    <!-- Logo -->
                    <div id="logo">
                        <a href="/OnlineShop/home">
                            <img src="Resource/images/logo.png" alt="Logo">
                        </a>
                    </div>
                    <!-- Navigation Menu -->
                    <nav class="navbar navbar-expand-lg" id="navbar">
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarNav">
                            <ul class="navbar-nav">
                                <li class="nav-item"><a href="/OnlineShop/home" class="nav-link">Home</a></li>
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="shopDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">Category</a>
                                    <ul class="dropdown-menu" aria-labelledby="shopDropdown">
                                        <li><a href="/OnlineShop/productListUpdate" class="dropdown-item">All</a></li>
                                            <c:forEach var="cat" items="${catList}">
                                            <li><a href="/OnlineShop/productListUpdate?cidd=${cat.categoryId}" class="dropdown-item">${cat.name}</a></li>
                                            </c:forEach>
                                    </ul>
                                </li>
                                <li class="nav-item">
                                    <a href="blog" class="nav-link"> Blog</a>
                                </li>
                                <li class="nav-item"><a href="/OnlineShop/contact" class="nav-link">Contact</a></li>
                            </ul>
                        </div>
                    </nav>
                    <!-- Search Box and Icons -->
                    <div id="nav-icons">
                        <div id="search-box">
                            <form action="productListUpdate" method="get">
                                <input type="text" name="key" placeholder="Search"/>
                                <button>
                                    <i class="fas fa-search"></i>
                                </button>
                            </form>
                        </div>
                        <div id="shop-menu">
                            <ul class="nav">
                                <li class="nav-item"><a href="/OnlineShop/cartdetail" class="nav-link"><i class="fa fa-shopping-cart"></i></a></li>
                                        <c:if test="${customer != null}">
                                    <li class="nav-item">
                                        <img id="user-logo" src="Resource/images/login.png" width="60px" height="60px" alt="User Avatar" class="avatar user-button"/>
                                        <ul class="subnav" id="userDropdown">
                                            <li><a href="/OnlineShop/userprofile" class="dropdown-item">Profile</a></li>
                                            <li><a href="/OnlineShop/changePassword" class="dropdown-item">Change Password</a></li>
                                            <li><a href="myorder" class="dropdown-item">My Orders</a></li>
                                            <li><a href="/OnlineShop/logout" class="dropdown-item">Logout</a></li>
                                        </ul>
                                    </li>
                                </c:if>
                                <c:if test="${customer == null}">
                                    <li class="nav-item">
                                        <a href="/OnlineShop/login" class="nav-link"><i class="fa fa-user"></i></a>
                                    </li>
                                </c:if>
                            </ul>
                        </div>
                    </div>
                </div>
            </header>

            <div class="container">
                <!-- Order Details Table -->
                <div class="order-info mb-4">
                    <h4>Order Details</h4>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Customer Name</th>
                                <th>Email</th>
                                <th>Mobile</th>
                                <th>Order Date</th>
                                <th>Total Cost</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>${customer.fullname}</td>
                                <td>${customer.email}</td>
                                <td>${order.phoneNumber}</td>
                                <td>${order.orderDate}</td>
                                <td>${order.totalCost}</td>
                                <td>
                                    ${order.status_name}
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <table border="0">
                        <thead>
                            <tr>
                                <th><c:if test="${order.status_name == 'Submitted'}">
                                        <div id="confirmationModal" class="modal">
                                            <div class="modal-content">
                                                <p>Are you sure you want to cancel this order?</p>
                                                <!-- Form xác nhận hủy -->
                                                <form action="orderdetail" method="post" style="display: inline;">
                                                    <input type="hidden" name="orderId" value="${order.orderId}" />
                                                    <button type="submit" class="btn btn-danger">Yes, Cancel Order</button>
                                                </form>
                                                <!-- Nút thoát modal -->
                                                <button onclick="document.getElementById('confirmationModal').style.display = 'none'" class="btn btn-secondary">No, Go Back</button>
                                            </div>
                                        </div>

                                        <!-- Nút Cancel Order -->
                                        <button onclick="document.getElementById('confirmationModal').style.display = 'flex'" class="btn btn-danger">Cancel Order</button>
                                    </c:if></th>
                                <th><a href="myorder" class="btn btn-custom">Back to my order</a></th>
                            </tr>
                        </thead>
                    </table>



                </div>

                <div class="row">
                    <!-- Receiver Info -->
                    <div class="col-md-3 receiver-info">
                        <h4>Receiver Info</h4>
                        <p><strong>Full Name:</strong> ${customer.fullname}</p>
                        <p><strong>Gender:</strong> ${customer.gender == 1 ? 'Male' : 'Female'}</p>
                        <p><strong>Email:</strong> ${customer.email}</p>
                        <p><strong>Mobile:</strong> ${customer.numberphone}</p>
                        <p><strong>Address:</strong> ${customer.address}</p>
                    </div>

                    <!-- Order Items -->
                    <div class="col-md-9 items-table">
                        <h4>Order Items</h4>
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>Number</th>
                                    <th>Name</th>
                                    <th>Category</th>
                                    <th>Size, Color</th>
                                    <th>Quantity</th>
                                    <th>Total Cost</th>
                                    <th>Feedback</th>
                                    <th style="display:none;">Order Detail ID</th> <!-- Ẩn cột orderDetailId -->
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="o" items="${orderL}" varStatus="status">
                                    <tr>
                                        <td>${status.index + 1}</td> <!-- Hiển thị số thứ tự -->
                                        <td>${o.product_name}</td>
                                        <td>${o.category_name}</td>
                                        <td>${o.product_size_name}</td>
                                        <td>${o.quantity}</td>
                                        <td>${o.total_cost}</td>
                                        <td style="display:none;">
                                            <input type="hidden" name="orderDetailId" value="${o.orderDetailId}" />
                                        </td>
                                        <td>
                                            <c:if test="${order.status == 6}">
                                                <a href="cus-feedback?orderId=${order.orderId}&productId=${o.productId}" class="btn btn-secondary">
                                                    Leave Feedback
                                                </a>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                </div>
            </div>    

            <footer class="bg-dark text-light py-4 mt-5">
                <div class="container">
                    <div class="row">
                        <div class="col-md-4">
                            <h5>About Us</h5>
                            <p>Your go-to online shop for the latest fashion trends and styles. We provide high-quality clothing for all occasions.</p>
                        </div>
                        <div class="col-md-4">
                            <h5>Customer Service</h5>
                            <ul class="list-unstyled">
                                <li><a href="#" class="text-light" style="text-decoration: none">Contact Us</a></li>
                                    <li><a href="#" class="text-light" style="text-decoration: none">Returns & Exchanges</a></li>
                                    <li><a href="#" class="text-light" style="text-decoration: none">Shipping Information</a></li>
                                    <li><a href="#" class="text-light" style="text-decoration: none">FAQs</a></li>
                                </ul>
                            </div>
                            <div class="col-md-4">
                                <h5>Follow Us</h5>
                                <ul class="list-inline">
                                    <li class="list-inline-item"><a href="#" style="text-decoration: none" class="text-light"><i class="fab fa-facebook-f"></i></a></li>
                                    <li class="list-inline-item"><a href="#" class="text-light"><i class="fab fa-twitter"></i></a></li>
                                    <li class="list-inline-item"><a href="#" class="text-light"><i class="fab fa-instagram"></i></a></li>
                                    <li class="list-inline-item"><a href="#" class="text-light"><i class="fab fa-pinterest"></i></a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="text-center mt-4">
                            <p class="mb-0">&copy; 2024 Your Online Shop. All Rights Reserved.</p>
                        </div>
                    </div>
                </footer>                        
            </body>
        </html>