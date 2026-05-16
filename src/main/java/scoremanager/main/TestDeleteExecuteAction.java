package scoremanager.main;

import bean.School;
import bean.Staff;
import bean.Student;
import bean.Subject;
import bean.Test;
import dao.StudentDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestDeleteExecuteAction extends Action {

	// 選択されたテスト成績情報のバリデーションを行い、問題がなければデータベースから削除する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		String studentNo = req.getParameter("student_no");
		String subjectCd = req.getParameter("subject_cd");
		String numStr = req.getParameter("num");

		// バリデーション
		if (studentNo == null || studentNo.isEmpty() ||
				subjectCd == null || subjectCd.isEmpty() ||
				numStr == null || numStr.isEmpty()) {
			req.setAttribute("error", "削除対象の指定が不正です。");
			req.getRequestDispatcher("TestList.action").forward(req, res);
			return;
		}

		int num = 0;
		try {
			num = Integer.parseInt(numStr);
		} catch (NumberFormatException e) {
			req.setAttribute("error", "テスト回数の指定が不正です。");
			req.getRequestDispatcher("TestList.action").forward(req, res);
			return;
		}

		// DBへ反映
		StudentDao studentDao = new StudentDao();
		Student student = studentDao.get(studentNo);

		Subject subject = new Subject();
		subject.setCd(subjectCd);

		// テストDAOを使って削除対象が存在するかチェック
		TestDao testDao = new TestDao();
		Test test = testDao.get(student, subject, school, num);

		if (test != null) {
			// データが存在した場合、削除を実行
			testDao.delete(test);
		}

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/TestDeleteDone.action");
	}
}