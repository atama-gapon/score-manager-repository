package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class PositionUpdateDoneAction extends Action {

	// 役職変更完了画面のJSPへフォワード遷移する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/position_update_done.jsp").forward(req, res);
	}
}