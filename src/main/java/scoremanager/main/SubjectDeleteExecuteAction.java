package scoremanager.main;

import bean.School;
import bean.Staff;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		String cd = req.getParameter("cd");

		// 【DBから科目を削除する】
		SubjectDao sDao = new SubjectDao();
		Subject subject = new Subject();
		subject.setCd(cd);
		subject.setSchool(school);
		sDao.delete(subject);
		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/subject_delete_done.jsp").forward(req, res);
	}
}