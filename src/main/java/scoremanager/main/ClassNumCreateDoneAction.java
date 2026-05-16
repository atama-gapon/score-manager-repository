package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ClassNumCreateDoneAction extends Action {

	// クラス番号作成完了画面のJSPへフォワード遷移する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/class_num_create_done.jsp").forward(req, res);
	}
}