<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

            <form method="get" action="TestListSubjectExecute.action">
                <div class="border mx-3 mb-3 py-3 px-4 rounded shadow-sm">
                    <div class="mb-2 fw-bold">科目情報</div>
                    <div class="row g-3 align-items-end">
                        <div class="col-md-3">
                            <label class="form-label" for="f1">入学年度</label>
                            <select class="form-select" name="f1" id="f1">
                                <option value="0">----------</option>
                                <c:forEach var="year" items="${ent_year_set}">
                                    <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label" for="f2">クラス</label>
                            <select class="form-select" name="f2" id="f2">
                                <option value="0">----------</option>
                                <c:forEach var="num" items="${class_num_set}">
                                    <option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label" for="f3">科目</label>
                            <select class="form-select" name="f3" id="f3">
                                <option value="0">----------</option>
                                <c:forEach var="subject" items="${subjects}">
                                    <%-- ここを subject.cd == f3 に修正 --%>
                                    <option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>${subject.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <button class="btn btn-secondary w-100">検索</button>
                        </div>
                    </div>
                    <c:if test="${not empty message}">
                        <div class="mt-2 text-warning">${message}</div>
                    </c:if>
                </div>
            </form>

            <form method="get" action="TestListStudentExecute.action">
			    <div class="border mx-3 mb-3 py-3 px-4 rounded shadow-sm">
			        <div class="mb-2 fw-bold">学生情報</div>
			        <div class="row g-3 align-items-center"> <div class="col-auto"> <label class="form-label mb-0" for="f4">学生番号</label>
			            </div>
			            
			            <div class="col-md-4">
			                <input type="text" id="f4" name="f4" value="${f4}" class="form-control" maxlength="10" placeholder="学生番号を入力してください" required>
			            </div>
			
			            <div class="col-md-2">
			                <button class="btn btn-secondary w-100">検索</button>
			            </div>
			        </div>
			    </div>
			</form>

            <c:if test="${empty testListSubjects && empty testListStudents}">
                <div class="mx-3 mt-4 text-info">
                    科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
                </div>
            </c:if>

            <%-- ここに検索結果のテーブル等が表示される --%>
            
        </section>
    </c:param>
</c:import>