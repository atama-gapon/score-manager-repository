<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">クラス情報削除</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<form action="ClassNumDeleteExecute.action" method="post" class="px-4">
			<input type="hidden" name="num" value="<c:out value='${num}' />">
			<p>
				「
				<c:out value="${num}" />
				」を削除してもよろしいですか
			</p>
			<my:error message="${errors.num}" />
			<div class="mt-4">
				<button class="btn btn-danger px-3" type="submit">削除</button>
			</div>
			<div class="mt-3">
				<a href="ClassNumList.action">戻る</a>
			</div>
		</form>
	</c:param>
</c:import>