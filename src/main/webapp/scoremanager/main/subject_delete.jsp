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
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目削除</h2>
	        <form action="SubjectDeleteExecute.action" method="get">
	        	<p>下記の情報を削除しますか？ <input type="submit" value="削除"></p>
	        	<input name="cd" type="hidden" value='${ subject.cd }'>
	        </form>
	        <table class="table table-hover">
	            <tr>
	                <th>科目番号</th>
	                <th>科目名</th>
	            </tr>
	                <tr>
	                    <td>${ subject.cd }</td>
	                    <td>${ subject.name }</td>
	                </tr>
	        </table>
	        <p><a href="SubjectList.action">戻る</a></p>
		</section>
	</c:param>
</c:import>