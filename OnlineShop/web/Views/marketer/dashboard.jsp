<%-- 
    Document   : dashboard
    Created on : Oct 7, 2024, 4:53:30 PM
    Author     : Hieu
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.util.Map" %> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Marketing Dashboard</title>
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
                padding: 15px;
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
                <a href="logout" style="color: white; text-decoration: none;">Logout</a>
            </div>
            <div class="content">
                <h1 style="text-align: center; margin-top: 30px;">Dashboard</h1>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" style="text-align: center;">${error}</div>
                </c:if>

                <!-- Filter Form -->
                <form method="get" action="mkt-dashboard" style="text-align: center; margin-bottom: 20px;">
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
                          <a href="postlist" style="text-decoration: none">
                        <div class="card-body">
                            <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">Total Posts</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${postCount}</div>
                        </div>
                          </a>
                    </div>
                    <div class="card border-left-success shadow">
                          <a href="mkt-product-list" style="text-decoration: none">
                        <div class="card-body">
                            <div class="text-xs font-weight-bold text-success text-uppercase mb-1">Total Products</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${productCount}</div>
                        </div>
                          </a>
                    </div>
                    <div class="card border-left-warning shadow">
                        <a href="mkt-customer-list" style="text-decoration: none">
                            <div class="card-body">
                                <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">Total Customers</div>
                                <div class="h5 mb-0 font-weight-bold text-gray-800">${customerCount}</div>
                            </div></a>
                    </div>
                    <div class="card border-left-info shadow">
                          <a href="feedbackList" style="text-decoration: none">
                        <div class="card-body">
                            <div class="text-xs font-weight-bold text-info text-uppercase mb-1">Total Feedbacks</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${fbCount}</div>
                        </div></a>
                    </div>
                </div>

                <!-- Grid layout for the charts -->
                <div class="row" style="margin-bottom: 100px">
                    <div class="col-md-6 chart-container">
                        <canvas id="customerTrendsChart" width="600" height="400"></canvas>
                        <h5 class="text-center">Customer Trends</h5>
                    </div>
                    <div class="col-md-6 chart-container">
                        <canvas id="salesChart" width="300" height="200"></canvas>
                        <h5 class="text-center">Product Sales Percentage</h5>
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
            Map<String, Integer> productSales = (Map<String, Integer>) request.getAttribute("productSales");

            // Check if productSales is null
            if (productSales == null) {
                throw new ServletException("Product sales data is not available.");
            }

            // Convert productSales Map to JSON
            Gson gson = new Gson();
            String productSalesJson = gson.toJson(productSales);
        %>
        <script>
            const customerTrendsJson = '${customerTrends}'; // Use EL to retrieve the JSON
            const customerTrends = JSON.parse(customerTrendsJson); // Parse it back to JavaScript object

            // Logic to create your chart with customer trends data
            const customerTrendLabels = Object.keys(customerTrends);
            const customerTrendData = Object.values(customerTrends);

            const ctx = document.getElementById('customerTrendsChart').getContext('2d');
            new Chart(ctx, {
                type: 'line',
                data: {
                    labels: customerTrendLabels,
                    datasets: [{
                            label: 'Customer Trends',
                            data: customerTrendData,
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

            const productSales = <%= productSalesJson %>;
            const totalSales = ${totalSales}; // Ensure this is a valid number

            // Prepare data for the pie chart
            const labels = Object.keys(productSales);
            const data = Object.values(productSales).map(value => (value / totalSales * 100).toFixed(2)); // Convert to percentage

            const charCtx = document.getElementById('salesChart').getContext('2d'); // Use charCtx for the pie chart
            const salesChart = new Chart(charCtx, {// Initialize with charCtx
                type: 'pie',
                data: {
                    labels: labels,
                    datasets: [{
                            label: 'Product Sales Percentage',
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
        </script>

    </body>
</html>
