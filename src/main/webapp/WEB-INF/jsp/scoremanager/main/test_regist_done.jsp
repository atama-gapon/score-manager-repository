<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">成績登録</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<c:import url="/WEB-INF/jsp/common/done.jsp">
			<c:param name="successMessage">登録が完了しました</c:param>
			<c:param name="nextStepLinks">
				<a href=TestRegist.action?ent_year=${ent_year}&class_num=${class_num}&is_attend=${is_attend}&f4=${f4}&search=true " class="text-decoration-underline">戻る</a>
				<a href="TestList.action" class="text-decoration-underline">成績参照</a>
			</c:param>
		</c:import>
	</c:param>
</c:import>