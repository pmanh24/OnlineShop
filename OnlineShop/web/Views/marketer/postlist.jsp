<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Post List</title>
        <script src="https://kit.fontawesome.com/c203902cd2.js" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/assets/css/sb-admin-2.min.css" rel="stylesheet">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <style>
            body {
                font-family: 'Nunito', sans-serif;
                background-color: #f8f9fc;
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

            .content {
                margin-top: 80px;
                margin-left: 250px; /* Adjust this value to create space from the sidebar */
            }

            .sidebar {
                width: 250px; /* Set a fixed width for the sidebar */
                position: fixed;
                top: 0;
                left: 0;
                height: 100%;
                z-index: 999999;
                background-color: #36b9cc;
                padding-top: 20px;
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

            .container {
                margin-left: 260px; /* Creates space between content and sidebar */
                padding: 20px;
            }

            .search-container {
                display: flex;
                justify-content: left;
                align-items: center;
                margin-bottom: 20px;
            }

            .search-box,
            .dropdown,
            button {
                padding: 10px;
                margin-right: 10px;
                border-radius: 5px;
                border: 1px solid #ccc;
            }

            button {
                background-color: #36b9cc;
                color: white;
                border: none;
                padding: 10px 20px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            button:hover {
                background-color: #258391;
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

            .btn {
                background-color: #1cc88a;
                color: white;
                padding: 5px 10px;
                border-radius: 5px;
                text-decoration: none;
            }

            .btn:hover {
                background-color: #17a673;
            }

            .mt-4.text-center .btn {
                background-color: #36b9cc;
                display: ruby;
            }

            .mt-4.text-center .btn:hover {
                background-color: #2c9faf;
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
            <div class="navbar1">
                <a href="logout">Logout</a>
            </div>

            <!-- Main Content -->
            <div class="content">
                <div class="search-container">
                    <form action="postlist" method="POST" class="d-flex">
                        <input type="text" name="searchTitle" placeholder="Search by Title" required="" class="search-box" value="${param.searchTitle}">
                        <button type="submit">Search</button>
                    </form>
                    <form action="postlist" method="post" class="d-flex ml-4">
                        <select required="" name="category" class="dropdown">
                            <option value="">Select Category</option>
                            <c:forEach var="category" items="${category}">
                                <option value="${category.categoryId}">${category.name}</option>
                            </c:forEach>
                        </select>
                        <button type="submit">Filter</button>
                    </form>
                    <a href="postlist" class="btn ml-4">Back to Post List</a>
                </div>
                <!-- Add New Blog Button -->
                

                <!-- Table Data -->
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th><a href="postlist?page=${currentPage}&sort=${param.sort == 'asc'?'desc':'asc'}&orderby=updateDate">Update Date</a></th>
                            <th>Detail</th>
                            <th>Category</th>
                            <th>Image</th>
                            <th><a href="postlist?page=${currentPage}&sort=${param.sort == 'asc'?'desc':'asc'}&orderby=status">Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="blog" items="${blogs}">
                            <tr>
                                <td>${blog.title}</td>
                                <td>${blog.updateDate}</td>
                                <td>${blog.detail}...</td>
                                <td>${blog.categoryName}</td>
                                <td class="text-center">
                                    <c:choose>
                                        <c:when test="${not empty blog.image}">
                                            <img src="${blog.image}" alt="Image" style="width: 50px; height: 50px;">
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fas fa-times"></i>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${blog.status == 1}">
                                            Active
                                        </c:when>
                                        <c:when test="${blog.status == 0}">
                                            Inactive
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td class="text-center">
                                    <a href="postdetail?blogId=${blog.blogId}" class="btn btn-success">Edit</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    
                </table>
                <div class="pagination">
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <a href="postlist?page=${i}&sort=${param.sort}&orderby=${param.orderby}" class="${i == currentPage ? 'active' : ''}">${i}</a>
                    </c:forEach>
                </div>
                
                <div class="mt-4 text-center">
                    <a href="addpost" class="btn btn-primary">Add New Blog</a>
                </div>
            </div>
                        
                        
        </div>

        <!-- Footer Section -->
        <footer class="footer">
            <span>&copy; 2023 All Rights Reserved by Company</span>
        </footer>

    </body>
</html>
