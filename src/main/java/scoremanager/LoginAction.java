package scoremanager;

import bean.Staff;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class LoginAction extends Action {

	// ログイン状態をチェックし、未ログインまたはタイムアウト時はログイン画面のJSPへフォワード遷移する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得（セッションの存在確認）
		HttpSession session = req.getSession(false);

		if (session != null) {
			Staff staff = (Staff) session.getAttribute("user");
			// ログイン済みであればメインメニューへ自動的にリダイレクト
			if (staff != null) {
				res.sendRedirect(req.getContextPath() + "/scoremanager/main/Menu.action");
				return;
			}
		}

		String timeout = req.getParameter("timeout");

		// 画面表示用のデータを準備
		prepareViewData(req, timeout);

		// ログイン画面のJSPへリクエストを転送
		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/index.jsp").forward(req, res);
	}

	private void prepareViewData(HttpServletRequest req, String timeout) throws Exception {
		// timeoutが null でもなく、空でもなく、かつ確実に "true" という文字列の時だけメッセージを出す
		if (timeout != null && timeout.equalsIgnoreCase("true")) {
			req.setAttribute("message", "セッションが終了しました。再度ログインをお願いします。");
		}
	}
}