<%-- 
    Document   : blogdetail
    Created on : Sep 18, 2024, 3:37:18 PM
    Author     : T
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="Resource/css/menu.css"/>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
        <!-- Bootstrap CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" 
              integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" 
              crossorigin="anonymous">

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" 
                integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" 
        crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <!--        <script src="https://cdn.tailwindcss.com"></script>-->
        <style>
            /* Căn giữa toàn bộ main content */
            .container {
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 80vh; /* Đảm bảo main content chiếm đủ không gian */
            }

            /* Căn giữa và định dạng cho main content */
            .main-content {
                background-color: #ffffff;
                padding: 2rem;
                border-radius: 0.5rem;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                max-width: 2500px;
                width: 100%;
                margin-top: 30px;
            }

            /* Căn giữa văn bản và các thành phần bên trong main content */
            .main-content * {
                text-align: center;
                margin: 0 auto;
            }

            /* Nút màu cam với hiệu ứng hover */
            .btn-orange {
                background-color: #ff7f0e;
                color: white;
                padding: 0.5rem 1rem;
                border: none;
                border-radius: 0.25rem;
                text-decoration: none;
                display: inline-block;
                transition: background-color 0.3s ease, transform 0.2s ease;
            }

            .btn-orange:hover {
                background-color: #e56b00;
                transform: scale(1.05);
            }

            /* Form tìm kiếm căn giữa */
            .search-box {
                display: flex;
                justify-content: center;
                gap: 0.5rem;
            }

            .search-box input {
                width: 100%;
                padding: 0.5rem;
                border: 1px solid #ddd;
                border-radius: 1.25rem;
            }

            .search-box button {
                background-color: #ff7f0e;
                color: white;
                padding: 0.5rem 1rem;
                border: none;
                border-radius: 1.25rem;
                transition: background-color 0.3s ease;
            }

            .search-box button:hover {
                background-color: #e56b00;
            }
        </style>

    </head>
    <body class="bg-gray-100">
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
        <div class="container">
            <div class="main-content">
                <!-- Search Box -->
                <div class="search-box">
                    <form action="blogdetail" method="GET" class="d-flex justify-center">
                        <input type="text" name="Search" placeholder="Search by title" style="width: 300px" />
                        <button type="submit" class="btn-orange">Search</button>
                    </form>
                </div>

                <!-- Nội dung blog -->
                <c:if test="${blog != null}">
                    <h1 class="text-2xl font-bold">${blog.title}</h1>
                    <div><span>${blog.updateDate}</span></div>
                    <div><span>Category: ${blog.category_name}</span></div>
                    <div class="mt-4">
                        <img alt="Blog Image" class="w-1/2 h-auto object-cover rounded" style="width: 55%" src="${blog.image}" />
                    </div>
                    <p class="mt-4 text-gray-700">${blog.detail}</p>
                </c:if>

                <!-- Nút Back to Blog Lists -->
                <div class="text-center">
                    <a href="blog" class="btn-orange mt-4">Back to Blog Lists</a>
                </div>
            </div>
        </div>

    </body>
</html>

