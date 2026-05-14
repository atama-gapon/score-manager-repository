<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">状態管理</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<div class="my-2 text-end px-4">
			<a href="StatusCreate.action">新規登録</a>
		</div>
		<c:choose>
			<c:when test="${ status_list.size() > 0 }">
				<div>検索結果：${ status_list.size() }件</div>
				<table class="table table-hover">
					<tr>
						<th>状態名</th>
						<th>並び順</th>
						<th></th>
						<th></th>
					</tr>
					<c:forEach var="status" items="${status_list}">
						<tr>
							<td>${ status.name }</td>
							<td>${ status.sortOrder }</td>
							<td><a href="StatusUpdate.action?id=${ status.id }">変更</a></td>
							<td><a href="StatusDelete.action?id=${ status.id }">削除</a></td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<div>状態情報が存在しませんでした。</div>
			</c:otherwise>
		</c:choose>
	</c:param>
</c:import>