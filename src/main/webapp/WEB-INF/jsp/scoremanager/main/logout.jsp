<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">ログアウト</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<my:done message="ログアウトしました">
			<a class="text-decoration-underline" href="../Login.action">ログイン</a>
		</my:done>
	</c:param>
</c:import>