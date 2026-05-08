<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/WEB-INF/jsp/common/base.jsp">
    <c:param name="title">ステータス管理</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">ステータス管理</h2>

            <div class="my-2 text-end px-4">
                <a href="StatusCreate.action">新規登録</a>
            </div>

            <c:choose>
                <c:when test="${ statusList.size() > 0 }">
                    <div>登録件数：${ statusList.size() }件</div>

                    <table class="table table-hover">
                        <tr>
                            <th>ID</th>
                            <th>ステータス名</th>
                            <th>並び順</th>
                            <th></th>
                        </tr>

                        <c:forEach var="s" items="${ statusList }">
                            <tr>
                                <td>${ s.id }</td>
                                <td>${ s.name }</td>
                                <td>${ s.sortOrder }</td>
                                <td>
                                    <a href="StatusUpdate.action?id=${ s.id }">変更</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>

                <c:otherwise>
                    <div>ステータス情報が存在しませんでした。</div>
                </c:otherwise>
            </c:choose>
        </section>
    </c:param>
</c:import>
