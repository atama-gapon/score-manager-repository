<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">科目情報削除</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<p>「${ cd }(${ name })」を削除してもよろしいですか</p>
		<form action="SubjectDeleteExecute.action" method="post">
			<input name="cd" type="hidden" value="${ cd }">
			<input type="submit" class="btn btn-danger px-3" value="削除">
		</form>
		<br>
		<br>
		<br>
		<br>
		<p>
			<a href="SubjectList.action">戻る</a>
		</p>
	</c:param>
</c:import>