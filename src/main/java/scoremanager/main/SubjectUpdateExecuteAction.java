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

public class SubjectUpdateExecuteAction extends Action {

	// 入力された科目変更情報のバリデーションを行い、問題がなければデータベースを更新する
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
		if (name == null || name.isEmpty()) {
			errors.put("name", "科目名を入力してください");
		}

		Subject subject = null;
		if (cd != null && !cd.isEmpty()) {
			subject = subjectDao.get(cd, school);
			if (subject == null) {
				errors.put("cd", "該当する科目が存在しません。既に削除された可能性があります。");
			}
		} else {
			errors.put("cd", "科目コードが不正です");
		}

		// エラーがある場合は入力画面へ戻す
		if (!errors.isEmpty()) {
			// 画面表示用のデータを準備
			req.setAttribute("cd", cd);
			req.setAttribute("name", name);
			req.setAttribute("errors", errors);
			// 変更画面（SubjectUpdateAction）側のDB再取得をスキップさせるフラグ
			req.setAttribute("submitted", "true");

			req.getRequestDispatcher("SubjectUpdate.action").forward(req, res);
			return;
		}

		// DBへ反映
		if (subject != null) {
			subject.setCd(cd);
			subject.setName(name);
			subject.setSchool(school);

			subjectDao.save(subject);
		}

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/SubjectUpdateDone.action");
	}
}