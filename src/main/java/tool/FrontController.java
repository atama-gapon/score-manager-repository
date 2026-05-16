package tool;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * すべての拡張子「.action」のリクエストを一括で受け取り、対応するActionクラスを動的に呼び出すフロントコントローラーサーブレット
 */
@WebServlet(urlPatterns = { "*.action" })
@MultipartConfig(maxFileSize = 10000000, maxRequestSize = 10000000, fileSizeThreshold = 10000000)
public class FrontController extends HttpServlet {

	// GETメソッドによるリクエストを受け取り、対応するアクションのビジネスロジックを実行する
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			// リクエストパスから先頭の「/」を除去し、クラス名への変換準備を行う
			String path = req.getServletPath().substring(1);
			// 拡張子「.action」を「Action」クラス名に置換し、URL階層をパッケージの「.」区切りに変換
			String name = path.replace(".a", "A").replace('/', '.');

			// デバッグ用ログ出力
			System.out.println("★ servlet path -> " + req.getServletPath());
			System.out.println("★ class name   -> " + name);

			// アクションの完全修飾クラス名を使って、動的にインスタンスを生成
			Action action = (Action) Class.forName(name).getDeclaredConstructor().newInstance();

			// 対象アクションのビジネスロジックを実行
			action.execute(req, res);

		} catch (Exception e) {
			// 例外発生時はスタックトレースを出力し、エラー専用画面へフォワードして安全に保護
			e.printStackTrace();
			req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, res);
		}
	}

	// POSTメソッドによるリクエストを受け取り、doGetメソッドと同一の共通処理へ委譲する
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// POSTリクエストもGET処理に集約して一元管理
		doGet(req, res);
	}
}