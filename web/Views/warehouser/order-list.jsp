<%-- 
    Document   : warehouser-order-list
    Created on : Oct 10, 2024, 1:17:12 PM
    Author     : Hieu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Warehouse Management</title>
        <script src="https://kit.fontawesome.com/c203902cd2.js" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
        <link href="assets/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="assets/css/sb-admin-2.min.css" rel="stylesheet">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <style>
            body {
                overflow-x: hidden;
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
            footer{
                z-index: 999;
            }
            .status-button {
                padding: 10px 20px;
                margin: 10px;
                margin-bottom: 20px;
                border: 1px solid #007bff; /* Blue border */
                background-color: blue; /* Default background color */
                color: white; /* Text color */
                cursor: pointer;
                border-radius: 4px;
                transition: background-color 0.3s, color 0.3s; /* Smooth transition */
            }

            .status-button.selected {
                background-color: red; /* Background color when selected */
                border-color: #0056b3; /* Darker border when selected */
            }

            .status-buttonn:hover {
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
                    <div class="sidebar-brand-text mx-3">Warehouse</div>
                </a>
                <hr class="sidebar-divider my-0">
                <li class="nav-item active">
                    <a class="nav-link" href="#">
                        <i class="fas fa-fw fa-tachometer-alt"></i>
                        <span>Dashboard</span>
                    </a>
                </li>
                <hr class="sidebar-divider">
                <div class="sidebar-heading">MANAGEMENT</div>
                <li class="nav-item">
                    <a class="nav-link collapsed" href="warehouser-order-list">
                        <i class="fas fa-box fa-sm fa-fw"></i>
                        <span>Order List</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link collapsed" href="warehouser-product-list">
                        <i class="fas fa-box fa-sm fa-fw"></i>
                        <span>Product List</span>
                    </a>
                </li>
            </ul>
            <!-- End of Sidebar -->

            <!-- Header Section -->
            <div class="navbar1">
                <a href="logout">Logout</a>
            </div>

            <div class="content">
                <h1 class="h3 mb-4 text-gray-800" style="margin-top:50px">List of Warehouse Orders</h1>
                <!-- Display error message if exists -->
                <c:if test="${not empty param.message}">
                    <div class="alert ${param.updateSuccess == 'true' ? 'alert-danger' : 'alert-success'}">
                        ${param.message}
                    </div>
                </c:if>

                <form method="GET" action="warehouser-order-list" class="mb-4">
                    <div class="form-row align-items-center">
                        <div class="col-sm-3 mb-2">
                            <input type="text" name="search" class="form-control" placeholder="Search by order number or customer name" value="${param.search}" />
                        </div>

                        <div class="col-auto">
                            <button type="submit" class="btn btn-primary" style="background-color: #36b9cc; margin-top: -8px ">Filter</button>
                        </div>
                    </div>
                </form>
                <div class="form-row align-items-center">

                    <form action="warehouser-order-list" method="get"> 
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
                <!-- DataTales Example -->
                <div class="card shadow mb-4">
                    <div class="card-header py-3 d-flex justify-content-between">
                        <h6 class="m-0 font-weight-bold text-primary">Manage Orders (${totalOrders})</h6>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover" width="100%" cellspacing="0">
                                <thead class="thead-light">
                                    <tr class="text-gray-800 text-center">
                                        <th style="width: 5%;">#</th>
                                        <th>
                                            <a href="?index=${param.index}&search=${param.search}&status=${param.status}&sort=order_id&sortOrder=${currentSort == 'order_id' && currentSortOrder == 'desc' ? 'asc' : 'desc'}" class="${currentSort == 'order_id' ? 'sorted' : ''}">
                                                Order ID
                                            </a>
                                        </th>
                                        <th style="width: 15%;">Customer Name</th>
                                        <th style="width: 10%;">
                                            <a href="?index=${param.index}&search=${param.search}&status=${param.status}&sort=order_date&sortOrder=${currentSort == 'order_date' && currentSortOrder == 'desc' ? 'asc' : 'desc'}" class="${currentSort == 'order_date' ? 'sorted' : ''}">
                                                Order Date
                                            </a>
                                        </th>
                                        <th style="width: 10%;">
                                            <a href="?index=${param.index}&search=${param.search}&status=${param.status}&sort=total_cost&sortOrder=${currentSort == 'total_cost' && currentSortOrder == 'desc' ? 'asc' : 'desc'}" class="${currentSort == 'total_cost' ? 'sorted' : ''}">
                                                Total Amount
                                            </a>
                                        </th>
                                        <th style="width: 10%;">Address</th>
                                        <th>Phone Number</th>
                                        <th style="width: 18%;">Product Name</th>
                                        <th>
                                            Quantity
                                        </th>
                                        <th style="width: 5%;">Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead> 
                                <tbody>
                                    <c:forEach var="order" items="${orderList}" varStatus="status">
                                        <tr>
                                            <td>${status.index + 1}</td>
                                            <td class="text-center">
                                                <a href="warehouser-order-detail?orderId=${order.getOrderId()}">${order.orderId}</a></td>
                                            <td>${order.customer.fullName}</td>
                                            <td>
                                                <fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd" />
                                            </td>
                                            <td><fmt:formatNumber value="${order.totalCost}" pattern="#,###"/> VND</td>
                                            <td>${order.address}</td>
                                            <td>${order.phoneNumber}</td>
                                            <td>
                                                <c:forEach var="entry" items="${order.productNames}" varStatus="status">
                                                    <c:if test="${status.index == 0}">
                                                        ${entry.value} <br/>
                                                    </c:if>
                                                </c:forEach>
                                            </td>
                                            <td class="text-center">
                                                <c:set var="totalQuantity" value="0" /> <!-- Initialize total quantity -->
                                                <c:forEach items="${order.quantities}" var="entry">
                                                    <c:set var="quantity" value="${entry.value}" />
                                                    <c:set var="totalQuantity" value="${totalQuantity + quantity}" /> <!-- Sum quantities -->
                                                </c:forEach>
                                                ${totalQuantity} <!-- Display the total -->
                                            </td>
                                            <td>
                                                <form action="${pageContext.request.contextPath}/warehouser-order-list" method="post" style="display:inline-block;" onsubmit="return confirmUpdate(this);">
                                                    <input type="hidden" name="orderId" value="${order.orderId}" />
                                                    <select name="status" onchange="confirmStatusChange(this)">
                                                        <option value="">Select Status</option>
                                                        <c:forEach var="status" items="${statusList}">
                                                            <c:set var="roleIdsArray" value="${fn:split(status.roleId, ',')}" />
                                                            <c:set var="roleIdFound" value="false" />

                                                            <c:forEach var="roleId" items="${roleIdsArray}">
                                                                <c:if test="${roleId == role_id}">
                                                                    <c:set var="roleIdFound" value="true" />
                                                                </c:if>
                                                            </c:forEach>

                                                            <c:if test="${roleIdFound}">
                                                                <option value="${status.statusId}" ${status.statusId == order.status ? 'selected' : ''}>
                                                                    ${status.statusName}
                                                                </option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </form>
                                            </td>
                                            <td>
                                                <button type="button" class="btn btn-info" onclick="printOrder('${order.orderId}')">Print</button>
                                            </td>

                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty orderList}">
                                        <tr>
                                            <td colspan="11" class="text-center">No orders found.</td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>

                        <!-- Pagination -->
                        <div class="d-flex justify-content-center">
                            <c:set var="totalPages" value="${(totalOrders + pageSize - 1) / pageSize}" />
                            <c:set var="currentIndex" value="${param.index != null ? param.index - 1 : 0}" /> <!-- Adjust current index to 0-based for iteration -->

                            <c:if test="${totalPages > 1}">
                                <nav aria-label="Page navigation">
                                    <ul class="pagination">
                                        <!-- Previous Button -->
                                        <c:if test="${currentIndex > 0}">
                                            <li class="page-item">
                                                <a class="page-link" href="?index=${currentIndex}&search=${param.search}&status=${param.status}&sort=${currentSort}&sortOrder=${currentSortOrder}" aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </li>
                                        </c:if>

                                        <!-- Page Number Links -->
                                        <c:forEach var="i" begin="1" end="${totalPages}">
                                            <li class="page-item ${i - 1 == currentIndex ? 'active' : ''}">
                                                <a class="page-link" href="?index=${i}&search=${param.search}&status=${param.status}&sort=${currentSort}&sortOrder=${currentSortOrder}">${i}</a>
                                            </li>
                                        </c:forEach>

                                        <!-- Next Button -->
                                        <c:if test="${currentIndex < totalPages - 1}">
                                            <li class="page-item">
                                                <a class="page-link" href="?index=${currentIndex + 2}&search=${param.search}&status=${param.status}&sort=${currentSort}&sortOrder=${currentSortOrder}" aria-label="Next">
                                                    <span aria-hidden="true">&raquo;</span>
                                                </a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </nav>
                            </c:if>
                        </div>
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

        <!-- Confirmation Modal -->
        <div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="confirmModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="confirmModalLabel">Confirm Update</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        Are you sure you want to update the order status?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-primary" id="confirmUpdate">Update</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Toast Notification -->
        <div id="toast" class="toast" role="alert" aria-live="assertive" aria-atomic="true"></div>


        <script>
            function printOrder(orderId) {
                // Open a new window for printing
                var printWindow = window.open('printorder?orderId=' + orderId, '_blank', 'width=800,height=600');

                // Focus on the new window
                printWindow.focus();
            }
            let currentForm; // To store the current form reference

            // Function to show toast notification
            function showToast(title, message) {
                document.getElementById('toastTitle').innerText = title;
                document.getElementById('toastBody').innerText = message;
                $('#toast').toast({delay: 3000});
                $('#toast').toast('show');
            }

            // Function to close toast
            function closeToast() {
                $('#toast').toast('hide');
            }

            // Function to confirm update
            function confirmUpdate(form) {
                currentForm = form; // Store the current form
                $('#confirmModal').modal('show'); // Show confirmation modal
            }

            // Confirm button in the modal
            document.getElementById('confirmUpdate').addEventListener('click', function () {
                currentForm.submit(); // Submit the form
                showToast('Success', 'Order status updated successfully!'); // Show success toast
                $('#confirmModal').modal('hide'); // Hide confirmation modal
            });
            function confirmStatusChange(select) {
                var selectedOption = select.options[select.selectedIndex];
                var selectedValue = selectedOption.value;
                var selectedText = selectedOption.text;

                // Check if a valid status is selected
                if (selectedValue !== "") {
                    var confirmed = confirm("Are you sure you want to change the status to " + selectedText + "?");
                    if (confirmed) {
                        // If confirmed, submit the form
                        select.form.submit();
                    } else {
                        // If not confirmed, reset the select box to the previous value
                        select.value = select.dataset.previousValue;
                    }
                } else {
                    // Handle case where "Select Status" is chosen
                    alert("Please select a valid status.");
                }
            }
            document.addEventListener("DOMContentLoaded", function () {
                var selects = document.querySelectorAll("select[name='status']");
                selects.forEach(function (select) {
                    select.dataset.previousValue = select.value; // Store the initial value
                });
            });

        </script>
    </body>
</html>
