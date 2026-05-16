package scoremanager.main;

import bean.Status;
import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StatusDeleteAction extends Action {

	// 選択された在籍状態情報を取得し、削除確認画面のJSPへフォワード遷移する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 画面表示用のデータを準備
		prepareViewData(req);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/status_delete.jsp").forward(req, res);
	}

	private void prepareViewData(HttpServletRequest req) throws Exception {
		// 必要なリクエスト情報の取得
		String idStr = req.getParameter("id");

		StatusDao statusDao = new StatusDao();
		Status status = null;

		// IDが取得できている場合はデータベースから在籍状態情報を取得
		if (idStr != null && !idStr.isEmpty()) {
			int id = Integer.parseInt(idStr);
			status = statusDao.get(id);
		}

		req.setAttribute("status", status);
	}
}