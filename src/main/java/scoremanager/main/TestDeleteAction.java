package scoremanager.main;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestDeleteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//セッションからログインユーザーのデータを取得
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		School school = teacher.getSchool();
		
		//パラメーターを取得
		//削除対象のキー
		String studentNo = req.getParameter("studentNo");
		String subjectCd = req.getParameter("subjectCd");
		
		int no = Integer.parseInt(req.getParameter("num"));
		
		
		StudentDao studentDao = new StudentDao();
		Student student = studentDao.get(studentNo);
		
		SubjectDao subjectDao = new SubjectDao();
		Subject subject = subjectDao.get(subjectCd, school);
		
		
		TestDao testDao = new TestDao();
		Test test = testDao.get(student, subject, school, no);

		req.setAttribute("test", test);
		req.setAttribute("subjectCd", subjectCd);
		req.setAttribute("no", no);
		req.getRequestDispatcher("test_delete.jsp").forward(req, res);
	}
}