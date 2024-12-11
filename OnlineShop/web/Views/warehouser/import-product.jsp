<%-- 
    Document   : import-product
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
        <title>Import Product</title>
        <script src="https://kit.fontawesome.com/c203902cd2.js" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/assets/css/sb-admin-2.min.css" rel="stylesheet">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

        <style>
            .navbar1 {
                background-color: #36b9cc;
                padding: 10px;
                position: fixed;
                top: 0;
                left: 0;
                right: 0;
                z-index: 1000;
                display: flex;
                justify-content: flex-end;
            }
            .navbar1 a {
                color: white;
                margin: 0 15px;
                text-decoration: none;
            }
            .navbar1 a:hover {
                text-decoration: underline;
            }
            .image-preview {
                display: flex;
                flex-wrap: wrap;
                margin-top: 10px;
            }
            .image-preview img {
                width: 100px;
                height: 100px;
                margin-right: 10px;
                margin-bottom: 10px;
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
                    <div class="sidebar-brand-text mx-3">Warehouse</div>
                </a>
                <hr class="sidebar-divider my-0">
                <li class="nav-item active">
                    <a class="nav-link" href="#">
                        <i class="fas fa-fw fa-tachometer-alt"></i>
                        <span>Dashboard</span>
                    </a>
                </li>
                <hr class="sidebar-divider">
                <div class="sidebar-heading">MANAGEMENT</div>
                <li class="nav-item">
                    <a class="nav-link collapsed" href="warehouser-order-list">
                        <i class="fas fa-box fa-sm fa-fw"></i>
                        <span>Order List</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link collapsed" href="warehouser-product-list">
                        <i class="fas fa-box fa-sm fa-fw"></i>
                        <span>Product List</span>
                    </a>
                </li>
            </ul>
            <!-- End of Sidebar -->

            <!-- Header Section -->
            <div class="navbar1">
                <a href="logout">Logout</a>
            </div>

            <!-- Main Content -->
            <div class="content" style="padding-bottom: 50px;">
                <h2 class="text-center mb-4 mt-4">Import New Product</h2>

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


                <c:set var="product" value="${product}"/>
                <form id="myForm" action="warehouser-import-product" method="POST">
                    <div class="row">
                        <!-- Left Column -->
                        <div class="col-md-7">
                            <div class="ms-panel ms-panel-fh">
                                <div class="ms-panel-header mt-4">
                                    <h6>Input information of product</h6>
                                </div>
                                <div class="ms-panel-body">
                                    <div class="form-row">
                                        <div class="col-md-12 mb-4">
                                            <h6>Size, Color, Import Price</h6>
                                            <div id="sizeColorContainer">
                                                <c:forEach var="size" items="${sizes}">
                                                    <div class="row mb-2 d-flex align-items-center">
                                                        <div class="col-md-2    ">
                                                            <span for="productSize">Size</span>
                                                            <input type="text" class="form-control" name="productSize[]" value="${size.productSizeName}" placeholder="Size (e.g., S, M, L)" required>
                                                        </div>
                                                        <div class="col-md-2">
                                                            <span for="productColor">Color</span>
                                                            <input type="text" class="form-control" name="productColor[]" value="${size.productColor}" placeholder="Color (e.g., Đỏ, Xanh)" required>
                                                        </div>
                                                        <div class="col-md-2">
                                                            <span for="productQuantity">Quantity</span>
                                                            <input type="number" class="form-control" name="productQuantity[]" value="${size.quantity}" min="0" step="1" placeholder="Quantity" required>
                                                        </div>
                                                        <div class="col-md-2">
                                                            <span for="importPrice">Import Price</span>
                                                            <input type="number" class="form-control" name="importPrice[]" value="${size.importPrice}" min="0" step="1" placeholder="Import price" required>
                                                        </div>
                                                        <div class="col-md-2">
                                                            <span for="prices">Sale Price</span>
                                                            <input type="number" class="form-control" readonly name="prices[]" value="${size.prices}" min="0" step="1" placeholder="Sale price" required>
                                                        </div>
                                                        <div class="col-md-2">
                                                            <label for="hold">Hold</label>
                                                            <input type="number" readonly class="form-control" required name="hold[]" value="${size.hold}" min="0" step="1" placeholder="Hold">
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                            <button type="button" class="btn btn-secondary mt-2" id="addSizeColorBtn">Thêm Kích cỡ/Màu sắc</button>
                                            <input type="hidden" name="productId" value="${product.productId}"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Right Column -->
                        <div class="col-md-5">
                            <div class="col-md-12 mb-4">
                                <div class="ms-panel-header mt-4">
                                    <h6>Information inputted</h6>
                                </div>
                                <h6>Product</h6>
                                <table class="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th>Product ID</th>
                                            <th>Product Name</th>
                                            <th>Create Date</th>
                                        </tr>
                                    </thead>
                                    <tbody id="productInfoTableBody">
                                        <tr>
                                            <td>${product.productId}</td>
                                            <td>${product.productName}</td>
                                            <td>${product.createDate}</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <!-- Action Buttons -->
                    <div class="col-lg-12 text-center mb-4">
                        <button type="button" class="btn btn-dark">
                            <a href="warehouser-product-list" style="color: white; text-decoration: none;">Close</a>
                        </button>
                        <button type="submit" class="btn btn-success mx-5" style="background-color: #36b9cc;">Import Product</button>
                    </div>
                </form>
                <!-- Display success and error messages -->
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>
                <c:if test="${not empty success}">
                    <div class="alert alert-success">${success}</div>
                </c:if>
            </div>
            <!-- End of Main Content -->

            <!-- Footer -->
            <footer class="sticky-footer bg-white">
                <div class="container my-auto">
                    <div class="copyright text-center my-auto">
                        <span>Copyright &copy; Online Shop Website 2024</span>
                    </div>
                </div>
            </footer>

            <!-- Scripts -->
            <script>
                function showToast(message, type) {
                    const toastBody = document.getElementById('toastBody');
                    const toastHeader = document.getElementById('toastHeader');
                    toastBody.textContent = message;
                    toastHeader.className = 'toast-header text-white ' + (type === 'success' ? 'bg-success' : 'bg-danger');
                    $('#liveToast').toast({delay: 3000});
                    $('#liveToast').toast('show');
                }
                $(document).ready(function () {
                    $('#addSizeColorBtn').click(function () {
                        const newField = `<div class="row mb-2 d-flex align-items-center">
            <div class="col-md-2">
                <label for="productSize">Size</label>
                <input type="text" class="form-control" name="productSize[]" placeholder="Size (e.g., S, M, L)" required>
            </div>
            <div class="col-md-2">
                <label for="productColor">Color</label>
                <input type="text" class="form-control" name="productColor[]"  placeholder="Color (e.g., Đỏ, Xanh)" required>
            </div>
            <div class="col-md-2">
                <label for="productQuantity">Quantity</label>
                <input type="number" class="form-control" name="productQuantity[]" min="0" step="1" placeholder="Quantity" required>
            </div>
            <div class="col-md-2">
                <label for="importPrice">Import Price</label>
                <input type="number" class="form-control" name="importPrice[]" min="0" step="1" placeholder="Import price" required>
            </div>
            <div class="col-md-2">
                <label for="prices">Sale Price</label>
                <input type="number"  readonly class="form-control" name="prices[]" min="0" value="0" step="1" placeholder="" required>
            </div>
            <div class="col-md-2">
                <label for="hold">Hold</label>
                <input type="number" readonly class="form-control" required name="hold[]" value="0" min="0" step="1" placeholder="Hold">
            </div>
        </div>`;
                        $('#sizeColorContainer').append(newField);
                    });
                });
            </script>
    </body>
</html>
