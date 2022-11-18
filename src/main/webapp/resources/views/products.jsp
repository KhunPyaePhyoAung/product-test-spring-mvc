<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Products</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://kit.fontawesome.com/a01dff536c.js"></script>
<c:url var="styleCssUrl" value="/resources/css/style.css"></c:url>
<link rel="stylesheet" href="${styleCssUrl}" />
<c:url var="mainJsUrl" value="/resources/js/main.js"></c:url>
<script type="text/javascript" src="${mainJsUrl}" defer></script>
</head>
<body class="container-fluid px-5">
	<div class="d-flex align-items-center col-12 mt-4">
		<c:url var="homeUrl" value="/"></c:url>
		<a href="${homeUrl}"><i class="fa-solid fa-house fs-2"></i></a>
		<h1 class="fs-2 ms-4">Products</h1>
	</div>
	<c:if test="${not empty alert}">
		<div class="d-flex mt-4 alert-message">
			<c:choose>
				<c:when test="${alert.getStatus() == 'SUCCESS'}">
					<c:set var="alertType" value="alert-success"></c:set>
				</c:when>
				<c:when test="${alert.getStatus() == 'ERROR'}">
					<c:set var="alertType" value="alert-danger"></c:set>
				</c:when>
				<c:when test="${alert.getStatus() == 'WARNING'}">
					<c:set var="alertType" value="alert-warning"></c:set>
				</c:when>
				<c:when test="${alert.getStatus() == 'INFO'}">
					<c:set var="alertType" value="alert-info"></c:set>
				</c:when>
			</c:choose>
			<div class="alert ${alertType}">
				${alert.getMessage()}
				<button class="btn btn-close"></button>
			</div>
		</div>
	</c:if>
	<main class="mt-4">
		<div class="d-flex">
			<c:url var="productsUrl" value="/products"></c:url>
			<form action="${productsUrl}">
                   <div class="d-flex">
                       <div class="input-group">
                           <input type="text" class="form-control" name="keyword" value="${param.keyword}" placeholder="Search Products" />
                           <button type="submit" class="btn btn-primary">
                               <i class="fa-solid fa-magnifying-glass"></i>
                           </button>
                       </div>
                   </div>
			</form>
			<c:url var="addNewProductUrl" value="/product/edit"></c:url>
			<a href="${addNewProductUrl}" class="btn btn-primary ms-3">
                   <i class="fa-solid fa-plus"></i>
                   Add New Product
               </a>
		</div>
		
		<c:if test="${not empty param.keyword}">
			<p class="mt-4 text-success">
				Search results for : ${param.keyword}
			</p>
		</c:if>
		
		<div class="table-responsive mt-2">
			<table class="table table-striped table-hover align-middle">
				<thead>
					<tr>
						<th scope="col">#</th>
						<th scope="col">Name</th>
						<th scope="col">Category</th>
						<th scope="col" class="text-end pe-5">Price</th>
						<th scope="col">Description</th>
						<th scope="col" class="text-center">Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${products.isEmpty()}">
						<tr>
							<td colspan="6" class="text-center">
								${empty param.keyword ? 'There is no content.' : 'No result found.'}
							</td>
						</tr>
					</c:if>
					<c:forEach var="product" items="${products}">
						<tr>
							<th scope="row">${product.getId()}</th>
							<td>${product.getName()}</td>
							<td>${product.getCategoryName()}</td>
							
							<td class="text-end pe-5">
								<f:formatNumber type="currency" value="${product.getUnitPrice()}" currencySymbol="" maxFractionDigits="0">
								</f:formatNumber>
							</td>
							<td>${product.getDescription()}</td>
							<td class="text-center" nowrap="nowrap">
								<c:url var="productEditUrl" value="/product/edit">
									<c:param name="id">${product.getId()}</c:param>
								</c:url>
								<a class="btn btn-success" href="${productEditUrl}">
									<i class="fa-solid fa-pen-to-square"></i>
								</a>
								<c:url var="productDetailUrl" value="/product/detail">
									<c:param name="id">${product.getId()}</c:param>
								</c:url>
								<a class="btn btn-secondary" href="${productDetailUrl}">
									<i class="fa-solid fa-circle-info"></i>
								</a>
								<c:url var="productDeleteUrl" value="/product/delete">
									<c:param name="id">${product.getId()}</c:param>
								</c:url>
								<a class="btn btn-danger" href="${productDeleteUrl}" onclick="return confirm('Are you sure to delete this product?')">
									<i class="fa-solid fa-trash-can"></i>
								</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</main>
</body>
</html>