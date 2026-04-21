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
        	<!-- 名前変更
        		科目削除→科目情報削除 -->
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>
	        <form action="SubjectDeleteExecute.action" method="get">
	        	<p>「${subject.name}(${subject.cd })」を削除してもよろしいですか？</p>
	        	<input type="submit" class="btn btn-danger px-3"  value="削除">
	        	<input name="cd" type="hidden" value='${ subject.cd }'>
	        </form>
	        <!-- 画面設計になかったので
	        <table class="table table-hover">
	            <tr>
	                <th>科目番号</th>
	                <th>科目名</th>
	            </tr>
	                <tr>
	                    <td>${ subject.cd }</td>
	                    <td>${ subject.name }</td>
	                </tr>
	        </table> -->
	        <br><br><br>
	        <p><a href="SubjectList.action">戻る</a></p>
		</section>
	</c:param>
</c:import>