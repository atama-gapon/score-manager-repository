<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
<c:param name="title">得点管理システム</c:param>
<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
		<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>
		<p>「${ cd }(${ name })」を削除してもよろしいですか</p>
		<form action="SubjectDeleteExecute.action" method="get">
			<input name="cd" type="hidden" value="${ cd }">
			<input type="submit" class="btn btn-danger px-3"  value="削除">
		</form>
			
			<br><br><br><br>
			
			<p><a href="SubjectList.action">戻る</a></p>
		</section>
	</c:param>
</c:import>