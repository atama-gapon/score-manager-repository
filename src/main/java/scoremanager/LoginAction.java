package scoremanager;

import bean.Teacher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class LoginAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(false);
		
		if (session != null) {
		    Teacher teacher = (Teacher) session.getAttribute("user");
			// ログイン済みなら
			if (teacher != null ) {
				res.sendRedirect("main/Menu.action");
				return;
			}
		}
		
		res.sendRedirect(req.getContextPath() + "/scoremanager/login.jsp");
	}
}