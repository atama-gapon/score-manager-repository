<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<style>
input::placeholder {
    font-size: 15px;
}

.form-control {
    height: 35px;
}
</style>

<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>
	
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>
			<form action="SubjectUpdateExecute.action" method="get">
				<label class="form-label">科目コード</label>
				<input style="border: none; box-shadow: none;" type="text" class="form-control" name="cd" value="${ cd }" readonly>

				<div class="col-12 mt-2 text-warning">${ errors.get("subject_exist") }</div>

				<label class="form-label">科目名</label>
				<input type="text" class="form-control" placeholder="科目名を入力してください" maxlength="20" name="name" value="${ name }" required>
				<br>
				<input type="submit" class="btn btn-primary px-3" value="変更">
			</form>
			<p><a href="SubjectList.action">戻る</a></p>
		</section>
	</c:param>
</c:import>