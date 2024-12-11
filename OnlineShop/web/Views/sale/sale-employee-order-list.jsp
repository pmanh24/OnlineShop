<%-- 
    Document   : add-product
    Created on : Sep 30, 2024, 2:23:02 PM
    Author     : Hieu
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sale Employee Order List</title>
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

            .filters {
                display: flex;
                flex-direction: column; /* Stack rows vertically */
                margin-bottom: 20px;
            }

            .filter-row {
                display: flex;

                margin-bottom: 15px; /* Space between rows */
            }

            .filters input, .filters select {
                margin-right: 20px;
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 4px;
                flex: 0.4; /* Allow inputs to grow */
                min-width: 100px; /* Set a minimum width */


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

            table {
                width: 100%;
                border-collapse: collapse;
            }

            table th, table td {
                padding: 12px;
                border: 1px solid #ddd;
                text-align: left;
            }

            table th a {
                text-decoration: none;
                color: black;
            }

            table th a:hover {
                text-decoration: underline;
            }

            .pagination {
                display: flex;
                justify-content: left;
                margin-top: 20px;
            }

            .page-btn {
                margin: 0 5px;
                padding: 10px 15px;
                border: 1px solid #007bff;
                background-color: #fff;
                color: #007bff;
                cursor: pointer;
                border-radius: 5px;
                transition: background-color 0.3s, color 0.3s;
            }

            .page-btn:hover {
                background-color: #007bff;
                color: white;
            }
            .filters .statusButtons-button {
                padding: 10px 20px;
                margin: 5px;
                border: 1px solid #007bff; /* Blue border */
                background-color: blue; /* Default background color */
                color: white; /* Text color */
                cursor: pointer;
                border-radius: 4px;
                transition: background-color 0.3s, color 0.3s; /* Smooth transition */
            }

            .filters .statusButtons-button.selected {
                background-color: red; /* Background color when selected */
                border-color: #0056b3; /* Darker border when selected */
            }

            .filters .statusButtons-button:hover {
                background-color: lightblue; /* Change color on hover */
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

                <hr class="sidebar-divider">
                <div class="sidebar-heading">MANAGEMENT</div>
                <li class="nav-item">
                    <a class="nav-link collapsed" href="sale-employee-order-list">
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
                <h1>Order List</h1>

                <div class="filters">
                    <form action="sale-employee-order-list" method="get">
                        <div class="filter-row">
                            <label for="search" style="margin-right: 10px;margin-top: 10px">Search:</label>
                            <input type="text" placeholder="Search by Order ID" id="searchOrderId" name="orderId" value="${orderIdValue}" style="max-width: 600px;margin-left: 33px;">                           
                            <input type="text" placeholder="Search by Customer Name" id="searchCustomerName" name="customerName"value="${customerNameValue}" style="max-width: 600px">
                        </div>
                        <div class="filter-row">
                            <label for="dateFrom" style="margin-right: 10px;margin-top: 10px">Order Date:</label>
                            <input type="date" id="dateFrom" placeholder="From" name="dateFrom"value="${dateFromValue}" style="max-width: 600px">
                            <input type="date" id="dateTo" placeholder="To" name="dateTo"value="${dateToValue}" style="max-width: 600px">                         
                        </div>
                        <div class="filter-row">
                            <button id="filterButton" onclick="this.form.submit()">Filter</button>
                        </div>
                    </form>
                    <form action="sale-employee-order-list" method="get"> 
                        <div id="statusButtons">
                            <c:forEach items="${statusList}" var="s">
                                <button type="button"  value="${s.getStatusId()}" class="status-button" style="background-color: #007bff"
                                        onclick="document.querySelector('#selectedStatus').value = this.value; this.form.submit();"
                                        >
                                    ${s.getStatusName()} : ${s.getOrderCount()} Orders
                                </button>
                            </c:forEach>
                        </div>
                        <input type="hidden" id="selectedStatus" name="status" value="${statusValue}" />
                    </form>
                </div>

                <table>
                    <thead>
                        <tr>
                            <th><a href="#" class="sortable" data-sort="id">Order ID</a></th>
                            <th><a href="#" class="sortable" data-sort="date">Ordered Date</a></th>
                            <th><a href="#" class="sortable" data-sort="customer">Customer Name</a></th>
                            <th>Sale Name</th>
                            <th>Product</th>
                            <th>Quantity</th>
                            <th><a href="#" class="sortable" data-sort="total">Total Cost</a></th>
                            <th><a href="#" class="sortable" data-sort="status">Status</a></th>
                        </tr>
                    </thead>
                    <tbody id="orderList">
                        <!-- Example Order Rows -->
                        <c:forEach items="${orderList}" var="s" varStatus="status">
                            <tr>
                                <td><a href="sale-order-detail?orderId=${s.getOrderId()}">${s.getOrderId()}</a></td>
                                <td>${s.getOrderDate()}</td>
                                <td>${s.getCustomer().getFullName() }</td>
                                <td>${s.getEmployee().getFullName() }</td>
                                <td>${s.getProductName() }</td>
                                <td>${s.getOrderDetail().getQuantity() }</td>
                                <td>${s.getTotalCost()}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${fn:substring(statusListTable[status.index], 0, 1) == '0'}">
                                            ${s.getStatusO().getStatusName()}
                                        </c:when>
                                        <c:otherwise>
                                            <form action="sale-employee-order-list" method="post">
                                                <input type="hidden" name="orderIdUpdate" value="${s.getOrderId()}" >
                                                <select name="statusIdUpdate">
                                                    <c:forEach items="${statusList}" var="statusItem">
                                                        <c:if test="${fn:contains(statusListTable[status.index], statusItem.getStatusId())}">
                                                            <option  value="${statusItem.getStatusId()}" ${statusItem.getStatusId() == s.getStatusO().getStatusId() ? 'selected' : ''  }>
                                                                ${statusItem.getStatusName()}
                                                            </option>
                                                        </c:if>

                                                    </c:forEach>
                                                </select>
                                                <button class="btn btn-primary btn-sm" onclick="this.form.submit()">Update</button>
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>

                    </tbody>
                </table>

                <div class="pagination">
                    <c:set var="page" value="${requestScope.page}"/>              
                    <c:set var="path" value="${requestScope.path}"/>
                    <c:forEach begin="${1}" end ="${requestScope.numpage}" var="i">
                        <li class="page-btn"><a  href="sale-employee-order-list?page=${i}&${path}">${i}</a></li>
                        </c:forEach>

                    <!-- Add more buttons as needed -->
                </div>
            </div>
            <!-- End of Main Content -->
        </div>

        <script>
            document.querySelectorAll('.sortable').forEach(header => {
                header.addEventListener('click', () => {
                    const tableBody = document.getElementById('orderList');
                    const rows = Array.from(tableBody.rows);
                    const sortBy = header.dataset.sort;

                    const sortedRows = rows.sort((a, b) => {
                        let aValue, bValue;

                        if (sortBy === 'date') {
                            aValue = new Date(a.cells[1].textContent);
                            bValue = new Date(b.cells[1].textContent);
                            return aValue - bValue;
                        } else if (sortBy === 'customer') {
                            aValue = a.cells[2].textContent.toLowerCase();
                            bValue = b.cells[2].textContent.toLowerCase();
                            return aValue.localeCompare(bValue);
                        } else if (sortBy === 'total') {
                            aValue = parseFloat(a.cells[6].textContent);
                            bValue = parseFloat(b.cells[6].textContent);
                            return aValue - bValue;
                        } else if (sortBy === 'status') {
                            aValue = a.cells[7].textContent.toLowerCase();
                            bValue = b.cells[7].textContent.toLowerCase();
                            return aValue.localeCompare(bValue);
                        }
                        return 0;
                    });

                    // Clear the table and append sorted rows
                    tableBody.innerHTML = '';
                    sortedRows.forEach(row => tableBody.appendChild(row));
                });
            });
        </script>
    </body>
</html>
