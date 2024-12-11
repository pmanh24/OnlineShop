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
            /* Adjust the content margin to account for the fixed navbar */
            .content {
                margin-top: 60px; /* Add space for the navbar */
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
            .sidebar a {
                color: white;
                text-decoration: none;
                display: block;
                padding: 15px;
            }

            .sidebar a:hover {
                background-color: #258391;
            }

            h1 {
                text-align: center;
            }

            .search-container {
                display: flex; /* Align search box and dropdown in a row */
                justify-content: left; /* Center them */
                margin-bottom: 20px; /* Space below the search bar */
                align-items: center; /* Align items vertically */
            }

            .search-box {
                padding: 10px;
                font-size: 16px;
                border: 1px solid #ccc; /* Border for search box */
                border-radius: 4px; /* Rounded corners */
                margin-right: 10px; /* Space between search box and dropdown */
            }

            .status-label {
                margin-right: 10px; /* Space between label and dropdown */
            }

            .dropdown {
                padding: 10px;
                font-size: 16px;
                border: 1px solid #ccc; /* Border for dropdown */
                border-radius: 4px; /* Rounded corners */
            }
            .item-list {
                list-style-type: none; /* Remove bullet points */
                padding: 0; /* Remove default padding */
            }

            .item {
                margin: 10px 0; /* Space between items */
                padding: 10px;
                border: 1px solid #ccc; /* Optional: border around items */
                border-radius: 5px; /* Optional: rounded corners */
            }

            .info-container {
                display: flex; /* Use flexbox to align items */
                align-items: center; /* Center items vertically */
            }

            .item-text {
                margin: 5px 0; /* Space between text items */
                flex-grow: 1; /* Allow text to take remaining space */
            }

            .status-label {
                margin-right: 20px; /* Space between label and switch */

            }

            .switch {
                position: relative;
                display: flex; /* Use flexbox for alignment */
                align-items: left; /* Center items vertically */
                margin-right: 50px
            }

            .switch input {
                opacity: 0; /* Hide the default checkbox */
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
                background-color: #ccc; /* Off state */
                transition: .4s;
                border-radius: 20px; /* Rounded slider */
                height: 24px; /* Increase height of the switch */
                width: 54px
            }

            .slider:before {
                position: absolute;
                content: "";
                height: 24px; /* Increase height of the knob */
                width: 24px; /* Increase width of the knob */
                left: 2px; /* Position of the switch knob */
                bottom: 2px;
                background-color: white; /* Knob color */
                transition: .4s;
                border-radius: 50%; /* Round knob */
            }

            input:checked + .slider {
                background-color: #2196F3; /* On state */
            }

            input:checked + .slider:before {
                transform: translateX(26px); /* Adjust to account for the bigger knob */
            }

            .big-image {
                margin-top: 10px; /* Space above the image */
                max-width: 100%; /* Responsive image */
                height: auto; /* Maintain aspect ratio */
            }
            footer {
                background-color: #f8f9fc;
                padding: 20px 0;
                text-align: center;
                margin-top: 30px;
                border-top: 1px solid #e3e6f0;
            }

            footer span {
                color: #5a5c69;
            }
            table {
                width: 100%;
                background-color: white;
                border-collapse: collapse;
                margin-top: 20px;
                border-radius: 8px;
                overflow: hidden;
            }

            table th,
            table td {
                padding: 15px;
                text-align: center;
                border-bottom: 1px solid #ddd;
            }

            table th {
                background-color: #36b9cc;
                color: white;
            }

            table td {
                background-color: #f8f9fc;
            }
            .pagination {
                display: flex;
                justify-content: center;
                margin-top: 20px;
            }

            .pagination a {
                padding: 10px;
                margin: 5px;
                border: 1px solid #ddd;
                text-decoration: none;
                color: #333;
            }

            .pagination a.active {
                background-color: #36b9cc;
                color: white;
                border: 1px solid #36b9cc;
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
            <div class="navbar1 bg-gradient-info">
                <a href="logout">Logout</a>
            </div>


            <!-- Main Content -->
            <div class="content">
                <c:set var="sliders" value="${requestScope.sliderList}"/>
                <h1>Sliders List</h1>
                <div class="search-container">
                    <form action="mkt-slider-list" method="get">
                        <input type="text" placeholder="Search..." class="search-box" name="key" onchange="this.form.submit()">
                    </form>
                    <label for="status-dropdown" class="status-label">Status:</label>
                    <form action="mkt-slider-list" method="get">
                        <select class="dropdown" name="status" onchange="this.form.submit()">                       
                            <option value="0" ${index==0?"selected":""}>All</option>
                            <option value="1" ${index==1?"selected":""}>Active</option>
                            <option value="2" ${index==2?"selected":""}>InActive</option>
                        </select>
                    </form>
                </div>

                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Slider ID</th>
                            <th>Title</th>
                            <th>Back link</th>
                            <th>Image</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="s" items="${sliderList}">
                            <tr>
                                <td>${s.getSliderId()}</td>
                                <td>${s.getTitle()}</td>
                                <td>${s.getBackLink()}</td> 
                                <td class="text-center">
                                    <c:choose>
                                        <c:when test="${not empty s.getImageUrl()}">
                                            <img src="${s.getImageUrl()}" alt="Image" style="width: 50px; height: 50px;">
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fas fa-times"></i>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${s.getStatusNum() == 1}">
                                            Active
                                        </c:when>
                                        <c:otherwise>
                                            Inactive
                                        </c:otherwise>                                                                                 
                                    </c:choose>
                                </td>
                                <td class="text-center">
                                    <a href="sliderdetail?sliderId=${s.getSliderId()}" class="btn btn-success">Edit</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>

                </table>
                <div class="pagination">
                    <c:forEach var="i" begin="1" end="1">
                        <a href="mkt-slider-list?page=${i}" class="${i == 1 ? 'active' : ''}">${i}</a>
                    </c:forEach>
                </div>
            </div>
            <!-- End of Main Content -->
        </div>
        <!-- Footer -->
        <footer class="sticky-footer bg-white">
            <div class="container my-auto">
                <div class="copyright text-center my-auto">
                    <span>Copyright &copy; Online Shop Website 2024</span>
                </div>
            </div>
        </footer>
        <!-- End of Footer -->
    </body>
</html>
