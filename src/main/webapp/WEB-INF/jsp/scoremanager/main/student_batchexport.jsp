<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">学生情報一括保存</h2>
		<div class="card shadow-sm">
			<div class="card-header bg-secondary text-white">
				<h2 class="h5 mb-0">学生情報一括保存（CSVダウンロード）</h2>
			</div>
			<div class="card-body">
				<c:if test="${not empty error}">
					<div class="alert alert-danger" role="alert">
						<i class="bi bi-exclamation-triangle-fill"></i> ${error}
					</div>
				</c:if>
				<p class="text-muted small">
					 学籍番号, 氏名, 入学年度, クラス番号, 在学フラグ(true/false)の形式で保存されます。
				</p>
				<form action="StudentBulkExecute.action" method="post" enctype="multipart/form-data" class="mt-4">
					<div class="row border mx-3 mb-3 py-3 align-items-end rounded bg-light" id="filter">
						<div class="col-md-3">
							<label class="form-label" for="student-f1-select">入学年度</label>
							<select class="form-select" id="student-f1-select" name="f1">
								<option value="0">--------</option>
								<c:forEach var="year" items="${ ent_year_set }">
									<option value="${ year }" <c:if test="${ year==f1 }">selected</c:if>>${ year }</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-3">
							<label class="form-label" for="student-f2-select">クラス</label>
							<select class="form-select" id="student-f2-select" name="f2">
								<option value="0">--------</option>
								<c:forEach var="num" items="${ class_num_set }">
									<option value="${ num }" <c:if test="${ num==f2 }">selected</c:if>>${ num }</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-3 pb-2">
							<div class="form-check">
								<input class="form-check-input" type="checkbox" id="student-f3-check" name="f3" value="t" <c:if test="${ f3 }">checked</c:if>>
								<label class="form-check-label" for="student-f3-check">在学中のみ</label>
							</div>
						</div>
						<div class="col-md-3 d-grid">
							<button type="submit" class="btn btn-secondary" id="filter-button">保存</button>
						</div>
						<c:if test="${not empty errors.get('f1')}">
							<div class="col-12 mt-2 text-danger small">${ errors.get("f1") }</div>
						</c:if>
					</div>
				</form>
			</div>
		</div>
	</c:param>
</c:import>