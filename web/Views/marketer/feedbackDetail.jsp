<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Feedback Detail</title>
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
                font-size: 1.5rem;
            }

            /* Feedback Detail */
            .feedback-detail {
                background-color: white;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                padding: 20px;
                margin-top: 20px;
            }

            .feedback-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                border-bottom: 2px solid #36b9cc;
                padding-bottom: 10px;
                margin-bottom: 20px;
            }

            .feedback-header h2 {
                font-size: 24px;
                color: #36b9cc;
            }

            .feedback-header a {
                background-color: #36b9cc;
                color: white;
                padding: 10px 15px;
                text-decoration: none;
                border-radius: 5px;
                transition: background-color 0.3s;
            }

            .feedback-header a:hover {
                background-color: #258391;
            }

            .feedback-content {
                font-size: 1.5rem;
                line-height: 1.6;
                margin-bottom: 20px;
            }

            .feedback-info {
                display: grid;
                grid-template-columns: 1fr 1fr; /* Sử dụng lưới hai cột */
                gap: 20px;
            }

            .feedback-info div {
                margin-bottom: 10px;
            }

            .feedback-info .stars {
                display: inline-flex;
                align-items: center;
            }

            button.btn-custom {
                padding: 6px 12px;
                font-size: 14px;
                border: none;
                border-radius: 4px;
                background-color: #36b9cc;
                color: white;
                transition: background-color 0.3s;
            }

            button.btn-custom:hover {
                background-color: #258391;
            }

            h4 {
                margin-top: 10px;
                padding: 6px;
                background-color: #f8f9fc;
                color: #155724;
                border: 1px solid #f8f9fc;
                border-radius: 4px;
                font-size: 14px;
                display: inline-block;
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

            /* Button Styles */
            .btn-custom {
                background-color: #36b9cc;
                color: white;
                padding: 10px 15px;
                border-radius: 5px;
                text-decoration: none;
                transition: background-color 0.3s;
            }

            .btn-custom:hover {
                background-color: #258391;
            }
            
            .feedback-image {
    width: 45%; /* Ảnh sẽ chiếm 25% chiều rộng */
     /* Tạo khoảng cách giữa ảnh và nội dung */
   
}

.feedback-image img {
    width: 66%;
    height: auto; /* Đảm bảo ảnh không bị méo */
    object-fit: cover;
    border-style: groove;
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
                <!-- Feedback Detail Section -->
                <div class="feedback-detail">
                    <div class="feedback-header">
                        <h2>Feedback Detail</h2>
                        <a href="feedbackList" class="btn-custom">Back to List</a>
                    </div>

                    <div class="feedback-info">
                        <div>
                            <span><strong>Product:</strong></span> ${feedback.product}
                        </div>
                        <div>
                            <span><strong>Customer:</strong></span> ${feedback.customer}
                        </div>
                        <div>
                            <span><strong>Email:</strong> </span> ${feedback.email}
                        </div>
                        <div>
                            <span><strong>Phone Number:</strong> </span> ${feedback.phone_number}
                        </div>
                        <div>
                            <span><strong>Rated:</strong></span>
                            <div class="stars">
                                <c:forEach var="i" begin="1" end="${feedback.rated_star}">
                                    <i class="fas fa-star" style="color: gold;"></i>
                                </c:forEach>
                                <c:forEach var="i" begin="${feedback.rated_star + 1}" end="5">
                                    <i class="far fa-star" style="color: gold;"></i>
                                </c:forEach>
                            </div>
                        </div>
                        <div>
                            <span><strong>Status:</strong></span>
                            <form action="feedbackDetail" method="post">
                                <select name="status">
                                    <option value="1" ${feedback.status == 1 ? 'selected' : ''}>Active</option>
                                    <option value="0" ${feedback.status == 0 ? 'selected' : ''}>Inactive</option>
                                </select>
                                <input type="hidden" name="feedback_id" value="${feedback.feedback_id}">
                                <button type="submit" class="btn-custom">Update Status</button> <!-- Nút gửi -->

                            </form>
                            <h4>${mess}</h4>
                        </div>
                        
                          <div class="feedback-content">
                        <span><strong>Content:</strong></span> ${feedback.content}
                    
                    </div>
                        <div><span><strong>Image: </strong></span></div>
                        <div></div>

                    <div class="feedback-image" >
                        
                        <img src="data:image/png;base64,${feedback.image}" alt="Image">
                    </div>  
                        
                    </div>

                   
                </div>
                <!-- End of Main Content -->

                <!-- Footer -->
                <footer>
                    <span>&copy; 2023 All Rights Reserved by Company</span>
                </footer>
            </div>
    </body>
</html>
