package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class LogoutAction extends Action {

	// セッションからログイン情報を削除し、ログアウト画面のJSPへフォワード遷移する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得（セッションの取得）
		HttpSession session = req.getSession(false);

		// セッションが存在する場合はログインユーザーデータを削除し、セッション自体を無効化
		if (session != null) {
			session.removeAttribute("user");
			session.invalidate();
		}

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/logout.jsp").forward(req, res);
	}
}