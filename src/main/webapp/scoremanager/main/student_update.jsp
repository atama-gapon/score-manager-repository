<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<style>
/*readonlyと指定されたtextの枠線を消す*/
.input-control {
    border: none;       /* 枠線を消す */
    outline: none;      /* フォーカス時の青枠も消す */
    background-color: transparent;
    padding-left: 1.0rem;
</style>

    <!-- ページのタイトル -->
   <c:import url="/common/base.jsp">
    <c:param name="title" value="学生情報変更" />
    <c:param name="content">
        <section class="me-4">

            <!-- 見出し -->
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>

            <!-- エラーがあれば表示 -->
            <c:if test="${not empty message}">
                <div class="text-danger px-4">${message}</div>
            </c:if>

            <!-- 更新フォーム -->
            <form action="StudentUpdateExecute.action" method="post" class="px-4">

                <!-- 入学年度 -->
                
                <div class="mb-3">
                    <label class="form-label">入学年度</label><br>
                    <input type="text" class="input-control"
                        name="ent_year"
                        value="${student.entYear}"
                        readonly>
                </div>

                <!-- 学生番号 -->
                <div class="mb-3">
                    <label class="form-label">学生番号</label><br>
                    <input type="text" class="input-control"
                        name="no"
                        value="${student.no}"
                        readonly>
                </div>

                <!-- 氏名（編集） -->
                <div class="mb-3">
                    <label class="form-label">氏名</label>
                    <input type="text" class="form-control"
                        name="name"
                        value="${student.name}"
                        maxlength="30"
                        placeholder="氏名を入力してください"
                        required>
                </div>

                <!-- クラス選択 -->
                <div class="mb-3">
                    <label class="form-label">クラス</label>
                    <select class="form-select" name="class_num">
                        <c:forEach var="c" items="${class_num_set}">
                            <!-- 今のクラスなら selected -->
                            <option value="${c}" <c:if test="${c == student.classNum}">selected</c:if>>
                                ${c}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <!-- 在学中チェック -->
                <div class="mb-3">
                    <label for="attend-check" class="me-1">在学中</label>
                    <input class="form-check-input"
                        type="checkbox"
                        id="attend-check"
                        name="is_attend"
                        value="t"
                        <c:if test="${student.isAttend()}">checked</c:if>>
                </div>
                               
                <!-- ボタン -->
                <div class="mt-4">
                    <input type="submit" name="login" value="変更" class="btn btn-primary"><br>
                     <p></p>
                    <a href="StudentList.action">戻る</a>
                </div>

            </form>

        </section>

    </c:param>

</c:import>
