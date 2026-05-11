<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">状態情報削除</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<div class="px-4">
			<p>以下のステータスを削除します。よろしいですか？</p>
			<table class="table">
				<tr>
					<th>ID</th>
					<td>${ id }</td>
				</tr>
				<tr>
					<th>ステータス名</th>
					<td>${ name }</td>
				</tr>
				<tr>
					<th>並び順</th>
					<td>${ sortOrder }</td>
				</tr>
			</table>
			<form action="StatusDeleteExecute.action" method="post">
				<input type="hidden" name="id" value="${ id }">
				<button class="btn btn-danger">削除する</button>
				<a href="StatusList.action" class="btn btn-secondary ms-3">戻る</a>
			</form>
		</div>
	</c:param>
</c:import>
