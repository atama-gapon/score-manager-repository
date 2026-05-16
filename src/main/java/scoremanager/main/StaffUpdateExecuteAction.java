package scoremanager.main;

import java.util.Map;

import bean.School;
import bean.Staff;
import dao.PositionDao;
import dao.StaffDao;
import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StaffUpdateExecuteAction extends Action {

	// 入力された教職員変更情報のバリデーションを行い、問題がなければデータベースを更新する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		StaffForm form = new StaffForm(req);
		StaffDao staffDao = new StaffDao();

		// 変更対象の元の職員情報を取得
		Staff original = staffDao.get(form.getNo(), school);

		// バリデーション
		Map<String, String> errors = form.validateForUpdate(school);

		// エラーがある場合は入力画面へ戻す
		if (!errors.isEmpty()) {
			// 画面表示用のデータを準備
			prepareViewData(req, school);
			form.setAttributes(req);
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("StaffUpdate.action").forward(req, res);
			return;
		}

		// DBへ反映
		Staff updated = form.toEntityForUpdate(original, school);
		staffDao.update(updated);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/StaffUpdateDone.action");
	}

	private void prepareViewData(HttpServletRequest req, School school) throws Exception {
		PositionDao positionDao = new PositionDao();
		StatusDao statusDao = new StatusDao();

		req.setAttribute("position_list", positionDao.filter(school));
		req.setAttribute("status_list", statusDao.filter(school));
	}
}