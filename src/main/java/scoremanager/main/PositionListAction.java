package scoremanager.main;

import java.util.List;

import bean.Position;
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

		PositionDao dao = new PositionDao();
		List<Position> positionList = dao.filter(school);

		req.setAttribute("position_list", positionList);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/position_list.jsp").forward(req, res);
	}
}
