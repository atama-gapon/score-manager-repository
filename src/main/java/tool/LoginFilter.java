package tool;

import java.io.IOException;

import bean.Staff;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * 全リクエストに対してセッションの認証状態を検証し、未ログインユーザーの不正アクセスを遮断する認証制御フィルター
 */
@WebFilter(urlPatterns = { "/*" })
public class LoginFilter implements Filter {

	// フィルターの初期化処理を実行する
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 特殊な初期化処理が必要ないため空メソッドのまま維持
	}

	// リクエストのセッション認証状態を判定し、未ログイン時はログイン画面へリダイレクト、ログイン時は処理を継続する
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();

		// リクエストされたサーブレットパスを取得（例：/scoremanager/main/Menu.action）
		String path = req.getServletPath();

		// 1. 認証チェックを除外するパスの判定（ログイン関連、および画像・CSS・JSなどの静的ファイル）
		if (path.contains("Login.action") ||
				path.contains("LoginExecute.action") ||
				path.startsWith("/css/") ||
				path.startsWith("/js/") ||
				path.startsWith("/images/")) {

			// 除外対象のパスは認証チェックをスキップして後続処理へチェーン
			chain.doFilter(request, response);
			return;
		}

		// 2. セッションからログインユーザー情報を取得
		Staff staff = (Staff) session.getAttribute("user");

		// 3. 認証状態の判定によるアクセス制御
		if (staff == null) {
			// 未ログイン：セッションタイムアウトのパラメータを付与してログイン画面へ強制リダイレクト
			res.sendRedirect(req.getContextPath() + "/scoremanager/Login.action?timeout=true");
		} else {
			// ログイン済み：リクエストスコープに教員情報を設定し、後続のフロントコントローラーへ進む
			req.setAttribute("staff", staff);
			chain.doFilter(request, response);
		}
	}

	// フィルターの破棄処理を実行する
	@Override
	public void destroy() {
		// 特殊な解放処理が必要ないため空メソッドのまま維持
	}
}