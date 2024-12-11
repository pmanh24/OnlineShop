<%-- 
    Document   : CartContact
    Created on : Sep 28, 2024, 10:11:46 PM
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
        <title>Cart Contact</title>
        <!-- Font Awesome CDN -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
        <link rel="stylesheet" href="Resource/css/menu.css"/>
        <link rel="stylesheet" href="Resource/css/cart.css"/>    
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
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
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
            .section-title {

                font-size: 1.25rem; /* Kích thước nhỏ hơn, vừa phải */
                font-weight: 700; /* Độ đậm vừa, không quá nặng */
                color: #343a40; /* Sử dụng màu xám đậm, hài hòa với form */
                letter-spacing: 0.5px; /* Khoảng cách giữa các chữ vừa đủ */
                padding-bottom: 5px; /* Khoảng cách giữa text và nội dung bên dưới */
                border-bottom: 1px solid #ddd; /* Đường viền dưới rất mỏng, tinh tế */
            }

            .form-check {
                margin-bottom: 10px; /* Tạo khoảng cách giữa các radio button */
            }

            .form-check-label {
                margin-left: 10px; /* Tạo khoảng cách giữa label và radio button */
                font-weight: 500; /* Tạo độ đậm nhẹ cho label */
            }

            .form-check-input {
                accent-color: orange; /* Đồng bộ màu với nút "Pay" */
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
        <!-- Carousel items -->
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
                <h3 class="text-center pb-3" style="color: #FF9A00;font-weight: bold">Cart Contact</h3>
                <section class="mt-5 col-7">
                    <div class="container">
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col" class="h4">Product</th>
                                        <th scope="col">Unit Price</th>
                                        <th scope="col">Quantity</th>
                                        <th scope="col">Total</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${itemList}">
                                        <tr>
                                            <th scope="row">
                                                <div class="d-flex align-items-center">
                                                    <img src="${item.productSize.product.imageUrl}" class="img-fluid rounded-3" style="width: 120px;" alt="Book">
                                                    <div class="flex-column ms-4">
                                                        <p class="mb-2" style="font-weight: bold;">${item.productSize.product.productName}</p>
                                                        <p class="mb-2" style="font-weight: normal;">Size: ${item.productSize.productSizeName}</p>
                                                        <p class="mb-2" style="font-weight: normal;">Color: ${item.productSize.productColor}</p>
                                                    </div>
                                                </div>
                                            </th>
                                            <td class="align-middle">
                                                <p class="mb-0" style="font-weight: 500;">${item.productSize.prices} VND</p>
                                            </td>
                                            <td class="align-middle">
                                                <div class="d-flex flex-row">
                                                    <input id="form1" min="0" name="quantity" value="${item.quantity}" type="number" readonly=""
                                                           class="form-control form-control-sm" style="width: 50px;" />
                                                </div>
                                            </td>
                                            <td class="align-middle">
                                                <p class="mb-0" style="font-weight: 500;">${item.productSize.prices * item.quantity} VND</p>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <!-- Phần Total bên dưới danh sách sản phẩm -->
                        <div class="d-flex flex-column align-items-end mt-4 p-3 border-top" style="background-color: #f8f9fa; border-radius: 0.5rem;">
                            <div class="d-flex justify-content-between align-items-center w-100 mb-2">
                                <h5 class="mb-0 fw-bold">Total:</h5>
                                <h5 class="mb-0 text-success fw-bold" style="font-size: 1.5rem; margin-left: 10px;">${total} VND</h5>

                            </div>
                            <div class="d-flex justify-content-end w-100">
                                <div class="checkout">
                                    <a href="cartdetail" class="btn btn-warning" style="background-color: #ff8306; font-weight: bold; padding: 0.5rem 1rem;">Change</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>


                <div class="mt-5 col-5">
                    <div class="checkout p-4 border rounded shadow-sm bg-light">
                        <h5 class="section-title text-center mb-4">Contact Information</h5>
                        <form action="cartcontact" method="post">
                            <div class="row">
                                <div class="col-7 form-floating mb-3">
                                    <input type="text" id="name" value="${requestScope.fullname}" name="name" class="form-control" required>
                                    <label for="name" style="margin-left: 10px;">Full Name</label>

                                </div>
                                <!-- Gender -->
                                <div class="col-5 form-floating mb-3">
                                    <select id="gender" name="gender" class="form-select" required>
                                        <option ${(requestScope.gender == true)?'selected':''} value="0">Male</option>
                                        <option ${(requestScope.gender == false)?'selected':''} value="1">Female</option>
                                    </select>
                                    <label for="gender" style="margin-left: 10px;">Gender</label> <!-- Thêm padding ở đây -->
                                </div>

                            </div>

                            <div class="row">
                                <div class="col-7 form-floating mb-3">
                                    <input type="email" value="${requestScope.email}" id="email" name="email" class="form-control" required>
                                    <label for="email" style="margin-left: 10px;">Email</label>
                                </div>

                                <!-- Phone Number -->
                                <div class="col-5 form-floating mb-3">
                                    <input type="tel" value="${requestScope.phone}" id="phone" name="phone" class="form-control" required>
                                    <label for="phone" style="margin-left: 10px;">Phone Number</label> <!-- Thêm padding ở đây -->
                                </div>
                            </div>



                            <c:choose>
                                <c:when test="${empty addressList}">
                                    <div class="row mb-3">
                                        <div class="col-10">
                                            <div class="form-floating">
                                                <input type="text" id="newAddress" name="newAddress1" class="form-control" required="">
                                                <label for="newAddress">Enter address</label>
                                            </div>
                                        </div>
                                        <div class="col-2 d-flex align-items-end justify-content-end">
                                            <button class="btn btn-warning rounded-circle" onclick="addAddressToDatabase()" type="button" style="width: 50px; height: 50px; padding: 0;">
                                                <i class="fas fa-plus" style="font-size: 1.5rem;"></i>
                                            </button>
                                        </div>
                                    </div>
                                </c:when>  
                                <c:otherwise>
                                    <div id="addressSelect" class="form-floating mb-3">
                                        <select id="address" name="address" class="form-select" required>
                                            <c:forEach var="a" items="${addressList}">
                                                <option value="${a.id}" <c:if test="${a.def == true}">selected</c:if>>${a.address}</option>
                                            </c:forEach>
                                        </select>
                                        <label for="address">Address</label>
                                    </div>
                                    <div id="addressOptions" style="display: none;">
                                        <div class="row mb-3" id="newAddressContainer">
                                            <div class="col-10">
                                                <div class="form-floating">
                                                    <input type="text" id="newAddress" name="newAddress" class="form-control" placeholder=" " >
                                                    <label for="newAddress">Enter new address</label>
                                                </div>
                                            </div>
                                            <div class="col-2 d-flex align-items-end justify-content-end">
                                                <button class="btn btn-warning rounded-circle" onclick="addAddressToDatabase()" type="button" style="width: 50px; height: 50px; padding: 0;">
                                                    <i class="fas fa-plus" style="font-size: 1.5rem;"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-6">
                                            <button type="button" id="oldButton" class="btn btn-secondary" onclick="selectOldAddress()" style="display: none;">Select Old Address</button>
                                            <button type="button" id="otherButton" class="btn btn-primary" onclick="showNewAddressInput()">Other Address</button>
                                        </div>
                                        <div class="form-check col-6">
                                            <input type="checkbox" name="setDefault" id="setDefaultCheckbox" class="form-check-input">
                                            <label class="form-check-label" for="setDefaultCheckbox">Set as default address</label>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <div class="row mt-3">
                                <div class="col-6 form-floating mb-3">
                                    <textarea id="note" name="note" class="form-control" style="min-height: 130px" rows="2"></textarea>
                                    <label for="note" style="margin-left: 10px;">Note</label>
                                </div>
                                <!-- Payment Method Radio Buttons -->
                                <div class="col-6 form-group mb-3">
                                    <label class="form-label d-block mb-2">Select the payment method</label>
                                    <div class="form-check mt-4">
                                        <input class="form-check-input" type="radio" id="paymentCOD" name="payment" value="COD" required>
                                        <label class="form-check-label" style="margin-top: -12%"for="paymentCOD">
                                            <img src="Resource/images/cod.png" alt="Cash on Delivery" style="height: 30%; width: 40%;">
                                        </label>
                                    </div>
                                    <!--                                    <div class="form-check">
                                                                            <input class="form-check-input" type="radio" id="paymentVNPAYQR" name="payment" value="VNPAYQR" required>
                                                                            <label class="form-check-label" for="paymentVNPAYQR">Thanh toán quét mã QR</label>
                                                                        </div>-->
<!--                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" id="paymentNCB" name="payment" value="NCB" required >
                                        <label class="form-check-label" for="paymentINTCARD" style="margin-top: -5%">
                                            <img src="https://sandbox.vnpayment.vn/paymentv2/images/img/logos/bank/big/ncb.svg"  alt="NCB" style="height: 80%; width: 50%;">
                                        </label>
                                    </div>-->
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" id="paymentINTCARD" name="payment" value="INTCARD" required>
                                        <label class="form-check-label" for="paymentINTCARD">
                                            <img src="https://sandbox.vnpayment.vn/paymentv2/images/icons/mics/176x24-credit.svg" alt="International Card" style="height: 80%; width: 110%;">
                                        </label>
                                    </div>
                                </div>
                                <input type="text" name="txtTotal" value="${total}" hidden />

                            </div>



                            <div class="d-flex justify-content-center">
                                <button type="submit" class="btn btn-warning w-50 mx-auto mt-4" style="font-size: 1rem; font-weight: bold;">
                                    <i class="bi bi-credit-card"></i> Pay
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

                <section class="mt-5">
                    <div class="row">
                        <h3 class="text-center pb-3" style="color: #FF9A00;font-weight: bold">Hot Posts</h3>
                        <!-- Loop through the blog list -->
                        <c:forEach var="blog" items="${blogList}">
                            <div class="col-sm-4 blog-wrap">
                                <a href="blogdetail?blogID=${blog.blogId}" style="text-decoration: none">
                                    <div class="blog-content-wrapper">
                                        <!-- Loop through each blog's images -->
                                        <c:forEach var="image" items="${blog.images}">
                                            <img src="${image.imageUrl}" alt="${blog.title}" class="img-fluid" style="max-height: 200px; object-fit: cover; width: 100%; border-radius: 8px;"/>
                                        </c:forEach>
                                        <h4>${blog.title}</h4>
                                        <!-- Shorten the blog detail (first 100 characters) -->
                                        <p>${fn:substring(blog.detail, 0, 100)}...</p>
                                        <small>Updated on: ${blog.updateDate}</small>
                                    </div></a>
                            </div>
                        </c:forEach>
                    </div>
                </section>
            </div>
        </div>


        <footer class="bg-dark text-light py-4">
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

        function showNewAddressInput() {
            const addressOptions = document.getElementById('addressOptions');
            addressOptions.style.display = 'block';
            const selectAddress = document.getElementById('addressSelect');
            selectAddress.style.display = 'none';
            const otherButton = document.getElementById('otherButton');
            otherButton.style.display = 'none';

            const oldButton = document.getElementById('oldButton');
            oldButton.style.display = "block";
            const newAddressInput = document.getElementById("newAddress");
            if (addressOptions.style.display === "block") {
                newAddressInput.setAttribute("required", "");
            } else {
                newAddressInput.removeAttribute("required");
            }
        }

        function selectOldAddress() {
            const addressOptions = document.getElementById('addressOptions');
            addressOptions.style.display = 'none';

            const selectAddress = document.getElementById('addressSelect');
            selectAddress.style.display = 'block';


            const oldButton = document.getElementById('oldButton');
            oldButton.style.display = "none";

            const otherButton = document.getElementById('otherButton');
            otherButton.style.display = 'block';
            const newAddressInput = document.getElementById("newAddress");
            if (addressOptions.style.display === "block") {
                newAddressInput.setAttribute("required", "");
            } else {
                newAddressInput.removeAttribute("required");
            }
        }

        function addAddressToDatabase() {
            const newAddress = document.getElementById("newAddress").value;

            // Gửi yêu cầu AJAX đến servlet
            const xhr = new XMLHttpRequest();
            xhr.open("POST", "modify-address", true); // Thay 'AddAddressServlet' bằng đường dẫn servlet của bạn
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

            xhr.onreadystatechange = function () {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    const response = JSON.parse(xhr.responseText);
                    if (response.success) {
                        // Cập nhật danh sách địa chỉ
                        updateAddressList(response.addressList);
                        // Hiển thị thông báo thành công bằng SweetAlert
                        Swal.fire({
                            title: 'Success!',
                            text: response.message,
                            icon: 'success',
                            confirmButtonText: 'OK'
                        });
                    } else {
                        // Hiển thị thông báo lỗi bằng SweetAlert
                        Swal.fire({
                            title: 'Error!',
                            text: response.message,
                            icon: 'error',
                            confirmButtonText: 'Try Again'
                        });
                    }
                }
            };

            xhr.send("address=" + encodeURIComponent(newAddress));
        }

        function updateAddressList(addressList) {
            const addressSelect = document.getElementById("address");
            addressSelect.innerHTML = ""; // Xóa nội dung cũ

            // Thêm các địa chỉ mới vào dropdown
            addressList.forEach(address => {
                const option = document.createElement("option");
                option.value = address.id;
                option.textContent = address.address;
                if (address.def) {
                    option.selected = true; // Nếu địa chỉ là mặc định, chọn nó
                }
                addressSelect.appendChild(option);
            });
        }


    </script>

</html>

