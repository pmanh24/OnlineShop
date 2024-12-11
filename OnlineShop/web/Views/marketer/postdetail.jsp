




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
                background-color: #36b9cc; /* Primary color */
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
                margin-top: 19px;
                margin-right: 54px;
    margin-left: 300px;
    max-width: 1444px;
            }
            .search-container {
                display: flex;
                justify-content: left;
                align-items: center;
                margin-bottom: 20px;
            }
            .search-box, .dropdown, button {
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
            table th, table td {
                padding: 15px;
                text-align: center;
                border-bottom: 1px solid #ddd;
            }
            table th {
                background-color: #36b9cc; /* Thay đổi màu nền cho tiêu đề bảng */
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
                display: block; /* Đảm bảo phần tử này hiện ra */
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
    <h1 class="text-center my-4">Post Detail & Edit</h1>
    <div class="row">
        <!-- Detail of Post -->
        <div class="col-md-6 mb-4">
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">Detail of Post</h5>
                </div>
                <div class="card-body">
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">
                            <strong>Title:</strong> ${posts.title}
                        </li>
                        <li class="list-group-item">
                            <strong>Image:</strong><br>
                            <img src="${posts.image}" alt="${posts.title}" class="img-fluid">
                        </li>
                        <li class="list-group-item">
                            <strong>Update Date:</strong> ${posts.updateDate}
                        </li>
                        <li class="list-group-item">
                            <strong>Detail:</strong> ${posts.detail}
                        </li>
                        <li class="list-group-item">
                            <strong>Status:</strong> ${posts.status == 1 ? 'Active' : 'Inactive'}
                        </li>
                        <li class="list-group-item">
                            <strong>Category:</strong> ${posts.categoryName}
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <!-- Edit Post Form -->
        <div class="col-md-6 mb-4">
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">Edit Post</h5>
                </div>
                <div class="card-body">
                    <form action="postdetail" method="post" enctype="multipart/form-data">
                        <!-- Hidden input for post ID -->
                        <input type="hidden" name="post_id" value="${posts.blogId}">

                        <!-- Title input -->
                        <div class="mb-3">
                            <label class="small mb-1" for="inputTitle">Title</label>
                            <input class="form-control" id="inputTitle" type="text" name="title" value="${posts.title}" placeholder="Enter Title" required>
                        </div>

                        <!-- Image input -->
                        <div class="mb-3">
                            <label class="small mb-1" for="inputImage">Image</label>
                            <input class="form-control" id="inputImage" type="file" name="image">
                            
                        </div>

                        <!-- Update Date input -->
                        <div class="mb-3">
                            <label class="small mb-1" for="inputUpdateDate">Update Date</label>
                            <input class="form-control" id="inputUpdateDate" type="date" name="updateDate" value="${posts.updateDate}" required>
                        </div>

                        <!-- Detail input -->
                        <div class="mb-3">
                            <label class="small mb-1" for="inputDetail">Detail</label>
                            <textarea class="form-control" id="inputDetail" name="detail" rows="4" placeholder="Enter post detail" required>${posts.detail}</textarea>
                        </div>

                        <!-- Status input -->
                        <div class="mb-3">
                            <label class="small mb-1" for="inputStatus">Status</label>
                            <select class="form-control" id="inputStatus" name="status" required>
                                <option value="1" ${posts.status == 1 ? 'selected' : ''}>Active</option>
                                <option value="0" ${posts.status == 0 ? 'selected' : ''}>Inactive</option>
                            </select>
                        </div>

                        <!-- Category input -->
                        <div class="mb-3">
                            <label for="category" class="small mb-1">Category</label><br>
                            <c:forEach var="category" items="${categories}">
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="category" value="${category.categoryId}" id="category${category.categoryId}">
                                    <label class="form-check-label" for="category${category.categoryId}">${category.name}</label>
                                </div>
                            </c:forEach>
                        </div>

                        <!-- Save changes button -->
                        <button class="btn btn-primary" type="submit">Save changes</button>
                        <h2>${message}</h2>
                    </form>
                    <br>
                    <a href="${pageContext.request.contextPath}/postlist" class="btn btn-secondary">Back to post list</a>
                </div>
            </div>
        </div>
    </div>
</div>


            <!-- Footer Section -->
            <footer>
                <span>&copy; 2023 All Rights Reserved by Company</span>
            </footer>
        </div>
    </body>
</html>
