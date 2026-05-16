<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">役職情報削除</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<form class="px-4" action="PositionDeleteExecute.action" method="post">
			<p>
				「
				<c:out value="${name}" />
				」 を削除してもよろしいですか
			</p>
			<input type="hidden" name="id" value="<c:out value='${id}' />">
			<div class="mt-4">
				<input class="btn btn-danger px-3" type="submit" value="削除">
			</div>
			<div class="mt-3">
				<a class="btn btn-link p-0" href="PositionList.action">戻る</a>
			</div>
		</form>
	</c:param>
</c:import>