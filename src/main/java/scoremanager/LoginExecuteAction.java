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

	// 送信されたログイン情報を検証し、認証に成功した場合はセッションを確立してメインメニューへリダイレクトする
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得（既存セッションの確認）
		HttpSession session = req.getSession(false);

		if (session != null) {
			Staff staff = (Staff) session.getAttribute("user");
			// すでにログイン済みであればメインメニューへ自動的にリダイレクト（二重ログイン防止）
			if (staff != null) {
				res.sendRedirect(req.getContextPath() + "/scoremanager/main/Menu.action");
				return;
			}
		}

		String schoolCd = req.getParameter("school_cd");
		String no = req.getParameter("no");
		String password = req.getParameter("password");

		// DBへ反映
		StaffDao staffDao = new StaffDao();
		String storedPasswordHash = staffDao.findPasswordHashByStaffNo(no, schoolCd);

		// 認証失敗時のガード句処理
		if (storedPasswordHash == null || storedPasswordHash.isEmpty()
				|| !PasswordHasher.verify(password, storedPasswordHash)) {

			// 画面表示用のデータを準備（入力値を維持して復帰）
			req.setAttribute("message", "ログインに失敗しました。学校コードまたは職員番号またはパスワードが正しくありません");
			req.setAttribute("school_cd", schoolCd);
			req.setAttribute("no", no);
			// ログイン画面（LoginAction）側のセッション再判定等をスキップさせる制御フラグ
			req.setAttribute("submitted", "true");

			// ログインTOPコントローラー（Action）へフォワードして安全にエラー画面を表示
			req.getRequestDispatcher("Login.action").forward(req, res);
			return;
		}

		// 認証成功時：教員フルエンティティを取得して認証済みフラグを立てる
		Staff staff = staffDao.get(no, new SchoolDao().get(schoolCd));
		if (staff != null) {
			staff.setAuthenticated(true);
		}

		// セッションの新規確立とユーザーデータの格納
		session = req.getSession(true);
		session.setAttribute("user", staff);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/Menu.action");
	}
}