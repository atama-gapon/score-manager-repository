<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">成績一覧（科目）</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<my:test_search_form />
		<my:error message="${message}" />
		<c:choose>
			<c:when test="${ not empty test_subject_list }">
				<div class="mt-3">
					科目：
					<c:out value="${ subject.name }" />
				</div>
				<table class="table table-hover mt-3">
					<thead>
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>1回</th>
							<th>2回</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="tlsubject" items="${ test_subject_list }">
							<tr>
								<td><c:out value="${ tlsubject.entYear }" /></td>
								<td><c:out value="${ tlsubject.classNum }" /></td>
								<td><c:out value="${ tlsubject.studentNo }" /></td>
								<td><c:out value="${ tlsubject.studentName }" /></td>
								<td><c:set var="done1" value="false" /> <c:forEach var="point" items="${tlsubject.points}">
										<c:if test="${point.key == 1}">
											<c:out value="${point.value}" />
											<c:set var="done1" value="true" />
										</c:if>
									</c:forEach> <c:if test="${!done1}">-</c:if></td>
								<td><c:set var="done2" value="false" /> <c:forEach var="point" items="${tlsubject.points}">
										<c:if test="${point.key == 2}">
											<c:out value="${point.value}" />
											<c:set var="done2" value="true" />
										</c:if>
									</c:forEach> <c:if test="${!done2}">-</c:if></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<div class="mx-2 my-2">学生情報が存在しませんでした</div>
			</c:otherwise>
		</c:choose>
	</c:param>
</c:import>