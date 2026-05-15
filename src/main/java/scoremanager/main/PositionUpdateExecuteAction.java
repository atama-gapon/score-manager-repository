package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.Position;
import dao.PositionDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class PositionUpdateExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		String idStr = req.getParameter("id");

		String name = req.getParameter("name");

		String sortOrderStr = req.getParameter("sort_order");

		req.setAttribute("name", name);

		req.setAttribute("sort_order", sortOrderStr);

		Map<String, String> errors = new HashMap<>();

		if (name == null || name.isEmpty()) {

			errors.put("name", "役職名を入力してください");
		}

		if (sortOrderStr == null || sortOrderStr.isEmpty()) {

			errors.put("sort_order", "表示順を入力してください");
		}

		if (!errors.isEmpty()) {

			req.setAttribute("errors", errors);

			Position position = new Position();

			position.setId(Integer.parseInt(idStr));

			position.setName(name);

			if (sortOrderStr != null && !sortOrderStr.isEmpty()) {

				position.setSortOrder(Integer.parseInt(sortOrderStr));
			}

			req.setAttribute("position", position);

			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/position_update.jsp").forward(req, res);

			return;
		}

		Position p = new Position();

		p.setId(Integer.parseInt(idStr));

		p.setName(name);

		p.setSortOrder(Integer.parseInt(sortOrderStr));

		PositionDao dao = new PositionDao();

		dao.update(p);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/PositionUpdateDone.action");
	}
}
