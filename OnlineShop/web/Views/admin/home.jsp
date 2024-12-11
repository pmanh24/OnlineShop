<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.text.NumberFormat" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard</title>
        <script src="https://kit.fontawesome.com/c203902cd2.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <link href="${pageContext.request.contextPath}/assets/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/assets/css/sb-admin-2.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
            }
            .navbar1 {
                background-color: #36b9cc;
                padding: 10px;
                position: fixed;
                top: 0;
                left: 0;
                right: 0;
                z-index: 1000;
                display: flex;
                justify-content: flex-end;
            }
            .navbar1 a {
                color: white;
                margin: 0 15px;
                text-decoration: none;
            }
            .navbar1 a:hover {
                text-decoration: underline;
            }
            .statistics {
                display: flex;
                justify-content: space-around;
                flex-wrap: wrap;
                margin-bottom: 20px;
            }
            .card {
                flex: 1;
                min-width: 200px;
                margin: 10px;
                border-radius: 8px;
                overflow: hidden;
                background: white;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }
            .chart-container {
                width: 100%;
                max-width: 600px;
                margin: 20px auto;
            }
            .content-wrapper {
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                gap: 20px;
                margin: 20px 0;
            }
            footer {
                background: #f8f9fc;
                padding: 20px;
                text-align: center;
            }
        </style>
    </head>
    <body id="page-top">
        <div id="wrapper">
            <!-- Sidebar -->
            <ul class="navbar-nav bg-gradient-info sidebar sidebar-dark accordion">
                <a class="sidebar-brand d-flex align-items-center justify-content-center" href="admin">
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
               
            </ul>
            <!-- End of Sidebar -->

            <!-- Header Section -->
            <div class="navbar1">
                <a href="logout" style="color: white; text-decoration: none;">Logout</a>
            </div>
            <div class="content">
                <h1 style="text-align: center; margin-top: 20px;">Admin Dashboard</h1>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" style="text-align: center;">${error}</div>
                </c:if>

                <!-- Filter Form -->
                <form method="get" action="admin" style="text-align: center; margin-bottom: 20px;">
                    <label for="startDate">Start Date:</label>
                    <input type="date" id="startDate" name="startDate" 
                           value="${not empty startDate ? startDate : pageContext.request.getAttribute('today')}" required>

                    <label for="endDate">End Date:</label>
                    <input type="date" id="endDate" name="endDate" 
                           value="${not empty endDate ? endDate : pageContext.request.getAttribute('today')}" required>

                    <input type="submit" class="btn-primary" value="Filter" style="background-color: #36b9cc;border-radius: 8px; padding: 5px">
                </form>

                <!-- Static statistics displayed above charts -->
                <div class="statistics">
                    <div class="card border-left-primary shadow">
                        <div class="card-body">
                            <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">New Orders</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${orderCount}</div>
                        </div>
                    </div>

                    <div class="card border-left-success shadow">
                        <div class="card-body">
                            <div class="text-xs font-weight-bold text-success text-uppercase mb-1">Total Revenue</div>
                            <div id="totalRevenue" class="h5 mb-0 font-weight-bold text-gray-800">${totalRevenue}</div>
                        </div>
                    </div>
                    <div class="card border-left-warning shadow">
                        <div class="card-body">
                            <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">New Customers</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${customerCount}</div>
                        </div>
                    </div>
                    <div class="card border-left-info shadow">
                        <div class="card-body">
                            <div class="text-xs font-weight-bold text-info text-uppercase mb-1">Total Feedbacks</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${fbCount}</div>
                        </div>
                    </div>
                </div>

                <!-- Grid layout for the charts -->
                <div class="row" style="margin-bottom: 100px">
                    <div class="col-md-6 chart-container">
                        <canvas id="orderTrendsChart" width="600" height="400"></canvas>
                        <h5 class="text-center">Order Trends</h5>
                    </div>
                    <div class="col-md-6 chart-container">
                        <canvas id="ordersChart" width="300" height="200"></canvas>
                        <h5 class="text-center">Order status</h5>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 100px">
                    <div class="col-md-6 chart-container">
                        <canvas id="revenueCateChart" width="600" height="400"></canvas>
                        <h5 class="text-center">Revenue Category</h5>
                    </div>
                    <div class="col-md-6 chart-container">
                        <canvas id="customerChart" width="300" height="200"></canvas>
                        <h5 class="text-center">Customer Chart</h5>
                    </div>
                </div>


                <div class="row" style="margin-bottom: 100px">
                    <div class="col-md-6 chart-container">
                        <canvas id="rateCateChart" width="600" height="400"></canvas>
                        <h5 class="text-center">Rate Category</h5>
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

        <%
            // Retrieve the productSales attribute set in the servlet
            Map<String, Integer> orderStatus = (Map<String, Integer>) request.getAttribute("orderStatus");

            // Check if productSales is null
            if (orderStatus == null) {
                throw new ServletException("Order status data is not available.");
            }

            // Convert productSales Map to JSON
            Gson gson = new Gson();
            String orderStatusJson = gson.toJson(orderStatus);
            
            // revenue
            Map<String, Long> revenueCate = (Map<String, Long>) request.getAttribute("revenueCate");

            // Check if productSales is null
            if (revenueCate == null) {
                throw new ServletException("revenue cate data is not available.");
            }

            // Convert productSales Map to JSON
            Gson gson1 = new Gson();
            String revenueCateJson = gson1.toJson(revenueCate);
            

            //bought
            Map<String, Integer> customerBought = (Map<String, Integer>) request.getAttribute("customerBought");

            // Check if customerBought is null
            if (customerBought == null) {
                throw new ServletException("customer bought data is not available.");
            }

            // Convert productSales Map to JSON
            Gson gson2 = new Gson();
            String customerBoughtJson = gson2.toJson(customerBought);
            

            //rate
            Map<String, Double> rateCate = (Map<String, Double>) request.getAttribute("rateCate");
            Map<String, Integer> voteCounts = (Map<String, Integer>) request.getAttribute("voteCounts"); // Lấy số lượng vote

            // Check if rateCate or voteCounts is null
            if (rateCate == null || voteCounts == null) {
                throw new ServletException("rateCate or voteCounts data is not available.");
            }

            // Convert rateCate Map to JSON
            Gson gson3 = new Gson();
            String rateCateJson = gson3.toJson(rateCate);
            String voteCountsJson = gson3.toJson(voteCounts); // Chuyển đổi số lượng vote sang JSON


        %>                

        <script>

            //format
            const totalRevenue = ${totalRevenue};

            // Định dạng số với dấu phân cách hàng nghìn
            const formattedTotalRevenue = totalRevenue.toLocaleString('en-US');

            // Gán giá trị đã định dạng vào phần tử HTML
            document.getElementById('totalRevenue').innerText = formattedTotalRevenue;


            //order

            const orderTrendsJson = '${orderTrends}'; // Use EL to retrieve the JSON
            const orderTrends = JSON.parse(orderTrendsJson); // Parse it back to JavaScript object

            // Logic to create your chart with order trends data
            const orderTrendLabels = Object.keys(orderTrends);
            const orderTrendData = Object.values(orderTrends);

            const ctx = document.getElementById('orderTrendsChart').getContext('2d');
            new Chart(ctx, {
                type: 'line',
                data: {
                    labels: orderTrendLabels,
                    datasets: [{
                            label: 'Order Trends',
                            data: orderTrendData,
                            borderColor: 'rgba(75, 192, 192, 1)',
                            backgroundColor: 'rgba(75, 192, 192, 0.2)',
                            tension: 0.1
                        }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });


            const orderStatus = <%= orderStatusJson %>;
            const totalStatus = ${totalStatus}; // Ensure this is a valid number

            // Prepare data for the pie chart
            const labels = Object.keys(orderStatus);
            const data = Object.values(orderStatus).map(value => (value / totalStatus * 100).toFixed(2)); // Convert to percentage

            const charCtx = document.getElementById('ordersChart').getContext('2d'); // Use charCtx for the pie chart
            const ordersChart = new Chart(charCtx, {// Initialize with charCtx
                type: 'pie',
                data: {
                    labels: labels,
                    datasets: [{
                            label: 'Order status Percentage',
                            data: data,
                            backgroundColor: [
                                'rgba(75, 192, 192, 0.6)',
                                'rgba(255, 99, 132, 0.6)',
                                'rgba(255, 206, 86, 0.6)',
                                'rgba(54, 162, 235, 0.6)',
                                'rgba(153, 102, 255, 0.6)'
                            ],
                        }]
                },
                options: {
                    responsive: true,
                    aspectRatio: 1.5,
                    plugins: {
                        legend: {
                            display: true,
                            position: 'bottom',
                        },
                        tooltip: {
                            callbacks: {
                                label: function (context) {
                                    var label = context.label || '';
                                    if (label) {
                                        label += ': ';
                                    }
                                    label += context.raw + '%'; // Add percentage sign
                                    return label;
                                }
                            }
                        }}
                }
            });

            // revenue
            const revenueCate = <%= revenueCateJson %>;
            const totalCate = ${totalCate};

            const revenueLabels = Object.keys(revenueCate); // Renamed
            const revenueData = Object.values(revenueCate).map(value => (value / totalCate * 100).toFixed(2));

// Use a different variable for chart context
            const revenueChartCtx = document.getElementById('revenueCateChart').getContext('2d');

            const revenueCateChart = new Chart(revenueChartCtx, {
                type: 'pie',
                data: {
                    labels: revenueLabels,
                    datasets: [{
                            label: 'Revenue Category Percentage',
                            data: revenueData,
                            backgroundColor: [
                                'rgba(75, 192, 192, 0.6)',
                                'rgba(255, 99, 132, 0.6)',
                                'rgba(255, 206, 86, 0.6)',
                                'rgba(54, 162, 235, 0.6)',
                                'rgba(153, 102, 255, 0.6)'
                            ],
                        }]
                },
                options: {
                    responsive: true,
                    aspectRatio: 1.5,
                    plugins: {
                        legend: {
                            display: true,
                            position: 'bottom',
                        },
                        tooltip: {
                            callbacks: {
                                label: function (context) {
                                    let label = context.label || '';
                                    let rawValue = revenueCate[context.label]; // Get the raw revenue value for that category
                                    let percentage = context.raw; // Get the percentage value
                                    if (label) {
                                        label += ': ';
                                    }
                                    label += rawValue.toLocaleString('en-US', {style: 'currency', currency: 'VND'}) + `, ` + percentage + `%`;
                                    return label;
                                }
                            }
                        }
                    }
                }
            });


            // bought
            const customerBought = <%= customerBoughtJson %>; // JSON chứa số lượng khách hàng đã mua và chưa mua
            const totalBought = ${totalBought}; // Tổng số khách hàng đã đăng ký

            const customerLabels = Object.keys(customerBought); // Lấy nhãn từ bản đồ khách hàng
            const customerData = Object.values(customerBought).map(value => (value / totalBought * 100).toFixed(2)); // Tính phần trăm


            const customerChartCtx = document.getElementById('customerChart').getContext('2d');

            const customerChart = new Chart(customerChartCtx, {
                type: 'pie',
                data: {
                    labels: customerLabels,
                    datasets: [{
                            label: 'Customer Category Percentage',
                            data: customerData,
                            backgroundColor: [
                                'rgba(75, 192, 192, 0.6)',
                                'rgba(255, 99, 132, 0.6)',
                                'rgba(255, 206, 86, 0.6)',
                                'rgba(54, 162, 235, 0.6)',
                                'rgba(153, 102, 255, 0.6)'
                            ],
                        }]
                },
                options: {
                    responsive: true,
                    aspectRatio: 1.5,
                    plugins: {
                        legend: {
                            display: true,
                            position: 'bottom',
                        },
                        tooltip: {
                            callbacks: {
                                label: function (context) {
                                    let label = context.label || '';
                                    if (label) {
                                        label += ': ';
                                    }
                                    label += context.raw + '%'; // Thêm ký hiệu phần trăm
                                    return label;
                                }
                            }
                        }
                    }
                }
            });


            // rate
            const rateCate = <%= rateCateJson %>; // JSON chứa số sao trung bình
            const voteCounts = <%= voteCountsJson %>; // JSON chứa số lượng vote
            const totalVotes = ${totalRate}; // Tổng số lượng vote

            const rateLabels = Object.keys(voteCounts); // Lấy nhãn từ bản đồ số sao trung bình
            const rateData = Object.values(voteCounts).map(value => (value / totalVotes * 100).toFixed(2)); // Tính phần trăm

            const rateChartCtx = document.getElementById('rateCateChart').getContext('2d');

// Vẽ biểu đồ với thông tin cho tooltip
            const rateChart = new Chart(rateChartCtx, {
                type: 'pie', // Sử dụng biểu đồ hình tròn
                data: {
                    labels: rateLabels,
                    datasets: [{
                            label: 'Votes Distribution',
                            data: rateData,
                            backgroundColor: [
                                'rgba(75, 192, 192, 0.6)',
                                'rgba(255, 99, 132, 0.6)',
                                'rgba(255, 206, 86, 0.6)',
                                'rgba(54, 162, 235, 0.6)',
                                'rgba(153, 102, 255, 0.6)'
                            ],
                        }]
                },
                options: {
                    responsive: true,
                    aspectRatio: 1.5,
                    plugins: {
                        legend: {
                            display: true,
                            position: 'bottom',
                        },
                        tooltip: {
                            callbacks: {
                                label: function (context) {
                                    let label = context.label || ''; // Tên danh mục
                                    const voteCount = voteCounts[context.label]; // Số lượng vote cho category
                                    const averageStar = rateCate[context.label]; // Số sao trung bình cho category
                                    const percentage = context.raw; // Phần trăm đã tính

                                    // Tạo label cho tooltip
                                    if (label) {
                                        label += ': ';
                                    }
                                    label += voteCount + ` votes, Avg:` + averageStar.toFixed(2) + ` stars, ` + context.raw + `%`;
                                    return label;
                                }
                            }
                        }
                    }
                }
            });




        </script>
    </body>
</html>
