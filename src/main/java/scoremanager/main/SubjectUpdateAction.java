package scoremanager.main;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectUpdateAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String cd = req.getParameter("cd");
		// 【科目コードと学校コードに合致するデータを取得】
		// 【テスト環境の処理】
		School school = new School();
		school.setCd("oom");
		school.setName("テスト：oom");
		// 【/テスト環境の処理】
		
		// 【本番環境の処理】
//				HttpSession session = req.getSession();
//				Teacher teacher = (Teacher)session.getAttribute("user");
//				School school = teacher.getSchool()
		// 【/本番環境の処理】

		SubjectDao sDao = new SubjectDao();
		Subject subject = sDao.get(cd, school);
		req.setAttribute("subject", subject);
		req.getRequestDispatcher("subject_update.jsp").forward(req, res);
		
	}
}