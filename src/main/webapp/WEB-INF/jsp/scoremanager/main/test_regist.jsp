<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">成績登録</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<form method="post">
			<div class="row border mx-3 mb-3 py-2 align-items-end rounded" id="filter">
				<div class="col-2">
					<label class="form-label" for="student-ent_year-select">入学年度</label>
					<select class="form-select" id="student-ent_year-select" name="ent_year">
						<option value="0">----------</option>
						<c:forEach var="year" items="${ ent_year_list }">
							<option value="${ year }" <c:if test="${ year==ent_year }">selected</c:if>>${ year }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-2">
					<label class="form-label" for="student-class_num-select">クラス</label>
					<select class="form-select" id="student-class_num-select" name="class_num">
						<option value="0">----------</option>
						<c:forEach var="num" items="${ class_num_list }">
							<option value="${ num }" <c:if test="${ num==class_num }">selected</c:if>>${ num }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-3">
					<label class="form-label" for="student-is_attend-select">科目</label>
					<select class="form-select" id="student-is_attend-select" name="is_attend">
						<option value="0">----------</option>
						<c:forEach var="subject" items="${ subject_list }">
							<option value="${ subject.cd }" <c:if test="${ subject.cd==is_attend }">selected</c:if>>${ subject.name }</option>
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
					<button type="submit" name="search" value="true" class="btn btn-secondary">検索</button>
				</div>
				<div class="col-12 mt-2 text-warning">${ errors.get("ent_year") }</div>
			</div>
		</form>
		<c:if test="${not empty message}">
			<div class="alert alert-danger mt-3">${message}</div>
		</c:if>
		<c:choose>
			<c:when test="${not empty tests}">
				<div>科目：${subject.name} （${num}回）</div>
				<form action="TestRegistExecute.action" method="post">
					<table class="table table-hover">
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>学籍番号</th>
							<th>氏名</th>
							<th>点数</th>
							<th>採点者</th>
						</tr>
						<c:forEach var="test" items="${ tests }">
							<tr>
								<td>${ test.student.entYear }</td>
								<td>${ test.classNum }</td>
								<td>${ test.student.no }</td>
								<td>${ test.student.name }</td>
								<td><input type="number" name="point_${test.student.no}" value="${test.point == -1 ? '' : test.point}" class="form-control" style="width: 100px;"> <c:if test="${not empty message_over && (test.point < 0 || test.point > 100)}">
										<div class="text-warning">${message_over}</div>
										<input type="hidden" name="old_point_${test.student.no}" value="${test.point}">
									</c:if> <input type="hidden" name="student_no_list" value="${test.student.no}"></td>
								<td>${ test.markerStaff.lastName }&nbsp;${ test.markerStaff.firstName }（${ test.markerStaff.no }）</td>
							</tr>
						</c:forEach>
					</table>
					<div class="mt-3">
						<button type="submit" name="regist" class="btn btn-secondary">登録して終了</button>
					</div>
					<input type="hidden" name="ent_year" value="${ent_year}">
					<input type="hidden" name="class_num" value="${class_num}">
					<input type="hidden" name="is_attend" value="${is_attend}">
					<input type="hidden" name="f4" value="${f4}">
				</form>
			</c:when>
			<c:when test="${not empty tests}">
				<div class="mt-3">科目：${subject.name} （${num}回）</div>
			</c:when>
			<c:when test="${not empty ent_year}">
				<c:if test="${empty message}">
					<div class="alert alert-danger mt-3">学生情報が存在しませんでした。</div>
				</c:if>
			</c:when>
		</c:choose>
	</c:param>
</c:import>