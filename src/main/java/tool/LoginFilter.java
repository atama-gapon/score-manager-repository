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

@WebFilter(urlPatterns = { "/*" })
public class LoginFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();

		// リクエストされたパスを取得（例：/scoremanager/main/Menu.action）
		String path = req.getServletPath();

		// 1. ログインチェックを除外するパス
		// ログイン画面表示、ログイン実行
		if (path.contains("Login.action") || path.contains("LoginExecute.action")) {
			chain.doFilter(request, response);
			return;
		}

		// 2. セッションからログインユーザー情報を取得
		Staff staff = (Staff) session.getAttribute("user");

		// 3. 判定
		if (staff == null) {
			// 未ログイン：ログイン画面へリダイレクト
			res.sendRedirect(req.getContextPath() + "/scoremanager/Login.action?timeout=true");
		} else {
			// ログイン済み：次の処理（フロントコントローラーなど）へ進む
			req.setAttribute("staff", staff);
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig filterConfig) {
	}

	public void destroy() {
	}
}