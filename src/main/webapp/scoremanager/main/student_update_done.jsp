<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!-- baseを継承 -->

<c:import url="/common/base.jsp">
    <c:param name="title" value="学生情報変更" />
    <c:param name="content">
    <!-- 内容 -->
               
    <!-- 参考url:https://moku-moku.net/bootstrap/102 -->
   	<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
       学生情報変更
    </h2>
    <p class="mb-3 fw-normal bg-success py-1 px-4 text-center" style="--bs-bg-opacity: .6" >変更が完了しました</p>
       
    <br><br><br><br>
       
    <a href="StudentList.action">学生一覧</a>
		
    </c:param>
</c:import>
