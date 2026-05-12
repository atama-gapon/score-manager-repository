<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">状態情報変更</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<form action="StatusUpdateExecute.action" method="post" class="px-4">
			<input type="hidden" name="id" value="${status.id}">
			<div class="mb-3">
				<label class="form-label">状態名</label>
				<input type="text" name="name" value="${name}" class="form-control" maxlength="20" placeholder="状態名を入力してください" required>
				<c:if test="${not empty errors.name}">
					<div class="mt-2 text-warning">${errors.name}</div>
				</c:if>
			</div>
			<div class="mb-3">
				<label class="form-label">並び順</label>
				<input type="number" name="sort_order" value="${sort_order}" class="form-control" placeholder="並び順を入力してください" required>
			</div>
			<div class="mt-4">
				<button type="submit" class="btn btn-secondary">変更</button>
			</div>
			<div class="mt-3">
				<a href="StatusList.action">戻る</a>
			</div>
		</form>
	</c:param>
</c:import>