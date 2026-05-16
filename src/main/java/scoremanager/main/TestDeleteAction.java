package scoremanager.main;

import bean.School;
import bean.Staff;
import bean.Student;
import bean.Subject;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestDeleteAction extends Action {

	// 削除対象のテスト成績情報を取得し、テスト成績削除確認画面のJSPへフォワード遷移する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		String studentNo = req.getParameter("studentNo");
		String subjectCd = req.getParameter("subjectCd");
		String numStr = req.getParameter("num");

		// 画面表示用のデータを準備
		prepareViewData(req, school, studentNo, subjectCd, numStr);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/test_delete.jsp").forward(req, res);
	}

	private void prepareViewData(HttpServletRequest req, School school, String studentNo, String subjectCd, String numStr) throws Exception {
		// 安全対策：必須パラメータのNull・空文字チェック
		if (studentNo == null || studentNo.isEmpty() ||
				subjectCd == null || subjectCd.isEmpty() ||
				numStr == null || numStr.isEmpty()) {
			return;
		}

		// 安全対策：回数の数値変換チェック（500エラー防止）
		int no = 0;
		try {
			no = Integer.parseInt(numStr);
		} catch (NumberFormatException e) {
			return; // 不正な数値の場合は処理を中断
		}

		// 各種DAOの生成とデータの引き込み（DAOの隠蔽・局所化）
		StudentDao studentDao = new StudentDao();
		Student student = studentDao.get(studentNo);

		SubjectDao subjectDao = new SubjectDao();
		Subject subject = subjectDao.get(subjectCd, school);

		// 学生と科目が存在する場合のみ、テスト成績を取得
		if (student != null && subject != null) {
			TestDao testDao = new TestDao();
			Test test = testDao.get(student, subject, school, no);

			if (test != null) {
				req.setAttribute("test", test);
				req.setAttribute("subjectCd", subjectCd);
				req.setAttribute("no", no);
			}
		}
	}
}