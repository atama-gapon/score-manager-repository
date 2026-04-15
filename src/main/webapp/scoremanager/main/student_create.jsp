<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
<c:param name="content">

<h2>学生登録</h2>

<% String message = (String)request.getAttribute("message"); %>
<% if (message != null) { %>
    <p style="color:red;"><%= message %></p>
<% } %>

<form action="StudentCreateExecute.action" method="post">

    <!-- 入学年度 -->
    <label class="form-label">入学年度</label>
    <select class="form-select" name="entYear">
        <option value="">選択してください</option>
        <c:forEach var="year" items="${ ent_year_set }">
            <option value="${ year }">${ year }</option>
        </c:forEach>
    </select>

    <br><br>

    <!-- 学生番号 -->
    <label class="form-label">学生番号</label>
    <input type="text" name="no" class="form-control">

    <br><br>

    <!-- 氏名 -->
    <label class="form-label">氏名</label>
    <input type="text" name="name" class="form-control">

    <br><br>

    <!-- クラス -->
    <label class="form-label">クラス</label>
<select class="form-select" name="classNum">
    <option value="">選択してください</option>
    <c:forEach var="c" items="${ class_num_set }">
        <option value="${ c }">${ c }</option>
    </c:forEach>
</select>
<br><br>


    <input type="submit" value="登録" class="btn btn-primary">
</form>

<br>
<a href="menu.jsp">終了</a>

</c:param>
</c:import>
