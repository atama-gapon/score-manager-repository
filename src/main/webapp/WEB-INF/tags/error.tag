<%-- エラー --%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%-- rtexprvalue="true" ⇒ EL式を使えるようにする。falseだと文字列しか渡せない。 --%>
<%@ attribute name="message" required="true" rtexprvalue="true"%>
<c:if test="${not empty message}">
	<div class="mt-2 text-warning">
		<c:out value="${message}" />
	</div>
</c:if>