<%-- 
    Document   : home
    Created on : Sep 15, 2024, 12:30:37 AM
    Author     : Hieu
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Home Page</title>
        <!-- Font Awesome CDN -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
        <link rel="stylesheet" href="Resource/css/menu.css"/>

        <!-- Bootstrap CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" 
              integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" 
              crossorigin="anonymous">

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" 
                integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" 
        crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <!-- Slick CSS -->
        <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
        <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css"/>

        <!-- Slick JS -->
        <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>

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
                margin-bottom: 30px; /* Margin between products */
                margin-right: 20px;
                border-radius: 8px;
                overflow: hidden;
                transition: box-shadow 0.3s ease;
                text-align: center;
                padding: 10px; /* Add padding around the product wrapper */
            }

            .product-image-wrapper:hover {
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            }

            .single-products {
                /*padding: 15px;*/
                position: relative;
                overflow: hidden;
            }

            /* Center the image within the container */
            .single-products img {
                width: 100%; /* Responsive width */
                max-width: 200px; /* Ensure product images are not too small */
                height: 200px; /* Fixed height for product images */
                object-fit: cover; /* Ensure images cover the designated area proportionally */
                display: block; /* Ensure the image is a block element */
                margin: 0 auto; /* Center the image */
                transition: transform 0.3s ease;
            }

            .single-products img:hover {
                transform: scale(1.1); /* Zoom effect on hover */
            }

            .productinfo {
                text-align: center;
                margin-top: 10px;
            }

            .productinfo h2 {
                font-size: 18px; /* Slightly smaller font size */
                color: #333;
                margin: 5px 0; /* Add margin for better spacing */
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

            .productinfo .add-to-cart:hover {
                background-color: #ff3d29; /* Change the hover color for Add to Cart */
            }

            /* Responsive adjustments */
            @media (max-width: 768px) {
                .col-sm-6.col-md-3 {
                    flex: 0 0 50%; /* Two items per row on small screens */
                    max-width: 50%;
                }
            }

            @media (max-width: 576px) {
                .col-sm-6.col-md-3 {
                    flex: 0 0 100%; /* One item per row on extra small screens */
                    max-width: 100%;
                }
            }

            .blog-wrap {
                padding: 15px;
            }

            .blog-content-wrapper {
                border: 1px solid #ddd;
                background-color: #fff;
                margin-bottom: 30px;
                padding: 50px;
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
            .carousel-indicators {
                list-style: none; /* Removes default numbering */
                padding: 0;
                margin: 0;
            }
            .indicator-title {
                display: none;
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
                            <li class="nav-item"><a href="cartdetail" class="nav-link"><i class="fa fa-shopping-cart"></i></a></li>
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

                <section>
                    <div class="row" style="margin-bottom: 30px;"> <!-- Add margin to the row if needed -->
                        <h3 class="text-center pb-3" style="color: #FF9A00; font-weight: bold">Featured Products</h3>
                        <div class="product-carousel"> <!-- Slick carousel wrapper -->
                            <!-- Loop through the product list -->
                            <c:forEach var="product" items="${productList}">
                                <div class="col-sm-6 col-md-3 product-wrap"> <!-- Adjusted for better responsiveness -->
                                    <div class="product-image-wrapper">
                                        <div class="single-products">
                                            <div class="productinfo text-center">
                                                <a name="toDetail" href="productdetail?productId=${product.productId}"
                                                   onclick="addProductToRecentViews(${product.productId})">
                                                    <img src="${product.imageUrl}" alt="${product.productName}" /> 
                                                    <h2>${fn:substring(product.productName, 0, 25)}</h2>
                                                </a>
                                                <div style="display: flex; align-items: baseline; justify-content: center;">
                                                    <c:set var="originalPrice" value="${product.originalPrice}" />
                                                    <c:set var="salePrice" value="${product.salePrice}" />
                                                    <c:set var="discountPercent" value="0" />

                                                    <c:if test="${originalPrice > 0}">
                                                        <c:set var="discountPercent" 
                                                               value="${((originalPrice - salePrice) / originalPrice) * 100}" />
                                                    </c:if>

                                                    <h6 style="text-decoration: line-through; color: gray; margin-right: 3px;">
                                                        <fmt:formatNumber value="${originalPrice}" pattern="#,###"/> VND
                                                    </h6>
                                                    <h6 style="color: red; margin: 0; margin-right: 2px">
                                                        <fmt:formatNumber value="${salePrice}" pattern="#,###"/> VND
                                                    </h6>
                                                    <!--                                                    <span style="color: green; margin: 0; font-size: smaller;">
                                                                                                            (-<fmt:formatNumber value="${discountPercent}" pattern="##"/>%)
                                                                                                        </span>-->
                                                </div>
                                                <c:if test="${product.numberLeft > 0}">
                                                    <a href="productdetail?productId=${product.productId}" class="btn btn-default add-to-cart">
                                                        <i class="fa fa-shopping-cart"></i> Buy Now
                                                    </a>
                                                </c:if>
                                                <c:if test="${product.numberLeft <= 0}">
                                                    <h3 class="text-danger mb-3">Out of Stock</h3>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div><!-- End Slick carousel wrapper -->
                    </div><!-- End row for product items -->
                </section>

                <section>
                    <div class="row" style="margin-bottom: 30px;"> <!-- Add margin to the row if needed -->
                        <h3 class="text-center pb-3" style="color: #FF9A00; font-weight: bold">New Arrivals</h3>
                        <div class="product-carousel"> <!-- Slick carousel wrapper -->
                            <!-- Loop through the product list -->
                            <c:forEach var="product" items="${productListCurrent}">
                                <div class="col-sm-6 col-md-3 product-wrap"> <!-- Adjusted for better responsiveness -->
                                    <div class="product-image-wrapper">
                                        <div class="single-products">
                                            <div class="productinfo text-center">
                                                <a name="toDetail" href="productdetail?productId=${product.productId}"
                                                   onclick="addProductToRecentViews(${product.productId})">
                                                    <img src="${product.imageUrl}" alt="${product.productName}" /> 
                                                    <h2>${fn:substring(product.productName, 0, 25)}</h2>
                                                </a>
                                                <div style="display: flex; align-items: baseline; justify-content: center;">
                                                    <c:set var="originalPrice" value="${product.originalPrice}" />
                                                    <c:set var="salePrice" value="${product.salePrice}" />
                                                    <c:set var="discountPercent" value="0" />

                                                    <c:if test="${originalPrice > 0}">
                                                        <c:set var="discountPercent" 
                                                               value="${((originalPrice - salePrice) / originalPrice) * 100}" />
                                                    </c:if>

                                                    <h6 style="text-decoration: line-through; color: gray; margin-right: 3px;">
                                                        <fmt:formatNumber value="${originalPrice}" pattern="#,###"/> VND
                                                    </h6>
                                                    <h6 style="color: red; margin: 0; margin-right: 2px">
                                                        <fmt:formatNumber value="${salePrice}" pattern="#,###"/> VND
                                                    </h6>
                                                    <!--                                                    <span style="color: green; margin: 0; font-size: smaller;">
                                                                                                            (-<fmt:formatNumber value="${discountPercent}" pattern="##"/>%)
                                                                                                        </span>-->
                                                </div>
                                                <c:if test="${product.numberLeft > 0}">
                                                    <a href="productdetail?productId=${product.productId}" class="btn btn-default add-to-cart">
                                                        <i class="fa fa-shopping-cart"></i> Buy Now
                                                    </a>
                                                </c:if>
                                                <c:if test="${product.numberLeft <= 0}">
                                                    <h3 class="text-danger ">Out of Stock</h3>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div><!-- End Slick carousel wrapper -->
                    </div><!-- End row for product items -->
                </section>

                <section>
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
                <section>
                    <div class="row">
                        <h3 class="text-center pb-3" style="color: #FF9A00; font-weight: bold">Recent Posts</h3>
                        <div class="blog-carousel"> <!-- Slick carousel wrapper -->
                            <!-- Loop through the blog list -->
                            <c:forEach var="blog" items="${blogListRecent}">
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
                                        </div>
                                    </a>
                                </div>
                            </c:forEach>
                        </div> <!-- End of blog-carousel -->
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
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('.product-carousel').slick({
                dots: true,
                infinite: true,
                speed: 300,
                slidesToShow: 4, // Number of products to show at once
                slidesToScroll: 1,
                autoplay: true, // Enable auto-scrolling
                autoplaySpeed: 2000, // Set the speed for auto-scrolling (in milliseconds)
                responsive: [
                    {
                        breakpoint: 1024,
                        settings: {
                            slidesToShow: 3,
                            slidesToScroll: 1,
                            dots: true
                        }
                    },
                    {
                        breakpoint: 768,
                        settings: {
                            slidesToShow: 2,
                            slidesToScroll: 1,
                            dots: true
                        }
                    },
                    {
                        breakpoint: 480,
                        settings: {
                            slidesToShow: 1,
                            slidesToScroll: 1,
                            dots: true
                        }
                    }
                ]
            });
        });
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('.blog-carousel').slick({
                dots: true, // Show dots for navigation
                infinite: true, // Infinite scrolling
                speed: 300, // Transition speed
                slidesToShow: 3, // Number of slides to show at once
                slidesToScroll: 1, // Number of slides to scroll
                responsive: [
                    {
                        breakpoint: 1024,
                        settings: {
                            slidesToShow: 2,
                            slidesToScroll: 1,
                            dots: true // Show dots for navigation on smaller screens
                        }
                    },
                    {
                        breakpoint: 768,
                        settings: {
                            slidesToShow: 1,
                            slidesToScroll: 1,
                            dots: true // Show dots for navigation on smaller screens
                        }
                    }
                ]
            });
        });
    </script>

</html>
