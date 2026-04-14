<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">ログイン</h2>
            <div class="row text-center px-4 fs-3 my-5">
            <c:if test="${message}"><p>IDまたはパスワードが確認できませんでした</p></c:if>
              	<form action="LoginExecute.action" method="get">
					<input name="id" type="text">
					<input name="password" type="text">
					<input type="submit" value="送信">
				</form>
            </div>
        </section>
    </c:param>
</c:import>