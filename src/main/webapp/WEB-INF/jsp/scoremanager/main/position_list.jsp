<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">役職管理</h2>
		<div class="my-2 text-end px-4">
			<a href="PositionCreate.action">新規登録</a>
		</div>
		<c:choose>
			<c:when test="${ position_set.size() > 0 }">
				<div>検索結果：${ position_set.size() }件</div>
				<table class="table table-hover">
					<tr>
						<th>役職名</th>
						<th>並び順</th>
						<th></th>
						<th></th>
					</tr>
					<c:forEach var="position" items="${ position_set }">
						<tr>
							<td>${ position.name }</td>
							<td>${ position.sortOrder }</td>
							<td><a href="PositionUpdate.action?id=${ position.id }">変更</a></td>
							<td><a href="PositionDelete.action?id=${ position.id }">削除</a></td>
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