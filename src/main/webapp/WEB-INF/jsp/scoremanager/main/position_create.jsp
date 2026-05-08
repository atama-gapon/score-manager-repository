<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<section class="me-4">
		<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">役職情報登録</h2>
			<form action="PositionCreateExecute.action" method="post" class="px-4">
				<label>役職名</label> <input type="text" name="name"
					class="form-control" value="${name}">
				<c:if test="${not empty errors.name}">
					<div class="col-12 mt-2 text-warning">${errors.name}</div>
				</c:if>
				<br>
				<label>表示順</label>
				<input type="number" name="sort_order" class="form-control" value="${sort_order}">
				<c:if test="${not empty errors.sort_order}">
					<div class="col-12 mt-2 text-warning">${errors.sort_order}</div>
				</c:if>
				<br>
				<button type="submit" class="btn btn-secondary">登録して終了</button>
				<br>
			</form>
			<p>
				<a href="PositionList.action">戻る</a>
			</p>
		</section>
	</c:param>
</c:import>