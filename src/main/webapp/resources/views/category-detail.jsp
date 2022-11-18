<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Category Detail</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://kit.fontawesome.com/a01dff536c.js"></script>
<c:url var="styleCssUrl" value="/resources/css/style.css"></c:url>
<link rel="stylesheet" href="${styleCssUrl}" />
<c:url var="mainJsUrl" value="/resources/js/main.js"></c:url>
<script type="text/javascript" src="${mainJsUrl}" defer></script>
</head>
<body class="container">
	<div class="col-6 offset-3 mt-4">
		<h1 class="fs-2">Category Detail</h1>
	</div>
	<main class="mt-4 col-6 offset-3 text-start">
        <form>
       		<div class="form-group">
                 <label class="form-label" for="id">ID</label>
                 <input class="form-control" type="text" id="id" value="${category.getId()}" readonly>
             </div>
            <div class="mt-3">
                <label class="form-label" for="name">Name</label>
                <input class="form-control" type="text" id="name" value="${category.getName()}" readonly>
            </div>
            <div class="mt-3">
                <label class="form-label" for="description">Description</label>
                <textarea class="form-control" id="description"  readonly>${category.getDescription()}</textarea>
            </div>
        </form>
        <c:url var="categoryEditUrl" value="/category/edit">
        	<c:param name="id">${category.getId()}</c:param>
        </c:url>
        <a href="${categoryEditUrl}" class="btn btn-primary mt-4 form-control p-2">
            <i class="fa-solid fa-pen-to-square"></i>
            Edit
        </a>
        <c:url var="backUrl" value="/categories"></c:url>
        <a href="${backUrl}" class="btn btn-secondary mt-2 p-2 w-100">
        	<i class="fa-solid fa-arrow-left"></i>
        	Back
        </a>
    </main>
</body>
</html>