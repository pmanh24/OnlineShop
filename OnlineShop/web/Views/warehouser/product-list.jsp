<%-- 
    Document   : product-list
    Created on : Sep 27, 2024, 9:40:14 PM
    Author     : Hieu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
                background-color: #4CAF50;
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
                bottom: 20px;
                right: 20px;
                z-index: 999999;
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
            <!-- Begin Page Content -->
            <div class="content">

                <!-- Toast Notification -->
                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div id="toastHeader" class="toast-header">
                        <strong class="me-auto">Notification</strong>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div id="toastBody" class="toast-body"></div>
                </div>

                <!-- Page Heading -->
                <h1 class="h3 mb-2 mt-5 text-gray-800">List of Products</h1>

                <form method="GET" action="warehouser-product-list">
                    <input type="hidden" name="showProduct" value="true" />
                    <div class="form-row align-items-center mb-4">
                        <div class="col-auto">
                            <input type="text" name="search" class="form-control" placeholder="Search by title" value="${param.search}" />
                        </div>
                        <div class="col-auto">
                            <select name="category" class="form-control">
                                <option value="">Select Category</option>
                                <c:forEach var="cat" items="${catList}">
                                    <option value="${cat.categoryId}" <c:if test="${param.category == cat.categoryId}">selected</c:if>>${cat.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-auto">
                            <button type="submit" class="btn btn-primary" style="background-color: #36b9cc">Filter</button>
                        </div>
                    </div>
                </form>

                <!-- DataTales Example -->
                <div class="card shadow mb-4">
                    <div class="card-header py-3 d-flex">
                        <h6 class="m-0 font-weight-bold text-primary" style="flex: 1">Manage Product (${totalProduct})</h6>
<!--                        <a href="warehouser-import-product" class="btn btn-success"  style="background-color: #36b9cc">
                            <i class="fa-solid fa-user-plus"></i> <span>Import New Product</span>
                        </a>-->
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                 <tr class="text-gray-800 text-center">
                                        <th style="width: 5%;" scope="col">#</th>
                                        <th style="width: 5%;" scope="col">Product ID</th>
                                        <th style="width: 25%;" scope="col">
                                            <a href="warehouser-product-list?search=${not empty search ? search : ''}&category=${not empty category ? category : ''}&status=${not empty status ? status : ''}&sortBy=product_name&sortDirection=${param.sortDirection == 'asc' ? 'desc' : 'asc'}">Product Name</a>
                                        </th>
                                        <th style="width: 10%;" scope="col">
                                            <a href="warehouser-product-list?search=${not empty search ? search : ''}&category=${not empty category ? category : ''}&status=${not empty status ? status : ''}&sortBy=category_id&sortDirection=${param.sortDirection == 'asc' ? 'desc' : 'asc'}">Category</a>
                                        </th>
                                        <th style="width: 10%;" scope="col">
                                            <a href="warehouser-product-list?search=${not empty search ? search : ''}&category=${not empty category ? category : ''}&status=${not empty status ? status : ''}&sortBy=original_price&sortDirection=${param.sortDirection == 'asc' ? 'desc' : 'asc'}">Original Price</a>
                                        </th>
                                        <th style="width: 10%;" scope="col">
                                            <a href="warehouser-product-list?search=${not empty search ? search : ''}&category=${not empty category ? category : ''}&status=${not empty status ? status : ''}&sortBy=sale_price&sortDirection=${param.sortDirection == 'asc' ? 'desc' : 'asc'}">Sale Price</a>
                                        </th>
                                        <th style="width: 10%;" scope="col">
                                            <a href="warehouser-product-list?search=${not empty search ? search : ''}&category=${not empty category ? category : ''}&status=${not empty status ? status : ''}&sortBy=number_left&sortDirection=${param.sortDirection == 'asc' ? 'desc' : 'asc'}">Quantity</a>
                                        </th>
                                        <th style="width: 5%;" scope="col">
                                            <a href="warehouser-product-list?search=${not empty search ? search : ''}&category=${not empty category ? category : ''}&status=${not empty status ? status : ''}&sortBy=total_hold&sortDirection=${param.sortDirection == 'asc' ? 'desc' : 'asc'}">Hold</a>
                                        </th>
                                        <th style="width: 10%;" scope="col">Thumbnail</th>
                                        <th style="width: 10%;" scope="col">
                                            <a href="warehouser-product-list?search=${not empty search ? search : ''}&category=${not empty category ? category : ''}&status=${not empty status ? status : ''}&sortBy=featured&sortDirection=${param.sortDirection == 'asc' ? 'desc' : 'asc'}">Featured</a>
                                        </th>
                                        <th style="width: 10%;" scope="col">
                                            <a href="warehouser-product-list?search=${not empty search ? search : ''}&category=${not empty category ? category : ''}&status=${not empty status ? status : ''}&sortBy=status&sortDirection=${param.sortDirection == 'asc' ? 'desc' : 'asc'}">Status</a>
                                        </th>
                                        <th style="width: 10%;" scope="col">Actions</th>
                                    </tr>
                                <tbody>
                                    <c:forEach var="lp" items="${productList}" varStatus="status">
                                        <tr class="text-center">
                                           <td>${status.index + 1}</td> <!-- STT -->
                                            <td>${lp.productId}</td>
                                            <td>${lp.productName}</td> <!-- Tên sản phẩm -->
                                            <td>${lp.category != null ? lp.category.name : 'No category'}</td> <!-- Phân loại -->
                                            <td><fmt:formatNumber value="${lp.originalPrice}" pattern="#,###"/> VND</td> <!-- Giá gốc -->
                                            <td><fmt:formatNumber value="${lp.salePrice}" pattern="#,###"/> VND</td> <!-- Giá bán -->
                                            <td>${lp.numberLeft}</td> <!-- Số lượng -->
                                            <td>${lp.hold}</td> 
                                            <!--<td>da ban</td>--> 
                                            <td>
                                                <img src="${lp.imageUrl}" alt="${lp.productName}" style="width: 50px; height: 50px;" />
                                            </td> <!-- Thumbnail -->
                                            <td>
                                                <label class="switch">
                                                    <input type="checkbox" class="toggle-featured" disabled  ${lp.featured == 1 ? 'checked' : ''}>
                                                    <span class="slider round"></span>
                                                </label>
                                            </td>
                                            <td>
                                                ${lp.status == 1 ? 'Active' : (lp.status == 0 ? 'Stop selling' : 'Coming soon')}
                                            </td>
                                            <td>
                                                <button type="button" class="btn btn-primary" style="background-color: #36b9cc;">
                                                    <a href="warehouser-import-product?productId=${lp.productId}" class="text-light">Import</a>
                                                </button>
                                            </td>
                                        </tr>

                                        <!-- Modal for managing size-color-price variants -->
                                    <div class="modal fade" id="setVariantsModal-${lp.productId}" tabindex="-1" role="dialog" aria-labelledby="setVariantsModalLabel" aria-hidden="true">
                                        <div class="modal-dialog modal-dialog-scrollable modal-lg" id="modalDialog-${lp.productId}" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="setVariantsModalLabel">Manage Variants for ${lp.productName}</h5>
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <form action="warehouser-product-list" method="post">
                                                    <div class="modal-body" id="sizeColorContainer-${lp.productId}">
                                                        <input type="hidden" name="productId" value="${lp.productId}"/>
                                                        <h6>Kích cỡ, Màu sắc và Giá Nhập</h6>

                                                        <c:set var="sizesKey" value="sizes_${lp.productId}" />
                                                        <c:choose>
                                                            <c:when test="${not empty requestScope[sizesKey]}">
                                                                <c:forEach var="size" items="${requestScope[sizesKey]}">
                                                                    <div class="row mb-2 variant-entry">
                                                                        <div class="col-md-3">
                                                                            <input type="text" class="form-control" name="productSize[${lp.productId}][]" value="${size.productSizeName}" placeholder="Kích cỡ (e.g., S, M, L)" required>
                                                                        </div>
                                                                        <div class="col-md-3">
                                                                            <input type="text" class="form-control" name="productColor[${lp.productId}][]" value="${size.productColor}" placeholder="Màu sắc (e.g., Đỏ, Xanh)" required>
                                                                        </div>
                                                                        <div class="col-md-3">
                                                                            <input type="number" class="form-control" name="productQuantity[${lp.productId}][]" value="${size.quantity}" min="0" step="1" placeholder="Số lượng" required>
                                                                        </div>
                                                                        <div class="col-md-3">
                                                                            <input type="number" class="form-control" name="importPrice[${lp.productId}][]" value="${size.importPrice}" min="0" step="0.01" placeholder="Giá Nhập" required>
                                                                        </div>
                                                                    </div>
                                                                </c:forEach>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <!-- Default empty row -->
                                                                <div class="row mb-2 variant-entry">
                                                                    <div class="col-md-3">
                                                                        <input type="text" class="form-control" name="productSize[${lp.productId}][]" placeholder="Kích cỡ (e.g., S, M, L)" required>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <input type="text" class="form-control" name="productColor[${lp.productId}][]" placeholder="Màu sắc (e.g., Đỏ, Xanh)" required>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <input type="number" class="form-control" name="productQuantity[${lp.productId}][]" min="0" step="1" placeholder="Số lượng" required>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <input type="number" class="form-control" name="importPrice[${lp.productId}][]" min="0" step="0.01" placeholder="Giá Nhập" required>
                                                                    </div>
                                                                </div>
                                                            </c:otherwise>
                                                        </c:choose>

                                                        <!-- Button to add more size/color inputs dynamically -->
                                                        <button id="addSizeColorBtn-${lp.productId}" type="button" class="btn btn-primary" onclick="addSizeColor(${lp.productId})">Thêm Kích cỡ và Màu sắc</button>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                        <button type="submit" class="btn btn-primary">Import</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <!-- Pagination -->
                        <div class="d-flex justify-content-center">
                            <nav aria-label="Page navigation example">
                                <c:if test="${not empty productList}">
                                    <ul class="pagination">
                                        <c:if test="${page1.index > 1}">
                                            <li class="page-item">
                                                <a class="page-link" href="warehouser-product-list?search=${not empty search ? search : ''}&category=${not empty category ? category : ''}&index=${page1.index - 1}&sortBy=${param.sortBy}&sortDirection=${param.sortDirection}" aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </li>
                                        </c:if>
                                        <c:forEach var="i" begin="1" end="${page1.totalPage}">
                                            <li class="page-item ${i == page1.index ? 'active' : ''}">
                                                <a class="page-link" href="warehouser-product-list?search=${not empty search ? search : ''}&category=${not empty category ? category : ''}&index=${i}&sortBy=${param.sortBy}&sortDirection=${param.sortDirection}">${i}</a>
                                            </li>
                                        </c:forEach>
                                        <c:if test="${page1.index < page1.totalPage}">
                                            <li class="page-item">
                                                <a class="page-link" href="warehouser-product-list?search=${not empty search ? search : ''}&category=${not empty category ? category : ''}&index=${page1.index + 1}&sortBy=${param.sortBy}&sortDirection=${param.sortDirection}" aria-label="Next">
                                                    <span aria-hidden="true">&raquo;</span>
                                                </a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </c:if>
                                <c:if test="${empty productList}">
                                    <p>No products available.</p>
                                </c:if>
                            </nav>
                        </div>
                    </div>
                </div>
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
            document.addEventListener('DOMContentLoaded', function () {
                function showToast(message, type) {
                    const toastBody = document.getElementById('toastBody');
                    const toastHeader = document.getElementById('toastHeader');

                    if (!toastBody || !toastHeader) {
                        console.error('Toast elements not found!');
                        return;
                    }

                    toastBody.textContent = message;

                    toastHeader.className = 'toast-header text-white ' + (type === 'success' ? 'bg-success' : 'bg-danger');

                    const toast = new bootstrap.Toast(document.getElementById('liveToast'));
                    toast.show();

                    setTimeout(() => {
                        toast.hide();
                    }, 10000); // 10 seconds
                }

                // Access error and success messages from JSP
                var errorMsg = "<%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>";
                var successMsg = "<%= request.getAttribute("success") != null ? request.getAttribute("success") : "" %>";

                if (errorMsg) {
                    showToast(errorMsg, 'danger');
                } else if (successMsg) {
                    showToast(successMsg, 'success');
                }
            });
            function addSizeColor(productId) {
                const container = document.getElementById(`sizeColorContainer-${productId}`);
                const row = document.createElement('div');
                row.className = 'row mb-2 variant-entry';

                row.innerHTML = `
            <div class="col-md-3">
                <input type="text" class="form-control" name="productSize[${productId}][]" placeholder="Kích cỡ (e.g., S, M, L)" required>
            </div>
            <div class="col-md-3">
                <input type="text" class="form-control" name="productColor[${productId}][]" placeholder="Màu sắc (e.g., Đỏ, Xanh)" required>
            </div>
            <div class="col-md-3">
                <input type="number" class="form-control" name="productQuantity[${productId}][]" min="0" step="1" placeholder="Số lượng" required>
            </div>
            <div class="col-md-3">
                <input type="number" class="form-control" name="importPrice[${productId}][]" min="0" step="0.01" placeholder="Giá Nhập" required>
            </div>
        `;
                container.appendChild(row);

                // Adjust modal height if needed based on number of entries
                adjustModalHeight(productId);
            }

            // Function to adjust modal height
            function adjustModalHeight(productId) {
                const variantEntries = document.querySelectorAll(`#sizeColorContainer-${productId} .variant-entry`).length;
                const modalDialog = document.getElementById(`modalDialog-${productId}`);

                if (variantEntries > 5) {
                    modalDialog.style.height = '80vh'; // Example height for large entries
                    modalDialog.classList.add('modal-dialog-scrollable'); // Enable scrolling
                } else {
                    modalDialog.style.height = ''; // Default height
                    modalDialog.classList.remove('modal-dialog-scrollable');
                }
            }
        </script>

        <style>
            /* Optional styling for modal scrolling */
            .modal-dialog-scrollable {
                max-height: 90vh;
                overflow-y: auto;
            }
        </style>
    </body>
</html>