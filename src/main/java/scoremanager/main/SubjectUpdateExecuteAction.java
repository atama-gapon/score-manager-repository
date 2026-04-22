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

public class SubjectUpdateExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		School school = teacher.getSchool();
		
		// 入力された値をDBに保存する
		String cd = req.getParameter("cd");
		String name = req.getParameter("name");
		
		//エラーメッセージを出せるように(追加)
		Map<String, String> errors = new HashMap<>();

		SubjectDao sDao = new SubjectDao();
		Subject subject = sDao.get(cd, school);
		
		//科目が更新中に削除されたときの処理(追加)
		if (subject == null) {
			errors.put("cd_length", "科目が存在していません");
			//エラーが起きた際にデータを映す。
			Subject vSub = new Subject();
		    vSub.setCd(cd);
		    vSub.setName(name);
		    vSub.setSchool(school);
		    
		    req.setAttribute("errors", errors);
		    req.setAttribute("subject", vSub);

			req.getRequestDispatcher("subject_update.jsp").forward(req, res);
			return;
		}
		
		subject.setCd(cd);
		subject.setName(name);
		subject.setSchool(school);
		sDao.save(subject);
		req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
	}

}
