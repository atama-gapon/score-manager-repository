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
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績削除</h2>
	        <form action="TestDeleteExecute.action" method="get">
	        	<p>下記の情報を削除しますか？ <input type="submit" value="削除"></p>
	        	<!-- ここ変える -->
	        	<input name="cd" type="hidden" value='${ subject.cd }'>
	        </form>
	        <table class="table table-hover">
				<tr>
				    <th>入学年度</th>
				    <th>クラス</th>
				    <th>学生番号</th>
				    <th>氏名</th>
				    <th>1回</th>
				    <th>2回</th>
				</tr>
	                <tr>
	                    <td>${ test.student.entYear }</td>
	                    <td>${ test.student.classNum }</td>
	                    <td>${ test.student.no }</td>
	                    <td>${ test.student.name }</td>
	                    <td>${ test.no }</td>
	                </tr>
	        </table>
	        <p><a href="TestDeleteAction?f4=${ test.student.no }">戻る</a></p>
		</section>
	</c:param>
</c:import>