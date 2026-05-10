<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">状態情報登録</h2>
		<form action="StatusCreateExecute.action" method="post" class="px-4">
			<div class="mb-3">
				<label class="form-label">状態名</label>
				<input type="text" name="name" value="${name}" class="form-control" maxlength="10" placeholder="状態名を入力してください" required>
				<c:if test="${not empty errors.name}">
					<div class="mt-2 text-warning">${errors.name}</div>
				</c:if>
			</div>
			<div class="mb-3">
				<label class="form-label">並び順</label>
				<input type="number" name="sort_order" value="${sort_order}" class="form-control" placeholder="並び順を入力してください" required>
			</div>
			<div class="mt-4">
				<button type="submit" class="btn btn-secondary">登録</button>
			</div>
			<div class="mt-3">
				<a href="StatusList.action">戻る</a>
			</div>
		</form>
	</c:param>
</c:import>