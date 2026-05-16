<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="scripts">
		<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	</c:param>
	<c:param name="title">成績一覧（学生）</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<div class="container-fluid d-flex justify-content-center">
			<div class="bg-white border rounded shadow-sm p-4" style="width: 100%; max-width: 1050px;">
				<form method="get" action="TestListSubjectExecute.action" class="mb-0">
					<div class="d-flex align-items-start">
						<div class="text-center mt-4" style="width: 100px; margin-right: 20px;">科目情報</div>
						<div class="d-flex align-items-end flex-wrap ms-4" style="gap: 15px;">
							<div style="width: 140px;">
								<label class="form-label small mb-1" for="ent_year">入学年度</label>
								<select class="form-select form-select-sm" name="ent_year" id="ent_year">
									<option value="0">----------</option>
									<c:forEach var="year" items="${ent_year_list}">
										<option value="${year}" <c:if test="${year == ent_year}">selected</c:if>>${year}</option>
									</c:forEach>
								</select>
							</div>
							<div style="width: 120px;">
								<label class="form-label small mb-1" for="class_num">クラス</label>
								<select class="form-select form-select-sm" name="class_num" id="class_num">
									<option value="0">----------</option>
									<c:forEach var="num" items="${class_num_list}">
										<option value="${num}" <c:if test="${num == class_num}">selected</c:if>>${num}</option>
									</c:forEach>
								</select>
							</div>
							<div style="width: 220px;">
								<label class="form-label small mb-1" for="is_attend">科目</label>
								<select class="form-select form-select-sm" name="is_attend" id="is_attend">
									<option value="0">----------</option>
									<c:forEach var="subject" items="${subject_list}">
										<option value="${subject.cd}" <c:if test="${subject.cd == is_attend}">selected</c:if>>${subject.name}</option>
									</c:forEach>
								</select>
							</div>
							<button type="submit" class="btn btn-secondary btn-sm px-3">検索</button>
						</div>
					</div>
				</form>
				<hr class="my-4 text-secondary opacity-25">
				<form method="get" action="TestListStudentExecute.action" class="mb-0">
					<div class="d-flex align-items-start">
						<div class="text-center mt-4" style="width: 100px; flex-shrink: 0; margin-right: 20px;">学生情報</div>
						<div class="ms-4">
							<label class="form-label small mb-1" for="f4">学生番号</label>
							<div class="d-flex align-items-center">
								<input type="text" id="f4" name="f4" value="${f4}" class="form-control form-control-sm me-2" style="width: 250px;" maxlength="10" placeholder="学生番号を入力してください" required>
								<button type="submit" class="btn btn-secondary btn-sm px-3">検索</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<section class="mt-4">
			<div class="mt-3 h5">氏名：${ student.name }（${ student.no }）</div>
			<c:choose>
				<c:when test="${ not empty testListStudents }">
					<div class="row mt-4">
						<div class="col-lg-7">
							<table class="table table-hover border">
								<thead class="table-light">
									<tr>
										<th>科目名</th>
										<th>科目コード</th>
										<th>回数</th>
										<th>点数</th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="tlstudent" items="${ testListStudents }">
										<tr>
											<td>${ tlstudent.subjectName }</td>
											<td>${ tlstudent.subjectCd }</td>
											<td>${ tlstudent.num }</td>
											<td>${ tlstudent.point }</td>
											<td><a href="TestDelete.action?studentNo=${student.no}&subjectCd=${tlstudent.subjectCd}&schoolCd=${student.school.cd}&num=${tlstudent.num}" class="text-danger">削除</a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="col-lg-5">
							<div class="card shadow-sm p-3">
								<h3 class="h6 text-center mb-3">成績分析レーダー</h3>
								<canvas id="gradeRadarChart" style="max-height: 400px;"></canvas>
							</div>
						</div>
					</div>
					<script>
						document
								.addEventListener(
										"DOMContentLoaded",
										function() {
											const canvas = document
													.getElementById('gradeRadarChart');
											if (!canvas)
												return; // キャンバスがない場合は終了

											const ctx = canvas.getContext('2d');

											// 1. データをオブジェクトに格納して整理する
											const latestGrades = {};

											<c:forEach var="item" items="${testListStudents}">(
													function() {
														const subject = '${item.subjectName}';
														const currentNum = parseInt('${item.num}');
														const currentPoint = parseInt('${item.point}');

														if (!latestGrades[subject]
																|| currentNum > latestGrades[subject].num) {
															latestGrades[subject] = {
																num : currentNum,
																point : currentPoint
															};
														}
													})();
											</c:forEach>

											// 2. 整理したデータからチャート用の配列を作成
											const labels = [];
											const points = [];

											for ( const subject in latestGrades) {
												labels
														.push(subject
																+ ' ('
																+ latestGrades[subject].num
																+ '回)');
												points
														.push(latestGrades[subject].point);
											}

											// 3. チャートの描画
											new Chart(
													ctx,
													{
														type : 'radar',
														data : {
															labels : labels,
															datasets : [ {
																label : '得点',
																data : points,
																fill : true,
																backgroundColor : 'rgba(54, 162, 235, 0.2)',
																borderColor : 'rgb(54, 162, 235)',
																pointBackgroundColor : 'rgb(54, 162, 235)',
																pointBorderColor : '#fff',
																pointHoverBackgroundColor : '#fff',
																pointHoverBorderColor : 'rgb(54, 162, 235)'
															} ]
														},
														options : {
															scales : {
																r : {
																	angleLines : {
																		display : true
																	},
																	suggestedMin : 0,
																	suggestedMax : 100,
																	ticks : {
																		stepSize : 20
																	}
																}
															},
															plugins : {
																legend : {
																	display : false
																}
															}
														}
													});
										});
					</script>
				</c:when>
				<c:otherwise>
					<div class="alert alert-warning mt-3">成績情報が存在しませんでした。</div>
				</c:otherwise>
			</c:choose>
	</c:param>
</c:import>