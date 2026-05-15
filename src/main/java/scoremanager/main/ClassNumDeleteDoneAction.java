package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ClassNumDeleteDoneAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/class_num_delete_done.jsp").forward(req, res);
	}
}
