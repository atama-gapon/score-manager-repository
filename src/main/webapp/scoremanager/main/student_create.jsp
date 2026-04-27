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
    	<!-- 修正箇所
    		bootstrapにあるクラスに変更 
    		style="color:orange" → class="col-12 mt-2 text-warning" -->
        <div class="col-12 mt-2 text-warning">${errors.ent_year}</div>
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
    	<!-- 修正箇所
    		bootstrapにあるクラスに変更 
    		style="color:orange" → class="col-12 mt-2 text-warning" -->
        <div class="col-12 mt-2 text-warning">${errors.no}</div>
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
    <!-- 修正箇所
     ボタンの色:primary→secondary 
     文字:登録→登録して終了
    -->
    
    <button type="submit" name="end" class="btn btn-secondary">
        登録して終了
    </button>
	<!-- 修正箇所
	改行を入れた -->
	<br>
	<p></p>
	
	<!-- 修正箇所
		遷移っするURLを「メニュー画面」から「学生管理画面」へ変更 -->
    <a href="StudentList.action">戻る</a>

</form>

</c:param>
</c:import>
