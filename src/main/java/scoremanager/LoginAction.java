package scoremanager;

import bean.Staff;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class LoginAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(false);

		if (session != null) {
			Staff staff = (Staff) session.getAttribute("user");
			// ログイン済みなら
			if (staff != null) {
				res.sendRedirect(req.getContextPath() + "/scoremanager/main/Menu.action");
				return;
			}
		}

		String timeout = req.getParameter("timeout");

		if (timeout != null) {
			req.setAttribute("message", "セッションが終了しました。再度ログインをお願いします。");
		}

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/index.jsp").forward(req, res);
	}
}
