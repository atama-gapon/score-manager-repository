<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">成績登録</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<form class="px-0" method="post">
			<div class="row border mx-3 mb-3 py-2 align-items-end rounded" id="filter">
				<div class="col-2">
					<label class="form-label" for="student-ent_year-select">入学年度</label>
					<select class="form-select" id="student-ent_year-select" name="ent_year">
						<option value="0">----------</option>
						<c:forEach var="year" items="${ ent_year_list }">
							<option value="<c:out value='${year}' />" <c:if test="${ year==ent_year }">selected</c:if>><c:out value="${year}" /></option>
						</c:forEach>
					</select>
				</div>
				<div class="col-2">
					<label class="form-label" for="student-class_num-select">クラス</label>
					<select class="form-select" id="student-class_num-select" name="class_num">
						<option value="0">----------</option>
						<c:forEach var="num" items="${ class_num_list }">
							<option value="<c:out value='${num}' />" <c:if test="${ num==class_num }">selected</c:if>><c:out value="${num}" /></option>
						</c:forEach>
					</select>
				</div>
				<div class="col-3">
					<label class="form-label" for="student-is_attend-select">科目</label>
					<select class="form-select" id="student-is_attend-select" name="is_attend">
						<option value="0">----------</option>
						<c:forEach var="subject" items="${ subject_list }">
							<option value="<c:out value='${subject.cd}' />" <c:if test="${ subject.cd==is_attend }">selected</c:if>><c:out value="${subject.name}" /></option>
						</c:forEach>
					</select>
				</div>
				<div class="col-2">
					<label class="form-label" for="student-f4-select">回数</label>
					<select class="form-select" id="student-f4-select" name="f4">
						<option value="0">----------</option>
						<option value="1" <c:if test="${f4 == 1}">selected</c:if>>1</option>
						<option value="2" <c:if test="${f4 == 2}">selected</c:if>>2</option>
					</select>
				</div>
				<div class="col-2">
					<button class="btn btn-secondary" type="submit" name="search" value="true">検索</button>
				</div>
				<div class="col-12 mt-2">
					<my:error message="${errors.ent_year}" />
				</div>
			</div>
		</form>
		<my:error message="${message}" />
		<c:choose>
			<c:when test="${not empty tests}">
				<div class="mb-2 px-3">
					科目：
					<c:out value="${subject.name}" />
					（
					<c:out value="${num}" />
					回）
				</div>
				<form class="px-3" action="TestRegistExecute.action" method="post">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>入学年度</th>
								<th>クラス</th>
								<th>学籍番号</th>
								<th>氏名</th>
								<th style="width: 150px;">点数</th>
								<th>採点者</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="test" items="${ tests }">
								<tr>
									<td><c:out value="${ test.student.entYear }" /></td>
									<td><c:out value="${ test.classNum }" /></td>
									<td><c:out value="${ test.student.no }" /></td>
									<td><c:out value="${ test.student.name }" /></td>
									<td><input class="form-control d-inline-block" type="number" name="point_<c:out value='${test.student.no}' />" value="<c:out value='${test.point == -1 ? \"\" : test.point}' />" style="width: 100px;" min="0" max="100"> <%-- 100点超過・マイナス値などのバリデーション警告表現 --%> <c:if test="${not empty message_over && (test.point < 0 || test.point > 100)}">
											<my:error message="${message_over}" />
											<input type="hidden" name="old_point_<c:out value='${test.student.no}' />" value="<c:out value='${test.point}' />">
										</c:if> <input type="hidden" name="student_no_list" value="<c:out value='${test.student.no}' />"></td>
									<td><c:out value="${ test.markerStaff.lastName }" />&nbsp;<c:out value="${ test.markerStaff.firstName }" /> <span class="text-muted small">（<c:out value="${ test.markerStaff.no }" />）
									</span></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="mt-4">
						<button class="btn btn-primary" type="submit" name="regist">登録して終了</button>
					</div>
					<input type="hidden" name="ent_year" value="<c:out value='${ent_year}' />">
					<input type="hidden" name="class_num" value="<c:out value='${class_num}' />">
					<input type="hidden" name="is_attend" value="<c:out value='${is_attend}' />">
					<input type="hidden" name="f4" value="<c:out value='${f4}' />">
				</form>
			</c:when>
			<c:when test="${not empty ent_year}">
				<c:if test="${empty message}">
					<div class="mx-2 my-2">学生情報が存在しませんでした</div>
				</c:if>
			</c:when>
		</c:choose>
	</c:param>
</c:import>