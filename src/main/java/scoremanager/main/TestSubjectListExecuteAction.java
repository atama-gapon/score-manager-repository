package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Staff;
import bean.Subject;
import bean.TestSubjectList;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestSubjectListDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestSubjectListExecuteAction extends Action {

	// 入力された検索条件に基づいて該当科目のクラス別テスト成績一覧を抽出し、科目別成績一覧画面のJSPへフォワード遷移する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		String entYearStr = req.getParameter("ent_year");
		String classNum = req.getParameter("class_num");
		String subjectCd = req.getParameter("subject_cd");

		// 画面表示用のデータを準備（検索用リスト、および条件に合致する成績データの抽出を一括集約）
		prepareViewData(req, school, entYearStr, classNum, subjectCd);

		// エラーメッセージの有無によってフォワード先を動的に切り替える
		if (req.getAttribute("message") != null) {
			// バリデーションエラー時は元のテスト成績検索画面（共通JSP）へ戻す
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/test_list.jsp").forward(req, res);
		} else {
			// 正常に抽出できた場合は科目別成績一覧画面のJSPへリクエストを転送
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/test_list_subject.jsp").forward(req, res);
		}
	}

	private void prepareViewData(HttpServletRequest req, School school, String entYearStr, String classNum, String subjectCd) throws Exception {
		// 各種DAOの生成とデータの引き込み（DAOの隠蔽・局所化）
		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();
		StudentDao studentDao = new StudentDao();

		// 共通の検索条件ドロップダウン用データをセット
		req.setAttribute("class_num_list", classNumDao.filter(school));
		req.setAttribute("subject_list", subjectDao.filter(school));
		req.setAttribute("ent_year_list", studentDao.getEntYearList(school));

		// 条件となる入力情報の引き継ぎセット
		req.setAttribute("ent_year", entYearStr);
		req.setAttribute("class_num", classNum);
		req.setAttribute("subject_cd", subjectCd);

		// バリデーション：未入力チェック
		if (entYearStr == null || entYearStr.isEmpty() ||
				classNum == null || classNum.isEmpty() ||
				subjectCd == null || subjectCd.isEmpty()) {
			req.setAttribute("message", "入学年度とクラスと科目を選択してください");
			return;
		}

		// 安全対策：数値変換チェック（システムクラッシュ防止）
		int entYear = 0;
		try {
			entYear = Integer.parseInt(entYearStr);
		} catch (NumberFormatException e) {
			req.setAttribute("message", "入学年度の数値変換に失敗しました。");
			return;
		}

		// 科目情報の取得
		Subject subject = subjectDao.get(subjectCd, school);

		// 入学年度、クラス、科目に合致する成績データを取得
		List<TestSubjectList> testSubjectList = new ArrayList<>();
		if (subject != null) {
			TestSubjectListDao testSubjectListDao = new TestSubjectListDao();
			testSubjectList = testSubjectListDao.filter(entYear, classNum, subject, school);
		}

		// 画面表示用のデータを準備（検索結果・選択状態のセット）
		req.setAttribute("subject", subject);
		req.setAttribute("test_subject_list", testSubjectList);
	}
}