<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/base.jsp">
	<c:param name="title">成績登録</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<my:done message="登録が完了しました">
			<a href=TestRegist.action?ent_year=${ent_year}&class_num=${class_num}&is_attend=${is_attend}&f4=${f4}&search=true " class="text-decoration-underline">戻る</a>
			<a href="TestList.action" class="text-decoration-underline">成績参照</a>
		</my:done>
	</c:param>
</c:import>
