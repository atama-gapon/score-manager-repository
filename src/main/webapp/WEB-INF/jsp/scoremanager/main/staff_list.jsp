<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">職員管理</h2>
			<div class="my-2 text-end px-4">
				<a href="StaffCreate.action">新規登録</a>
			</div>
			<table class="table table-hover">
				<tr>
					<th>職員番号</th>
					<th>氏名</th>
					<th>氏名（カナ）</th>
					<th>役職</th>
					<th>状態</th>
					<th></th>
					<th></th>
				</tr>
				<c:forEach var="staff" items="${ staff_set }">
					<tr>
						<td>${ staff.no }</td>
						<td>${ staff.lastName }${ staff.firstName }</td>
						<td>${ staff.lastNameKana }${ staff.firstNameKana }</td>
						<td>${ staff.position.name }</td>
						<td>${ staff.status.name }</td>
						<td><a href="StaffUpdate.action?cd=${ staff.no }">変更</a></td>
						<td><a href="StaffDelete.action?cd=${ staff.no }">削除</a></td>
					</tr>
				</c:forEach>
			</table>
		</section>
	</c:param>
</c:import>