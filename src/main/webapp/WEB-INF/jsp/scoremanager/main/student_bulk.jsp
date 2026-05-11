<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
			学生情報一括登録</h2>
			
			<div class="card-body">
				

				<p class="text-muted small">
					以下の形式のCSVファイルを選択してください。<br>
					学籍番号, 氏名, 入学年度, クラス番号, 在学フラグ(true/false)
				</p>

				<form action="StudentBulkExecute.action" method="post" enctype="multipart/form-data" class="mt-4">
					<div class="mb-3">
						<label for="csv" class="form-label">CSVファイルを選択</label>
						<input type="file" id="csv" name="csv" class="form-control" accept=".csv">
						<c:if test="${not empty error}">
							<div class="alert alert-danger mt-4" role="alert" >
								<i class="bi bi-exclamation-triangle-fill">${error}</i> 
							</div>
						</c:if>
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
		
	</c:param>
</c:import>
