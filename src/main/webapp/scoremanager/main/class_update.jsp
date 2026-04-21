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
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">クラス情報変更</h2>
            <p></p>
            
	        <form action="ClassUpdateExecute.action" method="get">
	        	<label for="class_num">クラス番号</label>
	        	<input id="class_num" class="form-control" name="class_num" value="${class_num}" type="text" maxlength="5" placeholder="クラス番号を入力してください" required>
	        	<div class="text-warning">${ errors.get("class_num_duplication") }</div>
	        	<input name="old_class_num" type="hidden" value='${ old_class_num }'>
	        	<p><input type="submit" value="変更"></p>
	        </form>
	        <p><a href="ClassList.action">戻る</a></p>
		</section>
	</c:param>
</c:import>