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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

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
                margin-left: 449px; /* Creates space between content and sidebar */
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
                display: block;
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
                <!-- Search and Filter Form -->
                <div class="search-container">
                    <form action="feedbackList" method="POST" class="d-flex">
                        <input required="" type="text" name="search" placeholder="Search by Product" class="search-box" value="${param.search}">
                        <button type="submit">Search</button>
                    </form>
                        
                    <form action="feedbackList" method="post" class="d-flex ml-4">
                        <select name="star" class="dropdown">
                            <option value="">Select Star</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select>
                        <select name="action" class="dropdown">
                            <option value="1">Active</option>
                            <option value="0">Inactive</option> 
                        </select>
                        <button type="submit">Filter</button>
                    </form>
                    <a href="feedbackList" class="btn ml-4">Back to FeedBack List</a>
                </div>
                <!-- Add New Blog Button -->


                <!-- Table Data -->
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th><a href="feedbackList?page=${currentPage}&sort=${param.sort == 'asc'?'desc':'asc'}&orderby=product_name">Product</a></th>
                            <th><a href="feedbackList?page=${currentPage}&sort=${param.sort == 'asc'?'desc':'asc'}&orderby=rated_star">Rated star</a></th>
                            <th>Content</th>
                            <th><a href="feedbackList?page=${currentPage}&sort=${param.sort == 'asc'?'desc':'asc'}&orderby=full_name">Customer</a></th>
                            <th><a href="feedbackList?page=${currentPage}&sort=${param.sort == 'asc'?'desc':'asc'}&orderby=status">Status</a></th>
                            <th>Image</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="fb" items="${feedback}">
                            <tr>
                                <td>${fb.product}</td>
                                <td>
                                    <c:forEach var="i" begin="1" end="${fb.rated_star}">
                                        <i class="fas fa-star" style="color: gold;"></i>
                                    </c:forEach>
                                    <c:forEach var="i" begin="${fb.rated_star + 1}" end="5">
                                        <i class="far fa-star" style="color: gold;"></i>
                                    </c:forEach>
                                </td>
                                <td>${fb.content}</td>
                                <td>${fb.customer}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${fb.status == 1}">
                                            Active
                                        </c:when>
                                        <c:when test="${fb.status == 0}">
                                            Inactive
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td class="text-center"><img src="data:image/png;base64,${fb.image}" alt="Image" style="width: 50px; height: 50px;"></td>
                                <td class="text-center">
                                    <a href="feedbackDetail?feedback_id=${fb.feedback_id}" class="btn btn-success">View</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="pagination">
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <a href="feedbackList?page=${i}&sort=${param.sort}&orderby=${param.orderby}" class="${i == currentPage ? 'active' : ''}">${i}</a>
                    </c:forEach>
                </div>


            </div>
        </div>

        <footer class="footer">
            <span>&copy; 2023 All RRRRRights Reserved by Company</span>
        </footer>

    </body>
</html>
