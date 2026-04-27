<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!-- baseを継承 -->

<c:import url="/common/base.jsp">
    <c:param name="title" value="学生情報変更" />
    <c:param name="content">
    <!-- 内容 -->
    <section class="me-4">            
    <!-- 参考url:https://moku-moku.net/bootstrap/102 -->
                <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                学生情報登録
            </h2>
				<!-- 修正箇所
					<div></div>をなくし形や色を調整した。
					「戻る」と「学生一覧」の位置を修正した -->
			    <p class="mb-3 fw-normal bg-success py-2 px-4 text-center" style="--bs-bg-opacity: .6">登録が完了しました</p>
			    
			    <br><br>
			    
				<a href = "StudentCreate.action">戻る</a>　　　　　
				
			    <a href="StudentList.action">学生一覧</a>
			
        </section>

    </c:param>
</c:import>
