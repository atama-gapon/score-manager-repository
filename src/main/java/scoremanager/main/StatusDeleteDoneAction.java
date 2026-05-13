package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StatusDeleteDoneAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/status_delete_done.jsp").forward(req, res);
	}
}
