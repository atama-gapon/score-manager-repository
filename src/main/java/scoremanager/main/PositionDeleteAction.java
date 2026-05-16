package scoremanager.main;

import bean.Position;
import dao.PositionDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class PositionDeleteAction extends Action {

	// 選択された役職情報を取得し、削除確認画面のJSPへフォワード遷移する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		String idStr = req.getParameter("id");

		PositionDao positionDao = new PositionDao();
		Position position = null;

		// IDが取得できている場合はデータベースから役職情報を取得
		if (idStr != null && !idStr.isEmpty()) {
			int id = Integer.parseInt(idStr);
			position = positionDao.get(id);
		}

		// 画面表示用のデータを準備
		req.setAttribute("position", position);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/position_delete.jsp").forward(req, res);
	}
}