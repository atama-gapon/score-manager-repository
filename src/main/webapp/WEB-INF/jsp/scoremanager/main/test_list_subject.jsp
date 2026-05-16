<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">成績一覧（科目）</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<div class="container-fluid d-flex justify-content-center">
			<div class="bg-white border rounded shadow-sm p-4 w-100" style="max-width: 1050px;">
				<form class="mb-0" method="get" action="TestSubjectListExecute.action">
					<div class="d-flex align-items-start">
						<div class="text-center mt-4 me-3" style="width: 100px; flex-shrink: 0;">科目情報</div>
						<div class="d-flex align-items-end flex-wrap ms-4 gap-3">
							<div style="width: 140px;">
								<label class="form-label small mb-1" for="ent_year">入学年度</label>
								<select class="form-select form-select-sm" id="ent_year" name="ent_year">
									<option value="0">----------</option>
									<c:forEach var="year" items="${ent_year_list}">
										<option value="<c:out value='${year}' />" <c:if test="${year == ent_year}">selected</c:if>><c:out value="${year}" /></option>
									</c:forEach>
								</select>
							</div>
							<div style="width: 120px;">
								<label class="form-label small mb-1" for="class_num">クラス</label>
								<select class="form-select form-select-sm" id="class_num" name="class_num">
									<option value="0">----------</option>
									<c:forEach var="num" items="${class_num_list}">
										<option value="<c:out value='${num}' />" <c:if test="${num == class_num}">selected</c:if>><c:out value="${num}" /></option>
									</c:forEach>
								</select>
							</div>
							<div style="width: 220px;">
								<label class="form-label small mb-1" for="is_attend">科目</label>
								<select class="form-select form-select-sm" id="is_attend" name="is_attend">
									<option value="0">----------</option>
									<c:forEach var="subject" items="${subject_list}">
										<option value="<c:out value='${subject.cd}' />" <c:if test="${subject.cd == is_attend}">selected</c:if>><c:out value="${subject.name}" /></option>
									</c:forEach>
								</select>
							</div>
							<button class="btn btn-secondary btn-sm px-3" type="submit">検索</button>
						</div>
					</div>
				</form>
				<hr class="my-4 text-secondary opacity-25">
				<form class="mb-0" method="get" action="TestStudentListExecute.action">
					<div class="d-flex align-items-start">
						<div class="text-center mt-4 me-3" style="width: 100px; flex-shrink: 0;">学生情報</div>
						<div class="ms-4">
							<label class="form-label small mb-1" for="f4">学生番号</label>
							<div class="d-flex align-items-center">
								<input class="form-control form-control-sm me-2" type="text" id="f4" name="f4" value="<c:out value='${f4}' />" style="width: 250px;" maxlength="10" placeholder="学生番号を入力してください" required>
								<button class="btn btn-secondary btn-sm px-3" type="submit">検索</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<my:error message="${message}" />
		<section class="mt-4 px-4">
			<c:choose>
				<c:when test="${ not empty test_subject_list }">
					<div class="mt-3 h5">
						科目：
						<c:out value="${ subject.name }" />
					</div>
					<table class="table table-hover mt-3">
						<thead>
							<tr>
								<th>入学年度</th>
								<th>クラス</th>
								<th>学生番号</th>
								<th>氏名</th>
								<th>1回</th>
								<th>2回</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="tlsubject" items="${ test_subject_list }">
								<tr>
									<td><c:out value="${ tlsubject.entYear }" /></td>
									<td><c:out value="${ tlsubject.classNum }" /></td>
									<td><c:out value="${ tlsubject.studentNo }" /></td>
									<td><c:out value="${ tlsubject.studentName }" /></td>
									<td><c:set var="done1" value="false" /> <c:forEach var="point" items="${tlsubject.points}">
											<c:if test="${point.key == 1}">
												<c:out value="${point.value}" />
												<c:set var="done1" value="true" />
											</c:if>
										</c:forEach> <c:if test="${!done1}">-</c:if></td>
									<td><c:set var="done2" value="false" /> <c:forEach var="point" items="${tlsubject.points}">
											<c:if test="${point.key == 2}">
												<c:out value="${point.value}" />
												<c:set var="done2" value="true" />
											</c:if>
										</c:forEach> <c:if test="${!done2}">-</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:otherwise>
					<div class="mx-2 my-2">学生情報が存在しませんでした</div>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>