package scoremanager.main;

import bean.Position;
import dao.PositionDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class PositionDeleteExecuteAction extends Action {

	public void execute(HttpServletRequest req,
			HttpServletResponse res)
			throws Exception {

		String idStr = req.getParameter("id");

		int id = Integer.parseInt(idStr);

		Position p = new Position();

		p.setId(id);

		PositionDao dao = new PositionDao();

		dao.delete(p);

		req.getRequestDispatcher(
			"/WEB-INF/jsp/scoremanager/main/position_delete_done.jsp"
		).forward(req, res);
	}
}