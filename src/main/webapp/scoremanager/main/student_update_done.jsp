<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
<!-- baseを継承 -->
    <c:param name="title" value="学生情報変更" />
    <c:param name="content">
    <!-- 内容 -->
    <section class="me-4">            
    <!-- 参考url:https://moku-moku.net/bootstrap/102 -->
                <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                学生情報変更
            </h2>
			<div class="px-4">
			    <div class="bg-success bg-opacity-25 py-3 px-4 rounded d-flex align-items-center">
			        <p class="fs-5 mb-0">変更が完了しました</p>
			    </div>
				<a href = "StudentUpdate.action">戻る</a>
				<br><br>
			    <a href="StudentList.action">学生一覧</a>
			</div>
        </section>

    </c:param>
</c:import>
