package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Student;
import bean.TestListStudent;
import dao.StudentDao;
import dao.TestListStudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListStudentExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッションのユーザーデータを取得
        // 【テスト環境の処理】
        // HttpSession session = req.getSession();
        // Teacher teacher = (Teacher)session.getAttribute("user");
        // School school = teacher.getSchool()
        School school = new School();
        school.setCd("oom");
        school.setName("テスト：oom");
        // 【/テスト環境の処理】
		
// 入力された学生番号の学生の成績データを取得する
		String no = req.getParameter("f4");
		StudentDao sDao = new StudentDao();
		Student student = sDao.get(no);
		List<TestListStudent> testListStudents = null;
		if (!(student == null)) {
			TestListStudentDao tDao = new TestListStudentDao();
			testListStudents = tDao.filter(student);
		}
		
// 入力欄に取得したデータを初期値としてセットし、一覧で表示する
		req.setAttribute("student", student);
		req.setAttribute("testListStudents", testListStudents);
		req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
	}
}