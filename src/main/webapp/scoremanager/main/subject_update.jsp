<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<style>
input::placeholder {
    font-size: 15px;
}

.form-control {
    height: 35px;
}
</style>

<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
        	<!-- 名前変更
        		科目更新→科目情報変更 -->
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>
	        <form action="SubjectUpdateExecute.action" method="get">
	        
	        	<label class="form-label">科目コード</label><br>
	        	<input name="cd" type="hidden" value='${ subject.cd }'>	
	        	<p>　${ subject.cd }</p>
	        	
	        	<!-- 科目変更中に科目が削除されたときのエラー(未完) -->
	        	<div class="col-12 mt-2 text-warning">${ errors.get("cd_length") }</div><br>
	        	
	        	<label class="form-label">科目名</label><br>
	        	<input type="text" class="form-control" placeholder="科目名を入力してください" name="name" value='${ subject.name }' required><br>
	        	
	        	<p> <input type="submit" class="btn btn-primary px-3" value="更新"></p>
	        	
	        	
	        </form>
	        <!-- 画面設計にはなかったので
	        <p>変更前↓↓↓</p>
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
	        <p><a href="SubjectList.action">戻る</a></p>
		</section>
	</c:param>
</c:import>