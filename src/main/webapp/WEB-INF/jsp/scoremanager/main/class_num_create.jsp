<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">クラス情報登録</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<form action="ClassNumCreateExecute.action" method="post" class="px-4">
			<div class="mb-3">
				<label class="form-label">クラス番号</label>
				<input class="form-control" type="text" name="num" value="<c:out value='${num}' />" maxlength="3" placeholder="クラス番号を入力してください" required>
				<my:error message="${errors.num}" />
			</div>
			<div class="mt-4">
				<button class="btn btn-primary px-3" type="submit">登録</button>
			</div>
			<div class="mt-3">
				<a href="ClassNumList.action">戻る</a>
			</div>
		</form>
	</c:param>
</c:import>