<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">学生情報CSV取込</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<div class="card-body">
			<c:if test="${not empty error}">
				<div class="alert alert-danger" role="alert">
					<i class="bi bi-exclamation-triangle-fill"></i>
					<c:out value="${error}" />
				</div>
			</c:if>
			<p class="text-muted small">
				以下の形式のCSVファイルを選択してください。<br> 学籍番号, 氏名, 入学年度, クラス番号, 在学フラグ(true/false)
			</p>
			<form class="mt-4" action="StudentBulkExecute.action" method="post" enctype="multipart/form-data">
				<div class="mb-3">
					<label class="form-label fw-bold" for="csv">CSVファイルを選択</label>
					<input class="form-control" type="file" id="csv" name="csv" accept=".csv" required>
					<div class="form-text">※ファイル形式は .csv のみ読み込めます。</div>
				</div>
				<div class="d-flex justify-content-between align-items-center mt-4">
					<a class="btn btn-outline-secondary" href="StudentList.action">
						<i class="bi bi-arrow-left"></i> 学生一覧に戻る
					</a>
					<button class="btn btn-primary px-5" type="submit">
						<i class="bi bi-cloud-upload"></i> 登録を実行する
					</button>
				</div>
			</form>
		</div>
	</c:param>
</c:import>