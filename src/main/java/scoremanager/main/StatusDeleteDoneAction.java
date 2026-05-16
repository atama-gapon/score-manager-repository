package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StatusDeleteDoneAction extends Action {

	// 在籍状態削除完了画面のJSPへフォワード遷移する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/status_delete_done.jsp").forward(req, res);
	}
}