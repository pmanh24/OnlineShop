<%-- 
    Document   : productDetails
    Created on : Sep 21, 2024, 10:33:57 PM
    Author     : GIGABYTE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Details</title>
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
        <style>
            .radio-options {
                margin-top: 10px;
            }

            .radio-label {
                display: block;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
                margin-bottom: 5px;
                cursor: pointer;
                transition: background-color 0.3s, transform 0.2s;
            }

            .radio-label:hover {
                background-color: #f0f0f0; /* Hover background color */
                transform: scale(1.02); /* Slight zoom on hover */
            }

            .radio-label input[type="radio"] {
                display: none; /* Hide the original radio button */
            }

            .radio-custom {
                display: inline-block;
                padding-left: 25px; /* Space for content */
                position: relative;
            }

            .radio-custom:before {
                content: "";
                position: absolute;
                left: 0;
                top: 50%;
                transform: translateY(-50%);
                width: 15px;
                height: 15px;
                border: 2px solid #007bff; /* Border color for radio */
                border-radius: 50%;
                background-color: white; /* Background color for radio */
                transition: background-color 0.2s;
            }

            input[type="radio"]:checked + .radio-custom:before {
                background-color: #007bff; /* Background color when selected */
            }

            .rate {
                float: left;
                height: 46px;
                padding: 0 10px;
            }

            .rate:not(:checked) > input {
                position: absolute;
                top: -9999px; /* Hide unchecked radios */
            }

            .rate:not(:checked) > label {
                float: right;
                width: 1em;
                cursor: pointer;
                font-size: 30px;
                color: #ccc; /* Unchecked star color */
            }

            .rate:not(:checked) > label:before {
                content: '★ '; /* Star character */
            }

            .rate > input:checked ~ label {
                color: #ffc700; /* Checked star color */
            }

            .rate:not(:checked) > label:hover,
            .rate:not(:checked) > label:hover ~ label {
                color: #deb217; /* Hover color for stars */
            }

            .star {
                color: gold; /* Color for filled stars */
                font-size: 1.5rem; /* Adjust size of stars */
                margin: 0 2px; /* Spacing between stars */
            }

            .feedback-item {
                border: 1px solid #e0e0e0; /* Light border around feedback items */
                border-radius: 8px; /* Rounded corners */
                transition: transform 0.2s; /* Smooth transition for hover effect */
            }

            .feedback-item:hover {
                transform: scale(1.02); /* Slightly scale up on hover */
            }
            .ps-popup__container {
                position: fixed;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                width: 80%;
                max-width: 600px;
                background-color: white;
                border-radius: 8px;
                box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
                z-index: 1000;
                padding: 20px;
                overflow-y: auto;
                display: none; /* Initially hidden */
            }

            .ps-popup__close {
                float: right;
                background: transparent;
                border: none;
                font-size: 20px;
                cursor: pointer;
            }

            .popup-size-guide {
                text-align: left;
            }

            .size-guide__title {
                font-size: 24px;
                margin-bottom: 15px;
            }

            .size-guide__table-wrap {
                margin-bottom: 15px;
            }

            .size-guide__table {
                width: 100%;
                border-collapse: collapse;
            }

            .size-guide__table-heading {
                background-color: #f2f2f2;
                text-align: left;
                padding: 10px;
            }

            .size-guide__table-row td {
                padding: 10px;
                border: 1px solid #ddd;
            }

            .size-guide__footer {
                margin-top: 15px;
            }

            .size-guide__link {
                color: #007bff;
                text-decoration: none;
            }

            .size-guide__link:hover {
                text-decoration: underline;
            }

            .u-bold {
                font-weight: bold;
                margin: 10px 0;
            }
            #displayPrice {
                transition: all 0.3s ease;
            }
        </style>
        <script>
            function updatePrice(price) {
                document.getElementById('displayPrice').innerHTML = price + ' VND';
            }
        </script>
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
        <section class="pt-5 pb-0">
            <div class="container">
                <div class="row gx-5">
                    <div class="col-lg-6 d-flex justify-content-center">
                        <div id="productCarousel" class="carousel slide" data-bs-ride="carousel" style="width: 70%;">
                            <div class="carousel-inner border rounded-4 mb-3" style="height: 70%;">
                                <c:forEach var="image" items="${images}">
                                    <div class="carousel-item <c:if test="${image == images[0]}">active</c:if>" style="height: 80vh;">
                                            <a class="rounded-4" target="_blank">
                                                <img class="rounded-4" style="height: 100%; width: 100%; object-fit: cover;" src="${image.imageUrl}" alt="${detail.productName}" />
                                        </a>
                                    </div>
                                </c:forEach>
                            </div>
                            <!-- Carousel controls -->
                            <button class="carousel-control-prev" type="button" data-bs-target="#productCarousel" data-bs-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="visually-hidden">Previous</span>
                            </button>
                            <button class="carousel-control-next" type="button" data-bs-target="#productCarousel" data-bs-slide="next">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="visually-hidden">Next</span>
                            </button>
                        </div>
                    </div>

                    <main class="col-lg-6">
                        <div class="ps-lg-3">
                            <h4 class="title text-dark">
                                ${detail.productName}
                            </h4>                           
                            <div class="mb-3">
                                <span class="h5">Information</span> : ${detail.description}                              
                            </div>
                            <div class="mb-3">
                                <span class="h6">Price:</span>
                                <span class="h6" id="displayPrice">${detail.salePrice} VND</span>
                            </div>
                            <div class="mb-3">
                                <span class="h6" >Original Price:</span>
                                <span class="h6" style="font-style: italic; text-decoration: line-through;">
                                    ${detail.originalPrice} VND
                                </span> <br>
                            </div>

                            <form action="productdetail" method="post">
                                <input type="hidden" name="selectedPrice" value="${detail.salePrice}" />

                                <hr/>
                                <div class="row mb-4">
                                    <div class="col-md-8 col-6">
                                        <span class="h5">Option:</span>
                                        <!--<button type="button" class="btn btn-link" onclick="toggleSizeGuide()">Size Guide</button>  Size Guide button -->
                                        <div class="radio-options">
                                            <c:forEach var="s" items="${size}">
                                                <label class="radio-label">
                                                    <input required="" type="radio" name="productSize" value="${s.productSizeId}" 
                                                           <c:if test="${s.quantity == 0}" >disabled</c:if> 
                                                           onclick="updatePrice('${s.prices}'); this.form.selectedPrice.value = '${s.prices}'">
                                                    <span class="radio-custom">
                                                        <span style="font-style: italic">Size:  </span>${s.productSizeName}, 
                                                        <span style="font-style: italic">Color:  </span>${s.productColor} 
                                                        <span style="font-style: italic">Available: </span>${s.quantity}
                                                    </span>
                                                    <input type="hidden" name="quantity_${s.productSizeId}" value="${s.quantity}" />
                                                </label>
                                            </c:forEach>
                                        </div>
                                    </div>

                                    <input type="text" name="pid" value="${detail.productId}" hidden/>
                                    <!-- col.// -->
                                    <div class="col-md-4 col-6 mb-3">
                                        <label class="mb-2 d-block">Quantity</label>
                                        <div class="input-group mb-3" style="width: 150px;">

                                            <input name="pquantity" value="1" type="number" min="1" class="form-control text-center border border-secondary" aria-label="Example text with button addon" aria-describedby="button-addon1" />

                                        </div>
                                    </div>
                                </div>
                                <button type="submit" class="btn btn-primary shadow-0"> <i class="me-1 fa fa-shopping-basket"></i> Add to cart </button>
                                <h5 style="color: red">${mess}</h5>
                            </form>
                        </div>
                    </main>
                </div>
            </div>
        </section>
                            <section class="container pb-4" style="margin-top: -170px">
            <div class="row">
                <div class="col-lg-8 offset-lg-2">
                    <h4 class="mb-4">Customer Feedback</h4>

                    <div class="feedback-list">
                        <c:forEach var="feedback" items="${feedbackList}">
                            <div class="feedback-item card p-3 mb-3">
                                <div class="card-body d-flex">
                                    <!-- Left Side: Feedback Information -->
                                    <div class="flex-grow-1">
                                        <div class="d-flex align-items-center">
                                            <h5 class="card-title me-2">Feedback by: <c:out value="${feedback.customer}"/></h5>
                                            <small class="text-muted">
                                                Posted on:
                                                <fmt:parseDate value="${feedback.createDate}" pattern="yyyy-MM-dd HH:mm:ss" var="parsedDate" />
                                                <fmt:formatDate value="${parsedDate}" pattern="dd-MM-yyyy HH:mm:ss" />
                                            </small>

                                        </div>
                                        <div class="d-flex align-items-center">
                                            <p class="card-text"><strong>Rating:</strong></p>
                                            <div class="rate mb-2">
                                                <input type="radio" value="5" <c:if test="${feedback.rated_star == 5}">checked</c:if> disabled><label></label>
                                                <input type="radio" value="4" <c:if test="${feedback.rated_star == 4}">checked</c:if> disabled><label></label>
                                                <input type="radio" value="3" <c:if test="${feedback.rated_star == 3}">checked</c:if> disabled><label></label>
                                                <input type="radio" value="2" <c:if test="${feedback.rated_star == 2}">checked</c:if> disabled><label></label>
                                                <input type="radio" value="1" <c:if test="${feedback.rated_star == 1}">checked</c:if> disabled><label></label>
                                                </div>
                                            </div>
                                            <p class="card-text"><strong>Size:</strong> <c:out value="${feedback.productSizeName} - ${feedback.productColor}"/></p>
                                        <p class="card-text"><strong>Content:</strong> <c:out value="${feedback.content}"/></p>

                                    </div>

                                    <!-- Right Side: Main Image Display -->
                                    <c:if test="${not empty feedback.imageFileNames}">
                                        <div class="feedback-images mt-2">
                                            <strong>Images:</strong>
                                            <div class="d-flex flex-wrap">
                                                <c:forEach var="image" items="${feedback.imageFileNames}">
                                                    <img src="data:image/png;base64,${image}" 
                                                         alt="Feedback Image" 
                                                         class="img-thumbnail me-2 mb-2" 
                                                         style="width: 100px; height: auto; cursor: pointer;" 
                                                         onclick="openModal('${image}')">
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </c:forEach>

                        <c:if test="${empty feedbackList}">
                            <p class="text-muted text-center">No feedback available for this product yet.</p>
                        </c:if>
                    </div>
                </div>
            </div>

            <!-- Modal for Viewing Images -->
            <div class="modal fade" id="imageModal" tabindex="-1" role="dialog" aria-labelledby="imageModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="imageModalLabel">View Image</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <img id="modalImage" src="" alt="Modal Image" class="img-fluid">
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <div class="ps-popup__container" id="sizeGuidePopup" style="display: none;">
            <button type="button" class="ps-popup__close icon-close" onclick="toggleSizeGuide()"></button>
            <div class="popup-size-guide">
                <h2 class="size-guide__title">Women's Size Guide</h2>
                <div class="sizeChartWrap">
                    <div class="size-guide__table-wrap">
                        <table class="size-guide__table">
                            <tr class="size-guide__table-row">
                                <th class="size-guide__table-heading">SIZE</th>
                                <th class="size-guide__table-heading">Chest (cm)</th>
                                <th class="size-guide__table-heading">Waist (cm)</th>
                                <th class="size-guide__table-heading">Hip (cm)</th>
                            </tr>
                            <!-- Size data rows -->
                            <tr class="size-guide__table-row"><td>22</td><td>122-126</td><td>106-110</td><td>131-135</td></tr>
                            <!-- Add more rows as needed -->
                        </table>
                    </div>
                </div>
                <p class="u-bold">Measuring Advice</p>
                <p>The fit of your clothes depends on the cut, fabric and styling of the garment...</p>
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
        function openModal(imageSrc) {
            // Set the image source in the modal
            document.getElementById('modalImage').src = "data:image/png;base64," + imageSrc;
            // Show the modal
            var myModal = new bootstrap.Modal(document.getElementById('imageModal'));
            myModal.show();
        }

        function toggleSizeGuide() {
            var popup = document.getElementById("sizeGuidePopup");
            if (popup.style.display === "none" || popup.style.display === "") {
                popup.style.display = "block";
            } else {
                popup.style.display = "none";
            }
        }
    </script>
    <script>
  var currentFeedbackIndex = 2;
  var feedbackCount = ${feedbackCount};

  function showMoreFeedback() {
    var feedbackList = document.querySelectorAll('.feedback-item');
    var showCount = 3;

    for (var i = currentFeedbackIndex + 1; i < currentFeedbackIndex + showCount + 1 && i < feedbackCount; i++) {
      feedbackList[i].style.display = 'block';
    }

    currentFeedbackIndex += showCount;

    if (currentFeedbackIndex >= feedbackCount - 1) {
      document.querySelector('.btn-primary').style.display = 'none';
    }
  }
</script>
</html>
