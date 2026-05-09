<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">ステータス新規登録</h2>
		<form action="StatusCreateExecute.action" method="post" class="px-4">
			<div class="mb-3">
				<label class="form-label">ステータス名</label>
				<input type="text" name="name" class="form-control" value="${ name }">
				<div class="text-warning">${ errors.get("name") }</div>
			</div>
			<div class="mb-3">
				<label class="form-label">並び順</label>
				<input type="number" name="sortOrder" class="form-control" value="${ sortOrder }">
				<div class="text-warning">${ errors.get("sortOrder") }</div>
			</div>
			<div class="mt-4">
				<button class="btn btn-secondary">登録</button>
				<a href="StatusList.action" class="ms-3">戻る</a>
			</div>
		</form>
	</c:param>
</c:import>