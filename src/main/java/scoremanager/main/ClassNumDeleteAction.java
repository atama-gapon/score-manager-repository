package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ClassNumDeleteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// リクエストパラメータの取得
		String num = req.getParameter("num");

		req.setAttribute("num", num);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/class_num_delete.jsp").forward(req, res);
	}
}
