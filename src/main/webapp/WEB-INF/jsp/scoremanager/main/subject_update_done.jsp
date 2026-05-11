<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">科目情報変更</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<c:import url="/WEB-INF/jsp/common/done.jsp">
			<c:param name="successMessage">変更が完了しました</c:param>
			<c:param name="nextStepLinks">
				<a href="SubjectList.action" class="text-decoration-underline">科目一覧</a>
			</c:param>
		</c:import>
	</c:param>
</c:import>