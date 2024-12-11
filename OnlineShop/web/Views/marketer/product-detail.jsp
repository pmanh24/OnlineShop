<%-- 
    Document   : product-detail
    Created on : Oct 3, 2024, 10:49:48 AM
    Author     : Hieu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Detail - Marketer</title>
        <script src="https://kit.fontawesome.com/c203902cd2.js" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
        <link href="assets/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="assets/css/sb-admin-2.min.css" rel="stylesheet">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <style>
            body{
                overflow-x: hidden;
            }
            .navbar1 {
                background-color: #36b9cc;
                padding: 10px;
                display: flex;
                justify-content: flex-end;
                position: fixed;
                width: 100%;
                top: 0;
                z-index: 1000;
            }

            .navbar1 a {
                color: white;
                margin-right: 20px;
                font-size: 18px;
            }

            .switch {
                position: relative;
                display: inline-block;
                width: 60px;
                height: 34px;
            }

            .switch input {
                opacity: 0;
                width: 0;
                height: 0;
            }

            .slider {
                position: absolute;
                cursor: pointer;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background-color: #ccc;
                transition: .4s;
            }

            .slider:before {
                position: absolute;
                content: "";
                height: 26px;
                width: 26px;
                left: 4px;
                bottom: 4px;
                background-color: white;
                transition: .4s;
            }

            input:checked + .slider {
                background-color: #4CAF50; /* Change to your preferred color */
            }

            input:checked + .slider:before {
                transform: translateX(26px);
            }

            /* Rounded sliders */
            .slider.round {
                border-radius: 34px;
            }

            .slider.round:before {
                border-radius: 50%;
            }
            .toast {
                position: fixed;
                top: 20px;
                right: 20px;
                z-index: 1050;
                display: none;
            }
            footer{
                z-index: 999;
            }
        </style>
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
            <div class="navbar1">
                <a href="logout">Logout</a>
            </div>
            <!-- Toast for Success Message -->
            <div id="successToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="toast-header">
                    <strong class="mr-auto">Success</strong>
                    <small class="text-muted">Just now</small>
                    <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="toast-body">
                    <c:if test="${not empty success}">
                        <c:out value="${success}"/>
                    </c:if>
                </div>
            </div>

            <!-- Toast for Error Message -->
            <div id="errorToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="toast-header">
                    <strong class="mr-auto">Error</strong>
                    <small class="text-muted">Just now</small>
                    <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="toast-body">
                    <c:if test="${not empty error}">
                        <c:out value="${error}"/>
                    </c:if>
                </div>
            </div>
            <div class="content mb-4 mt-5">

                <form id="myForm"  class="mb-4" action="product-detail" method="POST" enctype="multipart/form-data">
                    <c:set var="p" value="${product}" />
                    <input type="hidden" name="productId" value="${p.productId}"/>
                    <div class="row">
                        <!-- Left Column -->
                        <div class="col-md-8">
                            <div class="ms-panel ms-panel-fh">
                                <div class="ms-panel-header">
                                    <h6>Input information of product</h6>
                                </div>
                                <div class="ms-panel-body">
                                    <div class="form-row">
                                        <div class="col-md-6 mb-3">
                                            <!-- Product Category -->
                                            <div class="form-group">
                                                <label for="categoryProduct">Category</label>
                                                <div class="input-group">
                                                    <select class="form-control" id="categoryProduct" name="getCateProductId" required>
                                                        <c:forEach items="${requestScope.catList}" var="lcp">
                                                            <option value="${lcp.categoryId}" ${lcp.categoryId == p.categoryId ? 'selected' : ''}>${lcp.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <!-- Number Left -->
                                            <div class="form-group">
                                                <label for="numberLeft">Total quantity</label>
                                                <input type="number" class="form-control" name="numberLeft" min="0" step="1" required value="${not empty p.numberLeft ? p.numberLeft : ''}" readonly>
                                            </div>
                                        </div>

                                    <!-- Product Name -->
                                    <div class="col-md-12 mb-3">
                                        <label for="ProductName">Product Name</label>
                                        <div class="input-group">
                                            <input type="text" class="form-control" id="ProductName" name="productName" placeholder="Nhập tên sản phẩm" value="${p.productName}" required>
                                        </div>
                                    </div>

                                    <!-- Original Price --> 
                                    <div class="col-md-4 mb-3">
                                        <label for="salePrice">Original Price (VNĐ)</label>
                                        <div class="input-group">
                                            <input type="text" readonly class="form-control" id="originalPrice" name="originalPrice" placeholder="Nhập giá bán" value="${p.originalPrice}" required>
                                        </div>
                                    </div>
                                    <!-- Selling Price -->
                                    <div class="col-md-4 mb-3">
                                        <label for="salePrice">Sale Price (VNĐ)</label>
                                        <div class="input-group">
                                            <input type="text" readonly class="form-control" id="salePrice" name="salePrice" placeholder="Nhập giá bán" value="${p.salePrice}" required>
                                        </div>
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <label for="originPrice">Percent Sale (%)</label>
                                        <div class="input-group">
                                            <input type="number" class="form-control" id="salePercent" name="salePercent" 
                                                   placeholder="Input Percent %" min="1" step="1"
                                                   value="${salePercent}" 
                                                   required>
                                        </div>
                                    </div>

                                    <!-- Default Image -->
                                    <div class="col-md-12 mb-4">
                                        <label for="ProImgDefault">Thumbnail</label>
                                        <div class="custom-file">
                                            <input type="file" accept="image/*" class="custom-file-input" name="newProImgDefault" id="ProImgDefault">
                                            <label class="custom-file-label" for="ProImgDefault">Upload Thumbnail</label>
                                            <input type="hidden" name="proImgDefault" value="${p.imageUrl}"/>
                                        </div>
                                    </div>

                                    <!-- Side Images -->
                                    <input type="hidden" name="imageCount" value="${fn:length(productImage)}" />
                                    <c:forEach items="${productImage}" var="pi" varStatus="loop">
                                        <c:set var="count" value="${loop.index + 1}"/>
                                        <div class="col-md-12 mb-4">
                                            <label for="ProImage${count}">Product image ${count}</label>
                                            <div class="custom-file">
                                                <input type="file" accept="image/*" class="custom-file-input" name="newProImage${count}" id="ProImage${count}">
                                                <label class="custom-file-label" for="ProImage${count}">Upload product image ${count}</label>
                                                <input type="hidden" name="productImageId${count}" value="${pi.productImageId}"/>
                                            </div>
                                        </div>
                                    </c:forEach>

                                    <div class="col-md-12 mb-4">
                                        <h6>Sizes and Colors</h6>
                                        <div id="sizeColorContainer">
                                            <c:forEach var="productSize" items="${productSizes}">
                                                <div class="row mb-2">
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label for="productSize">Size</label>
                                                            <input type="text" class="form-control" readonly name="productSize[]" value="${productSize.productSizeName}" placeholder="Size (e.g., S, M, L)" required>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label for="productColor">Color</label>
                                                            <input type="text" class="form-control" readonly name="productColor[]" value="${productSize.productColor}" placeholder="Color (e.g., Đỏ, Xanh)" required>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label for="productQuantity">Quantity</label>
                                                            <input type="number" class="form-control" readonly name="productQuantity[]" value="${productSize.quantity}" min="0" step="1" placeholder="Quantity" required>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label for="importPrice">Import price</label>
                                                            <input type="number" class="form-control" readonly name="importPrice[]" value="${productSize.importPrice}" min="0" step="1" placeholder="" required>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label for="prices">Sale price</label>
                                                            <input type="number" class="form-control" required name="prices[]" value="${productSize.prices}" min="0" step="1" placeholder="Sale price vnd">
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label for="hold">Hold</label>
                                                            <input type="number" readonly class="form-control" required name="hold[]" value="${productSize.hold}" min="0" step="1" placeholder="Hold">
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Right Column -->
                    <div class="col-md-4">
                        <div class="ms-panel ms-panel-fh">
                            <div class="ms-panel-header">
                                <h6>Product Image</h6>
                            </div>
                            <div class="ms-panel-body">
                                <div id="imagesSlider" class="ms-image-slider carousel slide" data-ride="carousel">
                                    <div class="carousel-inner">
                                        <div class="carousel-item active">
                                            <img class="d-block w-100" id="imagePreview0" src="${p.imageUrl}" alt="Ảnh mặc định" style="height: 300px;">
                                        </div>
                                        <c:forEach items="${productImage}" var="pi" varStatus="loop">
                                            <div class="carousel-item">
                                                <img class="d-block w-100" id="imagePreview${loop.index + 1}" src="${pi.imageUrl}" alt="Ảnh bên lề ${loop.index + 1}" style="height: 300px;">
                                            </div>
                                        </c:forEach>
                                    </div>
                                    <ol class="carousel-indicators">
                                        <li data-target="#imagesSlider" data-slide-to="0"><img src="${p.imageUrl}" style="max-width: 100%;"></li>
                                            <c:forEach items="${productImage}" var="pi" varStatus="loop">
                                            <li data-target="#imagesSlider" data-slide-to="${loop.index + 1}"><img src="${pi.imageUrl}" style="max-width: 100%;"></li>
                                            </c:forEach>
                                    </ol>
                                </div>
                                <!-- Brief Information -->
                                <div class="col-md-12 mb-3">
                                    <label for="briefInformation">Brief description</label>
                                    <textarea class="form-control" name="briefInformation" rows="1" placeholder="Nhập mô tả ngắn">${p.briefInformation}</textarea>
                                </div>

                                <!-- Description -->
                                <div class="col-md-12 mb-3">
                                    <label for="description">Description</label>
                                    <div class="input-group">
                                        <textarea rows="4" id="description" name="proDescription" class="form-control" placeholder="Thêm mô tả" required>${p.description}</textarea>
                                    </div>
                                </div>

                                <div class="col-md-12 mb-4">
                                    <label>Nổi bật</label>
                                    <label class="switch">
                                        <input type="checkbox" id="featuredToggle" name="featured" value="1" ${p.featured == 1 ? 'checked' : ''} onchange="toggleFeatured()">
                                        <span class="slider round"></span>
                                    </label>
                                </div>

                                <div class="col-md-12 mb-4">
                                    <label for="statusSelect">Status</label>
                                    <select class="form-control" id="statusSelect" name="status" required>
                                        <option value="1" <c:if test="${p.status == '1'}">selected</c:if>>Active</option>
                                        <option value="0" <c:if test="${p.status == '0'}">selected</c:if>>Stop selling</option>
                                        <option value="2" <c:if test="${p.status == '2'}">selected</c:if>>Coming soon</option>
                                        </select>
                                    </div>

                                <c:if test="${not empty error}">
                                    <div class="alert alert-danger">${error}</div>
                                </c:if>
                                <c:if test="${not empty info}">
                                    <div class="alert alert-info">${info}</div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12 mb-4">
                        <!-- Cancel Button and Update Button -->
                        <div class="col-md-12 mb-4 d-flex justify-content-center">
                            <div class="d-flex justify-content-center" style="width: 50%;">
                                <a href="mkt-product-list" class="btn btn-danger mr-2" style="text-decoration: none; color: white;">Cancel</a>
                                <button type="submit" class="btn btn-primary">Update</button>
                            </div>
                        </div>
                    </div>
            </div>
        </form>
    </div>
    <footer class="sticky-footer bg-white">
        <div class="container my-auto">
            <div class="copyright text-center my-auto">
                <span>Copyright &copy; Online Shop Website 2024</span>
            </div>
        </div>
    </footer>
</div>
<script>
    $(document).ready(function () {
        // Check if the toast should be shown
        var showToast = '${not empty requestScope.success}';
        if (showToast) {
            $('#myToast').toast({
                autohide: true,
                delay: 3000 // Hide after 3 seconds
            }).toast('show');
        }
    });
    $(document).ready(function () {
        // Show toast if message exists
        const successMessage = '<c:out value="${success}"/>';
        const errorMessage = '<c:out value="${error}"/>';

        if (successMessage) {
            $('#successToast').toast('show');
        }

        if (errorMessage) {
            $('#errorToast').toast('show');
        }
    });
    function toggleFeatured() {
        const checkbox = document.getElementById('featuredToggle');
        checkbox.value = checkbox.checked ? '1' : ''; // Set value to 1 if checked, otherwise empty
    }
    ;
    $(document).ready(function () {
        $('#addSizeColorBtn').click(function () {
            const newField = `<div class="row mb-2">
    <div class="col-md-2">
                                                            <span for="productSize">Kích cỡ</span>
        <input type="text" class="form-control" name="productSize[]" placeholder="Kích cỡ (e.g., S, M, L)" required>
    </div>
    <div class="col-md-2">
  <span for="productColor">Màu sắc</span>
        <input type="text" class="form-control" name="productColor[]" placeholder="Màu sắc (e.g., Đỏ, Xanh)" required>
    </div>
    <div class="col-md-2">
   <span for="productQuantity">Số lượng</span>
        <input type="number" class="form-control" name="productQuantity[]" min="0" step="1" placeholder="Số lượng" required>
    </div>
    <div class="col-md-3">
    <span for="importPrice">Giá Nhập</span>
        <input type="number" class="form-control" name="importPrice[]" min="0" step="0.01" placeholder="Giá Nhập" required>
    </div>
    <div class="col-md-3">
 <span for="prices">Giá bán</span>
        <input type="number" class="form-control" name="prices[]" min="0" step="0.01" placeholder="Sale price vnd" required>
    </div>
</div>`;
            $('#sizeColorContainer').append(newField);
        });
    });
</script>


</body>
</html>
