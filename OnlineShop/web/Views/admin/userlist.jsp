

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
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
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
                    <div class="sidebar-brand-text mx-3">Admin</div>
                </a>
                <hr class="sidebar-divider my-0">
                <li class="nav-item active">
                    <a class="nav-link" href="admin">
                        <i class="fas fa-fw fa-tachometer-alt"></i>
                        <span>Dashboard</span>
                    </a>
                </li>
                <hr class="sidebar-divider">
                <div class="sidebar-heading">MANAGEMENT</div>
                <li class="nav-item">
                    <a class="nav-link collapsed" href="userlist">
                        <i class="fas fa-users"></i>
                        <span>User List</span>
                    </a>
                    <a class="nav-link collapsed" href="category-list">
                        <i class="fas fa-users"></i>
                        <span>Category list</span>
                    </a>
                </li>
                <!--                <li class="nav-item">
                                    <a class="nav-link collapsed" href="postlist">
                                        <i class="fa-solid fa-house-chimney-window"></i>
                                        <span>Danh sách bài đăng</span>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link collapsed" href="mkt-slider-list">
                                        <i class="fa-solid fa-house-chimney"></i>
                                        <span>Danh sách Slider</span>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link collapsed" href="mkt-customer-list">
                                        <i class="fa-solid fa-house-chimney"></i>
                                        <span>Danh sách khách hàng</span>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link collapsed" href="feedbackList">
                                        <i class="fa-solid fa-house-chimney-window"></i>
                                        <span>Danh sách phản hồi</span>
                                    </a>
                                </li>-->
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
                <h1 class="h3 mb-2 text-gray-800 mt-5">List of Employees</h1>

                <form method="GET" action="userlist">

                    <div class="form-row align-items-center mb-4">
                        <div class="col-auto">
                            <input type="text" name="search" class="form-control" placeholder="Search by full name, email, mobile" value="${param.search}" />
                        </div>
                        <div class="col-auto">
                            <select name="gender" class="form-control">
                                <option value="">Select Gender</option>
                                <option value="1" <c:if test="${param.gender == '1'}">selected</c:if>>Male</option>
                                <option value="0" <c:if test="${param.gender == '0'}">selected</c:if>>Female</option>
                                </select>
                            </div>
                            <div class="col-auto">
                                <select name="role" class="form-control">
                                    <option value="">Select Role</option>
                                <c:forEach items="${roles}" var="r">
                                    <option value="${r.roleId}" <c:if test="${param.role == r.roleId}">selected</c:if>>${r.roleName}</option>
                                </c:forEach>
                            </select>
                        </div>  
                        <div class="col-auto">
                            <select name="status" class="form-control">
                                <option value="">Select Status</option>
                                <option value="1" <c:if test="${param.status == '1'}">selected</c:if>>Active</option>
                                <option value="0" <c:if test="${param.status == '0'}">selected</c:if>>Inactive</option>
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
                            <h6 class="m-0 font-weight-bold text-primary" style="flex: 1">Manage Employee (${totalEmployee})</h6>
                        <a href="new-employee" class="btn btn-success"  style="background-color: #36b9cc">
                            <i class="fa-solid fa-user-plus"></i> <span>Add New Employee</span>
                        </a>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                    <tr class="text-gray-800 text-center">
                                        <th style="width: 5%;" scope="col">
                                            <a href="userlist?sortBy=employee_id&sortDirection=${param.sortBy == 'employee_id' && param.sortDirection == 'asc' ? 'desc' : 'asc'}">Id</a>
                                        </th>
                                        <th style="width: 20%;" scope="col">
                                            <a href="userlist?sortBy=full_name&sortDirection=${param.sortBy == 'full_name' && param.sortDirection == 'asc' ? 'desc' : 'asc'}">Full Name</a>
                                        </th>
                                        <th style="width: 10%;" scope="col">
                                            <a href="userlist?sortBy=gender&sortDirection=${param.sortBy == 'gender' && param.sortDirection == 'asc' ? 'desc' : 'asc'}">Gender</a>
                                        </th>
                                        <th style="width: 20%;" scope="col">
                                            <a href="userlist?sortBy=email&sortDirection=${param.sortBy == 'email' && param.sortDirection == 'asc' ? 'desc' : 'asc'}">Email</a>
                                        </th>
                                        <th style="width: 10%;" scope="col">
                                            <a href="userlist?sortBy=phone_number&sortDirection=${param.sortBy == 'phone_number' && param.sortDirection == 'asc' ? 'desc' : 'asc'}">Mobile</a>
                                        </th>
                                        <th style="width: 15%;" scope="col">
                                            <a href="userlist?sortBy=role_id&sortDirection=${param.sortBy == 'role_id' && param.sortDirection == 'asc' ? 'desc' : 'asc'}">Role</a>
                                        </th>
                                        <th style="width: 15%;" scope="col">
                                            <a href="userlist?sortBy=status&sortDirection=${param.sortBy == 'status' && param.sortDirection == 'asc' ? 'desc' : 'asc'}">Status</a>
                                        </th>
                                        <th style="width: 10%;" scope="col">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="emp" items="${employeeList}" varStatus="status">
                                        <tr class="text-center">
                                            <td>${emp.employeeId}</td> <!-- STT -->
                                            <td>${emp.fullName}</td> <!-- Full Name -->
                                            <td>
                                                <c:choose>
                                                    <c:when test="${emp.gender}">
                                                        Male <!-- Male -->
                                                    </c:when>
                                                    <c:otherwise>
                                                        Female <!-- Female -->
                                                    </c:otherwise>
                                                </c:choose>
                                            </td> <!-- Gender -->
                                            <td>${emp.email}</td> <!-- Email -->
                                            <td>${emp.phoneNumber}</td> <!-- Phone Number -->
                                            <td>${emp.role}</td> <!-- Role -->

                                            <td>
                                                <c:choose>
                                                    <c:when test="${emp.status}">
                                                        Active <!-- Male -->
                                                    </c:when>
                                                    <c:otherwise>
                                                        Inactive <!-- Female -->
                                                    </c:otherwise>
                                                </c:choose>
                                            </td> <!-- Gender -->
                                            <td>
                                                <div style="display: flex; gap: 5px;">
                                                    <a href="${pageContext.request.contextPath}/userdetail?employeeId=${emp.employeeId}&mode=1"  title="View detail" class="btn btn-success btn-sm">
                                                        <i class="fas fa-eye"></i> <!-- Eye icon -->
                                                    </a>
                                                    <c:if test="${emp.roleId != 1}">
                                                        <a href="${pageContext.request.contextPath}/userdetail?employeeId=${emp.employeeId}&mode=2" title="Edit" class="btn btn-warning btn-sm"><i class="fas fa-edit"></i></a>
                                                        </c:if>

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
                                <c:if test="${not empty employeeList}">
                                    <ul class="pagination">
                                        <c:if test="${page1.index > 1}">
                                            <li class="page-item">
                                                <a class="page-link" href="userlist?search=${param.search}&status=${param.status}&index=${page1.index - 1}&sortBy=${param.sortBy}&sortDirection=${param.sortDirection}" aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </li>
                                        </c:if>
                                        <c:forEach var="i" begin="1" end="${totalPage}">
                                            <li class="page-item ${i == page1.index ? 'active' : ''}">
                                                <a class="page-link" href="userlist?search=${param.search}&status=${param.status}&index=${i}&sortBy=${param.sortBy}&sortDirection=${param.sortDirection}">${i}</a>
                                            </li>
                                        </c:forEach>
                                        <c:if test="${page1.index < totalPage}">
                                            <li class="page-item">
                                                <a class="page-link" href="userlist?search=${param.search}&status=${param.status}&index=${page1.index + 1}&sortBy=${param.sortBy}&sortDirection=${param.sortDirection}" aria-label="Next">
                                                    <span aria-hidden="true">&raquo;</span>
                                                </a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </c:if>
                                <c:if test="${empty employeeList}">
                                    <td class="text-center">
                                        No employee available.
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
<script>


    function createSuccess() {
        // Hiển thị thông báo thành công
        Swal.fire({
            title: "Created!",
            text: "New employee has been created.",
            icon: "success"
        });
    }

    // Kiểm tra thuộc tính từ phía server
    var createSuccessValue = ${createSuccess}; // Biến này sẽ có giá trị true hoặc false

    if (createSuccessValue) {
        createSuccess();
    }


</script>