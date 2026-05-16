package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.School;
import bean.Staff;
import bean.Student;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

	// 入力された学生変更情報のバリデーションを行い、問題がなければデータベースを更新する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		String entYearStr = req.getParameter("ent_year");
		String no = req.getParameter("no");
		String name = req.getParameter("name");
		String classNum = req.getParameter("class_num");
		String isAttendStr = req.getParameter("is_attend");

		// インスタンス化
		StudentDao studentDao = new StudentDao();
		Map<String, String> errors = new HashMap<>();

		int entYear = 0;
		boolean isAttend = (isAttendStr != null);

		// バリデーション
		if (entYearStr != null && !entYearStr.isEmpty()) {
			try {
				entYear = Integer.parseInt(entYearStr);
			} catch (NumberFormatException e) {
				errors.put("ent_year", "入学年度は半角数字で入力してください");
			}
		} else {
			errors.put("ent_year", "入学年度を選択してください");
		}

		if (name == null || name.isEmpty()) {
			errors.put("name", "氏名を入力してください");
		}

		// エラーがある場合は入力画面へ戻す
		if (!errors.isEmpty()) {
			// 画面表示用のデータを準備
			Student student = new Student();
			student.setNo(no);
			student.setName(name);
			student.setEntYear(entYear);
			student.setClassNum(classNum);
			student.setAttend(isAttend);
			student.setSchool(school);

			req.setAttribute("student", student);
			req.setAttribute("errors", errors);
			// 変更画面（StudentUpdateAction）側の再取得をスキップさせるフラグ
			req.setAttribute("submitted", "true");

			req.getRequestDispatcher("StudentUpdate.action").forward(req, res);
			return;
		}

		// DBへ反映
		Student student = new Student();
		student.setNo(no);
		student.setName(name);
		student.setEntYear(entYear);
		student.setClassNum(classNum);
		student.setAttend(isAttend);
		student.setSchool(school);

		studentDao.save(student);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/StudentUpdateDone.action");
	}
}