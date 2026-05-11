<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">役職情報削除</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<p>「${ name }」 を削除してもよろしいですか</p>
		<form action="PositionDeleteExecute.action" method="get">
			<input name="id" type="hidden" value="${ id }">
			<input type="submit" class="btn btn-danger px-3" value="削除">
		</form>
		<br>
		<br>
		<br>
		<br>
		<p>
			<a href="PositionList.action"> 戻る </a>
		</p>
	</c:param>
</c:import>