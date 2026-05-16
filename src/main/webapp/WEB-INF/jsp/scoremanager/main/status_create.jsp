<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">状態情報登録</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<form class="px-4" action="StatusCreateExecute.action" method="post">
			<div class="mb-3">
				<label class="form-label">状態名</label>
				<input class="form-control" type="text" name="name" value="<c:out value='${name}' />" maxlength="10" placeholder="状態名を入力してください" required>
				<my:error message="${errors.name}" />
			</div>
			<div class="mb-3">
				<label class="form-label">並び順</label>
				<input class="form-control" type="number" name="sort_order" value="<c:out value='${sort_order}' />" placeholder="並び順を入力してください" required>
				<my:error message="${errors.sort_order}" />
			</div>
			<div class="mt-4">
				<button class="btn btn-secondary" type="submit">登録</button>
			</div>
			<div class="mt-3">
				<a href="StatusList.action">戻る</a>
			</div>
		</form>
	</c:param>
</c:import>