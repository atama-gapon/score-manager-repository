<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<style>
input::placeholder {
	font-size: 15px;
}

.form-control {
	height: 50px;
}

li {
	text-align: center; /* テキストを中央寄せ */
}
</style>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts">
		<script>
			function togglePassword() {
				const pw = document.getElementById("password");
				pw.type = (pw.type === "password") ? "text" : "password";
			}
		</script>
	</c:param>
	<c:param name="content">
		<div class="container mt-5">
			<div class="row justify-content-center">
				<div class="col-md-10">
					<div class="card shadow-sm">
						<div class="card-header text-center fw-bold">
							<h2>ログイン</h2>
						</div>
						<div class="card-body">
							<c:if test="${message != null}">
								<div class="mb-2">
									<ul>
										<li><c:out value="${message}" /></li>
									</ul>
								</div>
							</c:if>
							<form class="px-0" action="LoginExecute.action" method="post">
								<div class="mb-3">
									<input class="form-control" type="text" name="school_cd" value="<c:out value='${school_cd}' />" placeholder="学校コード" maxlength="3" required>
								</div>
								<div class="mb-3">
									<input class="form-control" type="text" name="no" value="<c:out value='${no}' />" placeholder="職員番号" maxlength="10" required>
								</div>
								<div class="mb-3">
									<input class="form-control" type="password" id="password" name="password" placeholder="パスワード" maxlength="30" required>
								</div>
								<div class="form-check d-flex justify-content-center mb-3">
									<input class="form-check-input me-2" type="checkbox" id="showPw" name="chk_d_ps" onclick="togglePassword()">
									<label class="form-check-label" for="showPw"> パスワードを表示 </label>
								</div>
								<div class="text-center">
									<button class="btn btn-primary px-4" type="submit" name="login">ログイン</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:param>
</c:import>