package scoremanager.main;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		School school = teacher.getSchool();
		
		String cd = req.getParameter("cd");
		SubjectDao sDao = new SubjectDao();
		Subject subject = sDao.get(cd, school);
		req.setAttribute("subject", subject);
		req.getRequestDispatcher("subject_update.jsp").forward(req, res);
	}
}