<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">クラス情報登録</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<form action="ClassCreateExecute.action" method="post" class="px-4">
			<div class="mb-3">
				<label class="form-label">クラス番号</label>
				<input type="text" name="class_num" value="${class_num}" class="form-control" maxlength="3" placeholder="クラス番号を入力してください" required>
				<c:if test="${not empty errors.class_num_duplication}">
					<div class="mt-2 text-warning">${errors.class_num_duplication}</div>
				</c:if>
			</div>
			<div class="mt-4">
				<button type="submit" class="btn btn-secondary">登録</button>
			</div>
			<div class="mt-3">
				<a href="ClassList.action">戻る</a>
			</div>
		</form>
	</c:param>
</c:import>