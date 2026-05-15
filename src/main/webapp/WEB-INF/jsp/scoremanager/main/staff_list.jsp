<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">職員管理</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<div class="my-2 text-end px-4">
			<a href="PositionList.action" class="me-3">役職管理</a>
			<a href="StatusList.action" class="me-3">状態管理</a>
			<c:if test="${staff.position.name eq '管理者'}">
				<a href="StaffCreate.action">新規登録</a>
			</c:if>
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
						<c:forEach var="p" items="${position_list}">
							<option value="${p.id}" <c:if test="${p.id == position_id}">selected</c:if>>${p.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-3">
					<label for="status_id" class="form-label">状態</label>
					<select name="status_id" id="status_id" class="form-select">
						<option value="">--------</option>
						<c:forEach var="s" items="${status_list}">
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
			<c:when test="${ staff_list.size() > 0 }">
				<div>検索結果：${ staff_list.size() }件</div>
				<table class="table table-hover">
					<tr>
						<th>職員番号</th>
						<th>氏名</th>
						<th>氏名（カナ）</th>
						<th>役職</th>
						<th>状態</th>
						<c:if test="${staff.position.name eq '管理者'}">
							<th></th>
							<th></th>
						</c:if>
					</tr>
					<c:forEach var="s" items="${ staff_list }">
						<tr>
							<td>${ s.no }</td>
							<td>${ s.lastName }&nbsp;${ s.firstName }</td>
							<td>${ s.lastNameKana }&nbsp;${ s.firstNameKana }</td>
							<td>${ s.position.name }</td>
							<td>${ s.status.name }</td>
							<c:if test="${staff.position.name eq '管理者'}">
								<td><a href="StaffUpdate.action?cd=${ s.no }">変更</a></td>
								<td><a href="StaffDelete.action?cd=${ s.no }">削除</a></td>
							</c:if>
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