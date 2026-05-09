<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">ステータス登録</h2>

			<p class="mb-3 fw-normal bg-success py-1 px-4 text-center" style="--bs-bg-opacity: .6">登録が完了しました</p>

			<br>
			<br> <a href="StatusCreate.action">戻る</a> <a href="StatusList.action" class="ms-3">ステータス一覧</a>
		</section>
	</c:param>
</c:import>
