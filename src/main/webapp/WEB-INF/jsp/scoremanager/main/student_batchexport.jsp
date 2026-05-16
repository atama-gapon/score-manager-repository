<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">学生情報CSV出力</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<div class="card-body">
			<p class="text-muted small">学籍番号, 氏名, 入学年度, クラス番号, 在学フラグ(true/false)の形式で保存されます。</p>
			<form class="px-0" action="StudentBatchExportExecute.action" method="post">
				<input type="hidden" name="submitted" value="true">
				<div class="row border mx-3 mb-3 py-3 align-items-end rounded" id="filter">
					<div class="col-4">
						<label class="form-label mb-1" for="ent_year">入学年度</label>
						<select class="form-select" id="ent_year" name="ent_year">
							<option value="">--------</option>
							<c:forEach var="year" items="${ ent_year_list }">
								<option value="<c:out value='${year}' />" <c:if test="${ year==ent_year }">selected</c:if>><c:out value="${year}" /></option>
							</c:forEach>
						</select>
					</div>
					<div class="col-4">
						<label class="form-label mb-1" for="class_num">クラス</label>
						<select class="form-select" id="class_num" name="class_num">
							<option value="">--------</option>
							<c:forEach var="num" items="${ class_num_list }">
								<option value="<c:out value='${num}' />" <c:if test="${ num eq class_num }">selected</c:if>><c:out value="${num}" /></option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2">
						<div class="form-check mb-2">
							<input class="form-check-input me-2" type="checkbox" id="is_attend" name="is_attend" value="true" <c:if test="${ is_attend }">checked</c:if>>
							<label class="form-check-label" for="is_attend">在学中</label>
						</div>
					</div>
					<div class="col-2 text-end">
						<button class="btn btn-secondary px-3" type="submit" id="filter-button">ダウンロード</button>
					</div>
					<c:if test="${not empty errors.exist}">
						<div class="col-12 mt-2">
							<my:error message="${errors.exist}" />
						</div>
					</c:if>
				</div>
			</form>
		</div>
		<div class="mt-3">
			<a class="btn btn-link p-0 text-decoration-underline" href="StudentList.action">戻る</a>
		</div>
	</c:param>
</c:import>