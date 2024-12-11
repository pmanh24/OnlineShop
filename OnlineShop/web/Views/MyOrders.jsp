<%-- 
    Document   : MyOrders
    Created on : Sep 28, 2024, 10:13:06 PM
    Author     : Admin
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>My Orders</title>
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
                padding-top: 2%;
            }
            /* Option 2: Use a CSS class */
            .carousel-item img {
                width: 100%;  /* Set width to 100% to be responsive */
                height: 300px; /* Fixed height */
                object-fit: cover; /* Maintain aspect ratio and cover the space */
            }
            body {
                font-family: Arial, sans-serif;
                background-color: #f9f9f9;
                margin: 0;
                padding: 0;
                overflow-x: hidden;
            }

            /* Slider Styles */
            #slider {
                margin-bottom: 30px;
            }
            .carousel-caption h5 {
                background-color: rgba(0, 0, 0, 0.1); /* Semi-transparent background */
                color: #fff; /* White text color */
                padding: 10px 0; /* Padding only on top and bottom */
                border-radius: 5px; /* Rounded corners */
            }


            /* Product Grid Styles */

            .product-image-wrapper {
                border: 1px solid #FF9A00;
                background-color: #fff;
                margin-bottom: 30px;
                border-radius: 8px;
                overflow: hidden;
                transition: box-shadow 0.3s ease;
                text-align: center;
            }

            .product-image-wrapper:hover {
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            }
            .blog-content-wrapper:hover{
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            }
            .single-products {
                padding: 15px;
                position: relative;
                overflow: hidden;
            }

            .single-products img {
                width: 100%;
                max-width: 200px; /* Ensure product images are not too small */
                height: 200px; /* Fixed height for product images */
                object-fit: cover; /* Ensure images cover the designated area proportionally */
                transition: transform 0.3s ease;
            }

            .single-products img:hover {
                transform: scale(1.1);
            }

            .productinfo {
                text-align: center;
                margin-top: 10px;
            }

            .productinfo h2 {
                font-size: 20px;
                color: #333;
            }
            .productinfo a {
                text-decoration: none; /* Ensure no underline for all anchor tags */
                color: #333; /* Default color for the product name */
            }
            .productinfo p {
                color: #777;
            }

            .productinfo .add-to-cart {
                display: inline-block;
                margin-top: 10px;
                background-color: #ff6f61;
                color: #fff;
                padding: 8px 20px;
                border-radius: 5px;
                transition: background-color 0.3s ease;
            }

            .productinfo a:hover {
                background-color: #ff3d29;
            }
            .blog-wrap {
                padding: 15px;
            }

            .blog-content-wrapper {
                border: 1px solid #ddd;
                background-color: #fff;
                margin-bottom: 30px;
                padding: 15px;
                border-radius: 8px;
                text-align: center;
            }
            .blog-wrap {
                padding: 15px;
            }

            .blog-content-wrapper {
                border: 1px solid #ddd;
                background-color: #fff;
                margin-bottom: 30px;
                padding: 15px;
                border-radius: 8px;
                text-align: center;
            }

            .blog-content-wrapper h4 {
                margin-top: 10px;
                font-size: 20px;
                color: #333;
            }

            .blog-content-wrapper p {
                color: #777;
                font-size: 14px;
            }

            .blog-content-wrapper small {
                display: block;
                margin-top: 10px;
                color: #999;
                font-size: 12px;
            }

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
                            <li class="nav-item"><a href="/OnlineShop/" class="nav-link">Contact</a></li>
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

        <div class="container home">
            <div class="row">
                <section id="slider">
                    <div class="container">
                        <div class="row">
                            <div class="col-sm-12">
                                <div id="slider-carousel" class="carousel slide" data-bs-ride="carousel">
                                    <!-- Carousel indicators -->
                                    <ol class="carousel-indicators">
                                        <c:forEach var="s" items="${slideList}" varStatus="status">
                                            <li data-bs-target="#slider-carousel" data-bs-slide-to="${status.index}" class="${status.index == 0 ? 'active' : ''}">
                                                <span class="indicator-title">${s.title}</span>
                                            </li>
                                        </c:forEach>
                                    </ol>


                                    <!-- Carousel items -->
                                    <div class="carousel-inner">
                                        <c:forEach var="s" items="${slideList}" varStatus="status">
                                            <div class="carousel-item ${status.index == 0 ? 'active' : ''}">
                                                <a href="${s.backLink}">
                                                    <img src="${s.imageUrl}" class="d-block w-100" alt="${s.sliderId}">
                                                    <div class="carousel-caption d-none d-md-block">
                                                        <h5>${s.title}</h5> <!-- Title Display -->
                                                    </div>
                                                </a>
                                            </div>
                                        </c:forEach>
                                    </div>


                                    <a href="#slider-carousel" class="carousel-control-prev" role="button" data-bs-slide="prev">
                                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                        <span class="visually-hidden">Previous</span>
                                    </a>
                                    <a href="#slider-carousel" class="carousel-control-next" role="button" data-bs-slide="next">
                                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                        <span class="visually-hidden">Next</span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <!--                <section class="mt-5 ml-2 col-12">
                                    <div id="search-box">
                                        <form action="myorder" method="post">
                                            <input type="text" name="key" placeholder="Search orders"/>
                                            <button>
                                                <i class="fas fa-search"></i>
                                            </button>
                                        </form>
                                    </div>
                                </section>-->
                <section class="mt-2 col-12">
                    <c:forEach items="${orders}" var="o" >
                        <div class="container mt-4 col-11">
                            <div class="card shadow">
                                <div class="row card-body">
                                    <div class="col-lg-8 col-md-6">
                                        <h5 class="card-title"><strong>Order ID:</strong> ${o.order.orderId}</h5>
                                        <p class="card-text"><strong>Product: ${o.firstProduct.productName}</strong>
                                            <c:if test="${o.number >= 1}">
                                                and ${o.number} products more
                                            </c:if>
                                        </p>
                                        <p class="card-text"><strong>Price:</strong> ${o.order.totalCost} VND</p>
                                        <p class="card-text"><strong>Date:</strong> ${o.order.orderDate}</p>
                                        <p class="card-text">
                                            <strong>Status:</strong>
                                            ${o.order.statusName}
                                        </p>
                                    </div>
                                    <div class="col-lg-4 col-md-6 d-flex flex-column align-items-center justify-content-center">
                                        <a href="orderdetail?orderId=${o.order.orderId}" class="btn btn-primary mb-2">
                                            View Order Detail
                                        </a>
                                        <!-- Conditionally show the feedback link if status is 6 -->
                                       
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </section>


                <ul class="pagination mt-5">
                    <c:set var="page" value="${requestScope.page}"/>
                    <c:set var="totalPages" value="${requestScope.numpage}"/>
                    <c:set var="path" value="${requestScope.path}"/>

                    <!--  First -->
                    <c:if test="${page > 1}">
                        <li class="page-item">
                            <a class="page-link" href="orderListUpdate?page=1&${path}">First</a>
                        </li>
                    </c:if>

                    <!--  Previous -->
                    <c:if test="${page > 1}">
                        <li class="page-item">
                            <a class="page-link" href="orderListUpdate?page=${page - 1}&${path}">Previous</a>
                        </li>
                    </c:if>

                    <!-- "..."  -->
                    <c:if test="${page > 3}">
                        <li class=" disabled"><a class="page-link">...</a></li>
                        </c:if>


                    <c:forEach begin="${page > 2 ? page - 2 : 1}" 
                               end="${page + 2 <= totalPages ? page + 2 : totalPages}" 
                               var="i">
                        <li class=" ${i == page ? 'active' : ''}">
                            <a class="page-link" href="orderListUpdate?page=${i}&${path}">${i}</a>
                        </li>
                    </c:forEach>

                    <c:if test="${page < totalPages - 2}">
                        <li class="disabled"><a class="page-link">...</a></li>
                        </c:if>

                    <!-- Next -->
                    <c:if test="${page < totalPages}">
                        <li class="page-item">
                            <a class="page-link" href="orderListUpdate?page=${page + 1}&${path}">Next</a>
                        </li>
                    </c:if>

                    <!-- End -->
                    <c:if test="${page < totalPages}">
                        <li class="page-item">
                            <a class="page-link" href="orderListUpdate?page=${totalPages}&${path}">End</a>
                        </li>
                    </c:if>
                </ul>
                <!----->

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
    <script>
        $(document).ready(function () {
            // Handle user name display
            var userNameElement = document.getElementById("user-name");
            if (userNameElement) {
                var fullName = userNameElement.textContent;
                var names = fullName.split(" ");
                if (names.length >= 2) {
                    userNameElement.textContent = names[1]; // Display the second name
                }
            }

            // Toggle subnav
            var userButton = document.getElementById("user-logo");
            var subnav = document.getElementById("userDropdown");

            if (userButton && subnav) {
                userButton.addEventListener('mouseenter', function () {
                    subnav.classList.add('show-dropdown');
                });

                userButton.addEventListener('mouseleave', function () {
                    subnav.classList.remove('show-dropdown');
                });
            }
        });
    </script>
    <script src="Resource/js/jquery.js"></script>
    <script src="Resource/js/price-range.js"></script>
    <script src="Resource/js/jquery.scrollUp.min.js"></script>
    <script src="Resource/js/bootstrap.min.js"></script>
    <script src="Resource/js/jquery.prettyPhoto.js"></script>
    <script src="Resource/js/main.js"></script>

</html>

