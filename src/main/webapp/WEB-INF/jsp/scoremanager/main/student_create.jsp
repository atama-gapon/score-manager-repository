<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">学生情報登録</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<form action="StudentCreateExecute.action" method="post" class="px-4">
			<div class="mb-3">
				<label class="form-label">入学年度</label>
				<select name="ent_year" class="form-select" required>
					<option value="">--------</option>
					<c:forEach var="y" items="${ent_year_list}">
						<option value="<c:out value='${y}' />" <c:if test="${y == ent_year}">selected</c:if>><c:out value="${y}" /></option>
					</c:forEach>
				</select>
				<my:error message="${errors.ent_year}" />
			</div>
			<div class="mb-3">
				<label class="form-label">学生番号</label>
				<input type="text" name="no" value="<c:out value='${no}' />" class="form-control" maxlength="10" placeholder="学生番号を入力してください" required>
				<my:error message="${errors.no}" />
			</div>
			<div class="mb-3">
				<label class="form-label">氏名</label>
				<input type="text" name="name" value="<c:out value='${name}' />" class="form-control" placeholder="氏名を入力してください" required>
				<my:error message="${errors.name}" />
			</div>
			<div class="mb-3">
				<label class="form-label">クラス</label>
				<select name="class_num" class="form-select" required>
					<option value="">--------</option>
					<c:forEach var="c" items="${class_num_list}">
						<option value="<c:out value='${c}' />" <c:if test="${c == class_num}">selected</c:if>><c:out value="${c}" /></option>
					</c:forEach>
				</select>
				<my:error message="${errors.class_num}" />
			</div>
			<div class="mb-3 form-check">
				<label class="form-check-label">在学中</label>
				<input class="form-check-input" type="checkbox" name="is_attend" value="true" <c:if test="${student.attend}">checked</c:if>>
				<my:error message="${errors.is_attend}" />
			</div>
			<div class="mt-4">
				<button type="submit" class="btn btn-secondary">登録</button>
			</div>
			<div class="mt-3">
				<a href="StudentList.action">戻る</a>
			</div>
		</form>
	</c:param>
</c:import>