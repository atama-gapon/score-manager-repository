<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="scripts">
		<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
		<script>
			document
					.addEventListener(
							"DOMContentLoaded",
							function() {
								const canvas = document
										.getElementById('gradeRadarChart');
								if (!canvas)
									return;

								const ctx = canvas.getContext('2d');
								const latestGrades = {};

								<c:forEach var="item" items="${test_student_list}">(
										function() {
											const subject = '<c:out value="${item.subjectName}" />';
											const currentNum = parseInt('<c:out value="${item.num}" />');
											const currentPoint = parseInt('<c:out value="${item.point}" />');

											if (!latestGrades[subject]
													|| currentNum > latestGrades[subject].num) {
												latestGrades[subject] = {
													num : currentNum,
													point : currentPoint
												};
											}
										})();
								</c:forEach>

								const labels = [];
								const points = [];

								for ( const subject in latestGrades) {
									labels.push(subject + ' ('
											+ latestGrades[subject].num + '回)');
									points.push(latestGrades[subject].point);
								}

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
	</c:param>
	<c:param name="title">成績一覧（学生）</c:param>
	<c:param name="content">
		<my:test_search_form />
		<c:choose>
			<c:when test="${ not empty test_student_list }">
				<div class="mt-3">
					氏名：<c:out value="${ student.name }" />（<c:out value="${ student.no }" />）
				</div>
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
								<c:forEach var="tlstudent" items="${ test_student_list }">
									<tr>
										<td><c:out value="${ tlstudent.subjectName }" /></td>
										<td><c:out value="${ tlstudent.subjectCd }" /></td>
										<td><c:out value="${ tlstudent.num }" /></td>
										<td><c:out value="${ tlstudent.point }" /></td>
										<td><a class="text-danger btn btn-link p-0" href="TestDelete.action?studentNo=<c:out value='${student.no}' />&subjectCd=<c:out value='${tlstudent.subjectCd}' />&schoolCd=<c:out value='${student.school.cd}' />&num=<c:out value='${tlstudent.num}' />">削除</a></td>
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
			</c:when>
			<c:otherwise>
				<div class="mx-2 my-2">成績情報が存在しませんでした</div>
			</c:otherwise>
		</c:choose>
	</c:param>
</c:import>