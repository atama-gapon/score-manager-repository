<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">職員情報変更</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<form class="px-4" action="StaffUpdateExecute.action" method="post">
			<input type="hidden" name="no" value="<c:out value='${staff.no}' />">
			<div class="mb-3">
				<label class="form-label">職員番号</label>
				<input class="form-control" type="text" value="<c:out value='${staff.no}' />" readonly>
			</div>
			<div class="mb-3">
				<label class="form-label">姓</label>
				<input class="form-control" type="text" name="last_name" value="<c:out value='${staff.lastName}' />">
				<my:error message="${errors.last_name}" />
			</div>
			<div class="mb-3">
				<label class="form-label">名</label>
				<input class="form-control" type="text" name="first_name" value="<c:out value='${staff.firstName}' />">
				<my:error message="${errors.first_name}" />
			</div>
			<div class="mb-3">
				<label class="form-label">姓（カタカナ）</label>
				<input class="form-control" type="text" name="last_name_kana" value="<c:out value='${staff.lastNameKana}' />">
				<my:error message="${errors.last_name_kana}" />
			</div>
			<div class="mb-3">
				<label class="form-label">名（カタカナ）</label>
				<input class="form-control" type="text" name="first_name_kana" value="<c:out value='${staff.firstNameKana}' />">
				<my:error message="${errors.first_name_kana}" />
			</div>
			<div class="mb-3">
				<label class="form-label">役職</label>
				<select class="form-select" name="position_id">
					<option value="">--------</option>
					<c:forEach var="p" items="${position_list}">
						<option value="<c:out value='${p.id}' />" <c:if test="${p.id == staff.position.id}">selected</c:if>><c:out value="${p.name}" /></option>
					</c:forEach>
				</select>
				<my:error message="${errors.position_id}" />
			</div>
			<div class="mb-3">
				<label class="form-label">状態</label>
				<select class="form-select" name="status_id">
					<option value="">--------</option>
					<c:forEach var="s" items="${status_list}">
						<option value="<c:out value='${s.id}' />" <c:if test="${s.id == staff.status.id}">selected</c:if>><c:out value="${s.name}" /></option>
					</c:forEach>
				</select>
				<my:error message="${errors.status_id}" />
			</div>
			<div class="mt-4">
				<button class="btn btn-secondary" type="submit">変更</button>
			</div>
			<div class="mt-3">
				<a href="StaffList.action">戻る</a>
			</div>
		</form>
	</c:param>
</c:import>