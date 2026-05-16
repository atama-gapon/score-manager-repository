package scoremanager.main;

import bean.Position;
import dao.PositionDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class PositionUpdateAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// インスタンス化
		PositionDao positionDao = new PositionDao();

		// リクエストパラメータの取得
		String id = req.getParameter("id");

		Position position = positionDao.get(Integer.parseInt(id));

		req.setAttribute("position", position);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/position_update.jsp").forward(req, res);
	}
}
