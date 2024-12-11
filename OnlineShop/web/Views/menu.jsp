<%-- 
    Document   : menu
    Created on : Sep 16, 2024, 3:43:26 PM
    Author     : Hieu
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Online Shop</title>
<link rel="stylesheet" href="Resource/css/menu.css"/>
        <!-- Font Awesome CDN -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">

        <!-- Bootstrap CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" 
              integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" 
              crossorigin="anonymous">

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" 
                integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" 
        crossorigin="anonymous"></script>

        <!-- Custom CSS for Orange Theme -->
        <link rel="stylesheet" href="Resource/css/menu.css"/>
    </head>
    <body>
<header id="header">
    <!-- Header Container -->
    <div class="header-container">
        <!-- Logo -->
        <div class="logo">
            <a href="/OnlineShop/home">
                <img src="Resource/images/logo.png" alt="Logo">
            </a>
        </div>
        <!-- Navigation Menu -->
        <nav class="navbar navbar-expand-lg">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item"><a href="/OnlineShop/home" class="nav-link">Home</a></li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="shopDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">Shop</a>
                        <ul class="dropdown-menu" aria-labelledby="shopDropdown">
                            <li><a href="/OnlineShop/shop/men" class="dropdown-item">Men</a></li>
                            <li><a href="/OnlineShop/shop/women" class="dropdown-item">Women</a></li>
                            <li><a href="/OnlineShop/shop/kids" class="dropdown-item">Kids</a></li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a href="#" class="nav-link dropdown-toggle" role="button" data-bs-toggle="dropdown" aria-expanded="false">Blog</a>
                    </li>
                    <li class="nav-item"><a href="/OnlineShop/contact" class="nav-link">Contact</a></li>
                </ul>
            </div>
        </nav>
        <!-- Search Box and Icons -->
        <div class="nav-icons">
            <div class="search_box">
                <input type="text" placeholder="Search"/>
                <button>
                    <i class="fas fa-search"></i>
                </button>
            </div>
            <div class="shop-menu">
                <ul class="nav">
                    <li class="nav-item"><a href="/OnlineShop/cart" class="nav-link"><i class="fa fa-shopping-cart"></i></a></li>
                    <c:if test="${customer != null}">
    <li class="nav-item">
        <img id="user-logo" src="Resource/images/login.png" width="60px" height="60px" alt="User Avatar" class="avatar user-button"/>
        <ul class="subnav" id="userDropdown">
            <li><a href="/OnlineShop/updateprofile" class="dropdown-item">Profile</a></li>
            <li><a href="/OnlineShop/changePassword" class="dropdown-item">Change Password</a></li>
            <li><a href="/OnlineShop/logout" class="dropdown-item">Logout</a></li>
        </ul>
        <!--<p class="text-secondary mt-1" id="user-name">${customer.fullName}</p>-->
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
        <script>
                       // Get the full name
            var fullName = document.getElementById("user-name").textContent;

            // Split the full name by space
            var names = fullName.split(" ");

            // Display the first two names
            if (names.length >= 2) {
                document.getElementById("user-name").textContent = names[2];
            }
            ;
            var subnav = document.querySelector(".subnav");

            function toggleSubnav() {
                if (subnav.style.display === "block") {
                    subnav.style.display = "none";
                } else {
                    subnav.style.display = "block";
                }
            }
document.addEventListener('DOMContentLoaded', function () {
    var userButton = document.getElementById("user-logo"); // Change to img
    var subnav = document.getElementById("userDropdown");

    // Optionally, you can still manage mouse events if needed
    userButton.addEventListener('mouseenter', function () {
        subnav.classList.add('show-dropdown');
    });

    userButton.addEventListener('mouseleave', function () {
        subnav.classList.remove('show-dropdown');
    });
});
        </script>
    </body>
</html>
