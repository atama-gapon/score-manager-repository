package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Staff;
import bean.Subject;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestRegistAction extends Action {

	// 成績登録・検索画面に必要な各種リストデータを準備し、リクエスト条件に応じたテスト成績一覧を抽出して画面へ遷移する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		String entYearStr = req.getParameter("ent_year");
		String classNum = req.getParameter("class_num");
		String subjectCd = req.getParameter("subject_cd");
		String numStr = req.getParameter("num");

		// 画面表示用のデータを準備（検索条件用の各種リストをセット）
		prepareViewData(req, school);

		// ボタン押下判定（検索、または登録ボタン押下時のみ抽出ロジックを実行）
		if (req.getParameter("search") != null || req.getParameter("regist") != null) {
			req.setAttribute("message_over", null);

			// バリデーション
			if (entYearStr == null || entYearStr.isEmpty() ||
					classNum == null || classNum.isEmpty() ||
					subjectCd == null || subjectCd.isEmpty() ||
					numStr == null || numStr.isEmpty()) {

				// 画面表示用のデータを準備（入力値を維持して復帰）
				req.setAttribute("message", "入学年度・クラス・科目・回数を入力してください");
				req.setAttribute("ent_year", entYearStr);
				req.setAttribute("class_num", classNum);
				req.setAttribute("subject_cd", subjectCd);
				req.setAttribute("num", numStr);

				req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/test_regist.jsp").forward(req, res);
				return;
			}

			int entYear = 0;
			int num = 0;
			try {
				entYear = Integer.parseInt(entYearStr);
				num = Integer.parseInt(numStr);
			} catch (NumberFormatException e) {
				req.setAttribute("message", "不正な数値が入力されました");
				req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/test_regist.jsp").forward(req, res);
				return;
			}

			// 条件に合致する成績リストを取得
			SubjectDao subjectDao = new SubjectDao();
			Subject subject = subjectDao.get(subjectCd, school);

			TestDao testDao = new TestDao();
			List<Test> testList = testDao.filter(entYear, classNum, subject, num, school);

			// 画面表示用のデータを準備（検索結果・選択状態の引き継ぎ）
			req.setAttribute("ent_year", entYearStr);
			req.setAttribute("class_num", classNum);
			req.setAttribute("subject_cd", subjectCd);
			req.setAttribute("tests", testList);
			req.setAttribute("num", num);
			req.setAttribute("subject", subject);
			req.setAttribute("school", school);
		}

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/test_regist.jsp").forward(req, res);
	}

	private void prepareViewData(HttpServletRequest req, School school) throws Exception {
		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();
		StudentDao studentDao = new StudentDao();

		req.setAttribute("class_num_list", classNumDao.filter(school));
		req.setAttribute("subject_list", subjectDao.filter(school));
		req.setAttribute("ent_year_list", studentDao.getEntYearList(school));
	}
}