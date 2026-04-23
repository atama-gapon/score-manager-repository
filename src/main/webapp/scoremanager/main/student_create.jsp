<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
<c:param name="content">

<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
    学生情報登録
</h2>

<form action="StudentCreateExecute.action" method="post" class="px-4">

    <!-- 入学年度 -->
    <label>入学年度</label>
        <select name="ent_year" class="form-select">
            <option value="">--------</option>
            <c:forEach var="y" items="${ent_year_set}">
                <option value="${y}" <c:if test="${y == ent_year}">selected</c:if>>
                    ${y}
                </option>
            </c:forEach>
        </select>
    <c:if test="${not empty errors.ent_year}">
        <div style="color:orange">${errors.ent_year}</div>
    </c:if>

    <br>

    <!-- 学生番号（10文字まで） -->
    <label>学生番号</label>
        <input type="text"
               name="no"
               class="form-control"
               maxlength="10"
               value="${no}"
               placeholder="学生番号を入力してください"
               required>
    <c:if test="${not empty errors.no}">
        <div style="color:orange">${errors.no}</div>
    </c:if>

    <br>

    <!-- 氏名30文字 -->
    <label>氏名</label>
        <input type="text"
               name="name"
               class="form-control"
               maxlength="30"
               value="${name}"
               placeholder="氏名を入力してください"
               required>

    <br>

    <!-- クラス（学校に紐づくクラス一覧） -->
    <div class="mb-3">
        <label class="form-label">クラス</label>
            <select name="class_num" class="form-select" required>
                <option value="">--------</option>
                <c:forEach var="c" items="${class_num_set}">
                    <option value="${c}" <c:if test="${c == class_num}">selected</c:if>>
                        ${c}
                    </option>
                </c:forEach>
            </select>
    </div>

    <!-- ボタン -->
    <button type="submit" name="end" class="btn btn-primary">
        登録
    </button>

    <a href="menu.jsp">戻る</a>

</form>

</c:param>
</c:import>
