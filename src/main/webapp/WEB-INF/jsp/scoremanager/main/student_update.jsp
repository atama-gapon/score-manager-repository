<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">学生情報変更</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<form class="px-4" action="StudentUpdateExecute.action" method="post">
			<input type="hidden" name="submitted" value="true">
			<div class="mb-3">
				<label class="form-label">入学年度</label>
				<select class="form-select" name="ent_year" required>
					<option value="">--------</option>
					<c:forEach var="y" items="${ent_year_list}">
						<option value="<c:out value='${y}' />" <c:if test="${y == student.entYear}">selected</c:if>><c:out value="${y}" /></option>
					</c:forEach>
				</select>
				<my:error message="${errors.ent_year}" />
			</div>
			<div class="mb-3">
				<label class="form-label">学生番号</label>
				<input class="form-control-plaintext" type="text" name="no" value="<c:out value='${student.no}' />" readonly>
				<my:error message="${errors.no}" />
			</div>
			<div class="mb-3">
				<label class="form-label">氏名</label>
				<input class="form-control" type="text" name="name" value="<c:out value='${student.name}' />" placeholder="氏名を入力してください" required>
				<my:error message="${errors.name}" />
			</div>
			<div class="mb-3">
				<label class="form-label">クラス</label>
				<select class="form-select" name="class_num" required>
					<option value="">--------</option>
					<c:forEach var="c" items="${class_num_list}">
						<option value="<c:out value='${c}' />" <c:if test="${c == student.classNum}">selected</c:if>><c:out value="${c}" /></option>
					</c:forEach>
				</select>
				<my:error message="${errors.class_num}" />
			</div>
			<div class="mb-3 form-check">
				<input class="form-check-input" type="checkbox" id="is_attend" name="is_attend" value="true" <c:if test="${student.attend}">checked</c:if>>
				<label class="form-check-label" for="is_attend">在学中</label>
				<my:error message="${errors.is_attend}" />
			</div>
			<div class="mt-4">
				<button class="btn btn-secondary" type="submit">変更</button>
			</div>
			<div class="mt-3">
				<a href="StudentList.action">戻る</a>
			</div>
		</form>
	</c:param>
</c:import>