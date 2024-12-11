<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sale Management</title>
        <script src="https://kit.fontawesome.com/c203902cd2.js" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/assets/css/sb-admin-2.min.css" rel="stylesheet">
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
            /* Adjust the content margin to account for the fixed navbar */
            .content {
                margin-top: 60px; /* Add space for the navbar */
            }
        </style>
    </head>
    <body id="page-top">
        <c:set var="c" value="${session.employee}"></c:set>

            <div id="wrapper">
                <!-- Sidebar -->
                <ul class="navbar-nav bg-gradient-info sidebar sidebar-dark accordion">
                    <a class="sidebar-brand d-flex align-items-center justify-content-center" href="home">
                        <div class="sidebar-brand-icon rotate-n-15">
                            <i class="fas fa-laugh-wink"></i>
                        </div>
                        <div class="sidebar-brand-text mx-3">Sale</div>
                    </a>
                    <hr class="sidebar-divider my-0">
                    <li class="nav-item active">
                        <a class="nav-link" href="index.html">
                            <i class="fas fa-fw fa-tachometer-alt"></i>
                            <span>Dashboard</span>
                        </a>
                    </li>
                    <hr class="sidebar-divider">
                    <div class="sidebar-heading">MANAGEMENT</div>
                    <li class="nav-item">
                        <a class="nav-link collapsed" href="order-list">
                            <i class="fas fa-user fa-sm fa-fw"></i>
                            <span>Order List</span>
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
                    <div class="container-fluid">
                    <c:choose>
                        <c:when test="${not empty param.showProduct}">
                            <div class="row">
                                <div class="col-12">
                                    <jsp:include page="order-list.jsp"></jsp:include> <!-- Adjusted path -->
                                    </div>
                                </div>
                        </c:when>
                        <c:otherwise>
                            <div class="row">
                                <h2 class="text-center">Please select an option from the menu.</h2>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <!-- End of Main Content -->
        </div>

        <!-- Footer -->
        <footer class="sticky-footer bg-white" style="z-index: 1000">
            <div class="container my-auto">
                <div class="copyright text-center my-auto">
                    <span>Copyright &copy; Online Shop Website 2024</span>
                </div>
            </div>
        </footer>
        <!-- End of Page Wrapper -->
    </body>
</html>
