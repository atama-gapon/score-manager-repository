package scoremanager.main;

import bean.School;
import bean.Staff;
import dao.PositionDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class PositionListAction extends Action {

	// 所属する学校の役職一覧を取得し、一覧画面のJSPへフォワード遷移する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		// 画面表示用のデータを準備
		prepareViewData(req, school);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/position_list.jsp").forward(req, res);
	}

	private void prepareViewData(HttpServletRequest req, School school) throws Exception {
		PositionDao positionDao = new PositionDao();
		req.setAttribute("position_list", positionDao.filter(school));
	}
}