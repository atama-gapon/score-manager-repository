<%-- 完了 --%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="message" required="true" type="java.lang.String"%>
<div class="mb-3 fw-normal bg-success py-2 px-4 text-center" style="--bs-bg-opacity: .6">${message}</div>
<div class="mt-5 pt-5">
	<div class="d-flex gap-5">
		<%-- タグの間に挟まれたHTMLがここに自動で入る --%>
		<jsp:doBody />
	</div>
</div>