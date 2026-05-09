package scoremanager.main;

import bean.Status;
import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StatusDeleteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));

		StatusDao dao = new StatusDao();
		Status status = dao.get(id);

		req.setAttribute("id", status.getId());
		req.setAttribute("name", status.getName());
		req.setAttribute("sortOrder", status.getSortOrder());

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/status_delete.jsp").forward(req, res);
	}
}