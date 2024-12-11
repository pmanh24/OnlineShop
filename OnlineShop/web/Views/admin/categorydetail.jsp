<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Category detail</title>
        <script src="https://kit.fontawesome.com/c203902cd2.js" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
        <link href="assets/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="assets/css/sb-admin-2.min.css" rel="stylesheet">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
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

            .custom-control-label::before {
                background-color: #ddd; /* Màu nền khi chưa chọn */
            }

            .custom-control-input:checked~.custom-control-label::before {
                background-color: #28a745; /* Màu khi "Active" */
            }

            .custom-control-input:not(:checked)~.custom-control-label::before {
                background-color: #ffc107; /* Màu khi "Inactive" */
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
                    <div class="sidebar-brand-text mx-3">Admin</div>
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
                    <a class="nav-link collapsed" href="userlist">
                        <i class="fas fa-user fa-sm fa-fw"></i>
                        <span>User List</span>
                    </a>
                    <a class="nav-link collapsed" href="category-list">
                        <i class="fas fa-users"></i>
                        <span>Category List</span>
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

            

            <div class="content">
                <form id="myForm" action="category-detail" method="POST">
                    <c:set var="c" value="${category}" />
                    <input type="hidden" name="categoryId" value="${c.getCategoryId()}"/>

                    <div class="text-center mb-4 mt-5">
                        <h1>Category Information Detail</h1>
                    </div>

                    <div class="row">
                        <!-- Left Column -->
                        <div class="col-md-6 form-panel">
                            <div class="card">
                                <div class="card-body">
                                    <div class="form-group">
                                        <!-- Id -->
                                        <label for="employeePhone">Category Id</label>
                                        <input type="text" class="form-control" id="categoryId" name="categoryId" placeholder="Enter id" value="${c.getCategoryId()}" required readonly>
                                    </div>
                                    <div class="form-group">
                                        <!-- Name -->
                                        <label for="employeeName">Category Name</label>
                                        <input type="text" class="form-control" id="categoryName" name="categoryName" placeholder="Enter category name" value="${c.getName()}" />
                                    </div>                                   
                                </div>
                            </div>
                        </div>

                        <!-- Right Column -->
                        <div class="col-md-6 form-panel">
                            <div class="card">
                                <div class="card-body">                                   
                                    <!-- Status Display -->
                                    <div class="form-group">

                                        <c:if test="${mode == 2}">
                                            <label class="status-label" for="statusSwitch">Status</label>
                                            <div class="custom-control custom-switch">
                                                <input type="checkbox" class="custom-control-input" id="statusSwitch" name="status" ${c.getStatus() == 1 ? 'checked' : ''}>
                                                <label class="custom-control-label" for="statusSwitch" id="statusLabel">
                                                    ${c.getStatus() == 1 ? 'Active' : 'Inactive'}
                                                </label>
                                                <input type="hidden" name="statusValue" id="statusValue" value="${c.getStatus() == 1 ? '1' : '0'}">
                                            </div>
                                        </c:if>
                                        <c:if test="${mode == 1}">
                                            <div class="ms-panel-footer">
                                                <label class="status-label">Status:</label>
                                                <c:choose>
                                                    <c:when test="${c.getStatus() == 1}">
                                                        <span class="text-success">Active</span>
                                                    </c:when>

                                                    <c:otherwise>
                                                        <span class="text-warning">Inactive</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </c:if>

                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Buttons -->
                    <div class="text-center mt-4">                       
                        <c:if test="${mode == 2}">
                            <button type="submit" onclick="callAlert(event, )" class="btn btn-primary">Save</button>
                        </c:if>
                        <a href="category-list" class="btn btn-secondary">Back to list</a>
                    </div>
                </form>
            </div>
        </div>

        <script>

            document.getElementById('statusSwitch').addEventListener('change', function () {
                var statusLabel = document.getElementById('statusLabel');
                var statusValue = document.getElementById('statusValue');
                if (this.checked) {
                    statusLabel.textContent = 'Active';
                    statusValue.value = 1;
                } else {
                    statusLabel.textContent = 'Inactive';
                    statusValue.value = 0;
                }
            });

            function callAlert(event) {
                event.preventDefault(); // Ngăn hành động mặc định của thẻ <a>

                Swal.fire({
                    title: "Are you sure?",
                    text: "This category information will be updated!",
                    icon: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#3085d6",
                    cancelButtonColor: "#d33",
                    confirmButtonText: "Yes!"
                }).then((result) => {
                    if (result.isConfirmed) {
                        // Lưu thông báo success vào localStorage
                        localStorage.setItem("updateSuccess", "true");

                        // Điều hướng sang trang mới
                        document.getElementById("myForm").submit();
                    }
                });
            }
            ;

            window.onload = function () {
                // Kiểm tra xem có thông báo success trong localStorage hay không
                if (localStorage.getItem("updateSuccess") === "true") {
                    // Hiển thị thông báo thành công
                    Swal.fire({
                        title: "Updated!",
                        text: "This category has been updated.",
                        icon: "success"
                    });

                    // Xóa giá trị trong localStorage để không hiển thị lại thông báo
                    localStorage.removeItem("updateSuccess");
                }
            };
        </script>