<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
<c:param name="content">

<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
    学生情報一括登録
</h2>
<form action="StudentBulkExecute.action" method="post" enctype="multipart/form-data">
	<div class="tr">
		<label for="csv" class="th">csv:</label>
		<div class="td">
			<input type="file" id="csv" name="csv" required>
		</div>
	</div>
	<div class="mt-3">
        <button type="submit" name="regist" class="btn btn-secondary">登録</button>
   	</div>
</form>


</c:param>
</c:import>
