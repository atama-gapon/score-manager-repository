package scoremanager.main;
 
import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;
 
public class SubjectDeleteExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
// 【セッションからユーザーデータを取得】
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		School school = teacher.getSchool();
		
		String cd = req.getParameter("cd");
		
// 【DBから科目を削除する】
		SubjectDao sDao = new SubjectDao();
		Subject subject = new Subject();
		subject.setCd(cd);
		subject.setSchool(school);
		sDao.delete(subject);
		req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
	}
}