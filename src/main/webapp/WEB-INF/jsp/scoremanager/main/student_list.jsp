<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">学生管理</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<div class="my-2 text-end px-4">
			<a href="StudentBatchExport.action" class="me-3">CSV出力</a>
			<a href="StudentBulk.action" class="me-3">CSV取込</a>
			<a href="StudentCreate.action">新規登録</a>
		</div>
		<form method="get">
			<input type="hidden" name="submitted" value="true">
			<div class="row border mx-3 mb-3 py-2 align-items-end rounded" id="filter">
				<div class="col-4">
					<label class="form-label" for="ent_year">入学年度</label>
					<select class="form-select" id="ent_year" name="ent_year">
						<option value="">--------</option>
						<c:forEach var="year" items="${ ent_year_list }">
							<option value="${ year }" <c:if test="${ year==ent_year }">selected</c:if>>${ year }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-4">
					<label class="form-label" for="class_num">クラス</label>
					<select class="form-select" id="class_num" name="class_num">
						<option value="">--------</option>
						<c:forEach var="num" items="${ class_num_list }">
							<option value="${ num }" <c:if test="${ num eq class_num }">selected</c:if>>${ num }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-2 py-3">
					<div class="form-check">
						<input class="form-check-input" type="checkbox" id="is_attend" name="is_attend" value="true" <c:if test="${ is_attend }">checked</c:if>>
						<label class="form-check-label" for="is_attend">在学中</label>
					</div>
				</div>
				<div class="col-2 py-3 text-center">
					<button type="submit" class="btn btn-secondary" id="filter-button">絞込み</button>
				</div>
				<my:error message="${errors.search}" />
			</div>
		</form>
		<c:choose>
			<c:when test="${ not empty student_list }">
				<div>検索結果：${ student_list.size() }件</div>
				<table class="table table-hover">
					<tr>
						<th>入学年度</th>
						<th>学生番号</th>
						<th>氏名</th>
						<th>クラス</th>
						<th class="text-center">在学中</th>
						<th></th>
					</tr>
					<c:forEach var="student" items="${ student_list }">
						<tr>
							<td><c:out value="${ student.entYear }" /></td>
							<td><c:out value="${ student.no }" /></td>
							<td><c:out value="${ student.name }" /></td>
							<td><c:out value="${ student.classNum }" /></td>
							<td class="text-center">
								<%-- 在学フラグがたっている場合「○」それ以外は「×」を表示 --%> <c:choose>
									<c:when test="${ student.attend }">○</c:when>
									<c:otherwise>×</c:otherwise>
								</c:choose>
							</td>
							<td><a href="<c:url value='StudentUpdate.action'><c:param name='no' value='${student.no}' /></c:url>">変更</a></td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<div>学生情報が存在しませんでした。</div>
			</c:otherwise>
		</c:choose>
	</c:param>
</c:import>