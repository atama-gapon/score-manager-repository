<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績一覧（学生）</h2>
            <form method="get" action="TestListSubjectExecute.action">
                <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
                科目情報
                    <div class="col-4">
                        <label class="form-label" for="student-f1-select">入学年度</label>
                        <select class="form-select" id="student-f1-select" name="f1">
                            <option value="0">----------</option>
                            <c:forEach var="year" items="${ ent_year_set }">
                                <%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
                                <option value="${ year }" <c:if test="${ year==f1 }">selected</c:if>>${ year }</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-4">
                        <label class="form-label" for="student-f2-select">クラス</label>
                        <select class="form-select" id="student-f2-select" name="f2">
                            <option value="0">----------</option>
                            <c:forEach var="num" items="${ class_num_set }">
                                <%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
                                <option value="${ num }" <c:if test="${ num==f2 }">selected</c:if>>${ num }</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="col-4">
                        <label class="form-label" for="student-f3-select">科目</label>
                        <select class="form-select" id="student-f3-select" name="f3">
                            <option value="0">----------</option>
                            <c:forEach var="subject" items="${ subjects }">
                                <%-- 現在のsubjectと選択されていたf3が一致していた場合selectedを追記 --%>
                                <option value="${ subject }" <c:if test="${ subject==f3 }">selected</c:if>>${ subject }</option>
                            </c:forEach>
                        </select>
                    </div>


                    <div class="col-2 text-center">
                        <button class="btn btn-secondary" id="filter-button">検索</button>
                    </div>
                    <div class="col-12 mt-2 text-warning">${ errors.get("f1") }</div>
                </div>
            </form>

            <form method="get" action="TestListStudentExecute.action">
                <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
                学生情報
                    <div class="col-4">
                        <label class="form-label" for="student-f4-select">学生番号</label>
                        <input type="text" name="f4" value="${param.f4}" maxlength="10" placeholder="学生番号を入力してください" required>
                    </div>
                    <div class="col-2 text-center">
                        <button class="btn btn-secondary" id="filter-button">検索</button>
                    </div>
                    </div>
            </form>
			<p class="text-info">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</p>
		</section>
		<div>氏名：${ student.name }（${ student.no }）</div>
		<c:choose>
			<c:when test="${ testListStudents.size() > 0 }">
		        <table class="table table-hover">
		            <tr>
		                <th>科目名</th>
		                <th>科目コード</th>
		                <th>回数</th>
		                <th>点数</th>
		            </tr>
		            <c:forEach var="tlstudent" items="${ testListStudents }">
		                <tr>
		                    <td>${ tlstudent.subjectName }</td>
		                    <td>${ tlstudent.subjectCd }</td>
		                    <td>${ tlstudent.num }</td>
		                    <td>${ tlstudent.point }</td>
		                </tr>
		            </c:forEach>
		        </table>
		    </c:when>
		    <c:otherwise>
		        <div>成績情報が存在しませんでした。</div>
		    </c:otherwise>
		</c:choose>
	</c:param>
</c:import>