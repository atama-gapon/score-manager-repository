<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">役職情報変更</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<my:done message="変更が完了しました">
			<a class="text-decoration-underline" href="PositionList.action">役職一覧</a>
		</my:done>
	</c:param>
</c:import>