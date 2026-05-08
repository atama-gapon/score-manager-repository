<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">役職情報変更</h2>

		<p
			class="mb-3 fw-normal bg-success py-1 px-4 text-center"
			style="--bs-bg-opacity: .6">

			変更が完了しました

		</p>

		<br>
		<br>
		<br>
		<br>

		<a href="PositionList.action">

			役職一覧

		</a>

	</c:param>
</c:import>