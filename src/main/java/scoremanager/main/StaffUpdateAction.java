package scoremanager.main;

import bean.School;
import bean.Staff;
import dao.PositionDao;
import dao.StaffDao;
import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StaffUpdateAction extends Action {

	// 選択された教職員情報、および選択肢となる役職・状態一覧を取得し、変更画面のJSPへフォワード遷移する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff loginStaff = (Staff) req.getAttribute("staff");
		School school = loginStaff.getSchool();
		String no = req.getParameter("no");

		// 画面表示用のデータを準備
		prepareViewData(req, school, no);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/staff_update.jsp").forward(req, res);
	}

	private void prepareViewData(HttpServletRequest req, School school, String no) throws Exception {
		StaffDao staffDao = new StaffDao();
		PositionDao positionDao = new PositionDao();
		StatusDao statusDao = new StatusDao();

		Staff targetStaff = null;

		// 職員番号が取得できている場合はデータベースから教職員情報を取得
		if (no != null && !no.isEmpty()) {
			targetStaff = staffDao.get(no, school);
		}

		req.setAttribute("staff", targetStaff);
		req.setAttribute("position_list", positionDao.filter(school));
		req.setAttribute("status_list", statusDao.filter(school));
	}
}