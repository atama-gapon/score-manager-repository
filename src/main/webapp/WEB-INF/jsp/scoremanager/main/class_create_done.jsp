<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">クラス情報登録</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<div class="mb-3 fw-normal bg-success py-2 px-4 text-center" style="--bs-bg-opacity: .6">登録が完了しました</div>
		<div class="mt-5 pt-5">
			<div class="d-flex gap-5">
				<a href="ClassCreate.action" class="text-decoration-underline">戻る</a>
				<a href="ClassList.action" class="text-decoration-underline">クラス一覧</a>
			</div>
		</div>
	</c:param>
</c:import>