<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">科目情報削除</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<form class="px-4" action="SubjectDeleteExecute.action" method="post">
			<input type="hidden" name="cd" value="<c:out value='${cd}' />">
			<p>
				「
				<c:out value="${cd}" />
				(
				<c:out value="${name}" />
				)」を削除してもよろしいですか
			</p>
			<my:error message="${errors.cd}" />
			<div class="mt-4">
				<input class="btn btn-danger px-3" type="submit" value="削除">
			</div>
			<div class="mt-3">
				<a href="SubjectList.action">戻る</a>
			</div>
		</form>
	</c:param>
</c:import>