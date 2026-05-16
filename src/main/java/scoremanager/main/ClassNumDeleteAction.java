package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ClassNumDeleteAction extends Action {

	// 選択されたクラス番号を取得し、削除確認画面のJSPへフォワード遷移する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String num = req.getParameter("num");
		req.setAttribute("num", num);
		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/class_num_delete.jsp").forward(req, res);
	}
}