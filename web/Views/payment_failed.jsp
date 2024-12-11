<%-- 
    Document   : payment_failed
    Created on : Nov 2, 2024, 9:12:45 PM
    Author     : OS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Payment Failed</title>
        <!-- Bootstrap CSS CDN -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container text-center mt-5">
            <div class="card border-danger">
                <div class="card-header bg-danger text-white">
                    <h2>Payment Failed</h2>
                </div>
                <div class="card-body">
                    <h4 class="card-title">We're sorry, but your payment was not successful.</h4>
                    <p class="card-text">Please try again or contact customer support if you need assistance.</p>
                    <a href="${pageContext.request.contextPath}/cartdetail" class="btn btn-danger">Retry Payment</a>
                    <a href="support.jsp" class="btn btn-secondary">Contact Support</a>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
