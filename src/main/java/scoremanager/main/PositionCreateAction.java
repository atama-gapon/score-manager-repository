package scoremanager.main;

import bean.Staff;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class PositionCreateAction extends Action {

	public void execute(HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		Staff staff = (Staff) req.getAttribute("staff");

		req.setAttribute("school_cd",
				staff.getSchool().getCd());

		req.getRequestDispatcher(
			"/WEB-INF/jsp/scoremanager/main/position_create.jsp"
		).forward(req, res);
	}
}