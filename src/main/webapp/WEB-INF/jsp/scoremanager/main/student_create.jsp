<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<!-- 画面タイトル -->
		<h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">学生情報登録</h2>

		<form action="StudentCreateExecute.action" method="post" class="px-4">
			<div class="mb-3">
				<!-- 入学年度 -->
				<label class="form-label">入学年度</label>
				<!-- 入学年度ボックス -->
				<select name="ent_year" class="form-select">
					<option value="">--------</option>
					<c:forEach var="y" items="${ent_year_set}">
						<option value="${y}" <c:if test="${y == ent_year}">selected</c:if>>${y}</option>
					</c:forEach>
				</select>
				<c:if test="${not empty errors.ent_year}">
					<div class="mt-2 text-warning">${errors.ent_year}</div>
				</c:if>
			</div>

			<div class="mb-3">
				<!-- 学生番号 -->
				<label class="form-label">学生番号</label>
				<!-- 学生番号入力テキスト -->
				<input type="text" name="no" value="${no}" class="form-control" maxlength="10" placeholder="学生番号を入力してください" required>
				<c:if test="${not empty errors.no}">
					<div class="mt-2 text-warning">${errors.no}</div>
				</c:if>
			</div>

			<div class="mb-3">
				<!-- 氏名 -->
				<label class="form-label">氏名</label>
				<!-- 氏名入力テキスト -->
				<input type="text" name="name" value="${name}" class="form-control" placeholder="氏名を入力してください" required>
				<c:if test="${not empty errors.name}">
					<div class="mt-2 text-warning">${errors.name}</div>
				</c:if>
			</div>

			<div class="mb-3">
				<!-- クラス -->
				<label class="form-label">クラス</label>
				<!-- クラスセレクトボックス -->
				<select name="class_num" class="form-select" required>
					<option value="">--------</option>
					<c:forEach var="c" items="${class_num_set}">
						<option value="${c}" <c:if test="${c == class_num}">selected</c:if>>${c}</option>
					</c:forEach>
				</select>
				<c:if test="${not empty errors.class_num}">
					<div class="mt-2 text-warning">${errors.class_num}</div>
				</c:if>
			</div>

			<div class="mt-4">
				<!-- 登録ボタン -->
				<button type="submit" class="btn btn-secondary">登録</button>
			</div>

			<div class="mt-3">
				<!-- 戻るリンク -->
				<a href="StudentList.action">戻る</a>
			</div>
		</form>
	</c:param>
</c:import>