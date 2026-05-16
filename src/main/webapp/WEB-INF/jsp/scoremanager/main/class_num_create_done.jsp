<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">クラス情報登録</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<my:done message="登録が完了しました">
			<a href="ClassNumCreate.action" class="text-decoration-underline">戻る</a>
			<a href="ClassNumList.action" class="text-decoration-underline">クラス一覧</a>
		</my:done>
	</c:param>
</c:import>
