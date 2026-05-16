package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.School;
import bean.Staff;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

	// 入力された科目新規登録情報のバリデーションを行い、問題がなければデータベースに登録する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		String cd = req.getParameter("cd");
		String name = req.getParameter("name");

		// インスタンス化
		SubjectDao subjectDao = new SubjectDao();
		Map<String, String> errors = new HashMap<>();

		// バリデーション
		if (cd == null || cd.isEmpty()) {
			errors.put("cd", "科目コードを入力してください");
		} else if (cd.length() != 3) {
			errors.put("cd", "科目コードは3文字で入力してください");
		}

		if (name == null || name.isEmpty()) {
			errors.put("name", "科目名を入力してください");
		}

		if (!errors.containsKey("cd") && cd != null) {
			Subject subject = subjectDao.get(cd, school);
			if (subject != null) {
				errors.put("cd", "科目コードが重複しています");
			}
		}

		// エラーがある場合は入力画面へ戻す
		if (!errors.isEmpty()) {
			// 画面表示用のデータを準備
			req.setAttribute("cd", cd);
			req.setAttribute("name", name);
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("SubjectCreate.action").forward(req, res);
			return;
		}

		// DBへ反映
		Subject subject = new Subject();
		subject.setCd(cd);
		subject.setName(name);
		subject.setSchool(school);

		subjectDao.save(subject);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/SubjectCreateDone.action");
	}
}