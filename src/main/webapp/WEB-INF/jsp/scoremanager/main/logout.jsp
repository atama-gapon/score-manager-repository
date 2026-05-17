<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">ログアウト</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">ログアウト</h2>
		<my:done message="ログアウトしました">
			<a class="text-decoration-underline" href="../Login.action">ログイン</a>
		</my:done>
	</c:param>
</c:import>