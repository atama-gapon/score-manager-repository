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
           	<div class="container-fluid d-flex justify-content-center">
            
            <div class="bg-white border rounded shadow-sm p-4" style="width: 100%; max-width: 1050px;">
                
                <form method="get" action="TestListSubjectExecute.action" class="mb-0">
                    <div class="d-flex align-items-start">
                        <div class="text-center mt-4" style="width: 100px; margin-right: 20px;">科目情報</div>
                        
                        <div class="d-flex align-items-end flex-wrap ms-4" style="gap: 15px;">
                            <div style="width: 140px;">
                                <label class="form-label small mb-1" for="f1">入学年度</label>
                                <select class="form-select form-select-sm" name="f1" id="f1">
                                    <option value="0">----------</option>
                                    <c:forEach var="year" items="${ent_year_set}">
                                        <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div style="width: 120px;">
                                <label class="form-label small mb-1" for="f2">クラス</label>
                                <select class="form-select form-select-sm" name="f2" id="f2">
                                    <option value="0">----------</option>
                                    <c:forEach var="num" items="${class_num_set}">
                                        <option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div style="width: 220px;">
                                <label class="form-label small mb-1" for="f3">科目</label>
                                <select class="form-select form-select-sm" name="f3" id="f3">
                                    <option value="0">----------</option>
                                    <c:forEach var="subject" items="${subjects}">
                                        <option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>${subject.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-secondary btn-sm px-3">検索</button>
                        </div>
                    </div>
                </form>

                <hr class="my-4 text-secondary opacity-25">

                <form method="get" action="TestListStudentExecute.action" class="mb-0">
                    <div class="d-flex align-items-start">
                        <div class="text-center mt-4" style="width: 100px; flex-shrink: 0; margin-right: 20px;">学生情報</div>
                        
                        <div class="ms-4">
                            <label class="form-label small mb-1" for="f4">学生番号</label>
                            <div class="d-flex align-items-center">
                                <input type="text" id="f4" name="f4" value="${f4}" 
                                       class="form-control form-control-sm me-2" style="width: 250px;" 
                                       maxlength="10" placeholder="学生番号を入力してください" required>
                                <button type="submit" class="btn btn-secondary btn-sm px-3">検索</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            </div> 
             
		</section>
							<div class="mt-3">氏名：${ student.name }（${ student.no }）</div>
		
		<c:choose>
			<c:when test="${ testListStudents.size() > 0 }">
			
		        <table class="table table-hover">
		            <tr>
		                <th>科目名</th>
		                <th>科目コード</th>
		                <th>回数</th>
		                <th>点数</th>
		                <th></th>
		            </tr>
		            <c:forEach var="tlstudent" items="${ testListStudents }">
		                <tr>
		                    <td>${ tlstudent.subjectName }</td>
		                    <td>${ tlstudent.subjectCd }</td>
		                    <td>${ tlstudent.num }</td>
		                    <td>${ tlstudent.point }</td>
		                    <td><a href="TestDelete.action?studentNo=${student.no}&subjectCd=${tlstudent.subjectCd}&schoolCd=${student.school.cd}&num=${tlstudent.num}">削除</a></td>
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