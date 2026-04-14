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
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">新規登録</h2>
            <div class="col-12 mt-2 text-warning">${ errors.get("cd_length") }</div>
            <form action="SubjectCreateExecute.action" method="get">
	            <input type="text" name="cd">
	            <input type="text" name="name">
            </form>
		</section>
	</c:param>
</c:import>