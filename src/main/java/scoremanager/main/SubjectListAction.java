package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
// 【セッションからユーザーデータを取得】
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		School school = teacher.getSchool();
		 
// 【セッションのユーザーデータから、ユーザーが所属している学校の科目一覧用データを取得】
		SubjectDao sDao = new SubjectDao();
		List<Subject> subjectSet = sDao.filter(school);

		req.setAttribute("subjects", subjectSet);
		req.getRequestDispatcher("subject_list.jsp").forward(req, res);
	}
}