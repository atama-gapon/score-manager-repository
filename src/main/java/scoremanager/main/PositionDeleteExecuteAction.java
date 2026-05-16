package scoremanager.main;

import dao.PositionDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class PositionDeleteExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// インスタンス化
		PositionDao positionDao = new PositionDao();

		// リクエストパラメータの取得
		String id = req.getParameter("id");

		// バリデーション（追加予定）

		// DBへ反映
		positionDao.delete(Integer.parseInt(id));

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/PositionDeleteDone.action");
	}
}
