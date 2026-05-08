package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Staff;
import dao.StaffDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StaffListAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		StaffDao staffDao = new StaffDao();
		List<Staff> staffSet = staffDao.filter(school);

		Map<String, String> errors = new HashMap<>();
		req.setAttribute("staff_set", staffSet);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/staff_list.jsp").forward(req, res);
	}
}