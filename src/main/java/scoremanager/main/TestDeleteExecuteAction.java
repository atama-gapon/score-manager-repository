package scoremanager.main;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestDeleteExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		School school = teacher.getSchool();
		
		
		String studentNo = req.getParameter("student_no");
		String subjectCd = req.getParameter("subject_cd");
		int num = Integer.parseInt(req.getParameter("num"));
		

		
		StudentDao sDao = new StudentDao();
		Student student = sDao.get(studentNo);
		
		Subject subject = new Subject();
		subject.setCd(subjectCd);

		
		TestDao tDao = new TestDao();
		Test test = tDao.get(student, subject, school, num);

		if (test != null) {

	        tDao.delete(test);

		}
		req.setAttribute("student_no", studentNo);
		

		req.getRequestDispatcher("test_delete_done.jsp").forward(req, res);
	}
}
