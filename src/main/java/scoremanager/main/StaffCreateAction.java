package scoremanager.main;

import java.util.List;

import bean.Position;
import bean.School;
import bean.Staff;
import bean.Status;
import dao.PositionDao;
import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StaffCreateAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		PositionDao positionDao = new PositionDao();
		List<Position> positionSet = positionDao.filter(school);

		StatusDao statusDao = new StatusDao();
		List<Status> statusSet = statusDao.filter(school);

		req.setAttribute("position_set", positionSet);
		req.setAttribute("status_set", statusSet);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/staff_create.jsp").forward(req, res);
	}
}