package scoremanager;

import bean.Staff;
import dao.SchoolDao;
import dao.StaffDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;
import tool.PasswordHasher;

public class LoginExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String schoolCd = req.getParameter("school_cd");
		String no = req.getParameter("no");
		String password = req.getParameter("password");

		StaffDao staffDao = new StaffDao();

		String storedPasswordHash = staffDao.findPasswordHashByStaffNo(no, schoolCd);

		if (storedPasswordHash == null || storedPasswordHash.isEmpty()
				|| !PasswordHasher.verify(password, storedPasswordHash)) {
			req.setAttribute("message", "ログインに失敗しました。学校コードまたは職員番号またはパスワードが正しくありません");
			req.setAttribute("school_cd", schoolCd);
			req.setAttribute("no", no);
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/index.jsp").forward(req, res);
			return;
		}

		Staff staff = staffDao.get(no, new SchoolDao().get(schoolCd));
		staff.setAuthenticated(true);

		// ユーザーデータをセッションに格納
		HttpSession session = req.getSession();
		session.setAttribute("user", staff);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/Menu.action");
	}
}
