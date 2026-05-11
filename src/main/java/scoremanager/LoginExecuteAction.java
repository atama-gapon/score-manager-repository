package scoremanager;

import bean.Staff;
import dao.StaffDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class LoginExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String schoolCd = req.getParameter("school_cd");
		String no = req.getParameter("no");
		String password = req.getParameter("password");

		StaffDao staffDao = new StaffDao();
		Staff staff = staffDao.login(schoolCd, no, password);

		if (staff == null) {
			req.setAttribute("message", "ログインに失敗しました。職員番号またはパスワードが正しくありません。");
			req.setAttribute("no", no);
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/index.jsp").forward(req, res);
			return;
		}

		// ユーザーデータをセッションに格納
		HttpSession session = req.getSession();
		staff.setAuthenticated(true);
		session.setAttribute("user", staff);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/Menu.action");
	}
}