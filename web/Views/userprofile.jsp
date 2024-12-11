<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile Page</title>
        <style>
            .card {
                background-color: #ffe5b4; /* Màu cam nhạt */
            }
            .btn-custom {
                background-color: #ffcc80; /* Màu cam nhạt cho nút */
                color: #000; /* Màu chữ trên nút */
                transition: background-color 0.4s, transform 0.4s; /* Hiệu ứng chuyển tiếp */
            }
            .btn-custom:hover {
                background-color: #ffb74d; /* Màu khi di chuột qua */
                transform: scale(1.05); /* Tăng kích thước nút khi di chuột */
            }
        </style>
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
        <div class="container-xl px-4 mt-4">
            <!-- Divider -->
            <hr class="mt-0 mb-4">
            
            <!-- Profile details section -->
            <div class="row">
                <!-- Profile Card -->
                <div class="col-xl-4">
                    <div class="card mb-4 mb-xl-0">
                        <div class="card-header">PROFILE</div>
                        <div class="card-body">
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item"><strong>Full Name:</strong> ${customer.fullname}</li>
                                <li class="list-group-item"><strong>Gender:</strong> ${customer.gender == 1 ? 'Male' : 'Female'}</li>
                                <li class="list-group-item"><strong>Phone Number:</strong> ${customer.numberphone}</li>
                                <li class="list-group-item"><strong>Email: </strong> ${customer.email}</li>
                                <li class="list-group-item"><strong>Address:</strong> ${customer.address}</li>
                            </ul>
                        </div>
                    </div>
                </div>
                
                <!-- Account Details Form -->
                <div class="col-xl-8">
                    <div class="card mb-4">
                        <div class="card-header">Account Details</div>
                        <div class="card-body">
                            <form action="userprofile" method="post">
                                <!-- Hidden input for customer ID -->
                                <input type="hidden" name="customer_id" value="${customer.id}">
                                
                                <!-- Full Name input -->
                                <div class="mb-3">
                                    <label class="small mb-1" for="inputName">Full Name</label>
                                    <input class="form-control" id="inputName" type="text" name="fullname" placeholder="Enter Full Name" required>
                                </div>

                                <!-- Gender input -->
                                <div class="mb-3">
                                    <label class="small mb-1" for="inputGender">Gender</label>
                                    <select class="form-control" id="inputGender" name="gender" required>
                                        <option value="1">Male</option>
                                        <option value="0">Female</option>
                                    </select>
                                </div>

                                <!-- Phone Number input -->
                                <div class="mb-3">
                                    <label class="small mb-1" for="inputPhone">Phone Number</label>
                                    <input class="form-control" id="inputPhone" type="text" name="numberphone" placeholder="Enter Phone Number" required>
                                </div>

                                <!-- Address input -->
                                <div class="mb-3">
                                    <label class="small mb-1" for="inputAddress">Address</label>
                                    <input class="form-control" id="inputAddress" type="text" name="address" placeholder="Enter Address" required>
                                </div>
                                <div><h7 style="color: red">* You cannot change email</h7></div>

                                <button class="btn-custom" type="submit">Save changes</button>
                                <h6 style="color: #20c914">${mess}</h6>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS and jQuery -->
        <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    </body>
</html>
