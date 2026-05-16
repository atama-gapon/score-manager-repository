package scoremanager.main;

import bean.School;
import bean.Staff;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListAction extends Action {

	// 検索条件に必要な各種リストデータを取得し、テスト成績一覧画面のJSPへフォワード遷移する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		// 画面表示用のデータを準備
		prepareViewData(req, school);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/test_list.jsp").forward(req, res);
	}

	private void prepareViewData(HttpServletRequest req, School school) throws Exception {
		// 各種DAOの生成とデータの引き込み（DAOの隠蔽・局所化）
		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();
		StudentDao studentDao = new StudentDao();

		// 検索条件の選択肢をセット
		req.setAttribute("class_num_list", classNumDao.filter(school));
		req.setAttribute("subject_list", subjectDao.filter(school));
		req.setAttribute("ent_year_list", studentDao.getEntYearList(school));
	}
}