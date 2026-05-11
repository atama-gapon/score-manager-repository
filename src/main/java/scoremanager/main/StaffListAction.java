package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Position;
import bean.School;
import bean.Staff;
import bean.Status;
import dao.PositionDao;
import dao.StaffDao;
import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StaffListAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		StaffDao staffDao = new StaffDao();
		List<Staff> staffSet = staffDao.filter(school);
		PositionDao positionDao = new PositionDao();
		List<Position> positionSet = positionDao.filter(school);
		StatusDao statusDao = new StatusDao();
		List<Status> statusSet = statusDao.filter(school);

		Map<String, String> errors = new HashMap<>();
		req.setAttribute("staff_set", staffSet);
		req.setAttribute("position_set", positionSet);
		req.setAttribute("status_set", statusSet);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/staff_list.jsp").forward(req, res);
	}
}