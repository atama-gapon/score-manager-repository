package tool;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 役割：フロントコントローラとなるサーブレット
@WebServlet(urlPatterns = {"*.action"})
@MultipartConfig( // ← これを追加しますのよ！
	    maxFileSize = 10000000, 
	    maxRequestSize = 10000000, 
	    fileSizeThreshold = 10000000
	)
public class FrontController extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			String path = req.getServletPath().substring(1);
			String name = path.replace(".a", "A").replace('/', '.');
			
			System.out.println("★ servlet path ->" + req.getServletPath());
			System.out.println("★ class name   ->" + name);
			
			// アクションのクラス名を使って、インスタンスを生成
			Action action = (Action)Class.forName(name).getDeclaredConstructor().newInstance();
			action.execute(req, res);
		} catch (Exception e) {
			e.printStackTrace();
			// エラーページへリダイレクト
			req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, res);
		}
	}
	
	// doGetメソッドと同じ処理を行う
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}