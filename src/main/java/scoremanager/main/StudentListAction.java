package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Staff;
import bean.Student;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentListAction extends Action {

	// 検索条件に応じた学生一覧を取得し、学生一覧画面のJSPへフォワード遷移する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		String entYearStr = req.getParameter("ent_year");
		String classNum = req.getParameter("class_num");
		String isAttendStr = req.getParameter("is_attend");
		String submitted = req.getParameter("submitted");

		// パラメータの初期化（Null安全対策）
		if (entYearStr == null)
			entYearStr = "";
		if (classNum == null)
			classNum = "";

		// インスタンス化
		StudentDao studentDao = new StudentDao();
		Map<String, String> errors = new HashMap<>();
		List<Student> studentList = new ArrayList<>();

		int entYear = 0;
		boolean isEntYearSelected = !entYearStr.isEmpty();
		boolean isClassNumSelected = !classNum.isEmpty();
		boolean isAttend = (isAttendStr != null);

		// バリデーション
		if (isEntYearSelected) {
			try {
				entYear = Integer.parseInt(entYearStr);
			} catch (NumberFormatException e) {
				errors.put("search", "入学年度は半角数字で入力してください");
			}
		}

		// バリデーション：相関チェック
		if (isClassNumSelected && !isEntYearSelected) {
			errors.put("search", "クラスを指定する場合は入学年度も指定してください");
		}

		// データの準備（対象データの抽出）
		if (!errors.isEmpty()) {
			// バリデーションエラー時は全件（または在籍フラグのみ）でフォワード
			studentList = studentDao.filter(school, isAttend);
		} else if (!"true".equals(submitted) || (!isEntYearSelected && !isClassNumSelected)) {
			// 初期表示、または検索条件が未指定の場合
			studentList = studentDao.filter(school, isAttend);
		} else {
			// 検索実行時
			studentList = findStudents(school, entYear, classNum, isClassNumSelected, isAttend, studentDao);
		}

		// 画面表示用のデータを準備
		prepareViewData(req, school);
		req.setAttribute("ent_year", entYearStr);
		req.setAttribute("class_num", classNum);
		req.setAttribute("is_attend", isAttendStr);
		req.setAttribute("errors", errors);
		req.setAttribute("student_list", studentList);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/student_list.jsp").forward(req, res);
	}

	private void prepareViewData(HttpServletRequest req, School school) throws Exception {
		StudentDao studentDao = new StudentDao();
		ClassNumDao classNumDao = new ClassNumDao();

		req.setAttribute("class_num_list", classNumDao.filter(school));
		req.setAttribute("ent_year_list", studentDao.getEntYearList(school));
	}

	private List<Student> findStudents(School school, int entYear, String classNum, boolean isClassNumSelected, boolean isAttend, StudentDao studentDao) throws Exception {
		if (isClassNumSelected) {
			return studentDao.filter(school, entYear, classNum, isAttend);
		}
		return studentDao.filter(school, entYear, isAttend);
	}
}