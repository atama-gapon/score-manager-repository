<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<style>
input::placeholder {
    font-size: 15px;
}

.form-control {
    height: 50px;
}


</style>


<script>
function togglePassword() {
    const pw = document.getElementById("password");
    pw.type = (pw.type === "password") ? "text" : "password";
}
</script>



<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>
	
    <c:param name="content">
        <section class="me-4">
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
										<li>${message}</li>
									</ul>
									</div>
								 </c:if>
								<form action="LoginExecute.action" method="post">
									<!-- ID -->
									<div class="mb-3">
										<input type="text" class="form-control" name="id" placeholder="ID" value="${id2}" required>
									</div>
									<!-- パスワード -->
									<div class="mb-3">
										<input type="password" class="form-control"id="password" name="password" placeholder="パスワード" required>
									</div>
									<!-- チェックボックス -->
									<div class="form-check d-flex justify-content-center mb-3">
										<input class="form-check-input me-2" type="checkbox" id="showPw" name="chk_d_ps" onclick="togglePassword()">
										<label class="form-check-label" for="showPw">
										パスワードを表示
										</label>
									</div>
									<!-- ボタン -->
									<div class="text-center">
										<button type="submit" class="btn btn-primary px-4">
											ログイン
										</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</c:param>
</c:import>