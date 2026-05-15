<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/WEB-INF/jsp/common/base.jsp">
    <c:param name="title">職員情報更新</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <c:import url="/WEB-INF/jsp/common/done.jsp">
            <c:param name="successMessage">更新が完了しました</c:param>
            <c:param name="nextStepLinks">
                <a href="StaffList.action" class="text-decoration-underline">職員一覧</a>
                <a href="StaffUpdate.action?no=${param.no}" class="text-decoration-underline">続けて編集</a>
            </c:param>
        </c:import>
    </c:param>
</c:import>
