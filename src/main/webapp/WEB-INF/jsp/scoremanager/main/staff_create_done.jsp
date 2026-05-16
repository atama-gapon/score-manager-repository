<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">職員情報登録</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<my:done message="登録が完了しました">
			<a class="text-decoration-underline" href="StaffCreate.action">戻る</a>
			<a class="text-decoration-underline" href="StaffList.action">職員一覧</a>
		</my:done>
	</c:param>
</c:import>