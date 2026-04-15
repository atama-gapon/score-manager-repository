package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectListAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
// 【セッションからユーザーデータを取得】
		// 【テスト環境の処理】
		School school = new School();
		school.setCd("oom");
		school.setName("テスト：oom");
		// 【/テスト環境の処理】
		
		// 【本番環境の処理】
		// HttpSession session = req.getSession();
		// Teacher teacher = (Teacher)session.getAttribute("user");
		// School school = teacher.getSchool()
		// 【/本番環境の処理】

// 【セッションのユーザーデータから、ユーザーが所属している学校の科目一覧用データを取得】
// 【学校コードに合致する科目の一覧を取得】
		SubjectDao sDao = new SubjectDao();
		List<Subject> subjectSet = sDao.filter(school);
		req.setAttribute("subjects", subjectSet);
		req.getRequestDispatcher("subject_list.jsp").forward(req, res);
	}
}