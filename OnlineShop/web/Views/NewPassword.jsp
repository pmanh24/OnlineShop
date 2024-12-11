<%-- 
    Document   : NewPassword
    Created on : Sep 16, 2024, 4:29:25 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset='utf-8'>
        <meta name='viewport' content='width=device-width, initial-scale=1'>
        <title>New Password</title>
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-social/5.1.1/bootstrap-social.css">
        <link
            href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css'
            rel='stylesheet'>
        <link
            href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.0.3/css/font-awesome.css'
            rel='stylesheet'>
        <script type='text/javascript'
        src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>

        <style>

            .placeicon {
                font-family: fontawesome
            }

            .custom-control-label::before {
                background-color: #dee2e6;
                border: #dee2e6
            }

        </style>
    </head>
    <body oncontextmenu='return false' class='snippet-body bg-light'>
        <!-- Container containing all contents -->
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-12 col-md-9 col-lg-7 col-xl-6 mt-5">
                    <!-- White Container -->
                    <div class="container bg-white rounded mt-2 mb-2 px-0">
                        <!-- Main Heading -->
                        <div class="row justify-content-center align-items-center pt-3" style="color: blue">
                            <h1>
                                <strong>Reset Password</strong>
                            </h1>
                        </div>
                        <div class="pt-3 pb-3">
                            <form class="form-horizontal" action="newpass" method="POST">
                                <!-- User Name Input -->
                                <div class="form-group row justify-content-center px-3">
                                    <div class="col-9 px-0">
                                        <input type="password" name="password" placeholder="&#xf084; &nbsp; New Password"
                                               class="form-control border-info placeicon">
                                    </div>
                                </div>
                                <!-- Password Input -->
                                <div class="form-group row justify-content-center px-3">
                                    <div class="col-9 px-0">
                                        <input type="password" name="confPassword"
                                               placeholder="&#xf084; &nbsp; Confirm New Password"
                                               class="form-control border-info placeicon">
                                    </div>
                                </div>
                                <!-- Log in Button -->
                                <div class="form-group row justify-content-center">
                                    <div class="col-3 px-3 mt-3">
                                        <input type="submit" value="Reset"
                                               class="btn btn-block btn-facebook trigger-btn" data-toggle="modal">
                                    </div>
                                    <div class="col-3 px-3 mt-3">
                                        <c:if test="${requestScope.role == 1}">
                                            <div class="row justify-content-center">
                                                <button type="button" class="btn btn-danger" style="background-color: none"><a href="employee/login" style="text-decoration: none" class="text-light">Back to login</a></button>
                                            </div>

                                        </c:if>
                                        <c:if test="${requestScope.role == 2}">
                                            <div class="row justify-content-center">
                                                <button type="button" class="btn btn-danger" style="background-color: none"><a href="login" style="text-decoration: none" class="text-light">Back to login</a></button>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <!-- Alternative Login -->
                        <div class="mx-0 px-0 bg-light">


                            <c:if test="${errMsg != null}">
                                <div class="alert alert-danger" role="alert">
                                    ${errMsg}
                                </div>
                            </c:if>
                            <c:if test="${status!=null}" var="c">
                                <div class="row justify-content-center align-items-center pt-4 pb-5">
                                    <div class="alert alert-success" role="alert">
                                        ${status}
                                    </div>
                                    <script>
                                        setTimeout(function () {
                                            window.location.href = 'login';
                                        }, 3000);
                                    </script>
                                </div>

                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type='text/javascript'
    src='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.bundle.min.js'></script>

</body>
</html>
