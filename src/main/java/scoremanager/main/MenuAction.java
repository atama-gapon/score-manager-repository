package scoremanager.main;

import bean.School;
import bean.Teacher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class MenuAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		 HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
        if (teacher == null) {
            teacher = (Teacher)session.getAttribute("loginUser"); // ← ログイン時の名前に合わせる
            session.setAttribute("user", teacher);
        }

        // ★ 認証済みフラグ
        teacher.setAuthenticated(true);

        School school = teacher.getSchool();

        session.setAttribute("school", school);
		
		
		req.getRequestDispatcher("menu.jsp").forward(req, res);
	}
}
