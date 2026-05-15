<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">学生情報CSV出力</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
			<div class="card-body">
				<p class="text-muted small">
					 学籍番号, 氏名, 入学年度, クラス番号, 在学フラグ(true/false)の形式で保存されます。
				</p>
				<form action="StudentBatchExportExecute.action" method="post" class="mt-4">
					<div class="row border mx-3 mb-3 py-3 align-items-end rounded bg-light" id="filter">
						<div class="col-md-3">
							<label class="form-label" for="student-f1-select">入学年度</label>
							<select class="form-select" id="student-f1-select" name="f1" required>
								<option value="">--------</option>
								<c:forEach var="year" items="${ ent_year_list }">
									<option value="${ year }" <c:if test="${ year==f1 }">selected</c:if>>${ year }</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-3">
							<label class="form-label" for="student-f2-select">クラス</label>
							<select class="form-select" id="student-f2-select" name="f2" required>
								<option value="">--------</option>
								<c:forEach var="num" items="${ class_num_list }">
									<option value="${ num }" <c:if test="${ num==f2 }">selected</c:if>>${ num }</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-3 pb-2">
							<div class="form-check">
								<input class="form-check-input" type="checkbox" id="student-f3-check" name="f3" value="t" <c:if test="${ f3 }">checked</c:if>>
								<label class="form-check-label" for="student-f3-check">在学中</label>
							</div>
						</div>
						<div class="col-md-3 d-grid">
							<button type="submit" class="btn btn-secondary" id="filter-button">ダウンロード</button>
						</div>
						<c:if test="${not empty errors.get('f1')}">
							<div class="col-12 mt-2 text-danger small">${ errors.get("f1") }</div>
						</c:if>
					</div>
				</form>
				<c:if test="${not empty message}">
					<div class="alert alert-danger mt-3">${message}</div>
				</c:if>
			</div>
			<a href="StudentList.action" class="text-decoration-underline">戻る</a>	
		</div>
		
	</c:param>
</c:import>