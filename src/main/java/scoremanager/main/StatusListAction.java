package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Staff;
import bean.Status;
import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StatusListAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		StatusDao dao = new StatusDao();
		List<Status> statusSet = dao.filter(school);

		req.setAttribute("status_set", statusSet);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/status_list.jsp").forward(req, res);
	}
}