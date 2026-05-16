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
					<thead>
						<tr>
							<th>科目コード</th>
							<th>科目名</th>
							<c:if test="${staff.position.name eq '管理者'}">
								<th></th>
								<th></th>
							</c:if>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="subject" items="${ subject_list }">
							<tr>
								<td><c:out value="${subject.cd}" /></td>
								<td><c:out value="${subject.name}" /></td>
								<c:if test="${staff.position.name eq '管理者'}">
									<td><a href="SubjectUpdate.action?cd=<c:out value='${subject.cd}' />">変更</a></td>
									<td><a class="text-danger btn btn-link p-0" href="SubjectDelete.action?cd=<c:out value='${subject.cd}' />">削除</a></td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<div class="mx-2 my-2">科目情報が存在しませんでした</div>
			</c:otherwise>
		</c:choose>
	</c:param>
</c:import>