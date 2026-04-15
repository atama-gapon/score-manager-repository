<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">学生情報変更</c:param>
    <c:param name="content">
        <form action="StudentUpdateExecute.action" method="post">
            <input type="hidden" name="no" value="${student.no}">

            氏名：<input type="text" name="name" value="${student.name}"><br>

            <label class="form-label">入学年度</label>
<select class="form-select" name="entYear">
    <c:forEach var="year" items="${ ent_year_set }">
        <option value="${ year }" 
            <c:if test="${ year == student.entYear }">selected</c:if>>
            ${ year }
        </option>
    </c:forEach>
</select>

            クラス：
            <select name="classNum">
                <c:forEach var="c" items="${classList}">
                    <option value="${c}" ${c == student.classNum ? "selected" : ""}>${c}</option>
                </c:forEach>
            </select><br>

            <c:if test="${not empty message}">
                <p style="color:red">${message}</p>
            </c:if>

            <button type="submit">変更</button>
        </form>
    </c:param>
</c:import>
