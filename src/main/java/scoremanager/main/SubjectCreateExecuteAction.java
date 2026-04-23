package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
// 【セッションからユーザーデータを取得】
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		School school = teacher.getSchool();
		
		String cd = req.getParameter("cd");
		String name = req.getParameter("name");

		Map<String, String> errors = new HashMap<>();
		
// 【科目コードが3文字でなかった場合】
		if (!(cd.length() == 3)) {
			errors.put("cd_length", "科目コードは3文字で入力してください");
			req.setAttribute("errors", errors);
			req.setAttribute("cd", cd);
			req.setAttribute("name", name);
			req.getRequestDispatcher("subject_create.jsp").forward(req, res);
			return;
		}
		
// 【科目コードと学校コードに合致するデータを取得する】
		SubjectDao sDao = new SubjectDao();

		Subject subject = sDao.get(cd, school);
		
// 【DBへの書き込みを辞め、「科目コードが重複しています」と表示する】
		if (subject != null) {
			errors.put("cd_length", "科目コードが重複しています");
			//エラー時データを保持する(追加)
			errors.put("cd", cd);
			errors.put("name", name);
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("subject_create.jsp").forward(req, res);
			//エラー時に戻るように(追加)
			return;
		}
		
// 【DBに科目を保存する】
		Subject subject2 = new Subject();
		subject2.setCd(cd);
		subject2.setName(name);
		subject2.setSchool(school);
		sDao.save(subject2);
		req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
	}
}
