package scoremanager.main;

import bean.School;
import bean.Staff;
import bean.Student;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
import tool.StudentUtil;

public class StudentUpdateAction extends Action {

	// 選択された学生情報を取得し、変更画面のJSPへフォワード遷移する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();
		String targetNo = req.getParameter("no");
		String submitted = req.getParameter("submitted");

		StudentDao studentDao = new StudentDao();

		// 初期表示時のみ（実行Actionからのエラー復帰時以外）、対象の学生情報をDBから取得
		if (!"true".equals(submitted)) {
			if (targetNo != null && !targetNo.isEmpty()) {
				Student student = studentDao.get(targetNo);
				req.setAttribute("student", student);
			}
		}

		// 画面表示用のデータを準備
		prepareViewData(req, school);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/student_update.jsp").forward(req, res);
	}

	private void prepareViewData(HttpServletRequest req, School school) throws Exception {
		ClassNumDao classNumDao = new ClassNumDao();

		req.setAttribute("class_num_list", classNumDao.filter(school));
		req.setAttribute("ent_year_list", StudentUtil.createEntYearList());
	}
}