<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product Spring MVC</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://kit.fontawesome.com/a01dff536c.js"></script>
<c:url var="styleCssUrl" value="/resources/css/style.css"></c:url>
<link rel="stylesheet" href="${styleCssUrl}" />
<c:url var="mainJsUrl" value="/resources/js/main.js"></c:url>
<script type="text/javascript" src="${mainJsUrl}" defer></script>
</head>
<body class="container">
    <div class="mt-4">
        <h1 class="fs-2">Product Spring MVC</h1>
    </div>
    <main class="mt-4">
        <div class="row">
        	<c:url var="categoriesUrl" value="/categories"></c:url>
       		<a href="${categoriesUrl}" class="col-6 p-2">
                <span class="card text-center bg-success text-white">
                    <span class="card-header fs-6">Categories</span>
                    <span class="card-body">
                        <span class="card-text fs-2">
                        	${categoryCount}
                        </span>
                    </span>
                </span>
            </a>
            <c:url var="productsUrl" value="/products"></c:url>
       		<a href="${productsUrl}" class="col-6 p-2">
                <span class="card text-center bg-warning text-black">
                    <span class="card-header fs-6">Products</span>
                    <span class="card-body">
                        <span class="card-text fs-2">
                        	${productCount}
                        </span>
                    </span>
                </span>
            </a>
        </div>
    </main>
</body>
</html>