package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListStudentExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッションのユーザーデータを取得
        // 【テスト環境の処理】
         HttpSession session = req.getSession();
         Teacher teacher = (Teacher)session.getAttribute("user");
         School school = teacher.getSchool();
//        School school = new School();
//        school.setCd("oom");
//        school.setName("テスト：oom");
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
		
		// ユーザーデータからユーザーが所属している学校のクラスデータを取得
				ClassNumDao cDao = new ClassNumDao();
		// 学校コードに合致するデータを取得
				List<String> cNumSet = cDao.filter(school);
		// ユーザーデータからユーザーが所属している学校の科目データを取得
				SubjectDao subjectDao = new SubjectDao();
		// 科目コードに合致するデータを取得
				List<Subject> subjects =  subjectDao.filter(school);
		// 入学年度リストを生成
				LocalDate todaysDate = LocalDate.now();
				int year = todaysDate.getYear();
				// 10年前から1年後までをリストに追加
				List<Integer> entYearSet = new ArrayList<>();
				for (int i=year-10; i<=year+1; i++) {
					entYearSet.add(i);
				}
				
		// 収集したデータをリクエストに設定
				req.setAttribute("class_num_set", cNumSet);
				req.setAttribute("subjects", subjects);
				req.setAttribute("ent_year_set", entYearSet);
		
// 入力欄に取得したデータを初期値としてセットし、一覧で表示する
		req.setAttribute("student", student);
		req.setAttribute("testListStudents", testListStudents);
		req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
	}
}