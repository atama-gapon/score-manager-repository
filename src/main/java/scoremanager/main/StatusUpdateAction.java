package scoremanager.main;

import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StatusUpdateAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		prepareViewData(req);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/status_update.jsp").forward(req, res);
	}

	private void prepareViewData(HttpServletRequest req) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));

		req.setAttribute("status", new StatusDao().get(id));
	}
}
