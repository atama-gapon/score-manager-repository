<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<style>
input::placeholder {
	font-size: 15px;
}

.form-control {
	height: 35px;
}
</style>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">クラス情報変更</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<form action="ClassUpdateExecute.action" method="post">
			<label class="form-label">科目コード</label>
			<label for="class_num">クラス番号</label>
			<input id="class_num" class="form-control" name="class_num" value="${class_num}" type="text" maxlength="5" placeholder="クラス番号を入力してください" required>
			<div class="text-warning">${ errors.get("class_num_duplication") }</div>
			<input name="old_class_num" type="hidden" value='${ old_class_num }'>
			<p>
				<input type="submit" value="変更">
			</p>
		</form>
		<p>
			<a href="ClassList.action">戻る</a>
		</p>
	</c:param>
</c:import>