<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">クラス管理</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<div class="my-2 text-end px-4">
			<c:if test="${staff.position.name eq '管理者'}">
				<a href="ClassNumCreate.action">新規登録</a>
			</c:if>
		</div>
		<c:choose>
			<c:when test="${ not empty class_num_list }">
				<table class="table table-hover">
					<tr>
						<th>クラス番号</th>
						<th></th>
					</tr>
					<c:forEach var="class_num" items="${ class_num_list }">
						<tr>
							<td>${ class_num }</td>
							<c:if test="${staff.position.name eq '管理者'}">
								<td><a href="ClassNumDelete.action?class_num=${ class_num }">削除</a></td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<div>クラス情報が存在しませんでした。</div>
			</c:otherwise>
		</c:choose>
	</c:param>
</c:import>