<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Catagories</title>
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
		<h1 class="fs-2 ms-4">Categories</h1>
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
	<main class="col-12 mt-4">
		<div class="d-flex">
			<c:url var="categoriesUrl" value="/categories"></c:url>
			<form action="${categoriesUrl}">
                   <div class="d-flex">
                       <div class="input-group">
                           <input type="text" class="form-control" name="keyword" value="${param.keyword}" placeholder="Search Categories" />
                           <button type="submit" class="btn btn-primary">
                               <i class="fa-solid fa-magnifying-glass"></i>
                           </button>
                       </div>
                   </div>
			</form>
			<c:url var="addNewCategoryUrl" value="/category/edit"></c:url>
			<a href="${addNewCategoryUrl}" class="btn btn-primary ms-3">
                   <i class="fa-solid fa-plus"></i>
                   Add New Category
               </a>
		</div>
		
		<c:if test="${not empty param.keyword}">
			<p class="mt-4 text-success">
				Search results for : ${param.keyword}
			</p>
		</c:if>
		
		<div class="table-responsive mt-4">
			<table class="table table-striped table-hover align-middle">
				<thead>
					<tr>
						<th scope="col">#</th>
						<th scope="col">Name</th>
						<th scope="col">Description</th>
	                    <th scope="col" class="text-center">Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${categories.isEmpty()}">
						<tr>
							<td colspan="4" class="text-center">
								${empty param.keyword ? 'There is no content.' : 'No result found.'}
							</td>
						</tr>
					</c:if>
					<c:forEach var="category" items="${categories}">
						<tr>
							<th scope="row">${category.getId()}</th>
							<td>${category.getName()}</td>
							<td>${category.getDescription()}</td>
							<td class="text-center">
								<c:url var="categoryEditUrl" value="/category/edit">
									<c:param name="id">${category.getId()}</c:param>
								</c:url>
								<a class="btn btn-success" href="${categoryEditUrl}">
									<i class="fa-solid fa-pen-to-square"></i>
								</a>
								<c:url var="categoryDetailUrl" value="/category/detail">
									<c:param name="id">${category.getId()}</c:param>
								</c:url>
								<a class="btn btn-secondary" href="${categoryDetailUrl}">
									<i class="fa-solid fa-circle-info"></i>
								</a>
								<c:url var="categoryDeleteUrl" value="/category/delete">
									<c:param name="id">${category.getId()}</c:param>
								</c:url>
								<a class="btn btn-danger" href="${categoryDeleteUrl}" onclick="return confirm('Are you sure to delete this category?')">
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