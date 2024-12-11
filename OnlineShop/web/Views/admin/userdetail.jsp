<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Customer Detail</title>
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

            <div class="content">
                <form id="myForm" action="userdetail" method="POST">
                    <c:set var="c" value="${employee}" />
                    <input type="hidden" name="customerId" value="${c.employeeId}"/>

                    <div class="text-center mb-4 mt-5">
                        <h1>Employee Information Detail</h1>
                    </div>

                    <div class="row">
                        <!-- Left Column -->
                        <div class="col-md-6 form-panel">
                            <div class="card">
                                <div class="card-body">
                                    <div class="form-group">
                                        <!-- Id -->
                                        <label for="employeePhone">Employee Id</label>
                                        <input type="text" class="form-control" id="employeeId" name="employeeId" placeholder="Enter id" value="${c.employeeId}" required readonly>
                                    </div>
                                    <div class="form-group">
                                        <!-- Name -->
                                        <label for="employeeName">Employee Name</label>
                                        <input type="text" class="form-control" id="employeeName" name="employeeName" placeholder="Enter employee name" value="${c.fullName}" required readonly/>
                                    </div>

                                    <div class="form-group">
                                        <!-- Email -->
                                        <label for="employeeEmail">Email</label>
                                        <input type="email" class="form-control" id="employeeEmail" name="employeeEmail" placeholder="Enter email" value="${c.email}" required readonly>
                                    </div>


                                </div>
                            </div>
                        </div>

                        <!-- Right Column -->
                        <div class="col-md-6 form-panel">
                            <div class="card">
                                <div class="card-body">
                                    <div class="form-group">
                                        <!-- Phone -->
                                        <label for="employeePhone">Phone Number</label>
                                        <input type="text" class="form-control" id="employeePhone" name="employeePhone" placeholder="Enter phone" value="${c.phoneNumber}" required readonly>
                                    </div>
                                    <div class="form-group">
                                        <!-- Gender -->
                                        <label for="employeeRole">Gender</label>
                                        <input type="text" class="form-control" id="employeeRole" name="employeeRole" value="${c.gender == true ? "Male":"Female"}" required readonly>
                                    </div>
                                    <div class="form-group">
                                        <!-- Role -->
                                        <label for="employeeRole">Role</label>
                                        <c:if test="${mode == 1}">
                                            <input type="text" class="form-control" id="employeeRole" name="employeeRole" value="${c.role}" readonly >
                                        </c:if>
                                        <c:if test="${mode == 2}">
                                            <select name="role" class="form-control">
                                                <c:forEach items="${roles}" var="r">
                                                    <option value="${r.roleId}" <c:if test="${r.roleId == c.roleId}">selected</c:if>>${r.roleName}</option>
                                                </c:forEach>
                                            </select>
                                        </c:if>
                                        <input type="text" name="mode" value="${mode}" hidden/>
                                    </div>


                                    <!-- Status Display -->
                                    <div class="form-group">

                                        <c:if test="${mode == 2}">
                                            <label class="status-label" for="statusSwitch">Status</label>
                                            <div class="custom-control custom-switch">
                                                <input type="checkbox" class="custom-control-input" id="statusSwitch" name="status" ${c.status == true ? 'checked' : ''}>
                                                <label class="custom-control-label" for="statusSwitch" id="statusLabel">
                                                    ${c.status == true ? 'Active' : 'Inactive'}
                                                </label>
                                                <input type="hidden" name="statusValue" id="statusValue" value="${c.status == true ? '1' : '0'}">
                                            </div>
                                        </c:if>
                                        <c:if test="${mode == 1}">
                                            <div class="ms-panel-footer">
                                                <label class="status-label">Status:</label>
                                                <c:choose>
                                                    <c:when test="${c.status == true}">
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
                        <c:if test="${mode == 1 && c.roleId != 1}">
                            <a href="${pageContext.request.contextPath}/userdetail?employeeId=${c.employeeId}&mode=2" title="Edit" class="btn btn-warning">Update information</a>
                        </c:if>
                        <c:if test="${mode == 2 && c.roleId != 1}">
                            <button type="submit" onclick="callAlert(event, )" class="btn btn-primary">Save</button>
                        </c:if>
                        <a href="userlist" class="btn btn-secondary">Back to list</a>
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
                    statusValue.value = true;
                } else {
                    statusLabel.textContent = 'Inactive';
                    statusValue.value = false;
                }
            });

            function callAlert(event) {
                event.preventDefault(); // Ngăn hành động mặc định của thẻ <a>

                Swal.fire({
                    title: "Are you sure?",
                    text: "This employee information will be updated!",
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
                        text: "This employee has been updated.",
                        icon: "success"
                    });

                    // Xóa giá trị trong localStorage để không hiển thị lại thông báo
                    localStorage.removeItem("updateSuccess");
                }
            };
        </script>