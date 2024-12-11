<%-- 
    Document   : register
    Created on : Sep 16, 2024, 11:38:15 PM
    Author     : GIGABYTE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>
    <body style="background-color: #F8F9FD">
        <section class="ftco-section">
            <div class="container">                                 
                <div class="row justify-content-center">
                    <div class="col-md-7 col-lg-5"  style="background-color: #FFFF; border-radius: 5%">
                        <div class="wrap">
                            <div class="login-wrap p-4 p-md-5">
                                <div class="d-flex">
                                    <div class="w-100">
                                        <h3 class="mb-4 text-center">Register a new account</h3>
                                    </div>
                                </div>
                                <form action="register" method="post" class="signin-form">
                                    <div class="form-group mt-3">
                                        <label class="form-control-placeholder" for="fullName">Full name</label>
                                        <input type="text" class="form-control" name="fullName" required>
                                    </div>
                                    <div class="form-group">
                                        <label class="form-control-placeholder" for="password">Password</label>
                                        <input type="password" class="form-control" name="password" required> 
                                    </div>
                                    <div class="form-group d-flex justify-content-between">
                                        <label class="form-control-placeholder" for="gender">Gender</label>
                                        <div class="col-md-3">
                                            <input type="radio" id="male" name="gender" value="1" required>
                                            <label for="male">Male</label>
                                        </div>
                                        <div class="col-md-4">
                                            <input type="radio" id="female" name="gender" value="0" required>
                                            <label for="female">Female</label>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="form-control-placeholder" for="email">Email</label>
                                        <input type="text" class="form-control" name="email" required> 
                                    </div>
                                    <div class="form-group">
                                        <label class="form-control-placeholder" for="phoneNumber">Mobile</label>
                                        <input type="text" class="form-control" name="phoneNumber" required> 
                                    </div>
                                    <div class="form-group">
                                        <label class="form-control-placeholder" for="address">Address</label>
                                        <input type="text" class="form-control" name="address" required> 
                                    </div>
                                    <c:if test="${requestScope.errorMessage!=null}">
                                        <p style="color: red;font-style:italic;margin-top: -4%">${requestScope.errorMessage}</p></c:if>
                                        <c:if test="${requestScope.successMessage!=null}">
                                        <p style="color: green;font-style:italic;margin-top: -4%">${requestScope.successMessage}</p></c:if>
                                        <div class="form-group">
                                            <button type="submit" class="form-control btn btn-primary rounded submit px-3" style="background-color: #FF9A00; color:  white">Register</button>
                                        </div>
                                                                                <div class="form-group">
                                                                                    <button type="submit" class="form-control btn  btn-primary rounded submit px-3" style="background-color: #FF9A00">
                                                                                        <a href="login" style="text-decoration: none;color:  white">Back to Login</a>
                                                                                    </button>
                                        </div>
                                   
                                </form>   
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>
