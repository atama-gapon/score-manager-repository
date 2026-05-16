package scoremanager.main;

import bean.Position;
import bean.School;
import bean.Staff;
import dao.PositionDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class PositionCreateExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		// インスタンス化
		PositionDao positionDao = new PositionDao();

		// リクエストパラメータの取得
		String name = req.getParameter("name");
		String sortOrder = req.getParameter("sort_order");

		// バリデーション（追加予定）

		// DBへ反映
		Position position = new Position();
		position.setSchool(school);
		position.setName(name);
		position.setSortOrder(Integer.parseInt(sortOrder));
		positionDao.save(position);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/PositionCreateDone.action");
	}
}
