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
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">クラス情報削除</h2>
	        <form action="ClassDeleteExecute.action" method="get">
				<p>「${class_num}」を削除してもよろしいですか</p>
				<div class="text-warning">${ errors.get("invalid") }</div>
				<div class="text-warning">${ errors.get("has_student") }</div>
				<input name="class_num" type="hidden" value="${ class_num }">
				<input type="submit" value="削除">
	        </form>
	        <p><a href="ClassList.action">戻る</a></p>
		</section>
	</c:param>
</c:import>