<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">科目情報登録</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<form class="px-4" action="SubjectCreateExecute.action" method="post">
			<div class="mb-3">
				<label class="form-label">科目コード</label>
				<input class="form-control" type="text" name="cd" value="<c:out value='${cd}' />" maxlength="3" placeholder="科目コードを入力してください" required>
				<my:error message="${errors.cd}" />
			</div>
			<div class="mb-3">
				<label class="form-label">科目名</label>
				<input class="form-control" type="text" name="name" value="<c:out value='${name}' />" maxlength="20" placeholder="科目名を入力してください" required>
				<my:error message="${errors.name}" />
			</div>
			<div class="mt-4">
				<button class="btn btn-secondary" type="submit">登録</button>
			</div>
			<div class="mt-3">
				<a href="SubjectList.action">戻る</a>
			</div>
		</form>
	</c:param>
</c:import>