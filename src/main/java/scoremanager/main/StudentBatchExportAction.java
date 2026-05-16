package scoremanager.main;

import bean.School;
import bean.Staff;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentBatchExportAction extends Action {

	// 入学年度一覧およびクラス番号一覧を取得し、学生一括エクスポート画面のJSPへフォワード遷移する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		// 画面表示用のデータを準備
		prepareViewData(req, school);

		// 一括エクスポート画面のJSPへリクエストを転送
		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/student_batchexport.jsp").forward(req, res);
	}

	private void prepareViewData(HttpServletRequest req, School school) throws Exception {
		StudentDao studentDao = new StudentDao();
		ClassNumDao classNumDao = new ClassNumDao();

		req.setAttribute("class_num_list", classNumDao.filter(school));
		req.setAttribute("ent_year_list", studentDao.getEntYearList(school));
	}
}