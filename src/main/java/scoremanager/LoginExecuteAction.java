package scoremanager;

import bean.Teacher;
import dao.TeacherDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class LoginExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
// 入力されたID,PWを元に認証
		String id = req.getParameter("id");
		String password = req.getParameter("password");
		
// ID,PWが合致するデータを取得
		TeacherDao tDao = new TeacherDao();
		Teacher teacher = tDao.login(id, password);
		
// IDかPWかのいずれかが正しくない場合
		if (teacher == null) {
			req.setAttribute("message", "ログインに失敗しました。IDまたはパスワードが正しくありません。");
			req.setAttribute("id2", id);
			req.getRequestDispatcher("login.jsp").forward(req, res);
			return;
		}
		
// ユーザーデータをセッションに格納
		HttpSession session = req.getSession();
		session.setAttribute("user", teacher);

		res.sendRedirect("main/Menu.action");
	}
}
