package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Staff;
import bean.Student;
import bean.TestStudentList;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestStudentListDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestStudentListExecuteAction extends Action {

	// 入力された学生番号に基づいて該当学生の全テスト成績一覧を抽出し、学生別成績一覧画面のJSPへフォワード遷移する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		String targetNo = req.getParameter("f4");

		// 画面表示用のデータを準備
		prepareViewData(req, school, targetNo);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/test_list_student.jsp").forward(req, res);
	}

	private void prepareViewData(HttpServletRequest req, School school, String targetNo) throws Exception {
		// 各種DAOの生成とデータの引き込み（DAOの隠蔽・局所化）
		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();
		StudentDao studentDao = new StudentDao();

		// 共通の検索条件ドロップダウン用データをセット
		req.setAttribute("class_num_list", classNumDao.filter(school));
		req.setAttribute("subject_list", subjectDao.filter(school));
		req.setAttribute("ent_year_list", studentDao.getEntYearList(school));

		// 条件となる入力情報の引き継ぎセット
		req.setAttribute("f4", targetNo);

		// 特定学生の成績データ抽出ロジック（Null安全対策の徹底）
		List<TestStudentList> testStudentList = new ArrayList<>();
		Student student = null;

		if (targetNo != null && !targetNo.isEmpty()) {
			student = studentDao.get(targetNo);

			if (student != null) {
				TestStudentListDao testStudentListDao = new TestStudentListDao();
				testStudentList = testStudentListDao.filter(student);
			}
		}

		// 画面へ返却するエンティティと成績リストをセット
		req.setAttribute("student", student);
		req.setAttribute("test_student_list", testStudentList);
	}
}