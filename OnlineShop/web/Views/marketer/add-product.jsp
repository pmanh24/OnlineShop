<%-- 
    Document   : add-product
    Created on : Sep 30, 2024, 2:23:02 PM
    Author     : Hieu
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Add Product</title>
        <script src="https://kit.fontawesome.com/c203902cd2.js" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/assets/css/sb-admin-2.min.css" rel="stylesheet">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

        <style>
            .navbar1 {
                background-color: #808080; /* Change background color to grey */
                padding: 10px; /* Padding */
                position: fixed; /* Fixed position for navbar */
                top: 0; /* Stick to the top */
                left: 0; /* Align to the left */
                right: 0; /* Align to the right */
                z-index: 1000; /* Ensure it stays on top */
                display: flex; /* Use Flexbox */
                justify-content: flex-end; /* Align links to the right */
            }
            .navbar1 a {
                color: white; /* Text color */
                margin: 0 15px; /* Space between links */
                text-decoration: none; /* Remove underline */
            }
            .navbar1 a:hover {
                text-decoration: underline; /* Underline on hover */
            }
            /* Styles for image previews */
            .image-preview {
                display: flex;
                flex-wrap: wrap;
                margin-top: 10px;
            }
            .image-preview img {
                width: 100px; /* Adjust as needed */
                height: 100px; /* Adjust as needed */
                margin-right: 10px;
                margin-bottom: 10px;
            }
        </style>
        <script>
            function previewImage(input, previewContainer) {
                const fileList = input.files;
                previewContainer.innerHTML = ""; // Clear previous previews
                for (let i = 0; i < fileList.length; i++) {
                    const file = fileList[i];
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        const img = document.createElement("img");
                        img.src = e.target.result;
                        previewContainer.appendChild(img);
                    };
                    reader.readAsDataURL(file);
                }
            }
            function previewImage(event, previewId) {
                var reader = new FileReader();
                reader.onload = function () {
                    var preview = document.getElementById(previewId);
                    preview.src = reader.result;
                };
                reader.readAsDataURL(event.target.files[0]);
            }

        </script>
    </head>
    <body id="page-top">
        <div id="wrapper">
                      <!-- Sidebar -->
            <ul class="navbar-nav bg-gradient-info sidebar sidebar-dark accordion">
                <a class="sidebar-brand d-flex align-items-center justify-content-center" href="home">
                    <div class="sidebar-brand-icon rotate-n-15">
                        <i class="fas fa-laugh-wink"></i>
                    </div>
                    <div class="sidebar-brand-text mx-3">Marketer</div>
                </a>
                <hr class="sidebar-divider my-0">
                <li class="nav-item active">
                    <a class="nav-link" href="mkt-dashboard">
                        <i class="fas fa-fw fa-tachometer-alt"></i>
                        <span>Dashboard</span>
                    </a>
                </li>
                <hr class="sidebar-divider">
                <div class="sidebar-heading">MANAGEMENT</div>
                <li class="nav-item">
                    <a class="nav-link collapsed" href="mkt-product-list">
                        <i class="fas fa-user fa-sm fa-fw"></i>
                        <span>Product List</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link collapsed" href="postlist">
                        <i class="fa-solid fa-house-chimney-window"></i>
                        <span>Post List</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link collapsed" href="mkt-slider-list">
                        <i class="fa-solid fa-house-chimney"></i>
                        <span>Slider List</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link collapsed" href="mkt-customer-list">
                        <i class="fa-solid fa-house-chimney"></i>
                        <span>Customer List</span>
                    </a>
                </li>

                <li class="nav-item">
                    <a class="nav-link collapsed" href="feedbackList">
                        <i class="fa-solid fa-house-chimney-window"></i>
                        <span>Feedback List</span>
                    </a>
                </li>
            </ul>
            <!-- End of Sidebar -->

            <!-- Header Section -->
            <div class="navbar1 bg-gradient-info">
                <a href="logout">Logout</a>
            </div>


            <!-- Main Content -->
            <div class="content">
                <div class="container-fluid mt-4" style="padding-bottom: 50px;">
                    <h2 class="text-center mb-4">Add New Product</h2>
                    <!-- Toast Notification -->
                    <div class="toast" id="liveToast" style="position: absolute; z-index: 999999; top: 20px; right: 20px;" role="alert" aria-live="assertive" aria-atomic="true">
                        <div class="toast-header" id="toastHeader">
                            <strong class="mr-auto">Notification</strong>
                            <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="toast-body" id="toastBody">This is a toast message!</div>
                    </div>
                    <!-- Error Message -->
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>

                    <form id="myForm" action="mkt-add-product" method="POST" enctype="multipart/form-data">
                        <div class="row">
                            <!-- Left Column -->
                            <div class="col-md-6">
                                <div class="ms-panel ms-panel-fh">
                                    <div class="ms-panel-header">
                                        <h6>Input information of product</h6>
                                    </div>
                                    <div class="ms-panel-body">
                                        <div class="form-row">
                                            <!-- Product Category -->
                                            <div class="col-md-12 mb-3">
                                                <label for="categoryProduct">Category</label>
                                                <div class="input-group">
                                                    <select class="form-control" id="categoryProduct" name="getCateProductId" required>
                                                        <option value="0">Choose a category</option>   
                                                        <c:forEach items="${requestScope.categories}" var="lcp">
                                                            <option value="${lcp.categoryId}">${lcp.name}</option>   
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>

                                            <!-- Product Name -->
                                            <div class="col-md-12 mb-3">
                                                <label for="ProductName">Product Name</label>
                                                <div class="input-group">
                                                    <input type="text" class="form-control" id="ProductName" name="productName" placeholder="Nhập tên sản phẩm" value="${getProName}" required>
                                                </div>
                                            </div>

                                            <!-- Default Image -->
                                            <div class="col-md-12 mb-4">
                                                <label for="ProImgDefault1">Thumbnail</label>
                                                <div class="custom-file">
                                                    <input type="file" accept="image/*" class="custom-file-input" name="proImgDefault" onchange="previewImage(event, 'imagePreview0')" required>
                                                    <label class="custom-file-label" for="ProImgDefault1">Upload thumnail</label>
                                                </div>
                                            </div>

                                            <!-- Side Images -->
                                            <div class="col-md-12 mb-4">
                                                <label for="ProImage1">Product Image 1</label>
                                                <div class="custom-file">
                                                    <input type="file" accept="image/*" class="custom-file-input" name="proImg1" onchange="previewImage(event, 'imagePreview1')" required>
                                                    <label class="custom-file-label" for="ProImage1">Upload product image 1</label>
                                                </div>
                                            </div>
                                            <div class="col-md-12 mb-4">
                                                <label for="ProImage2">Product Image 2</label>
                                                <div class="custom-file">
                                                    <input type="file" accept="image/*" class="custom-file-input" name="proImg2" onchange="previewImage(event, 'imagePreview2')" required>
                                                    <label class="custom-file-label" for="ProImage2">Upload product image 2</label>
                                                </div>
                                            </div>
                                            <div class="col-md-12 mb-4">
                                                <label for="ProImage3">Product Image 3</label>
                                                <div class="custom-file">
                                                    <input type="file" accept="image/*" class="custom-file-input" name="proImg3" onchange="previewImage(event, 'imagePreview3')" required>
                                                    <label class="custom-file-label" for="ProImage3">Upload product image 3</label>
                                                </div>
                                            </div>
                                            <div class="col-md-12 mb-3">
                                                <label for="briefInformation">Brief description</label>
                                                <textarea class="form-control"  name="briefInformation" rows="2"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Right Column -->
                            <div class="col-md-6">
                                <div class="ms-panel ms-panel-fh">
                                    <div class="ms-panel-header">
                                        <h6>Product Image</h6>
                                    </div>
                                    <div class="ms-panel-body">
                                        <div id="imagesSlider" class="ms-image-slider carousel slide" data-ride="carousel">
                                            <div class="carousel-inner">
                                                <div class="carousel-item active">
                                                    <img class="d-block w-100" id="imagePreview0" src="#" alt="Ảnh mặc định" style="height: 300px;">
                                                </div>
                                                <div class="carousel-item">
                                                    <img class="d-block w-100" id="imagePreview1" src="#" alt="Ảnh bên lề 1" style="height: 300px;">
                                                </div>
                                                <div class="carousel-item">
                                                    <img class="d-block w-100" id="imagePreview2" src="#" alt="Ảnh bên lề 2" style="height: 300px;">
                                                </div>
                                                <div class="carousel-item">
                                                    <img class="d-block w-100" id="imagePreview3" src="#" alt="Ảnh bên lề 3" style="height: 300px;">
                                                </div>
                                            </div>
                                            <ol class="carousel-indicators">
                                                <li data-target="#imagesSlider" data-slide-to="0"><img src="#" style="max-width: 100%;"></li>
                                                <li data-target="#imagesSlider" data-slide-to="1"><img src="#" style="max-width: 100%;"></li>
                                                <li data-target="#imagesSlider" data-slide-to="2"><img src="#" style="max-width: 100%;"></li>
                                                <li data-target="#imagesSlider" data-slide-to="3"><img src="#" style="max-width: 100%;"></li>
                                            </ol>
                                        </div>

                                        <div class="col-md-12 mb-3">
                                            <label for="description">Description</label>
                                            <div class="input-group">
                                                <textarea rows="5" id="description" name="proDescription" class="form-control" placeholder="Thêm mô tả" required>${getProDescription}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-12 mb-3">
                                        <label for="status">Status</label>
                                        <div class="input-group">
                                            <select class="form-control" name="proStatus" id="status" readonly>
                                                <!--                                                <option style="color: #20c914;" value="1" selected>Hoạt động</option>            -->
                                                <option style="color: #838383;" value="2">Coming soon</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-12 text-center mb-4">
                            <button type="button" class="btn btn-dark">
                                <a href="mkt-product-list" style="color: white;text-decoration: none">Close</a>
                            </button>
                            <button type="submit" class="btn btn-success mx-5"  style="background-color: #36b9cc">Add product</button>
                        </div>
                </div>


                </form>

            </div>
        </div>

        <h1 class="text-danger">${error}</h1>
        <h1 class="text-success">${success}</h1>
        <!-- End of Main Content -->
        <!-- Footer -->
        <footer class="sticky-footer bg-white">
            <div class="container my-auto">
                <div class="copyright text-center my-auto">
                    <span>Copyright &copy; Online Shop Website 2024</span>
                </div>
            </div>
        </footer>
        <!-- End of Footer -->
        <script>
            function showToast(message, type) {
                const toastBody = document.getElementById('toastBody');
                const toastHeader = document.getElementById('toastHeader');

                // Set the toast message
                toastBody.textContent = message;

                // Set the toast header classes based on the type
                toastHeader.className = 'toast-header text-white ' + (type === 'success' ? 'bg-success' : 'bg-danger');

                // Create and show the toast
                const toast = new bootstrap.Toast(document.getElementById('liveToast'));
                toast.show();

                // Hide the toast after 10 seconds
                setTimeout(() => {
                    toast.hide();
                }, 10000); // 10000 milliseconds = 10 seconds
            }

            window.onload = function () {
                var errorMsg = "${not empty error ? error : ''}"; // JSTL expression to retrieve error message
                var successMsg = "${not empty success ? success : ''}"; // JSTL expression to retrieve success message

                // Show the appropriate toast based on the message present
                if (errorMsg) {
                    showToast(errorMsg, 'danger'); // Display error toast
                } else if (successMsg) {
                    showToast(successMsg, 'success'); // Display success toast
                }
            };


            $(document).ready(function () {
                // Show toast for success message
                if ("${not empty success}") {
                    $("#toastBody").text("${success}");
                    $("#liveToast").toast("show");
                }

                // Show toast for error message
                if ("${not empty error}") {
                    $("#toastBody").text("${error}");
                    $("#liveToast").toast("show");
                }
            });
        </script>

    </body>
</html>
