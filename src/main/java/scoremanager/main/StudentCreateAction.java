package scoremanager.main;

import java.util.List;

import bean.School;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentCreateAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
// セッションのユーザーデータを取得
//		【テスト環境の処理】
//		HttpSession session = req.getSession();
//		Teacher teacher = (Teacher)session.getAttribute("user");
//		School school = teacher.getSchool()
		School school = new School();
		school.setCd("oom");
		school.setName("テスト：oom");
		// 【/テスト環境の処理】
		
		// 【本番環境の処理】
		// HttpSession session = req.getSession();
		// Teacher teacher = (Teacher)session.getAttribute("user");
		// School school = teacher.getSchool()
		// 【/本番環境の処理】
		
		// 【セッションのユーザーデータから、ユーザーが所属している学校のクラス一覧用データを取得】
		
		ClassNumDao cNumDao = new ClassNumDao();
//				【テスト環境の処理】
		List<String> classNumSet = cNumDao.filter(school);
//				【/テスト環境の処理】

		// 【本番環境の処理】
		// ログインユーザーの学校コードをもとにクラスの一覧を取得
//				List<String> list = cNumDao.filter(school);
		// 【/本番環境の処理】
		
		req.getRequestDispatcher("subject_create.jsp").forward(req, res);
	}
}
