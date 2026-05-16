package scoremanager.main;

import bean.School;
import bean.Staff;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectDeleteAction extends Action {

	// 削除対象の科目情報を取得し、科目削除確認画面のJSPへフォワード遷移する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();
		String cd = req.getParameter("cd");

		// 画面表示用のデータを準備
		prepareViewData(req, school, cd);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/subject_delete.jsp").forward(req, res);
	}

	private void prepareViewData(HttpServletRequest req, School school, String cd) throws Exception {
		if (cd != null && !cd.isEmpty()) {
			SubjectDao subjectDao = new SubjectDao();
			Subject subject = subjectDao.get(cd, school);

			if (subject != null) {
				req.setAttribute("cd", subject.getCd());
				req.setAttribute("name", subject.getName());
			}
		}
	}
}