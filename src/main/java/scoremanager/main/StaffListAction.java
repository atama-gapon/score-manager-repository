package scoremanager.main;

import bean.School;
import bean.Staff;
import dao.PositionDao;
import dao.StaffDao;
import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StaffListAction extends Action {

	// 所属する学校の教職員・役職・状態一覧を取得し、教職員一覧画面のJSPへフォワード遷移する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		// 画面表示用のデータを準備
		prepareViewData(req, school);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/staff_list.jsp").forward(req, res);
	}

	private void prepareViewData(HttpServletRequest req, School school) throws Exception {
		StaffDao staffDao = new StaffDao();
		PositionDao positionDao = new PositionDao();
		StatusDao statusDao = new StatusDao();

		req.setAttribute("staff_list", staffDao.filter(school));
		req.setAttribute("position_list", positionDao.filter(school));
		req.setAttribute("status_list", statusDao.filter(school));
	}
}