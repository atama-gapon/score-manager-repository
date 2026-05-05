package scoremanager;

import bean.Staff;
import dao.StaffDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class LoginExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
// 入力されたNO,PWを元に認証
		String no = req.getParameter("no");
		String password = req.getParameter("password");
		
// NO,PWが合致するデータを取得
		StaffDao staffDao = new StaffDao();
		Staff staff = staffDao.login(no, password);
		
// NOかPWかのいずれかが正しくない場合
		if (staff == null) {
			req.setAttribute("message", "ログインに失敗しました。職員番号またはパスワードが正しくありません。");
			req.setAttribute("no", no);
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/login.jsp").forward(req, res);
			return;
		}
		
// ユーザーデータをセッションに格納
		HttpSession session = req.getSession();
		staff.setAuthenticated(true);
		session.setAttribute("user", staff);
		
		res.sendRedirect("main/Menu.action");
	}
}