<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">学生情報CSV出力</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<div class="card-body">
			<p class="text-muted small">学籍番号, 氏名, 入学年度, クラス番号, 在学フラグ(true/false)の形式で保存されます。</p>
			<form action="StudentBatchExportExecute.action" method="post" class="mt-4">
				<div class="row border mx-3 mb-3 py-3 align-items-end rounded bg-light" id="filter">
					<div class="col-md-3">
						<label class="form-label" for="student-ent_year-select">入学年度</label>
						<select class="form-select" id="student-ent_year-select" name="ent_year" required>
							<option value="">--------</option>
							<c:forEach var="year" items="${ ent_year_list }">
								<option value="${ year }" <c:if test="${ year==ent_year }">selected</c:if>>${ year }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-3">
						<label class="form-label" for="student-class_num-select">クラス</label>
						<select class="form-select" id="student-class_num-select" name="class_num" required>
							<option value="">--------</option>
							<c:forEach var="num" items="${ class_num_list }">
								<option value="${ num }" <c:if test="${ num==class_num }">selected</c:if>>${ num }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-3 pb-2">
						<div class="form-check">
							<input class="form-check-input" type="checkbox" id="student-is_attend-check" name="is_attend" value="t" <c:if test="${ is_attend }">checked</c:if>>
							<label class="form-check-label" for="student-is_attend-check">在学中</label>
						</div>
					</div>
					<div class="col-md-3 d-grid">
						<button type="submit" class="btn btn-secondary" id="filter-button">ダウンロード</button>
					</div>
					<c:if test="${not empty errors.get('ent_year')}">
						<div class="col-12 mt-2 text-danger small">${ errors.get("ent_year") }</div>
					</c:if>
				</div>
			</form>
			<c:if test="${not empty message}">
				<div class="alert alert-danger mt-3">${message}</div>
			</c:if>
			<c:if test="${not empty errors.get('student_list_size')}">
				<div class="alert alert-danger mt-3">${errors.get('student_list_size')}</div>
			</c:if>
		</div>
		<a href="StudentList.action" class="text-decoration-underline">戻る</a>
		</div>
	</c:param>
</c:import>