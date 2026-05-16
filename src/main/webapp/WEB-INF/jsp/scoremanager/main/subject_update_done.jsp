<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">科目情報変更</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<my:done message="変更が完了しました">
			<a href="SubjectList.action" class="text-decoration-underline">科目一覧</a>
		</my:done>
	</c:param>
</c:import>
