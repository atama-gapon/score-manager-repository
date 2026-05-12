<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">ログアウト</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<p class="mb-3 fw-normal bg-success py-2 px-4 text-center" style="--bs-bg-opacity: .6">ログアウトしました</p>
		<a href="../Login.action">ログイン</a>
	</c:param>
</c:import>