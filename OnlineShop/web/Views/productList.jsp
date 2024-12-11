<%-- 
    Document   : productList
    Created on : Sep 18, 2024, 9:27:59 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
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

        <title>Shop | E-Shopper</title>
        <link href="Resource/css/bootstrap.min.css" rel="stylesheet">
        <link href="Resource/css/main.css" rel="stylesheet">
        <script type="text/javascript">
            function setCheck(obj) {
                var fries = document.getElementsByName('cidd');
                if ((obj.id == 'c0') && (fries[0].checked == true)) {
                    for (var i = 1; i < fries.length; i++)
                        fries[i].checked = false;
                } else {
                    for (var i = 1; i < fries.length; i++) {
                        if (fries[i].checked == true) {
                            fries[0].checked = false;
                            break;
                        }
                    }
                }
                document.getElementById('f1').submit();
            }
        </script>
        <style>
            #searchForm {
                display: none; /* Hides the form */
            }
            .pagination {
                display: flex; /* Use flexbox for horizontal layout */
                justify-content: center; /* Center align pagination */
                list-style: none; /* Remove default list styles */
                padding: 0; /* Remove padding */
            }

            .pagination .page-item {
                margin: 0 10px; /* Space between pagination items */
            }

            .pagination .page-link {
                text-decoration: none; /* Remove underline */
                padding: 8px 12px; /* Add some padding */
                border: 1px solid #ddd; /* Add border for visual effect */
                border-radius: 5px; /* Rounded corners */
                color: #007bff; /* Link color */
                transition: background-color 0.3s; /* Smooth transition for background */
            }

            .pagination .page-link:hover {
                background-color: #f1f1f1; /* Background color on hover */
            }

            .pagination .page-link.active {
                background-color: #007bff; /* Active page color */
                color: white; /* Active page text color */
            }
            a {
                display: inline-block; /* Makes the link behave like a block element */
                text-align: center; /* Centers the text horizontally within the anchor */
            }

            .product-name {
                display: inline-block; /* Treats the paragraph as a block for centering */
                white-space: nowrap; /* Prevents the text from wrapping to the next line */
                overflow: hidden; /* Hides any text that overflows the container */
                text-overflow: ellipsis; /* Adds the ellipsis (…) */
                width: 200px; /* Adjust the width as needed */
                text-align: center; /* Centers text horizontally inside the paragraph */
                margin: 0; /* Removes default margin for <p> */
                line-height: 1.5; /* Adjust the line height if needed for vertical spacing */
            }
        </style>
    </style>


</head><!--/head-->

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

    <section ><!--header-->
        <div class="header-bottom"><!--header-bottom-->
            <div class="container">
                <div class="row">
                    <div class="col-sm-9">

                        <div class="mainmenu pull-left">
                            <ul class="nav navbar-nav collapse navbar-collapse">                                                                    
                                <li>Sort by: </li>
                                <li><a href="productListUpdate?cid=1">Newest</a></li>
                                <li><a href="productListUpdate?cid=2">Price</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div >
                            <form id="searchForm" action="productListUpdate" method="get" t>
                                <input type="text" placeholder="Search" name="key"/>
                                <button onclick="this.form.submit()">
                                    <img src="Resource\images\shop\search.png"
                                         width="16px" height="16px"/>
                                </button>

                            </form>                            
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <section>
        <div class="container">
            <c:set var="cat" value ="${requestScope.data}"/>
            <div class="row">
                <div class="col-sm-3">
                    <div class="left-sidebar">
                        <c:set var ="chid" value="${requestScope.child}"/>
                        <h2>Category</h2>
                        <form id="f1" action="productListUpdate">
                            <input type="checkbox" id="c0" name="cidd"
                                   ${chid[0]?"checked":""}
                                   value="${0}"  onclick="setCheck(this)"/>All<br/>
                            <c:forEach begin="0" end="${cat.size()-1}" var="i">
                                <input type="checkbox" id="cm" name="cidd"                                          
                                       value="${cat.get(i).getCategoryId()}"
                                       ${chid[i+1]?"checked":""} onclick="setCheck(this)""/>
                                ${cat.get(i).getName()}
                                <br/>
                            </c:forEach>
                        </form>
                        <div class="shipping text-center"><!--shipping-->
                            <img src="Resource/images/home/shipping.jpg" alt="" />
                        </div><!--/shipping-->

                    </div>
                </div>

                <div class="col-sm-9 padding-right">
                    <div class="features_items"><!--features_items-->
                        <c:set var="products" value="${requestScope.products}"/>
                        <h2 class="title text-center">Features Items</h2>
                        <c:forEach items="${products}" var="p">
                            <div class="col-sm-4">
                                <div class="product-image-wrapper">
                                    <div class="single-products">
                                        <div class="productinfo text-center">
                                            <a href="productdetail?productId=${p.getProductId()}"><img src="${p.getImageUrl()}" alt=""  height="240px" width="20px"/>    </a>                                          
                                            <h2>Sale: ${p.getSalePrice()} VND</h2>
                                            <p>Original price: <s>${p.getOriginalPrice()} VND</s></p>
                                            <a href="productdetail?productId=${p.getProductId()}"><p class="product-name">${p.getProductName()}</p></a>
                                            <a href="productdetail?productId=${p.getProductId()}"  class="btn btn-fefault cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
                                        </div>

                                    </div>

                                </div>
                            </div>
                        </c:forEach>




                    </div><!--features_items-->
                    <ul class="pagination">
                        <c:set var="page" value="${requestScope.page}"/>              
                        <c:set var="path" value="${requestScope.path}"/>
                        <c:forEach begin="${1}" end="${requestScope.numpage}" var="i">
                            <li class="page-item ${i == page ? 'active' : ''}">
                                <a href="productListUpdate?page=${i}&${path}" class="page-link">${i}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>

            </div>
        </div>
    </section>

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



    <script src="Resource/js/jquery.js"></script>
    <script src="Resource/js/price-range.js"></script>
    <script src="Resource/js/jquery.scrollUp.min.js"></script>
    <script src="Resource/js/bootstrap.min.js"></script>
    <script src="Resource/js/jquery.prettyPhoto.js"></script>
    <script src="Resource/js/main.js"></script>

</body>
</html>
