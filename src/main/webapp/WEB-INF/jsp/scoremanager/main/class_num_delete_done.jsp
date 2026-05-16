<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">クラス情報削除</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<my:done message="削除が完了しました">
			<a class="text-decoration-underline" href="ClassNumList.action">クラス一覧</a>
		</my:done>
	</c:param>
</c:import>