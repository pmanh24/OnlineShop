<%-- 
    Document   : unauthorized
    Created on : Oct 19, 2024, 9:43:10 AM
    Author     : Hieu
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Unauthorized Access</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            text-align: center;
            padding: 50px;
        }
        .error-container {
            display: inline-block;
            padding: 30px;
            border: 1px solid #dc3545;
            background-color: #fff3cd;
            color: #721c24;
            border-radius: 10px;
        }
        h1 {
            font-size: 48px;
            margin-bottom: 20px;
        }
        p {
            font-size: 18px;
        }
        a {
            color: #007bff;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h1>403 - Unauthorized</h1>
        <p>Sorry, you don't have permission to access this page.</p>
        <p><a href="<%= request.getContextPath() %>/employee/login">Return to login</a> or <a href="<%= request.getContextPath() %>/home">Go to homepage</a></p>
    </div>
</body>
</html>
