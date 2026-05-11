<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">学生情報登録</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<p class="mb-3 fw-normal bg-success py-1 px-4 text-center" style="--bs-bg-opacity: .6">登録が完了しました</p>
		<br>
		<br>
		<a href="StudentCreate.action">戻る</a>
		<a href="StudentList.action">学生一覧</a>
	</c:param>
</c:import>