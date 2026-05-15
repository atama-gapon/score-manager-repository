<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/WEB-INF/jsp/common/base.jsp">
    <c:param name="title">職員情報変更</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">

        <form action="StaffUpdateExecute.action" method="post" class="px-4">

            <!-- 職員番号（hidden + readonly） -->
            <input type="hidden" name="no" value="${staff.no}">

            <div class="mb-3">
                <label class="form-label">職員番号</label>
                <input type="text"
                    value="${staff.no}"
                    class="form-control"
                    readonly>
            </div>

            <div class="mb-3">
                <label class="form-label">姓</label>
                <input type="text" name="last_name"
				    value="${empty last_name ? staff.lastName : last_name}"
				    class="form-control">

                <c:if test="${not empty errors.last_name}">
                    <div class="mt-2 text-warning">${errors.last_name}</div>
                </c:if>
            </div>

            <div class="mb-3">
                <label class="form-label">名</label>
				<input type="text" name="first_name"
				    value="${empty first_name ? staff.firstName : first_name}"
				    class="form-control">

                <c:if test="${not empty errors.first_name}">
                    <div class="mt-2 text-warning">${errors.first_name}</div>
                </c:if>
            </div>

            <div class="mb-3">
                <label class="form-label">姓（カタカナ）</label>
			<input type="text" name="last_name_kana"
			    value="${empty last_name_kana ? staff.lastNameKana : last_name_kana}"
			    class="form-control">


                <c:if test="${not empty errors.last_name_kana}">
                    <div class="mt-2 text-warning">${errors.last_name_kana}</div>
                </c:if>
            </div>

            <div class="mb-3">
                <label class="form-label">名（カタカナ）</label>
			  <input type="text" name="first_name_kana"
			    value="${empty first_name_kana ? staff.firstNameKana : first_name_kana}"
			    class="form-control">

                <c:if test="${not empty errors.first_name_kana}">
                    <div class="mt-2 text-warning">${errors.first_name_kana}</div>
                </c:if>
            </div>

            <div class="mb-3">
                <label class="form-label">役職</label>
                <select name="position_id" class="form-select">
                    <option value="">--------</option>
                    <c:forEach var="p" items="${position_list}">
                        <option value="${p.id}"
						    <c:if test="${p.id == (empty position_id ? staff.position.id : position_id)}">selected</c:if>>
						    ${p.name}
						</option>

                    </c:forEach>
                </select>
                <c:if test="${not empty errors.position_id}">
                    <div class="mt-2 text-warning">${errors.position_id}</div>
                </c:if>
            </div>

            <div class="mb-3">
                <label class="form-label">状態</label>
                <select name="status_id" class="form-select">
                    <option value="">--------</option>
                    <c:forEach var="s" items="${status_list}">
                       <option value="${s.id}"
						    <c:if test="${s.id == (empty status_id ? staff.status.id : status_id)}">selected</c:if>>
						    ${s.name}
						</option>

                    </c:forEach>
                </select>
                <c:if test="${not empty errors.status_id}">
                    <div class="mt-2 text-warning">${errors.status_id}</div>
                </c:if>
            </div>

            <div class="mt-4">
                <button type="submit" class="btn btn-secondary">変更</button>
            </div>

            <div class="mt-3">
                <a href="StaffList.action">戻る</a>
            </div>

        </form>

    </c:param>
</c:import>
