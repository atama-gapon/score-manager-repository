package tool;

import java.io.IOException;

import bean.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// 役割：フロントコントローラとなるサーブレット
@WebServlet(urlPatterns = {"*.action"})
@MultipartConfig( // ← これを追加しますのよ！
	    maxFileSize = 10000000, 
	    maxRequestSize = 10000000, 
	    fileSizeThreshold = 10000000
	)
public class FrontController extends HttpServlet {
	
	// 各メソッド内の処理はシーケンス図の確認時に記述

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			// 先頭の1文字（/）を除去したパスの取得。　⇒ 例：chapter23/Search.action
			String path = req.getServletPath().substring(1);
			// （「.a」を「A」に置換、「/」を「.」に置換　⇒ 例：chapter23.SearchAction
			// replaceメソッドは連続して使用することが可能
			String name = path.replace(".a", "A").replace('/', '.');
			
			System.out.println("★ servlet path ->" + req.getServletPath());
			System.out.println("★ class name   ->" + name);
			
			// アクションのクラス名を使って、インスタンスを生成
			Action action = (Action)Class.forName(name).getDeclaredConstructor().newInstance();
			
		    HttpSession session = req.getSession();
		    Teacher teacher = (Teacher) session.getAttribute("user");
			
		    // ログインされていないかつ、ログイン実行からのアクセスではない場合、ログイン画面に遷移する
		    if (teacher == null && !(name.equals("scoremanager.LoginExecuteAction"))) {
		    	action = (Action)Class.forName("scoremanager.LoginAction").getDeclaredConstructor().newInstance();
		    	action.execute(req, res);
			} else {
				action.execute(req, res);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// エラーページへリダイレクト
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
	}
	
	// doGetメソッドと同じ処理を行う
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}