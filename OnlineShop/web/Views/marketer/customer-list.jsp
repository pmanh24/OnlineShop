<%-- 
    Document   : customer-list
    Created on : Oct 17, 2024, 7:27:19 AM
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
            .dot {
                display: inline-block;
                width: 10px;
                height: 10px;
                border-radius: 50%;
                margin-right: 5px;
            }

            .contact {
                background-color: green;
            }

            .potential {
                background-color: yellow;
            }

            .customer {
                background-color: blue;
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
                <h1 class="h3 mb-2 text-gray-800 mt-5">List of Customers</h1>

                <form method="GET" action="mkt-customer-list">
                    <input type="hidden" name="showProduct" value="true" />
                    <div class="form-row align-items-center mb-4">
                        <div class="col-auto">
                            <input type="text" name="search" class="form-control" placeholder="Search by full name, email, mobile" value="${param.search}" />
                        </div>
                        <div class="col-auto">
                            <select name="status" class="form-control">
                                <option value="">Select Status</option>
                                <option value="2" <c:if test="${param.status == '2'}">selected</c:if>>Active</option>
                                <option value="0" <c:if test="${param.status == '0'}">selected</c:if>>Inactive</option>
<!--                                <option value="2" <c:if test="${param.status == '2'}">selected</c:if>>Potential</option>-->
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
                            <h6 class="m-0 font-weight-bold text-primary" style="flex: 1">Manage Customer (${totalCustomer})</h6>
                        <!--                        <a href="mkt-add-product" class="btn btn-success"  style="background-color: #36b9cc">
                                                    <i class="fa-solid fa-user-plus"></i> <span>Add New Customer</span>
                                                </a>-->
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                    <tr class="text-gray-800 text-center">
                                        <th style="width: 5%;" scope="col">STT</th>
                                        <th style="width: 20%;" scope="col">
                                            <a href="mkt-customer-list?sortBy=full_name&sortDirection=${param.sortBy == 'full_name' && param.sortDirection == 'asc' ? 'desc' : 'asc'}">Họ và tên</a>
                                        </th>
                                        <th style="width: 10%;" scope="col">Giới tính</th>
                                        <th style="width: 20%;" scope="col">
                                            <a href="mkt-customer-list?sortBy=email&sortDirection=${param.sortBy == 'email' && param.sortDirection == 'asc' ? 'desc' : 'asc'}">Email</a>
                                        </th>
                                        <th style="width: 10%;" scope="col">
                                            <a href="mkt-customer-list?sortBy=phone_number&sortDirection=${param.sortBy == 'phone_number' && param.sortDirection == 'asc' ? 'desc' : 'asc'}">Số điện thoại</a>
                                        </th>
                                        <th style="width: 15%;" scope="col">Địa chỉ</th>
                                        <th style="width: 15%;" scope="col">
                                            <a href="mkt-customer-list?sortBy=status&sortDirection=${param.sortBy == 'status' && param.sortDirection == 'asc' ? 'desc' : 'asc'}">Trạng thái</a>
                                        </th>
                                        <th style="width: 10%;" scope="col">Hành động</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="customer" items="${customerList}" varStatus="status">
                                        <tr class="text-center">
                                            <td>${status.index + 1}</td> <!-- STT -->
                                            <td>${customer.fullName}</td> <!-- Full Name -->
                                            <td>
                                                <c:choose>
                                                    <c:when test="${customer.gender}">
                                                        Nam <!-- Male -->
                                                    </c:when>
                                                    <c:otherwise>
                                                        Nữ <!-- Female -->
                                                    </c:otherwise>
                                                </c:choose>
                                            </td> <!-- Gender -->
                                            <td>${customer.email}</td> <!-- Email -->
                                            <td>${customer.phoneNumber}</td> <!-- Phone Number -->
                                            <td>${customer.address}</td> <!-- Address -->
                                            <td>
                                                <c:choose>
                                                    <c:when test="${customer.status == 2}">
                                                        <span class="dot contact"></span>Active
                                                    </c:when>

                                                     <c:when test="${customer.status == 0}">
                                                        <span class="dot customer"></span>Inactive
                                                    </c:when>
                                                </c:choose>
                                            </td> <!-- Status -->
                                            <td>
                                                <div style="display: flex; gap: 5px;">
                                                    <a href="${pageContext.request.contextPath}/mkt-customer-detail?customerId=${customer.customerId}"  title="Xem chi tiet" class="btn btn-success btn-sm">
                                                        <i class="fas fa-eye"></i> <!-- Eye icon -->
                                                    </a>
                                                    <!--<a href="${pageContext.request.contextPath}/mkt-customer-edit?customerId=${customer.customerId}" class="btn btn-warning btn-sm">Sửa</a>-->
                                                </div>
                                            </td> <!-- Action -->
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <!-- Pagination -->
                        <div class="d-flex justify-content-center">
                            <nav aria-label="Page navigation example">
                                <c:if test="${not empty customerList}">
                                    <ul class="pagination">
                                        <c:if test="${page1.index > 1}">
                                            <li class="page-item">
                                                <a class="page-link" href="mkt-customer-list?search=${param.search}&status=${param.status}&index=${page1.index - 1}&sortBy=${param.sortBy}&sortDirection=${param.sortDirection}" aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </li>
                                        </c:if>
                                        <c:forEach var="i" begin="1" end="${totalPage}">
                                            <li class="page-item ${i == page1.index ? 'active' : ''}">
                                                <a class="page-link" href="mkt-customer-list?search=${param.search}&status=${param.status}&index=${i}&sortBy=${param.sortBy}&sortDirection=${param.sortDirection}">${i}</a>
                                            </li>
                                        </c:forEach>
                                        <c:if test="${page1.index < totalPage}">
                                            <li class="page-item">
                                                <a class="page-link" href="mkt-customer-list?search=${param.search}&status=${param.status}&index=${page1.index + 1}&sortBy=${param.sortBy}&sortDirection=${param.sortDirection}" aria-label="Next">
                                                    <span aria-hidden="true">&raquo;</span>
                                                </a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </c:if>
                                <c:if test="${empty customerList}">
                                    <td class="text-center">
                                        No products available.
                                    </td>
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
        </script>
    </body>
</html>