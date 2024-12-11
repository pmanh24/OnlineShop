<%-- 
    Document   : CartDetail
    Created on : Sep 28, 2024, 10:01:37 PM
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
        <title>Cart Details</title>
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
        <!--        <script src="
                        https://cdn.jsdelivr.net/npm/sweetalert2@11.14.1/dist/sweetalert2.all.min.js
                "></script>
                <link href="
                      https://cdn.jsdelivr.net/npm/sweetalert2@11.14.1/dist/sweetalert2.min.css
                      " rel="stylesheet">-->

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <!--        <script src="sweetalert2.min.js"></script>
                <link rel="stylesheet" href="sweetalert2.min.css">-->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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
                <h3 class="text-center pb-3" style="color: #FF9A00;font-weight: bold">Cart Details</h3>
                <section class="mt-5 col-9">

                    <div class="container">
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col" class="h4">Product</th>
                                        <th scope="col">Unit Price</th>
                                        <th scope="col">Quantity</th>
                                        <th scope="col">Remove</th>
                                        <th scope="col">Total</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:if test="empty itemList">
                                    <h1>${txtCookie}</h1>
                                </c:if>   



                                <c:forEach var="item" items="${itemList}">
                                    <tr id="row-${item.productSize.productSizeId}">
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
                                                <!--                                                <button class="btn btn-link px-2">
                                                                                                    <a href="processcart?quantity=-1&id=${item.productSize.productSizeId}" onclick="checkQuantityAndAlert('${item.productSize.productSizeId}')" ><i class="fas fa-minus"></i></a>
                                                                                                </button>-->

                                                <input id="form1-${item.productSize.productSizeId}" min="0" name="quantity" value="${item.quantity}" type="number" 
                                                       class="form-control form-control-sm" style="width: 50px;" 
                                                       oninput="updateQuantityInCookie('${item.productSize.productSizeId}', this.value, ${item.productSize.prices})"/>

                                                <!--                                                <button class="btn btn-link px-2">
                                                                                                    <a href="processcart?quantity=1&id=${item.productSize.productSizeId}"><i class="fas fa-plus"></i></a>                                     
                                                                                                </button>-->
                                            </div>
                                        </td>
                                        <td class="align-middle">
                                            <p class="mb-0" style="font-weight: 500;"><a style="cursor: pointer" onclick="callAlert(event, 'processcart?productSizeId=${item.productSize.productSizeId}&isDelete=1')"><image src="Resource/images/cart/delete.png" style="width: 30px; margin-top: -15px; margin-left: 15px"/></a></p>
                                        </td>
                                        <td class="align-middle">
                                            <p  class="mb-0 total-price" style="font-weight: 500;">${item.productSize.prices * item.quantity} VND</p>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                        </div>
                    </div>
                </section>
                <!----->
                <div class="mt-5 col-3">
                    <div class="checkout">
                        <ul>
                            <li class="cart-total">Total
                                <span>${total} VND</span>
                            </li>
                        </ul>
                        <a href="cartcontact" class="proceed-btn" style="background-color: orange">Check out</a>
                        <c:if test="${errMsg!=null}">
                            <div class="alert alert-danger">
                                ${errMsg}
                            </div>
                        </c:if>

                    </div>

                </div>
                <div class="mt-5 col-2">
                    <div class="checkout">
                        <a href="productListUpdate" class="btn-success proceed-btn" style="background-color: #ff8306">More Product</a>
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

        <!-------Cart------>



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
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <script>
                                                function callAlert(event, url) {
                                                    event.preventDefault(); // Ngăn hành động mặc định của thẻ <a>

                                                    Swal.fire({
                                                        title: "Are you sure?",
                                                        text: "This product is no longer in your cart!",
                                                        icon: "warning",
                                                        showCancelButton: true,
                                                        confirmButtonColor: "#3085d6",
                                                        cancelButtonColor: "#d33",
                                                        confirmButtonText: "Yes, delete it!"
                                                    }).then((result) => {
                                                        if (result.isConfirmed) {
                                                            // Lưu thông báo success vào localStorage
                                                            localStorage.setItem("deleteSuccess", "true");

                                                            // Điều hướng sang trang mới
                                                            window.location.href = url;
                                                        }
                                                    });
                                                }
                                                ;

                                                window.onload = function () {
                                                    // Kiểm tra xem có thông báo success trong localStorage hay không
                                                    if (localStorage.getItem("deleteSuccess") === "true") {
                                                        // Hiển thị thông báo thành công
                                                        Swal.fire({
                                                            title: "Deleted!",
                                                            text: "Your product has been deleted.",
                                                            icon: "success"
                                                        });

                                                        // Xóa giá trị trong localStorage để không hiển thị lại thông báo
                                                        localStorage.removeItem("deleteSuccess");
                                                    }
                                                };
                                                function checkQuantityAndAlert(productId) {
                                                    const quantityInput = document.getElementById(`form1-` + productId);
                                                    const currentQuantity = parseInt(quantityInput.value);

                                                    if (currentQuantity === 1) {
                                                        // Gọi alert nếu số lượng là 1
                                                        callAlert(event, `processcart?quantity=-1&id=` + productId);
                                                    } else {
                                                        // Nếu số lượng lớn hơn 1, giảm số lượng và điều hướng
                                                        window.location.href = `processcart?quantity=-1&id=` + productId;
                                                    }
                                                }

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

                                                function updateQuantityInCookie(productId, quantity, price) {
                                                    quantity = parseInt(quantity, 10);



                                                    if (isNaN(quantity) || quantity < 0) {
                                                        // Hiển thị thông báo lỗi nếu quantity < 0 và đặt lại giá trị về 1
                                                        Swal.fire({
                                                            title: 'Error!',
                                                            text: 'Please input valid number of quantity again',
                                                            icon: 'error',
                                                            confirmButtonText: 'OK'
                                                        }).then(() => {
                                                            // Đặt lại giá trị input về 1
                                                            document.getElementById(`form1-${productId}`).value = 1;
                                                            quantity = 1; // Cập nhật quantity để đảm bảo giá trị đúng
                                                        });
                                                        return; // Dừng thực hiện hàm
                                                    }

                                                    if (quantity === 0) {
                                                        Swal.fire({
                                                            title: 'Delete Product',
                                                            text: "Do you really want to delete this product?",
                                                            icon: 'warning',
                                                            showCancelButton: true,
                                                            confirmButtonText: 'Delete',
                                                            cancelButtonText: 'Cancel'
                                                        }).then((result) => {
                                                            if (result.isConfirmed) {
                                                                // Nếu người dùng xác nhận xóa, gửi yêu cầu xóa sản phẩm
                                                                const xhr = new XMLHttpRequest();
                                                                xhr.open("POST", "process-quantity?productSizeId=" + productId + "&isDelete=1", true); // Gửi yêu cầu xóa
                                                                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                                                                xhr.onreadystatechange = function () {
                                                                    if (xhr.readyState === XMLHttpRequest.DONE) {
                                                                        if (xhr.status === 200) {
                                                                            const response = JSON.parse(xhr.responseText);
                                                                            if (response.success) {
                                                                                // Xóa hàng sản phẩm từ giao diện
                                                                                const productRow = document.getElementById(`row-` + productId);
                                                                                if (productRow) {
                                                                                    productRow.remove(); // Xóa hàng sản phẩm
                                                                                }
                                                                                // Cập nhật tổng tiền giỏ hàng
                                                                                document.querySelector(".cart-total span").innerText = response.total + " VND";
                                                                            } else {
                                                                                // Hiển thị thông báo lỗi nếu không xóa được
                                                                                Swal.fire({
                                                                                    title: 'Error!',
                                                                                    text: response.message,
                                                                                    icon: 'error',
                                                                                    confirmButtonText: 'OK'
                                                                                });
                                                                            }
                                                                        } else {
                                                                            // Xử lý lỗi kết nối hoặc phản hồi không thành công
                                                                            Swal.fire({
                                                                                title: 'Error!',
                                                                                text: 'Đã xảy ra lỗi trong quá trình xử lý yêu cầu. Vui lòng thử lại.',
                                                                                icon: 'error',
                                                                                confirmButtonText: 'OK'
                                                                            });
                                                                        }
                                                                    }
                                                                };

                                                                xhr.send(); // Gửi yêu cầu xóa
                                                            } else {
                                                                // Nếu người dùng không xác nhận, đặt lại giá trị input về 1
                                                                document.getElementById(`form1-` + productId).value = 1;
                                                            }
                                                        });
                                                        return; // Dừng thực hiện hàm
                                                    }
                                                    // Gửi yêu cầu AJAX đến servlet để cập nhật số lượng
                                                    const xhr = new XMLHttpRequest();
                                                    xhr.open("POST", "process-quantity", true); // Thay 'processcart' bằng đường dẫn servlet của bạn
                                                    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                                                    xhr.onreadystatechange = function () {
                                                        if (xhr.readyState === XMLHttpRequest.DONE) {
                                                            if (xhr.status === 200) {
                                                                const response = JSON.parse(xhr.responseText);
                                                                if (response.success) {
                                                                    // Cập nhật tổng tiền trên giao diện
                                                                    document.querySelector(".cart-total span").innerText = response.total + " VND";
                                                                    const totalProductPrice = price * quantity; // Tính tổng cho sản phẩm
                                                                    const productRow = document.getElementById(`row-` + productId);
                                                                    productRow.querySelector(".total-price").innerText = totalProductPrice + " VND"; // Cập nhật giá trị tổng của sản phẩm
                                                                    console.log(productRow);
                                                                    console.log(response.total);
                                                                    console.log(response.productId);
                                                                    console.log(response.quantity1);
                                                                    console.log(response.price);
                                                                    console.log(response.items);
                                                                    // Hiển thị thông báo thành công bằng SweetAlert
//                    Swal.fire({
//                        title: 'Success!',
//                        text: 'Số lượng đã được cập nhật thành công!',
//                        icon: 'success',
//                        confirmButtonText: 'OK'
//                    });
                                                                } else {
                                                                    // Hiển thị thông báo lỗi bằng SweetAlert
                                                                    Swal.fire({
                                                                        title: 'Error!',
                                                                        text: response.message,
                                                                        icon: 'error',
                                                                        confirmButtonText: 'Try Again'
                                                                    });
                                                                    console.log(response.total);
                                                                    console.log(response.quantity1);
                                                                    console.log(response.productId);
                                                                }
                                                            } else {
                                                                // Xử lý lỗi kết nối hoặc phản hồi không thành công
                                                                Swal.fire({
                                                                    title: 'Error!',
                                                                    text: 'Đã xảy ra lỗi trong quá trình xử lý yêu cầu. Vui lòng thử lại.',
                                                                    icon: 'error',
                                                                    confirmButtonText: 'OK'
                                                                });
                                                            }
                                                        }
                                                    };

                                                    xhr.send("id=" + productId + "&quantity=" + quantity);
                                                }


    </script>


</html>
