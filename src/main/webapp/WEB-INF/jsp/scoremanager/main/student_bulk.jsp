<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">学生情報CSV取込</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<div class="my-2 text-end px-4">
			<a class="me-3" href="StudentList.action">戻る</a>
		</div>
		<div class="card-body">
			<my:error message="${errors}" />
			<p class="text-muted small mb-2">以下の形式のCSVファイルを選択してください。</p>
			<ul class="mb-0">
				<li>学籍番号</li>
				<li>氏名</li>
				<li>入学年度</li>
				<li>クラス番号</li>
				<li>在学フラグ（true / false）</li>
			</ul>
			<form class="mt-4" action="StudentBulkExecute.action" method="post" enctype="multipart/form-data">
				<div class="mb-3">
					<label class="form-label fw-bold" for="csv">CSVファイルを選択</label>
					<input class="form-control" type="file" id="csv" name="csv" accept=".csv" required>
					<div class="form-text">※ファイル形式は .csv のみ読み込めます。</div>
				</div>
				<div class="d-flex justify-content-between align-items-center mt-4">
					<div></div>
					<div>
						<button class="btn btn-primary px-3" type="submit">アップロード</button>
					</div>
				</div>
			</form>
		</div>
	</c:param>
</c:import>