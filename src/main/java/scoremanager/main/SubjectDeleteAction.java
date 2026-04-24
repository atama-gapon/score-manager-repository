package scoremanager.main;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
// 【セッションからユーザーデータを取得】
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		School school = teacher.getSchool();
		
		String cd = req.getParameter("cd");
		
// 【科目の詳細データを取得】
		SubjectDao sDao = new SubjectDao();
		Subject subject = sDao.get(cd, school);
		
		req.setAttribute("cd", cd);
		req.setAttribute("name", subject.getName());
		req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
	}
}
