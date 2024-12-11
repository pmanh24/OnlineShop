<%-- 
    Document   : customer-detail
    Created on : Oct 17, 2024, 11:45:39 AM
    Author     : Hieu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer Detail</title>
        <script src="https://kit.fontawesome.com/c203902cd2.js" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
        <link href="assets/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="assets/css/sb-admin-2.min.css" rel="stylesheet">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <style>
            body {
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
            .toast {
                position: fixed;
                top: 20px;
                right: 20px;
                z-index: 1050;
                display: none;
            }
            .status-label {
                font-weight: bold;
                font-size: 1.1em;
            }
            .purchase-amount {
                font-size: 1.2em;
                color: #28a745; /* Change color to indicate importance */
            }
            .form-panel {
                margin-bottom: 20px;
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
            <div id="successToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-delay="3000">
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
            <div id="errorToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-delay="3000">
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


            <div class="content mb-4">
                <form id="myForm" action="mkt-customer-detail" method="POST">
                    <c:set var="c" value="${customer}" />
                    <input type="hidden" name="customerId" value="${c.customerId}"/>

                    <div class="ms-panel-header text-center mb-4" style="margin-top: 5%">
                        <h6>Thông tin cá nhân khách hàng</h6>
                    </div>

                    <div class="row">
                        <!-- Left Column -->
                        <div class="col-md-6 form-panel">
                            <div class="ms-panel ms-panel-fh">
                                <div class="ms-panel-body">
                                    <div class="form-row">
                                        <!-- Customer Name -->
                                        <div class="col-md-12 mb-3">
                                            <label for="customerName">Tên khách hàng</label>
                                            <input type="text" class="form-control" id="customerName" name="customerName" placeholder="Nhập tên khách hàng" value="${c.fullName}" readonly required>
                                        </div>

                                        <!-- Customer Email -->
                                        <div class="col-md-12 mb-3">
                                            <label for="customerEmail">Email</label>
                                            <input type="email" class="form-control" id="customerEmail" name="customerEmail" placeholder="Nhập email" value="${c.email}" required readonly>
                                        </div>

                                        <!-- Customer Phone -->
                                        <div class="col-md-12 mb-3">
                                            <label for="customerPhone">Số điện thoại</label>
                                            <input type="text" class="form-control" id="customerPhone" name="customerPhone" placeholder="Nhập số điện thoại" value="${c.phoneNumber}"  readonly required>
                                        </div>

                                        <!-- Customer Address -->
                                        <div class="col-md-12 mb-3">
                                            <label for="customerAddress">Địa chỉ</label>
                                            <input type="text" class="form-control" id="customerAddress" name="customerAddress" placeholder="Nhập địa chỉ" value="${c.address}" readonly required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Right Column -->
                        <div class="col-md-6 form-panel">
                            <div class="ms-panel ms-panel-fh">
                                <div class="ms-panel-body">
                                    <div class="form-row">
                                        <!-- Additional Info -->
                                        <div class="col-md-12 mb-3">
                                            <label for="additionalInfo">Thông tin bổ sung</label>
                                            <textarea class="form-control" id="additionalInfo" name="note" rows="5">${c.note}</textarea>
                                        </div>
                                    </div>
                                </div>
                                <!-- Status Display -->
                                <div class="ms-panel-footer">
                                    <label class="status-label">Trạng thái:</label>
                                    <c:choose>
                                        <c:when test="${c.status == '2'}">
                                            <span class="text-success">Active</span>
                                        </c:when>
                                        <c:when test="${c.status == '0'}">
                                            <span class="text-danger">InActive</span>
                                        </c:when>
                                    </c:choose>
                                </div>
                                <div class="ms-panel-footer">
                                    <!-- Purchase Amount -->
                                    <div class="form-group">
                                        <label class="purchase-amount">Tổng số tiền đã mua: 
                                            <fmt:formatNumber value="${totalPurchaseAmount}" type="currency" currencySymbol="₫"/>
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <c:if test="${not empty success}">
                        <h1 class="text-success">${success}</h1>
                    </c:if>
                    <c:if test="${not empty error}">
                        <h1 class="text-danger">${error}</h1>
                    </c:if>
                    <!-- Buttons -->
                    <div class="form-group text-center">
                        <button type="submit" class="btn btn-primary" style="background-color: #36b9cc">Cập nhật thông tin</button>
                        <a href="mkt-customer-list" class="btn btn-secondary">Trở lại</a>
                    </div>
                </form>
            </div>
        </div>
    </body>
    <script>
        $(document).ready(function () {
            // Check if there's a success message and show the toast
            if ($("#successToast .toast-body").text().trim().length > 0) {
                $('#successToast').toast({delay: 3000});
                $('#successToast').toast('show');
            }

            // Check if there's an error message and show the toast
            if ($("#errorToast .toast-body").text().trim().length > 0) {
                $('#errorToast').toast({delay: 3000});
                $('#errorToast').toast('show');
            }
        });
    </script>

</html>
