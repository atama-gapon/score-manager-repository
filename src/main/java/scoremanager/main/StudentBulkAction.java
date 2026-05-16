package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentBulkAction extends Action {

	// 学生一括処理画面のJSPへフォワード遷移する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/student_bulk.jsp").forward(req, res);
	}
}