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

    入学年度：<input type="text" name="entYear"><br><br>
    学生番号：<input type="text" name="no"><br><br>
    氏名：<input type="text" name="name"><br><br>
    クラス：<input type="text" name="classNum"><br><br>

    <input type="submit" value="登録">
</form>

<a href = "menu.jsp">終了</a>
    
</form>

</c:param>
</c:import>
