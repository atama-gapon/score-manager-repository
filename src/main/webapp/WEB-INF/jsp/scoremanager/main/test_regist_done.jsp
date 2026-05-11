<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">成績登録</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<p class="alert alert-success mx-3" role="alert">登録が完了しました</p>
		<div class="mx-3 mt-4">
			<a href="TestRegist.action?f1=${f1}&f2=${f2}&f3=${f3}&f4=${f4}&search=true" class="btn btn-link"> 戻る </a>
			<a href="TestList.action" class="ms-3">成績参照</a>
		</div>
	</c:param>
</c:import>