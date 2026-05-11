<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">職員管理</h2>
		<div class="my-2 text-end px-4">
			<a href="PositionList.action" class="me-3">役職管理</a>
			<a href="StatusList.action" class="me-3">状態管理</a>
			<a href="StaffCreate.action">新規登録</a>
		</div>
		<form method="get">
			<div class="row border mx-3 mb-3 py-3 align-items-end rounded bg-light" id="filter">
				<div class="col-md-3">
					<label class="form-label" for="name">名前</label>
					<input type="text" name="name" id="name" class="form-control" placeholder="氏名を入力">
				</div>
				<div class="col-md-3">
					<label for="position_id" class="form-label">役職</label>
					<select name="position_id" id="position_id" class="form-select">
						<option value="">--------</option>
						<c:forEach var="p" items="${position_set}">
							<option value="${p.id}" <c:if test="${p.id == position_id}">selected</c:if>>${p.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-3">
					<label for="status_id" class="form-label">状態</label>
					<select name="status_id" id="status_id" class="form-select">
						<option value="">--------</option>
						<c:forEach var="s" items="${status_set}">
							<option value="${s.id}" <c:if test="${s.id == status_id}">selected</c:if>>${s.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-3 d-grid">
					<button type="submit" class="btn btn-secondary" id="filter-button">絞込み</button>
				</div>
				<c:if test="${not empty errors.get('f1')}">
					<div class="col-12 mt-2 text-danger small">${ errors.get("f1") }</div>
				</c:if>
			</div>
		</form>
		<c:choose>
			<c:when test="${ staff_set.size() > 0 }">
				<div>検索結果：${ staff_set.size() }件</div>
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
							<td>${ staff.lastName }&nbsp;${ staff.firstName }</td>
							<td>${ staff.lastNameKana }&nbsp;${ staff.firstNameKana }</td>
							<td>${ staff.position.name }</td>
							<td>${ staff.status.name }</td>
							<td><a href="StaffUpdate.action?cd=${ staff.no }">変更</a></td>
							<td><a href="StaffDelete.action?cd=${ staff.no }">削除</a></td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<div>職員情報が存在しませんでした。</div>
			</c:otherwise>
		</c:choose>
	</c:param>
</c:import>