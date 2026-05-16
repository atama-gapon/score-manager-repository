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

		// インスタンス化
		StudentDao studentDao = new StudentDao();
		ClassNumDao classNumDao = new ClassNumDao();
		Map<String, String> errors = new HashMap<>();
		List<Student> studentList = new ArrayList<>();

		// 入力値を取得
		String entYearStr = req.getParameter("ent_year");
		if (entYearStr == null)
			entYearStr = "";
		String classNum = req.getParameter("class_num");
		if (classNum == null)
			classNum = "";
		String isAttendStr = req.getParameter("is_attend");
		String searched = req.getParameter("searched");

		boolean isEntYearSelected = !entYearStr.isEmpty();
		boolean isClassNumSelected = !classNum.isEmpty();
		boolean isAttend = isAttendStr != null;

		int entYear = 0;
		if (isEntYearSelected) {
			entYear = Integer.parseInt(entYearStr);
		}

		// データの準備
		prepareViewData(req, school, studentDao, classNumDao);
		req.setAttribute("ent_year", entYearStr);
		req.setAttribute("class_num", classNum);
		req.setAttribute("is_attend", isAttendStr);

		if (!"true".equals(searched) || (entYearStr.isEmpty() && classNum.isEmpty())) {
			studentList = studentDao.filter(school, isAttend);
		} else {
			// バリデーション
			if (isClassNumSelected && !isEntYearSelected) {
				errors.put("search", "クラスを指定する場合は入学年度も指定してください");
			}

			if (errors.isEmpty()) {
				studentList = findStudents(school, entYear, classNum, isEntYearSelected, isClassNumSelected, isAttend, studentDao);
			} else {
				studentList = studentDao.filter(school, isAttend);
				req.setAttribute("errors", errors);
			}
		}

		req.setAttribute("student_list", studentList);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/student_list.jsp").forward(req, res);
	}

	private void prepareViewData(HttpServletRequest req, School school, StudentDao studentDao, ClassNumDao classNumDao) throws Exception {
		req.setAttribute("class_num_list", classNumDao.filter(school));
		req.setAttribute("ent_year_list", studentDao.getEntYearList(school));
	}

	private List<Student> findStudents(School school, Integer entYear, String classNum, boolean isEntYearSelected, boolean isClassNumSelected, boolean isAttend, StudentDao studentDao) throws Exception {
		if (isClassNumSelected) {
			return studentDao.filter(school, entYear, classNum, isAttend);
		}

		return studentDao.filter(school, entYear, isAttend);
	}
}
