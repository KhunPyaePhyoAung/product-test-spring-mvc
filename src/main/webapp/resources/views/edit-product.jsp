<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${empty product.getId() ? 'Add New' : 'Edit'} Product</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://kit.fontawesome.com/a01dff536c.js"></script>
<c:url var="styleCssUrl" value="/resources/css/style.css"></c:url>
<link rel="stylesheet" href="${styleCssUrl}" />
<c:url var="mainJsUrl" value="/resources/js/main.js"></c:url>
<script type="text/javascript" src="${mainJsUrl}" defer></script>
</head>
<body class="container">
    <div class="mt-4 col-6 offset-3">
        <h1 class="fs-2">${empty product.getId() ? 'Add New' : 'Edit'} Product</h1>
    </div>
    <c:if test="${not empty exception}">
    	<p class="col-6 offset-3 text-danger mt-4">${exception.getMessage()}</p>
    </c:if>
    <main class="mt-4 col-6 offset-3 text-start">
    	<c:url var="productSaveUrl" value="/product/save"></c:url>
        <form action="${productSaveUrl}" method="post">
        	<c:if test="${not empty product.getId()}">
        		<div class="form-group">
	                 <label class="form-label" for="id">ID</label>
	                 <input class="form-control" type="text" id="id" name="id"value="${product.getId()}" readonly>
	             </div>
        	</c:if>
            <div class="mt-3">
                <label class="form-label" for="name">Name</label>
                <input class="form-control" type="text" id="name" name="name" value="${product.getName()}" autocomplete="off" required>
            </div>
            <div class="mt-3">
                <label class="form-label" for="category">Select Category</label>
                <select class="form-select" name="category" id="category" required>
                	<c:forEach var="category" items="${categories}">
                		<option value="${category.getId()}" ${product.getCategoryId() eq category.getId() ? 'selected' : ''}>
                			${category.getName()}
                		</option>
                	</c:forEach>
                </select>
            </div>
            <div class="mt-3">
                <label class="form-label" for="unitPrice">Unit Price</label>
                <input class="form-control" type="number" id="unitPrice" name="unitPrice" min="0" value="${product.getUnitPrice()}" autocomplete="off" required>
            </div>
            <div class="mt-3">
                <label class="form-label" for="description">Description</label>
                <textarea class="form-control" id="description" name="description">${product.getDescription()}</textarea>
            </div>
            <button type="submit" class="btn btn-primary mt-4 form-control p-2">
                <i class="fa-solid fa-floppy-disk"></i>
                Save
            </button>
        </form>
        <c:url var="cancelUrl" value="/products"></c:url>
        <a href="${cancelUrl}" class="btn btn-secondary mt-2 p-2 w-100">
        	<i class="fa-solid fa-xmark"></i>
        	Cancel
        </a>
    </main>
</body>
</html>