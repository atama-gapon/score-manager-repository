package tool;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 役割：フロントコントローラとなるサーブレット
@WebServlet(urlPatterns = {"*.action"})
public class FrontController extends HttpServlet {
	
	// 各メソッド内の処理はシーケンス図の確認時に記述

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			req.setCharacterEncoding("UTF-8");
			res.setContentType("text/html; charset=UTF-8");
			
			// 先頭の1文字（/）を除去したパスの取得。　⇒ 例：chapter23/Search.action
			String path = req.getServletPath().substring(1);
			// （「.a」を「A」に置換、「/」を「.」に置換　⇒ 例：chapter23.SearchAction
			// replaceメソッドは連続して使用することが可能
			String name = path.replace(".a", "A").replace('/', '.');

			System.out.println("★ servlet path ->" + req.getServletPath());
			System.out.println("★ class name   ->" + name);
			
			// アクションのクラス名を使って、インスタンスを生成
			Action action = (Action)Class.forName(name).getDeclaredConstructor().newInstance();
			
			// 遷移先URLを取得
			action.execute(req, res);
//			String url = action.execute(req, res);
//			req.getRequestDispatcher(url).forward(req, res);
		} catch (Exception e) {
			e.printStackTrace();

			// エラーページへリダイレクト
//			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
	}
	
	// doGetメソッドと同じ処理を行う
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}