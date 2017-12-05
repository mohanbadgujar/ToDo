<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<link rel="stylesheet" href="style/login.css">
<link rel="stylesheet" href="style/register.css">
<link rel="stylesheet" href="style/forgotPassword.css">
<link rel="stylesheet" href="style/home.css">
<link rel="stylesheet" href="style/topNavigationBar.css">
<link rel="stylesheet" href="style/sideNavigationBar.css">

<style type="text/css"></style>

</head>

<body ng-app="toDo">
	<div ui-view></div>
</body>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.6/angular.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-router/1.0.3/angular-ui-router.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


<script type="text/javascript" src="script/app.js"></script>

<script type="text/javascript" src="service/loginService.js"></script>
<script src="controller/loginController.js"></script>

<script type="text/javascript" src="service/registerService.js"></script>
<script src="controller/registerController.js"></script>

<script type="text/javascript" src="service/forgotPasswordService.js"></script>
<script src="controller/forgotPasswordController.js"></script>

<script type="text/javascript" src="service/resetPasswordService.js"></script>
<script src="controller/resetPasswordController.js"></script>

<script type="text/javascript" src="service/dammyPageService.js"></script>
<script src="controller/dammyPageController.js"></script>

<script type="text/javascript" src="service/homeService.js"></script>
<script src="controller/homeController.js"></script>

<script type="text/javascript" src="Directives/CustomiseDirectives.js"></script>
    
</html>