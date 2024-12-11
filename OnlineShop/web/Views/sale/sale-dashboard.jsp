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
        <title>Sale Dashboard</title>
        <script src="https://kit.fontawesome.com/c203902cd2.js" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/assets/css/sb-admin-2.min.css" rel="stylesheet">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            body {
                min-height: 100vh; /* Ensure the body takes at least the full viewport height */
                padding-bottom: 60px; /* Add padding at the bottom for footer */
            }
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

            .filters {
                display: flex;
                flex-direction: column; /* Stack rows vertically */
                margin-bottom: 20px;
            }

            .filter-row {
                display: flex;
                justify-content: space-between; /* Distribute space between items */
                margin-bottom: 15px; /* Space between rows */
            }

            .filters input, .filters select {
                margin-right: 10px;
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 4px;
                flex: 1; /* Allow inputs to grow */
                min-width: 120px; /* Set a minimum width */
            }

            .filters button {
                padding: 10px 20px; /* Make the button smaller */
                border: none;
                background-color: #5cb85c;
                color: white;
                border-radius: 4px;
                cursor: pointer;
                width: auto; /* Set width to auto */
            }

            .filters .filter-row:last-child {
                justify-content: flex-start; /* Align the button to the left */
            }

            .filters button:hover {
                background-color: #4cae4c;
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
                    <div class="sidebar-brand-text mx-3">Sale</div>
                </a>
                <hr class="sidebar-divider my-0">
                <li class="nav-item active">
                    <a class="nav-link" href="sale-dashboard-controller">
                        <i class="fas fa-fw fa-tachometer-alt"></i>
                        <span>Dashboard</span>
                    </a>
                </li>
                <hr class="sidebar-divider">
                <div class="sidebar-heading">MANAGEMENT</div>
                <li class="nav-item">
                    <a class="nav-link collapsed" href="sale-order-list">
                        <i class="fas fa-user fa-sm fa-fw"></i>
                        <span>Danh sách đơn hàng</span>
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
                <h1>Sale Dashboard</h1>

                <div class="filters">
                    <form action="sale-dashboard-controller" method="get">
                        <div class="filter-row">
                            <label for="search" style="margin-right: 10px;margin-top: 10px">Search:</label>
                            <input type="text" placeholder="Search by Sale Name" id="searchSaleName" name="saleName"value="${saleNameValue}">
                            <label for="status" style="margin-right: 10px;margin-top: 10px">Status:</label>
                            <select id="statusFilter" name="status">
                                <option value="0" ${statusValue==0?"selected":""}>Select Status</option>
                                <c:forEach items="${statusList}" var="s">
                                    <option value="${s.getStatusId()}" ${statusValue==s.getStatusId()?"selected":""}>${s.getStatusName()}</option>
                                </c:forEach>

                            </select>
                        </div>
                        <div class="filter-row">
                            <label for="dateFrom" style="margin-right: 10px;margin-top: 10px">Order Date:</label>
                            <input type="date" id="dateFrom" placeholder="From" name="dateFrom"value="${dateFromValue}">
                            <input type="date" id="dateTo" placeholder="To" name="dateTo"value="${dateToValue}">
                            

                        </div>

                        <div class="filter-row">
                            <button id="filterButton" onclick="this.form.submit()">Filter</button>
                        </div>
                    </form>
                </div>
                <div class="charts">
                    <h2>Number of Order:</h2>
                    <canvas id="orderChart" width="400" height="100"></canvas>
                    <h2>Revenue:</h2>
                    <canvas id="orderChart1" width="400" height="100"></canvas>
                </div>

            </div>
            <!-- End of Main Content -->
        </div>
        <!-- Footer -->
        
        <!-- End of Footer -->
        <% String jsonOrderCounts = (String) request.getAttribute("orderCounts"); %>
        <% String jsonCostCounts = (String) request.getAttribute("costCounts"); %>
        <script>           
            const orderCounts = <%= jsonOrderCounts %>;
            const costCounts = <%= jsonCostCounts %>;

            // Map order data to labels and data arrays
            const labels1 = orderCounts.map(order => order.date);
            const data1 = orderCounts.map(order => order.count);

            // Map cost data to labels and data arrays
            const labels2 = costCounts.map(order => order.date);
            const data2 = costCounts.map(order => order.totalCost);

            // Create the first chart for Number of Orders
            const ctx1 = document.getElementById('orderChart').getContext('2d');
            const orderChart = new Chart(ctx1, {
                type: 'bar',
                data: {
                    labels: labels1,
                    datasets: [{
                            label: 'Number of Orders',
                            data: data1,
                            backgroundColor: 'rgba(75, 192, 192, 0.2)',
                            borderColor: 'rgba(75, 192, 192, 1)',
                            borderWidth: 1
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

            // Create the second chart for Revenue
            const ctx2 = document.getElementById('orderChart1').getContext('2d');
            const revenueChart = new Chart(ctx2, {
                type: 'bar',
                data: {
                    labels: labels2,
                    datasets: [{
                            label: 'Revenue',
                            data: data2,
                            backgroundColor: 'rgba(153, 102, 255, 0.2)',
                            borderColor: 'rgba(153, 102, 255, 1)',
                            borderWidth: 1
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
        </script>
    </body>
</html>
