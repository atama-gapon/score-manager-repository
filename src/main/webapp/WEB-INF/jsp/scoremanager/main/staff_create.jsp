<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">職員情報登録</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<form action="StaffCreateExecute.action" method="post" class="px-4">
			<div class="mb-3">
				<label class="form-label">職員番号</label>
				<input type="text" name="no" value="${no}" class="form-control" maxlength="10" placeholder="職員番号を入力してください" required>
				<c:if test="${not empty errors.no}">
					<div class="mt-2 text-warning">${errors.no}</div>
				</c:if>
			</div>
			<div class="mb-3">
				<label class="form-label">姓</label>
				<input type="text" name="last_name" value="${last_name}" class="form-control" placeholder="姓を入力してください" required>
			</div>
			<div class="mb-3">
				<label class="form-label">名</label>
				<input type="text" name="first_name" value="${first_name}" class="form-control" placeholder="名を入力してください" required>
			</div>
			<div class="mb-3">
				<label class="form-label">姓（カタカナ）</label>
				<input type="text" name="last_name_kana" value="${last_name_kana}" class="form-control" placeholder="姓（カタカナ）を入力してください" required>
			</div>
			<div class="mb-3">
				<label class="form-label">名（カタカナ）</label>
				<input type="text" name="first_name_kana" value="${first_name_kana}" class="form-control" placeholder="名（カタカナ）を入力してください" required>
			</div>
			<div class="mb-3">
				<label class="form-label">パスワード</label>
				<input type="text" name="password" value="${password}" class="form-control" placeholder="パスワードを入力してください" required>
			</div>
			<div class="mb-3">
				<label class="form-label">パスワード（確認用）</label>
				<input type="text" name="password2" value="${password2}" class="form-control" placeholder="パスワード（確認用）を入力してください" required>
			</div>
			<div class="mb-3">
				<label class="form-label">役職</label>
				<select name="position_id" class="form-select" required>
					<option value="">--------</option>
					<c:forEach var="p" items="${position_set}">
						<option value="${p.id}" <c:if test="${p.id == position_id}">selected</c:if>>${p.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="mb-3">
				<label class="form-label">状態</label>
				<select name="status_id" class="form-select" required>
					<option value="">--------</option>
					<c:forEach var="s" items="${status_set}">
						<option value="${s.id}" <c:if test="${s.id == status_id}">selected</c:if>>${s.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="mt-4">
				<button type="submit" class="btn btn-secondary">登録</button>
			</div>
			<div class="mt-3">
				<a href="StaffList.action">戻る</a>
			</div>
		</form>
	</c:param>
</c:import>