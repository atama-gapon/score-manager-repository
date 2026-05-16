<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">成績情報削除</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<div class="px-4">
			<form class="px-0" action="TestDeleteExecute.action" method="post">
				<p>下記の成績情報を削除してもよろしいですか？</p>
				<input type="hidden" name="student_no" value="<c:out value='${test.student.no}' />">
				<input type="hidden" name="subject_cd" value="<c:out value='${subjectCd}' />">
				<input type="hidden" name="num" value="<c:out value='${test.no}' />">
				<div class="mt-3 mb-4">
					<input class="btn btn-danger px-3" type="submit" value="削除">
				</div>
			</form>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>入学年度</th>
						<th>クラス</th>
						<th>学生番号</th>
						<th>氏名</th>
						<th>科目</th>
						<th><c:out value="${test.no}" />回</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><c:out value="${test.student.entYear}" /></td>
						<td><c:out value="${test.classNum}" /></td>
						<td><c:out value="${test.student.no}" /></td>
						<td><c:out value="${test.student.name}" /></td>
						<td><c:out value="${subjectCd}" /></td>
						<td><c:out value="${test.point}" />点</td>
					</tr>
				</tbody>
			</table>
			<div class="mt-4">
				<a class="btn btn-link p-0" href="TestStudentListExecute.action?f4=<c:out value='${test.student.no}' />">戻る</a>
			</div>
		</div>
	</c:param>
</c:import>