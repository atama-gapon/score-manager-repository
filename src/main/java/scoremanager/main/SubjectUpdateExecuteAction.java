package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.School;
import bean.Staff;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		String cd = req.getParameter("cd");
		String name = req.getParameter("name");

		Map<String, String> errors = new HashMap<>();

		SubjectDao sDao = new SubjectDao();
		Subject subject = sDao.get(cd, school);

		// 【変更中に別画面から対象の科目が削除された場合】
		if (subject == null) {
			errors.put("subject_exist", "科目が存在していません");
			subject = new Subject();
			subject.setCd(cd);
			subject.setName(name);
			subject.setSchool(school);
			req.setAttribute("errors", errors);
			req.setAttribute("cd", cd);
			req.setAttribute("name", name);
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/subject_update.jsp").forward(req, res);
			return;
		}

		// 【DBに科目を保存する】
		subject.setCd(cd);
		subject.setName(name);
		subject.setSchool(school);
		sDao.save(subject);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/subject_update_done.jsp").forward(req, res);
	}
}