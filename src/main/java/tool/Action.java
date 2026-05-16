package tool;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * すべてのアクションクラスの基底となる抽象クラス（Commandパターン実装）
 */
public abstract class Action {

	/**
	 * 各ユースケースのビジネスロジックを実行します。
	 * 
	 * @param req  HttpServletRequestオブジェクト
	 * @param res  HttpServletResponseオブジェクト
	 * @threw Exception 処理中に発生した例外
	 */
	public abstract void execute(HttpServletRequest req, HttpServletResponse res) throws Exception;
}