<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">科目管理</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<div class="my-2 text-end px-4">
			<c:if test="${staff.position.name eq '管理者'}">
				<a href="SubjectCreate.action">新規登録</a>
			</c:if>
		</div>
		<c:choose>
		<c:when test="${ not empty subject_list }">
				<table class="table table-hover">
					<tr>
						<th>科目コード</th>
						<th>科目名</th>
						<c:if test="${staff.position.name eq '管理者'}">
							<th></th>
							<th></th>
						</c:if>
					</tr>
					<c:forEach var="subject" items="${ subject_list }">
						<tr>
							<td>${ subject.cd }</td>
							<td>${ subject.name }</td>
							<c:if test="${staff.position.name eq '管理者'}">
								<td><a href="SubjectUpdate.action?cd=${ subject.cd }">変更</a></td>
								<td><a href="SubjectDelete.action?cd=${ subject.cd }">削除</a></td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<div>科目情報が存在しませんでした。</div>
			</c:otherwise>
		</c:choose>
	</c:param>
</c:import>