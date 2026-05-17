<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">学生管理</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<div class="my-2 text-end px-4">
			<a class="me-3" href="StudentBatchExport.action">CSV出力</a>
			<a class="me-3" href="StudentBulk.action">CSV取込</a>
			<a href="StudentCreate.action">新規登録</a>
		</div>
		<form class="px-0" method="get">
			<input type="hidden" name="submitted" value="true">
			<div class="row border mx-3 mb-3 py-3 align-items-end rounded bg-white shadow-sm" id="filter">
				<div class="col-3">
					<label class="form-label mb-1" for="ent_year">入学年度</label>
					<select class="form-select" id="ent_year" name="ent_year">
						<option value="">--------</option>
						<c:forEach var="year" items="${ ent_year_list }">
							<option value="<c:out value='${year}' />" <c:if test="${ year==ent_year }">selected</c:if>><c:out value="${year}" /></option>
						</c:forEach>
					</select>
				</div>
				<div class="col-3">
					<label class="form-label mb-1" for="class_num">クラス</label>
					<select class="form-select" id="class_num" name="class_num">
						<option value="">--------</option>
						<c:forEach var="num" items="${ class_num_list }">
							<option value="<c:out value='${num}' />" <c:if test="${ num eq class_num }">selected</c:if>><c:out value="${num}" /></option>
						</c:forEach>
					</select>
				</div>
				<div class="col-4 pb-2">
					<div class="form-check">
						<input class="form-check-input me-2" type="checkbox" id="is_attend" name="is_attend" value="true" <c:if test="${ is_attend }">checked</c:if>>
						<label class="form-check-label" for="is_attend">在学中</label>
					</div>
				</div>
				<div class="col-2 text-end">
					<button class="btn btn-secondary w-100" type="submit" id="filter-button">絞込み</button>
				</div>
				<c:if test="${not empty errors.search}">
					<div class="col-12 mt-2">
						<my:error message="${errors.search}" />
					</div>
				</c:if>
			</div>
		</form>
		<c:choose>
			<c:when test="${ not empty student_list }">
				<div class="mb-2">
					検索結果：
					<c:out value="${ student_list.size() }" />
					件
				</div>
				<table class="table table-hover">
					<thead>
						<tr>
							<th>入学年度</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>クラス</th>
							<th class="text-center">在学中</th>
							<c:if test="${staff.position.name eq '管理者'}">
								<th></th>
							</c:if>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="student" items="${ student_list }">
							<tr>
								<td><c:out value="${ student.entYear }" /></td>
								<td><c:out value="${ student.no }" /></td>
								<td><c:out value="${ student.name }" /></td>
								<td><c:out value="${ student.classNum }" /></td>
								<td class="text-center"><c:choose>
										<c:when test="${ student.attend }">○</c:when>
										<c:otherwise>×</c:otherwise>
									</c:choose></td>
								<c:if test="${staff.position.name eq '管理者'}">
									<td><a href="StudentUpdate.action?no=<c:out value='${student.no}' />">変更</a></td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<div class="mx-2 my-2">学生情報が存在しませんでした</div>
			</c:otherwise>
		</c:choose>
	</c:param>
</c:import>