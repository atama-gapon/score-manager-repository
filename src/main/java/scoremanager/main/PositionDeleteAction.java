package scoremanager.main;

import bean.Position;
import dao.PositionDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class PositionDeleteAction extends Action {

	public void execute(HttpServletRequest req,
			HttpServletResponse res)
			throws Exception {

		String idStr = req.getParameter("id");

		int id = Integer.parseInt(idStr);

		PositionDao dao = new PositionDao();

		Position position = dao.get(id);

		req.setAttribute("id", id);

		req.setAttribute("name", position.getName());

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/position_delete.jsp").forward(req, res);
	}
}
