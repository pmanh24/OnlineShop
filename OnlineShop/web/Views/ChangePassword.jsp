<%-- 
    Document   : ChangePassword
    Created on : Sep 15, 2024, 11:52:31 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset='utf-8'>
        <meta name='viewport' content='width=device-width, initial-scale=1'>
        <title>Change Password</title>
        <link rel="stylesheet" href="Resource/css/menu.css"/>
        <link rel="stylesheet" href="Resource/css/cart.css"/>  
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-social/5.1.1/bootstrap-social.css">
        <link
            href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css'
            rel='stylesheet'>
        <link
            href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.0.3/css/font-awesome.css'
            rel='stylesheet'>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" 
              integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" 
              crossorigin="anonymous">
        <script src="https://kit.fontawesome.com/332a215f17.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" 
                integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" 
        crossorigin="anonymous"></script>
        <script type='text/javascript'
        src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
        <style>
            .placeicon {
                font-family: fontawesome
            }

            .custom-control-label::before {
                background-color: #dee2e6;
                border: #dee2e6
            }
            .btn-common{
                width: 110%;
                background-color: #FF9A00;
                color:#fff;
                /*border-radius:4px;*/
                border:none;
                cursor:pointer;
                /*line-height:40px;*/
                text-align:center;
            }
            .btn-common:hover{
                background-color:#dda20a;
            }
            .border-info {
                border-color: blue!important;
            }
        </style>
    </head>
    <body oncontextmenu='return false' class='snippet-body bg-light'>
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
        <!-- Container containing all contents -->
        <div class="container pb-5">
            <div class="row justify-content-center">
                <div class="col-12 col-md-9 bg-white col-lg-7 col-xl-6 mt-5">
                    <!-- White Container -->
                    <div class="container bg-white rounded mt-2 mb-2 px-0">
                        <!-- Main Heading -->
                        <div class="row justify-content-center align-items-center pt-3">
                            <h1 class="text-center pb-3" style="color: #FF9A00;">
                                <strong>Change Password</strong>
                            </h1>
                        </div>
                        <div class="pt-3 pb-3">
                            <form class="form-horizontal" action="changePassword" method="POST">

                                <div class="form-group row justify-content-center px-3">
                                    <div class="col-9 px-0">
                                        <input type="password" name="oldPassword" placeholder="&#xf084; &nbsp; Old Password"
                                               class="form-control border-warning placeicon">
                                    </div>
                                </div>
                                <div class="form-group row justify-content-center px-3">
                                    <div class="col-9 px-0">
                                        <input type="password" name="password" placeholder="&#xf084; &nbsp; New Password"
                                               class="form-control border-warning placeicon">
                                    </div>
                                </div>
                                <!-- Password Input -->
                                <div class="form-group row justify-content-center px-3">
                                    <div class="col-9 px-0">
                                        <input type="password" name="confPassword"
                                               placeholder="&#xf084; &nbsp; Confirm New Password"
                                               class="form-control border-warning placeicon">
                                    </div>
                                </div>

                                <!-- Log in Button -->
                                <div class="form-group row justify-content-center">
                                    <div class="col-3 px-3 mt-3">
                                        <input type="submit" value="Reset"
                                               class="btn btn-common proceed-btn">

                                    </div>



                                </div>


                            </form>

                        </div>
                        <!-- Alternative Login -->
                        <div class="mx-0 px-0 bg-white">


                            <!-- Register Now -->
                            <div class="pt-2">
                                <div class="row justify-content-center align-items-center pt-4 pb-5">

                                    <div class="col-12">
                                        <c:if test="${status!=null}" var="c">
                                            <h4 style="color:green">${status}</h4>
                                        </c:if>

                                        <c:if test="${errMsg != null}">
                                            <div class="alert alert-danger" role="alert">
                                                ${errMsg}
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
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
        <script type='text/javascript'
        src='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.bundle.min.js'></script>

    </body>
</html>
