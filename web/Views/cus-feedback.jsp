<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Feedback Page</title>
        <!-- Font Awesome Icons -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="Resource/css/menu.css"/>

        <style>
            /* Star rating */
            .rate {
                float: left;
                height: 46px;
                padding: 0 10px;
            }
            .rate:not(:checked) > input {
                position: absolute;
                top: -9999px;
            }
            .rate:not(:checked) > label {
                float: right;
                width: 1em;
                cursor: pointer;
                font-size: 30px;
                color: #ccc;
            }
            .rate:not(:checked) > label:before {
                content: '★ ';
            }
            .rate > input:checked ~ label {
                color: #ffc700;
            }
            .rate:not(:checked) > label:hover,
            .rate:not(:checked) > label:hover ~ label {
                color: #deb217;
            }
            .product-image-wrapper {
                overflow: hidden; /* Prevent image overflow */
            }

            .card {
                transition: transform 0.2s; /* Add a transition effect */
            }

            .card:hover {
                transform: scale(1.05); /* Scale the card on hover */
            }
            .carousel-indicators {
                list-style: none; /* Removes default numbering */
                padding: 0;
                margin: 0;
            }

        </style>
    </head>
    <body>
        <!-- Header section -->
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


        <!-- Main content section -->
        <div class="container mt-5">
            <div class="row">
                <div class="text-danger">
                    <h1 class="text-danger">${errorMessage}</h1>
                </div>

                <!-- Ordered products section (left side) -->
                <div class="col-md-4">
                    <h5 class="mb-4">Your Ordered Product:</h5>
                    <div class="row">
                        <c:set var="product" value="${orderedProducts}" />
                        <input type="hidden" name="orderId" value="${orderId}"/>
                        <a href="productdetail?productId=${product.productId}" style="text-decoration: none">
                            <div class="card shadow-sm h-100">
                                <img src="${product.imageUrl}" alt="${product.productName}" class="d-block w-100" style="height: 300px; object-fit: cover;">
                                <div class="card-body text-center">
                                    <h5 class="card-title">${product.productName}</h5>
                                    <p class="card-text">
                                        <fmt:formatNumber value="${product.salePrice}" type="currency" currencySymbol="₫"/>
                                    </p>
                                    <div class="sizes">
                                        <strong>Size and Color:</strong>
                                        <ul class="list-unstyled">
                                            <c:forEach var="size" items="${product.sizes}">
                                                <li>${size.productSizeName} - ${size.productColor}</li>
                                                </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>
                </div>

                <!-- Feedback form section (right side) -->
                <div class="col-md-8">
                    <h2 class="mb-4">Feedback</h2>
                    <form action="cus-feedback" method="POST" enctype="multipart/form-data">
                        <input type="hidden" name="orderId" value="${orderId}"/>
                        <input type="hidden" name="productId" value="${productId}"/>

                        <!-- User Details (Pre-filled) -->
                        <div class="row mb-4">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="fullName">Full Name</label>
                                    <input type="text" name="fullName" class="form-control" value="${customer.fullName}" readonly required>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="email">Email</label>
                                    <input type="email" name="email" class="form-control" value="${customer.email}" readonly required>
                                </div>
                            </div>
                        </div>
                        <div class="row mb-4">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="gender">Gender</label>
                                    <input type="text" class="form-control" value="${customer.gender == 'Male' ? 'Male' : 'Female'}" readonly required />
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="mobile">Mobile</label>
                                    <input type="tel" name="mobile" class="form-control" readonly value="${customer.phoneNumber}" required>
                                </div>
                            </div>
                        </div>

                        <div class="row mb-4">
                            <div class="col-md-6">
                                <div class="form-group d-flex  align-items-center">
                                    <label for="rate" class="me-3">Rate</label>
                                    <div class="rate">
                                        <c:choose>
                                            <c:when test="${not empty feedback}">
                                                <input type="radio" id="star5" name="rate_${productId}" value="5" ${feedback.rated_star == 5 ? 'checked' : ''}>
                                                <label for="star5"></label>
                                                <input type="radio" id="star4" name="rate_${productId}" value="4" ${feedback.rated_star == 4 ? 'checked' : ''}>
                                                <label for="star4"></label>
                                                <input type="radio" id="star3" name="rate_${productId}" value="3" ${feedback.rated_star == 3 ? 'checked' : ''}>
                                                <label for="star3"></label>
                                                <input type="radio" id="star2" name="rate_${productId}" value="2" ${feedback.rated_star == 2 ? 'checked' : ''}>
                                                <label for="star2"></label>
                                                <input type="radio" id="star1" name="rate_${productId}" value="1" ${feedback.rated_star == 1 ? 'checked' : ''}>
                                                <label for="star1"></label>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="radio" id="star5" name="rate_${productId}" value="5"><label for="star5"></label>
                                                <input type="radio" id="star4" name="rate_${productId}" value="4"><label for="star4"></label>
                                                <input type="radio" id="star3" name="rate_${productId}" value="3"><label for="star3"></label>
                                                <input type="radio" id="star2" name="rate_${productId}" value="2"><label for="star2"></label>
                                                <input type="radio" id="star1" name="rate_${productId}" value="1"><label for="star1"></label>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="size" class="form-label">Size:</label>
                                    <input hidden type="text" name="size" class="form-control" readonly value="${product.sizes[0].productSizeId}" required>
                                    <small class="form-text text-muted">${product.sizes[0].productSizeName} - ${product.sizes[0].productColor}</small>
                                </div>
                            </div>
                        </div>


                        <!-- Feedback Content -->
                        <div class="form-group mb-4">
                            <textarea name="feedContent_${productId}" class="form-control" rows="4" placeholder="Share your feedback..." required>${feedback != null ? feedback.content : ''}</textarea>
                        </div>

                        <!-- Image Upload -->
                        <div class="form-group mb-4">
                            <label for="feedbackImages_${productId}">Attach Images</label>
                            <input type="file" name="feedbackImage_${productId}" class="form-control" accept=".png, .jpg, .jpeg, .gif" multiple>
                        </div>

                        <!-- Display Uploaded Images -->
                        <c:if test="${not empty feedback && not empty feedback.imageFileNames}">
                            <div class="uploaded-images">
                                <label>Uploaded Images:</label>
                                <div class="image-gallery">
                                    <c:forEach var="image" items="${feedback.imageFileNames}">
                                        <div class="image-container">
                                            <img src="data:image/png;base64,${image}" alt="Feedback Image" style="max-width: 100px; max-height: 100px;"/>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${not empty successMessage}">
                            <div class="text-light mt-3">
                                <h5 class="alert alert-success">${successMessage}</h5>
                            </div>
                        </c:if>
                        <div class="mb-4 mt-2">
                            <button type="button" class="btn btn-danger">
                                <a href="orderdetail?orderId=${orderId}" style="color: white; text-decoration: none">Close</a>
                            </button>
                            <c:choose>
                                <c:when test="${existedfeedback}">
                                    <button type="submit" class="btn btn-primary">Update Feedback</button>
                                </c:when>
                                <c:otherwise>
                                    <button type="submit" class="btn btn-primary">Add Feedback</button>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </form>

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


        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
