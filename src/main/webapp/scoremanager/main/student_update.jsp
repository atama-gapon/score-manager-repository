<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title" value="学生情報変更" />
    <c:param name="scripts" value="" />
    <c:param name="content">

        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                学生情報変更
            </h2>

            <c:if test="${not empty message}">
                <div class="text-danger px-4">${message}</div>
            </c:if>

				<div class="mb-3">
				    <label class="form-label">入学年度</label>
				    <div>${student.entYear}</div>
				    <input type="hidden" name="entYear" value="${student.entYear}">
				</div>

				

				<div class="mb-3">
				    <label class="form-label">学生番号</label>
				    <div>${student.no}</div>
				    <input type="hidden" name="no" value="${student.no}">
				</div>


                <div class="mb-3">
                    <label class="form-label">氏名</label>
                    <input type="text" class="form-control"
                           name="name"
                           value="${student.name}"
                           placeholder="氏名を入力してください"
                           required>
                </div>

                <div class="mb-3">
			    <label class="form-label">クラス</label>
			    <select class="form-select" name="classNum">
			        <c:forEach var="c" items="${class_num_set}">
			            <option value="${c}"
			                <c:if test="${c == student.classNum}">selected</c:if>>
			                ${c}
			            </option>
			        </c:forEach>
			    </select>
			</div>
			

                <div class="mb-3 form-check">
                    <input class="form-check-input"
                           type="checkbox"
                           id="attend-check"
                           name="is_attend"
                           value="t"
                           <c:if test="${student.isAttend()}">checked</c:if>>
                    <label class="form-check-label" for="attend-check">
                        在学中
                    </label>
                </div>

                <div class="mt-4">
                    <button type="submit" class="btn btn-primary">変更</button>
                    <a href="StudentList.action" class="btn btn-secondary ms-2">戻る</a>
                </div>

            </form>

        </section>

    </c:param>
</c:import>
