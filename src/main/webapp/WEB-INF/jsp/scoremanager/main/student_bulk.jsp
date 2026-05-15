<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">学生情報CSV取込</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<div class="card-body">
			<c:if test="${not empty error}">
				<div class="alert alert-danger" role="alert">
					<i class="bi bi-exclamation-triangle-fill"></i> ${error}
				</div>
			</c:if>
			<p class="text-muted small">
				以下の形式のCSVファイルを選択してください。<br> 学籍番号, 氏名, 入学年度, クラス番号, 在学フラグ(true/false)
			</p>
			<form action="StudentBulkExecute.action" method="post" enctype="multipart/form-data" class="mt-4">
				<div class="mb-3">
					<label for="csv" class="form-label fw-bold">CSVファイルを選択</label>
					<input type="file" id="csv" name="csv" class="form-control" accept=".csv" required>
					<div class="form-text">※ファイル形式は .csv のみ読み込めます。</div>
				</div>
				<div class="d-flex justify-content-between align-items-center mt-4">
					<a href="StudentList.action" class="btn btn-outline-secondary">
						<i class="bi bi-arrow-left"></i> 学生一覧に戻る
					</a>
					<button type="submit" class="btn btn-primary px-5">
						<i class="bi bi-cloud-upload"></i> 登録を実行する
					</button>
				</div>
			</form>
		</div>
		</div>
	</c:param>
</c:import>