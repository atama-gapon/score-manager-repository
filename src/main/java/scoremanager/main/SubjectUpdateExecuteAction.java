package scoremanager.main;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 入力された値をDBに保存する
		String cd = req.getParameter("cd");
		String name = req.getParameter("name");
		// 【科目コードと学校コードに合致するデータを取得】
		// 【テスト環境の処理】
		School school = new School();
		school.setCd("oom");
		school.setName("テスト：oom");
		// 【/テスト環境の処理】
		
		// 【本番環境の処理】
//						HttpSession session = req.getSession();
//						Teacher teacher = (Teacher)session.getAttribute("user");
//						School school = teacher.getSchool()
		// 【/本番環境の処理】
		
		SubjectDao sDao = new SubjectDao();
		Subject subject = new Subject();
		subject.setCd(cd);
		subject.setName(name);
		subject.setSchool(school);
		sDao.save(subject);
		req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
	}

}
