package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Staff;
import bean.Student;
import bean.TestStudentList;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestStudentListDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestStudentListExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		// インスタンス化
		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();
		StudentDao studentDao = new StudentDao();
		List<TestStudentList> testStudentList = new ArrayList<>();

		// 入力値を取得
		String targetNo = req.getParameter("f4");

		// 入力された学生番号の学生の成績データを取得する
		Student student = studentDao.get(targetNo);

		if (!(student == null)) {
			TestStudentListDao testListStudentDao = new TestStudentListDao();
			testStudentList = testListStudentDao.filter(student);
		}

		// データの準備
		prepareViewData(req, school, studentDao, subjectDao, classNumDao);
		req.setAttribute("f4", targetNo);
		req.setAttribute("student", student);
		req.setAttribute("test_student_list", testStudentList);
		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/test_list_student.jsp").forward(req, res);
	}

	private void prepareViewData(HttpServletRequest req, School school, StudentDao studentDao, SubjectDao subjectDao, ClassNumDao classNumDao) throws Exception {
		req.setAttribute("class_num_list", classNumDao.filter(school));
		req.setAttribute("subject_list", subjectDao.filter(school));
		req.setAttribute("ent_year_list", studentDao.getEntYearList(school));
	}
}
