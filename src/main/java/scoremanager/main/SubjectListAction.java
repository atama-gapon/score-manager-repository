package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Staff;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectListAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		// 【セッションのユーザーデータから、ユーザーが所属している学校の科目一覧用データを取得】
		SubjectDao sDao = new SubjectDao();
		List<Subject> subjectList = sDao.filter(school);

		req.setAttribute("subject_list", subjectList);
		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/subject_list.jsp").forward(req, res);
	}
}
