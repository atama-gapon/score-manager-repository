package scoremanager.main;

import bean.School;
import bean.Staff;
import dao.PositionDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class PositionListAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();
		
		prepareViewData(req, school);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/position_list.jsp").forward(req, res);
	}

	private void prepareViewData(HttpServletRequest req, School school) throws Exception {
		req.setAttribute("position_list", new PositionDao().filter(school));
	}
}
