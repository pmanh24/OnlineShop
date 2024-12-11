<%-- 
    Document   : login
    Created on : Sep 7, 2024, 10:23:49 PM
    Author     : Hieu
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
</head>
<body class="bg-light min-vh-100 d-flex align-items-center">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card p-4">
                    <h1 class="text-center text-dark">Đăng nhập</h1>
                    <form class="form-group" action="login" method="post">
                        <div class="mb-3">
                            <label for="accountInput" class="form-label">Email</label>
                            <input type="email" class="form-control" value="${cookie.cEmail.value}"  id="accountInput" name="email" required>
                        </div>
                        <div class="mb-3">
                            <label for="passwordInput" class="form-label">Mật khẩu</label>
                            <input type="password" class="form-control" value="${cookie.cPass.value}" id="passwordInput" name="password" required>
                        </div>
                        <div class="mb-3 form-check">
                            <input type="checkbox" ${(cookie.cRem!=null?'checked':'')}  value="ON" class="form-check-input" id="rememberMe" name="rememberMe">
                            <label class="form-check-label" for="rememberMe">Remember me</label>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Đăng nhập</button>
                    </form>
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger mt-3" role="alert">
                            ${errorMessage}
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
