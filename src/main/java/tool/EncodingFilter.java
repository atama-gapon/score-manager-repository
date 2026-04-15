package tool;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

// @WebFilter：フィルター専用のアノテーション
// "/*" ⇒ 全てのサーブレット時に実行
// "chapter10/*" ⇒ chapter10以下の全てのサーブレット時に実行
@WebFilter(urlPatterns = {"/*"})
public class EncodingFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain
	) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		chain.doFilter(request, response);
	}
	
	public void init(FilterConfig filterConfig) {}
		
	public void destroy() {}
}
