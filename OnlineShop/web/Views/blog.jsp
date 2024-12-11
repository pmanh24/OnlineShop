<%-- 
    Document   : log
    Created on : Sep 18, 2024, 10:17:58 AM
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

        <!-- Bootstrap CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" 
              integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" 
              crossorigin="anonymous">

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" 
                integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" 
        crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
        <style>
    /* General Styles */
    body {
        font-family: 'Arial', sans-serif;
        background-color: #f4f4f9;
        margin: 0;
        padding: 0;
    }

    .container {
        max-width: 1200px;
        margin: 20px auto;
        padding: 15px;
    }

    /* Flex container for sidebar and blog section */
    .main-content {
        display: flex;
        align-items: stretch; /* Makes both sections equal in height */
    }

    /* Sidebar */
    .sidebar {
        width: 25%;
        background-color: #fff;
        padding: 20px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        border-radius: 8px;
    }

    .sidebar h2 {
        font-size: 20px;
        margin-bottom: 10px;
        color: orange; /* Màu cam cho chữ "category" */
    }

    .sidebar form label {
        display: block;
        margin-bottom: 15px; /* Tăng khoảng cách giữa các category */
        font-size: 16px;
        padding: 10px;
        border-radius: 5px;
        transition: background-color 0.3s ease;
    }

    /* Hiệu ứng khi di chuột qua category */
    .sidebar form label:hover {
        background-color: #ffeef0;
        box-shadow: 0 4px 8px rgba(255, 165, 0, 0.5);
    }

    /* Style cho các nút và liên kết */
    .sidebar button, .sidebar a {
        display: inline-block;
        margin-top: 10px;
        padding: 10px 15px;
        background-color: orange;
        color: #fff;
        text-decoration: none;
        border-radius: 5px;
        transition: background-color 0.3s ease, box-shadow 0.3s ease;
        font-weight: bold;
    }

    .sidebar button:hover, .sidebar a:hover {
        background-color: #ff8c00;
        box-shadow: 0 4px 8px rgba(255, 165, 0, 0.5);
    }

    /* Blog Section */
    .blog-section {
        width: 75%;
        background-color: #fff;
        padding: 20px;
        margin-left: 25px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        border-radius: 8px;
    }

    .blog-section h2 {
        font-size: 22px;
        margin-bottom: 20px;
        color: orange; /* Màu cam cho "latest from our blog" */
    }

    /* Blog Post Styles */
    .blog-post {
        display: flex;
        align-items: flex-start;
        margin-bottom: 30px;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    .blog-image {
        flex: 0 0 60%;
        margin-right: 20px;
    }

    .blog-image img {
        width: 100%;
        height: auto;
        border-radius: 5px;
    }

    .blog-content {
        flex: 1;
    }

    .blog-content h3 {
        font-size: 20px;
        color: #333;
        margin-top: 0;
    }

    .blog-content p {
        font-size: 16px;
        color: #555;
        margin-top: 15px;
    }

    .read-more-box {
        display: inline-block;
        margin-top: 10px;
        padding: 5px 10px;
        background-color: orange;
        color: #fff;
        text-decoration: none;
        border-radius: 5px;
        transition: background-color 0.3s ease, box-shadow 0.3s ease;
        font-weight: bold;
    }

    .read-more-box:hover {
        background-color: #ff8c00;
        box-shadow: 0 4px 8px rgba(255, 165, 0, 0.5);
    }

    /* Pagination */
    .pagination {
        display: flex;
        justify-content: center;
        margin-top: 20px;
    }

    .pagination span, .pagination a {
        display: inline-block;
        padding: 10px 15px;
        background-color: #fff;
        border: 1px solid #ddd;
        margin: 0 5px;
        cursor: pointer;
        transition: background-color 0.3s ease;
        color: #333;
    }

    .pagination span:hover, .pagination a:hover {
        background-color: #ff8c00;
        color: #fff;
    }

    .pagination a.active {
        background-color: #ff8c00;
        color: white;
        border: 1px solid #ff8c00;
    }

    /* Title box */
    .title-box {
        text-align: center;
        padding: 10px;
        background-color: #f8f8f8;
        border-radius: 10px;
        margin-bottom: 20px;
    }

    .title-box h2 {
        color: orange;
        margin: 0;
        font-size: 24px;
    }

    /* Search and Sort Form */
    .blog-section form {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 20px;
        background-color: #f9f9f9;
        padding: 10px;
        border-radius: 5px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .blog-section form input[type="text"] {
        width: 60%;
        padding: 8px 10px;
        font-size: 16px;
        border: 1px solid #ddd;
        border-radius: 4px;
        transition: border-color 0.3s ease;
    }

    .blog-section form input[type="text"]:focus {
        border-color: #ff8c00;
    }

    .blog-section form button {
        padding: 8px 15px;
        font-size: 16px;
        background-color: orange;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        transition: background-color 0.3s ease, box-shadow 0.3s ease;
        margin-left: 10px;
    }

    .blog-section form button:hover {
        background-color: #ff8c00;
        box-shadow: 0 4px 8px rgba(255, 165, 0, 0.5);
    }

    /* Responsive styles */
    @media (max-width: 768px) {
        .main-content {
            flex-direction: column;
        }

        .sidebar {
            width: 100%;
            margin-bottom: 20px;
        }

        .blog-section {
            width: 100%;
        }

        .blog-section form {
            flex-direction: column;
            align-items: flex-start;
        }

        .blog-section form input[type="text"],
        .blog-section form button {
            width: 100%;
            margin-bottom: 10px;
        }
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
        <div class="container">
            <div class="main-content">
                <!-- Sidebar for categories -->
                <div class="sidebar">
                    <h2>Categories</h2>
                    <form method="post" action="blog">
                        <c:forEach var="category" items="${catList}">
                            <label>
                                <input type="radio" name="category" value="${category.categoryId}">
                                ${category.name}
                            </label>
                        </c:forEach>
                        <button type="submit">Filter</button>
                    </form>
                    <a href="blog">Back to blog list</a>
                </div>

                <!-- Blog section -->
                <div class="blog-section">

                    <!-- Search and Sort -->
                    <form action="blog" method="post">
                        <input type="text" name="search" placeholder="Search by title" value="${param.search}" />
                        <button type="submit">Search</button>

                        <!-- Sort Button -->
                        <button type="submit" name="sortBy" value="updateDate">Sort by Update Date</button>
                    </form>
                    <!-- list blog -->
                    <h2>Latest from Our Blog</h2>
                    <c:forEach var="blog" items="${blog}">
                        <div class="blog-post">
                            <!-- Cột hình ảnh bên trái -->
                            <div class="blog-image">
                                <img src="${blog.image}" alt="Blog Image" style="width: 100%; border-radius: 5px;">
                            </div>

                            <!-- Cột nội dung bên phải -->
                            <div class="blog-content">
                                <h2  style="font-style: initial"  >${blog.title}</h2>
                                <p><strong></strong> ${blog.short_content}</p>
                                <p><strong>Updated:</strong> ${blog.updateDate}</p>
                                <a href="blogdetail?blogID=${blog.blogID}" class="read-more-box">Read More</a>
                            </div>
                        </div>
                    </c:forEach>
                    <div class="pagination">
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <a href="blog?page=${i}" class="${i == currentPage ? 'active' : ''}">${i}</a>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </body>

    <!-- JavaScript -->
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            // Handle pagination or other dynamic elements
            const paginationItems = document.querySelectorAll('.pagination span');

            paginationItems.forEach(item => {
                item.addEventListener('click', function () {
                    paginationItems.forEach(el => el.classList.remove('active'));
                    this.classList.add('active');
                });
            });
        });
    </script>

</html>
</html>

