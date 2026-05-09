package scoremanager.main;

import bean.School;
import bean.Staff;
import bean.Status;
import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StatusDeleteExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		int id = Integer.parseInt(req.getParameter("id"));

		Status status = new Status();
		status.setId(id);
		status.setSchool(school);

		StatusDao dao = new StatusDao();
		dao.delete(status);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/status_delete_done.jsp").forward(req, res);
	}
}