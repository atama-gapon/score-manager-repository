<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
<c:param name="content">

<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報登録</h2>

<form action="StudentCreateExecute.action" method="post" class="px-4">

<!-- 入学年度 -->
<label>入学年度</label>
<select name="entYear" class="form-select" >
    <option value="">--------</option>
    <c:forEach var="year" items="${ent_year_set}">
        <option value="${year}" <c:if test="${year == entYear}">selected</c:if>>
            ${year}
        </option>
    </c:forEach>
</select>
<c:if test="${not empty errors.entYear}">
    <div style="color:orange">${errors.entYear}</div>
</c:if>


<br>
<!-- 学生番号 -->
<label>学生番号</label>
<input type="text" name="no" class="form-control" value="${no}" required>
<c:if test="${not empty errors.no}">
    <div style="color:orange">${errors.no}</div>
</c:if>
<br>

<!-- 氏名 -->
<label>氏名</label>
<input type="text" name="name" class="form-control" value="${name}" required>
<!-- 氏名は required だけでOK。errors.name は使わない -->

<br>


    <!-- クラス -->
    <div class="mb-3">
        <label class="form-label">クラス</label>
        <select name="classNum" class="form-select" required>
         <option value="">--------</option>
            <c:forEach var="c" items="${class_num_set}">
                <option value="${c}" <c:if test="${c == classNum}">selected</c:if>>
                    ${c}
              </option>
            </c:forEach>
        </select>
    </div>

    <button type="submit" class="btn btn-primary">登録</button>
    <a href="menu.jsp">戻る</a>

</form>

</c:param>
</c:import>
