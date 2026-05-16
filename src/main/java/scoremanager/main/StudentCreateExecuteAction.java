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
import tool.Validator;

public class StudentCreateExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		// インスタンス化
		StudentDao studentDao = new StudentDao();
		Map<String, String> errors = new HashMap<>();

		// リクエストパラメータの取得
		String entYear = req.getParameter("ent_year");
		String no = req.getParameter("no");
		String name = req.getParameter("name");
		String classNum = req.getParameter("class_num");
		String isAttendStr = req.getParameter("is_attend");

		boolean isAttend = false;
		if (isAttendStr != null) {
			isAttend = true;
		}

		Student student = new Student();
		student.setNo(no);
		student.setName(name);
		student.setEntYear(Integer.parseInt(entYear));
		student.setClassNum(classNum);
		student.setAttend(isAttend);
		student.setSchool(school);

		// バリデーション
		Validator.required(entYear, "ent_year", "入学年度を選択してください", errors);

		if (studentDao.get(no) != null) {
			errors.put("no", "学生番号が重複しています");
		}

		// エラーがある場合は入力画面へ戻す
		if (!errors.isEmpty()) {
			req.setAttribute("student", student);
			req.setAttribute("errors", errors);

			req.getRequestDispatcher("StudentCreate.action").forward(req, res);
			return;
		}

		// 学生を登録
		studentDao.save(student);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/StudentCreateDone.action");
	}
}
