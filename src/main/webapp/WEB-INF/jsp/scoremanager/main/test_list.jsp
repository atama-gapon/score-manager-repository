<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">成績参照</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<my:test_search_form />
		<my:error message="${message}" />
		<form class="mb-0" method="get" action="TestStudentListExecute.action">
			<c:if test="${empty test_subject_list && empty test_student_list}">
				<div class="mt-2 text-info">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</div>
			</c:if>
	</c:param>
</c:import>