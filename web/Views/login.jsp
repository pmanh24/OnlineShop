<%-- 
    Document   : login
    Created on : Sep 7, 2024, 10:23:49 PM
    Author     : Hieu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Login</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>
    <body style="background-color: #F8F9FD">
        <section class="ftco-section">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-6 text-center">
                        <h2 class="heading-section" style="margin-top: 10%; font-weight: bold">Welcome back !</h2>
                        <h5 class="heading-section" style="font-weight: lighter">Please enter your login details</h5>
                    </div>
                </div>                                   
                <div class="row justify-content-center">
                    <div class="col-md-7 col-lg-5"  style="background-color: #FFFF; border-radius: 5%">
                        <div class="wrap">
                            <div class="img" style="background-image: url(../Resource/images/bg-1.jpg);"></div>
                            <div class="login-wrap p-4 p-md-5">
                                <div class="d-flex">
                                    <div class="w-100">
                                        <h3 class="mb-4 text-center"><img src="https://cdn-icons-png.flaticon.com/256/5087/5087579.png" alt="alt" style="width: 70px;height: 70px"/></h3>
                                    </div>
                                </div>
                                <div class="d-flex">
                                    <div class="w-100">
                                        <h3 class="mb-4 text-center">Log in</h3>
                                    </div>
                                </div>
                                <form action="login" method="post" class="signin-form">
                                    <div class="form-group mt-3">
                                        <label class="form-control-placeholder" for="email">Email</label>
                                        <input type="email" class="form-control" name="email" value="${cookie.cEmail.value}" required>
                                    </div>
                                    <div class="form-group">
                                        <label class="form-control-placeholder" for="password">Password</label>
                                        <input id="password-field" type="password" value="${cookie.cPass.value}" class="form-control" name="password" required> 
                                    </div>
                                    <c:if test="${requestScope.errorMessage!=null}">
                                        <p style="color: red;font-style:italic;margin-top: -4%">${requestScope.errorMessage}</p></c:if>
                                        <div class="form-group">
                                            <button type="submit" class="form-control btn rounded submit px-3" style="background-color: #FF9A00; color:  white">Log in</button>
                                        </div>
                                        <div class="form-group d-md-flex">
                                            <div class="w-50 text-left">
                                                <div class="form-check">
                                                    <input type="checkbox" ${(cookie.cRem!=null?'checked':'')}  value="ON" class="form-check-input" id="rememberMe" name="rememberMe">
                                                <label class="form-check-label" for="rememberMe">Remember Me</label>
                                            </div>
                                        </div>
                                        <div class="w-50 text-right">
                                            <a href="forgotpass" class="text-secondary">Forgot Password</a>
                                        </div>
                                    </div>
                                </form>
                                                <p class="text-center text-secondary">Not a member? <a data-toggle="tab" href="register" style="color: #FF9A00">Sign Up</a></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>


