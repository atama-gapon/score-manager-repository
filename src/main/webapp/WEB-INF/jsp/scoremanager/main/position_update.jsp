<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<style>
.input-control {
	border: none;
	outline: none;
	background-color: transparent;
	padding-left: 1rem;
}
</style>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">役職情報変更</h2>
		<form action="PositionUpdateExecute.action" method="post" class="px-4">
			<div class="mb-3">
				<label class="form-label"> 役職名 </label>
				<input type="text" class="form-control" name="name" value="${position.name}">
				<c:if test="${not empty errors.name}">
					<div class="col-12 mt-2 text-warning">${errors.name}</div>
				</c:if>
			</div>
			<div class="mb-3">
				<label class="form-label"> 表示順 </label>
				<input type="number" class="form-control" name="sort_order" value="${position.sortOrder}">
				<c:if test="${not empty errors.sort_order}">
					<div class="col-12 mt-2 text-warning">${errors.sort_order}</div>
				</c:if>
			</div>
			<div class="mt-4">
				<input type="submit" value="変更" class="btn btn-primary">
				<br>
				<a href="PositionList.action">戻る</a>
			</div>
		</form>
	</c:param>
</c:import>