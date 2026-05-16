<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">状態管理</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<div class="my-2 text-end px-4">
			<a class="btn btn-link p-0 me-3" href="StaffList.action">戻る</a>
			<c:if test="${staff.position.name eq '管理者'}">
				<a class="btn btn-primary" href="StatusCreate.action">新規登録</a>
			</c:if>
		</div>
		<c:choose>
			<c:when test="${ not empty status_list }">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>状態名</th>
							<th>並び順</th>
							<c:if test="${staff.position.name eq '管理者'}">
								<th></th>
								<th></th>
							</c:if>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="status" items="${status_list}">
							<tr>
								<td><c:out value="${status.name}" /></td>
								<td><c:out value="${status.sortOrder}" /></td>
								<c:if test="${staff.position.name eq '管理者'}">
									<td><a class="btn btn-link p-0" href="StatusUpdate.action?id=<c:out value='${status.id}' />">変更</a></td>
									<td><a class="btn btn-link p-0 text-danger" href="StatusDelete.action?id=<c:out value='${status.id}' />">削除</a></td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<div class="px-4 py-3 text-muted">状態情報が存在しませんでした</div>
			</c:otherwise>
		</c:choose>
	</c:param>
</c:import>