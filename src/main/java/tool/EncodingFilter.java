package tool;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

/**
 * 全リクエストおよびレスポンスの文字エンコーディングをUTF-8に統一する共通フィルター
 */
@WebFilter(urlPatterns = { "/*" })
public class EncodingFilter implements Filter {

	// フィルターの初期化処理を実行する
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 特殊な初期化処理が必要ないため空メソッドのまま維持
	}

	// リクエストおよびレスポンスにUTF-8エンコーディングを適用し、後続の処理にチェーンする
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		chain.doFilter(request, response);
	}

	// フィルターの破棄処理を実行する
	@Override
	public void destroy() {
		// 特殊な解放処理が必要ないため空メソッドのまま維持
	}
}