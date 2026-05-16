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

public class StaffCreateExecuteAction extends Action {

	// 入力された教職員情報のバリデーションを行い、問題がなければデータベースに登録する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		// パラメータの取得とFormへの詰め替え
		StaffForm form = new StaffForm(req);

		// バリデーション
		Map<String, String> errors = form.validate(school);

		// エラーがある場合は入力画面へ戻す
		if (!errors.isEmpty()) {
			// 画面表示用のデータを準備
			prepareViewData(req, school);
			form.setAttributes(req);
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("StaffCreate.action").forward(req, res);
			return;
		}

		// DBへ反映
		Staff newStaff = form.toEntity(school);
		StaffDao staffDao = new StaffDao();
		staffDao.save(newStaff);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/StaffCreateDone.action");
	}

	private void prepareViewData(HttpServletRequest req, School school) throws Exception {
		PositionDao positionDao = new PositionDao();
		StatusDao statusDao = new StatusDao();

		req.setAttribute("position_list", positionDao.filter(school));
		req.setAttribute("status_list", statusDao.filter(school));
	}
}