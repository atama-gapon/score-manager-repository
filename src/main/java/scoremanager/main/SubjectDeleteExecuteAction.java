package scoremanager.main;

import bean.School;
import bean.Staff;
import bean.Subject;
import dao.SubjectDao;
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

		// バリデーション（不正なパラメータによる誤削除・クラッシュ防止）
		if (cd == null || cd.isEmpty()) {
			req.setAttribute("error", "削除対象の科目コードが不正です。");
			req.getRequestDispatcher("SubjectList.action").forward(req, res);
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