package scoremanager.main;

import bean.School;
import bean.Staff;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectDeleteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		String cd = req.getParameter("cd");

		// 【科目の詳細データを取得】
		SubjectDao sDao = new SubjectDao();
		Subject subject = sDao.get(cd, school);

		req.setAttribute("cd", cd);
		req.setAttribute("name", subject.getName());
		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/subject_delete.jsp").forward(req, res);
	}
}
