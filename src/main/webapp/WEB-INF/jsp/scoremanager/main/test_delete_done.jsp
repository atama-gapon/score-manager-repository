<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">成績情報削除</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<p>
			<a href="TestListStudentExecute.action?f4=${student_no}">成績一覧</a>
		</p>
	</c:param>
</c:import>