<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">役職管理</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<div class="my-2 text-end px-4">
			<a href="StaffList.action" class="me-3">戻る</a>
			<c:if test="${staff.position.name eq '管理者'}">
				<a href="PositionCreate.action">新規登録</a>
			</c:if>
		</div>
		<c:choose>
			<c:when test="${ not empty position_list }">
				<table class="table table-hover">
					<tr>
						<th>役職名</th>
						<th>並び順</th>
						<c:if test="${staff.position.name eq '管理者'}">
							<th></th>
							<th></th>
						</c:if>
					</tr>
					<c:forEach var="position" items="${ position_list }">
						<tr>
							<td>${ position.name }</td>
							<td>${ position.sortOrder }</td>
							<c:if test="${staff.position.name eq '管理者' and position.name ne '管理者' }">
								<td><a href="PositionUpdate.action?id=${ position.id }">変更</a></td>
								<td><a href="PositionDelete.action?id=${ position.id }">削除</a></td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<div>役職情報が存在しませんでした。</div>
			</c:otherwise>
		</c:choose>
	</c:param>
</c:import>