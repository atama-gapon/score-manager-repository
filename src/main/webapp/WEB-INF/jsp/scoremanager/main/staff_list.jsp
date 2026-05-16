<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">職員管理</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<div class="my-2 text-end px-4">
			<a class="me-3" href="PositionList.action">役職管理</a>
			<a class="me-3" href="StatusList.action">状態管理</a>
			<c:if test="${staff.position.name eq '管理者'}">
				<a href="StaffCreate.action">新規登録</a>
			</c:if>
		</div>
		<!--
		<form method="get">
			<div class="row border mx-3 mb-3 py-3 align-items-end rounded bg-light" id="filter">
				<div class="col-md-3">
					<label class="form-label" for="name">名前</label>
					<input class="form-control" type="text" name="name" id="name" placeholder="氏名を入力">
				</div>
				<div class="col-md-3">
					<label class="form-label" for="position_id">役職</label>
					<select class="form-select" name="position_id" id="position_id">
						<option value="">--------</option>
						<c:forEach var="p" items="${position_list}">
							<option value="${p.id}" <c:if test="${p.id == position_id}">selected</c:if>>${p.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-3">
					<label class="form-label" for="status_id">状態</label>
					<select class="form-select" name="status_id" id="status_id">
						<option value="">--------</option>
						<c:forEach var="s" items="${status_list}">
							<option value="${s.id}" <c:if test="${s.id == status_id}">selected</c:if>>${s.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-3 d-grid">
					<button class="btn btn-secondary" type="submit" id="filter-button">絞込み</button>
				</div>
				<c:if test="${not empty errors.get('ent_year')}">
					<div class="col-12 mt-2 text-danger small">${ errors.get("ent_year") }</div>
				</c:if>
			</div>
		</form>
		-->
		<c:choose>
			<c:when test="${ not empty staff_list }">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>職員番号</th>
							<th>氏名</th>
							<th>氏名（カナ）</th>
							<th>役職</th>
							<th>状態</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="s" items="${ staff_list }">
							<tr>
								<td><c:out value="${ s.no }" /></td>
								<td><c:out value="${ s.lastName }" />&nbsp;<c:out value="${ s.firstName }" /></td>
								<td><c:out value="${ s.lastNameKana }" />&nbsp;<c:out value="${ s.firstNameKana }" /></td>
								<td><c:out value="${ s.position.name }" /></td>
								<td><c:out value="${ s.status.name }" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<div class="mx-2 my-2">職員情報が存在しませんでした</div>
			</c:otherwise>
		</c:choose>
	</c:param>
</c:import>