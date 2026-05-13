package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Staff;
import bean.Student;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentListAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		// 初期化
		ClassNumDao classNumDao = new ClassNumDao();
		StudentDao studentDao = new StudentDao();
		List<Student> studentSet = new ArrayList<>();
		Map<String, String> errors = new HashMap<>();
		boolean isClassNumSelected = false;
		boolean isEntYearSelected = false;
		boolean isAttend = false;

		// ゲット
		String entYearStr = req.getParameter("ent_year");
		String classNum = req.getParameter("class_num");
		String isAttendStr = req.getParameter("is_attend");

		List<String> classNumSet = classNumDao.filter(school);
		List<Integer> entYearSet = studentDao.getEntYearList(school);

		req.setAttribute("class_num_set", classNumSet);
		req.setAttribute("ent_year_set", entYearSet);

		// 初回アクセス
		if (classNum == null || entYearStr == null) {
			studentSet = studentDao.filter(school, isAttend);
			req.setAttribute("student_set", studentSet);
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/student_list.jsp").forward(req, res);
			return;
		}

		int entYear = Integer.parseInt(entYearStr);

		if (isAttendStr != null) {
			isAttend = true;
		}

		// セット
		req.setAttribute("ent_year", entYear);
		req.setAttribute("class_num", classNum);
		req.setAttribute("is_attend", isAttend);

		// バリデーション
		// 選択されたクラスがうちのクラスのなかか
		// 選択された年度がうちの年度のなかか
		// こんな不正な入力に対するバリデーションは、どこまで対応するべきか

		if (!classNum.equals("0")) {
			isClassNumSelected = true;
		}

		if (entYear != 0) {
			isEntYearSelected = true;
		}

		if (isClassNumSelected != isEntYearSelected) {
			errors.put("ent_year", "クラスを指定する場合は入学年度も指定してください");
		}

		// 検索条件
		if (isClassNumSelected && isEntYearSelected) {
			studentSet = studentDao.filter(school, entYear, classNum, isAttend);
		} else if (isEntYearSelected) {
			studentSet = studentDao.filter(school, entYear, isAttend);
		} else {
			studentSet = studentDao.filter(school, isAttend);
		}

		req.setAttribute("errors", errors);
		req.setAttribute("student_set", studentSet);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/student_list.jsp").forward(req, res);
	}
}
