<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">状態管理</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<div class="my-2 text-end px-4">
			<a href="StaffList.action" class="me-3">戻る</a>
			<c:if test="${staff.position.name eq '管理者'}">
				<a href="StatusCreate.action">新規登録</a>
			</c:if>
		</div>
		<c:choose>
			<c:when test="${ status_list.size() > 0 }">
				<table class="table table-hover">
					<tr>
						<th>状態名</th>
						<th>並び順</th>
						<c:if test="${staff.position.name eq '管理者'}">
							<th></th>
							<th></th>
						</c:if>
					</tr>
					<c:forEach var="status" items="${status_list}">
						<tr>
							<td>${ status.name }</td>
							<td>${ status.sortOrder }</td>
							<c:if test="${staff.position.name eq '管理者'}">
								<td><a href="StatusUpdate.action?id=${ status.id }">変更</a></td>
								<td><a href="StatusDelete.action?id=${ status.id }">削除</a></td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<div>状態情報が存在しませんでした。</div>
			</c:otherwise>
		</c:choose>
		<div class="mt-3">
			<a href="StaffList.action">戻る</a>
		</div>
	</c:param>
</c:import>