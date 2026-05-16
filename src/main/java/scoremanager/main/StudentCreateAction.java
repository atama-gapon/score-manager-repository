package scoremanager.main;

import bean.School;
import bean.Staff;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
import tool.StudentUtil;

public class StudentCreateAction extends Action {

	// 入学年度一覧およびクラス番号一覧を取得し、学生新規登録画面のJSPへフォワード遷移する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		// 画面表示用のデータを準備
		prepareViewData(req, school);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/student_create.jsp").forward(req, res);
	}

	private void prepareViewData(HttpServletRequest req, School school) throws Exception {
		ClassNumDao classNumDao = new ClassNumDao();

		req.setAttribute("class_num_list", classNumDao.filter(school));
		req.setAttribute("ent_year_list", StudentUtil.createEntYearList());
	}
}