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
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績一覧（科目）</h2>
            <form method="get" action="TestListSubjectExecute.action">
			    <div class="border mx-3 mb-3 py-3 px-4 rounded shadow-sm">
			        <div class="row">
			            <div class="col-auto">
						    <div class="text-center mt-4" style="width: 80px;">科目情報</div>
						</div>
			
			            <div class="col">
			                <div class="row g-3 align-items-end">
			                    <div class="col-md-3">
			                        <label class="form-label mb-1" for="f1">入学年度</label>
			                        <select class="form-select" name="f1" id="f1">
			                            <option value="0">----------</option>
			                            <c:forEach var="year" items="${ent_year_set}">
			                                <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
			                            </c:forEach>
			                        </select>
			                    </div>
			
			                    <div class="col-md-3">
			                        <label class="form-label mb-1" for="f2">クラス</label>
			                        <select class="form-select" name="f2" id="f2">
			                            <option value="0">----------</option>
			                            <c:forEach var="num" items="${class_num_set}">
			                                <option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
			                            </c:forEach>
			                        </select>
			                    </div>
			
			                    <div class="col-md-4">
			                        <label class="form-label mb-1" for="f3">科目</label>
			                        <select class="form-select" name="f3" id="f3">
			                            <option value="0">----------</option>
			                            <c:forEach var="subject" items="${subjects}">
			                                <option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>${subject.name}</option>
			                            </c:forEach>
			                        </select>
			                    </div>
			
			                    <div class="col-md-2">
			                        <button class="btn btn-secondary" >検索</button>
			                    </div>
			                </div>
			            </div>
			        </div>
			
			        <c:if test="${not empty message}">
			            <div class="mt-2 text-warning">${message}</div>
			        </c:if>
			    </div>
			</form>

            <form method="get" action="TestListStudentExecute.action">
			    <div class="border mx-3 mb-3 py-3 px-4 rounded shadow-sm">
			        <div class="d-flex align-items-start">
			            <div class="col-auto me-4">
			                <div class="text-center mt-4" style="width: 80px;">学生情報</div>
			            </div>
			        
			            <div>
			                <label class="form-label mb-1" for="f4">学生番号</label>
			                <div class="d-flex">
			                    <input type="text" id="f4" name="f4" value="${f4}" class="form-control me-2" style="width: 300px;" maxlength="10" placeholder="学生番号を入力してください" required>
			                    <button class="btn btn-secondary">検索</button>
			                </div>
			            </div>
			        </div>
			    </div>
			</form>
		</section>
		<c:choose>
			<c:when test="${ testListSubjects.size() > 0 }">
					<div>科目：${ subject.name }</div>
			
		        <table class="table table-hover">
		            <tr>
		                <th>入学年度</th>
		                <th>クラス</th>
		                <th>学生番号</th>
		                <th>氏名</th>
		                <th>1回</th>
		                <th>2回</th>
		            </tr>
		            <c:forEach var="tlsubject" items="${ testListSubjects }">
		                <tr>
		                    <td>${ tlsubject.entYear }</td>
		                    <td>${ tlsubject.classNum }</td>
		                    <td>${ tlsubject.studentNo }</td>
		                    <td>${ tlsubject.studentName }</td>
		                    
		                    <c:set var="count" value="0" />
		                    <c:forEach var="point" items="${tlsubject.points}">
		                    	<c:if test="${point.key == 1}">
		                    		<td>${point.value}</td>
		                    		<c:set var="count" value="${count + 1}" />
		                    	</c:if>
		                    	<c:if test="${(point.key == 2) && (count == 1)}">
		                    		<td>${point.value}</td>
		                    	</c:if>
							</c:forEach>
		                </tr>
		            </c:forEach>
		        </table>
		    </c:when>
		    <c:otherwise>
		        <div>学生情報が存在しませんでした。</div>
		    </c:otherwise>
		</c:choose>
	</c:param>
</c:import>