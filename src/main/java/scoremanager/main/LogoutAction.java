package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class LogoutAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
// セッションからユーザーデータを削除
		HttpSession session = req.getSession(true);
		session.removeAttribute("user");
		
		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/logout.jsp").forward(req, res);
	}
}