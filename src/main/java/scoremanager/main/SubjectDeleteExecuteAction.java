package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.School;
import bean.Staff;
import bean.Subject;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

	// 選択された科目情報のバリデーションを行い、問題がなければデータベースから削除する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();
		String cd = req.getParameter("cd");

		// インスタンス化
		TestDao testDao = new TestDao();
		Map<String, String> errors = new HashMap<>();

		// バリデーション
		if (cd == null || cd.isEmpty()) {
			req.setAttribute("error", "削除対象の科目コードが不正です");
			req.getRequestDispatcher("SubjectList.action").forward(req, res);
			return;
		}

		if (testDao.hasTestInSubject(school, cd)) {
			errors.put("cd", "この科目を用いている成績が存在しているため削除できません");
		}

		// エラーがある場合は入力画面へ戻す
		if (!errors.isEmpty()) {
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("SubjectDelete.action").forward(req, res);
			return;
		}

		// DBへ反映
		SubjectDao subjectDao = new SubjectDao();
		Subject subject = new Subject();
		subject.setCd(cd);
		subject.setSchool(school);

		// 科目の削除処理を実行
		subjectDao.delete(subject);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/SubjectDeleteDone.action");
	}
}