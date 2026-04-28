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
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
            
            <form method="post">
			    <div class="row border mx-3 mb-3 py-2 align-items-end rounded" id="filter">
			        <div class="col-2">
			            <label class="form-label" for="student-f1-select">入学年度</label>
			            <select class="form-select" id="student-f1-select" name="f1">
			                <option value="0">----------</option>
			                <c:forEach var="year" items="${ ent_year_set }">
			                    <option value="${ year }" <c:if test="${ year==f1 }">selected</c:if>>${ year }</option>
			                </c:forEach>
			            </select>
			        </div>
			
			        <div class="col-2">
			            <label class="form-label" for="student-f2-select">クラス</label>
			            <select class="form-select" id="student-f2-select" name="f2">
			                <option value="0">----------</option>
			                <c:forEach var="num" items="${ class_num_set }">
			                    <option value="${ num }" <c:if test="${ num==f2 }">selected</c:if>>${ num }</option>
			                </c:forEach>
			            </select>
			        </div>
			
			        <div class="col-3">
			            <label class="form-label" for="student-f3-select">科目</label>
			            <select class="form-select" id="student-f3-select" name="f3">
			                <option value="0">----------</option>
			                <c:forEach var="subject" items="${ subject_set }">
			                    <option value="${ subject.cd }" <c:if test="${ subject.cd==f3 }">selected</c:if>>${ subject.name }</option>
			                </c:forEach>
			            </select>
			        </div>
			
			        <div class="col-2">
			            <label class="form-label" for="student-f4-select">回数</label>
			            <select class="form-select" id="student-f4-select" name="f4">
			                <option value="0">----------</option>
			                <option value="1" <c:if test="${f4 == 1}">selected</c:if>>1</option>
			                <option value="2" <c:if test="${f4 == 2}">selected</c:if>>2</option>
			            </select>
			        </div>
			
			        <div class="col-2">
			            <button type="submit" name="search" value="true" class="btn btn-secondary">検索</button>
			        </div>
			
			        <div class="col-12 mt-2 text-warning">${ errors.get("f1") }</div>
			    </div>
			</form>
			<c:if test="${not empty message}">
    			<div class="alert alert-danger mt-3">${message}</div>
			</c:if>
			<c:choose>
			    <c:when test="${not empty tests}">
			        <div>科目：${subject.name} （${num}回）</div>
			        <form action="TestRegistExecute.action" method="post">
				        <table class="table table-hover">
				            <tr>
				                <th>入学年度</th>
				                <th>クラス</th>
				                <th>学籍番号</th>
				                <th>氏名</th>
				                <th>点数</th>
				            </tr>
				            <c:forEach var="test" items="${ tests }">
				                <tr>
				                    <td>${ test.student.entYear }</td>
				                    <td>${ test.classNum }</td>
				                    <td>${ test.student.no }</td>
				                    <td>${ test.student.name }</td>
				                    <td>
					                    <input type="number" name="point_${test.student.no}"
					                    		value="${test.point == -1 ? '' : test.point}" class="form-control" 
	                                       		style="width: 100px;">
	                                       		<c:if test="${not empty message_over && (test.point < 0 || test.point > 100)}">
									    			<div class="text-warning">${message_over}</div>
												</c:if>
					                    <input type="hidden" name="student_no_list" value="${test.student.no}">
					                    
										
					                </td>
				                </tr>
				            </c:forEach>
				        </table>
				        <div class="mt-3">
                			<button type="submit" name="regist" class="btn btn-secondary">登録して終了</button>
            			</div>
            			<input type="hidden" name="f1" value="${f1}">
						<input type="hidden" name="f2" value="${f2}">
						<input type="hidden" name="f3" value="${f3}">
						<input type="hidden" name="f4" value="${f4}">
			        </form>
			    </c:when>
			    
				<c:when test="${not empty tests}">
			        <div class="mt-3">科目：${subject.name} （${num}回）</div>
			        
			    </c:when>

			    
			    <c:when test="${not empty f1}">
			        
			        <c:if test="${empty message}">
			            <div class="alert alert-danger mt-3">学生情報が存在しませんでした。</div>
			        </c:if>
			    </c:when>
			
			    <%-- 初回表示時 --%>
			    <c:otherwise>
			       
			    </c:otherwise>
				
			    
			</c:choose>
		</section>
	</c:param>
</c:import>