<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<div class="px-0 mt-5">
	<div class="mx-3 mb-3 p-4 border rounded bg-white shadow-sm">
		<%-- 科目情報検索フォーム --%>
		<form class="mb-4" method="get" action="TestSubjectListExecute.action">
			<div class="row align-items-end">
				<div class="col-2 pb-3">科目情報</div>
				<div class="col-2">
					<label class="form-label mb-1" for="subj-ent_year">入学年度</label>
					<select class="form-select" id="subj-ent_year" name="ent_year" required>
						<option value="">----------</option>
						<c:forEach var="year" items="${ent_year_list}">
							<option value="<c:out value='${year}' />" <c:if test="${year == ent_year}">selected</c:if>><c:out value="${year}" /></option>
						</c:forEach>
					</select>
				</div>
				<div class="col-2">
					<label class="form-label mb-1" for="subj-class_num">クラス</label>
					<select class="form-select" id="select-class_num" name="class_num" required>
						<option value="">----------</option>
						<c:forEach var="num" items="${class_num_list}">
							<option value="<c:out value='${num}' />" <c:if test="${num == class_num}">selected</c:if>><c:out value="${num}" /></option>
						</c:forEach>
					</select>
				</div>
				<div class="col-4">
					<label class="form-label mb-1" for="subj-subject_cd">科目</label>
					<select class="form-select" id="subj-subject_cd" name="subject_cd" required>
						<option value="">----------</option>
						<c:forEach var="subject" items="${subject_list}">
							<option value="<c:out value='${subject.cd}' />" <c:if test="${subject.cd == subject_cd}">selected</c:if>><c:out value="${subject.name}" /></option>
						</c:forEach>
					</select>
				</div>
				<div class="col-2 text-end">
					<button class="btn btn-secondary w-100" type="submit">検索</button>
				</div>
			</div>
		</form>
		<hr class="my-4 text-secondary opacity-25">
		<%-- 学生情報検索フォーム --%>
		<form class="mb-0" method="get" action="TestStudentListExecute.action">
			<div class="row align-items-end">
				<div class="col-2 pb-3">学生情報</div>
				<div class="col-8">
					<label class="form-label mb-1" for="f4">学生番号</label>
					<input class="form-control" type="text" id="f4" name="f4" value="<c:out value='${f4}' />" maxlength="10" placeholder="学生番号を入力してください" required>
				</div>
				<div class="col-2 text-end">
					<button class="btn btn-secondary w-100" type="submit">検索</button>
				</div>
			</div>
		</form>
	</div>
</div>