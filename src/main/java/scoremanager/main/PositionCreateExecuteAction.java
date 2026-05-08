package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.Position;
import bean.School;
import bean.Staff;
import dao.PositionDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class PositionCreateExecuteAction extends Action {

	public void execute(HttpServletRequest req,
			HttpServletResponse res)
			throws Exception {

		Staff staff = (Staff) req.getAttribute("staff");

		School school = staff.getSchool();

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

			req.getRequestDispatcher(
					"/WEB-INF/jsp/scoremanager/main/position_create.jsp").forward(req, res);

			return;
		}

		Position p = new Position();

		//		p.setSchoolCd(school.getCd());

		p.setName(name);
		p.setSortOrder(Integer.parseInt(sortOrderStr));

		PositionDao dao = new PositionDao();

		boolean result = dao.save(p);

		if (!result) {

			req.setAttribute("message", "登録に失敗しました");

			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/position_create.jsp").forward(req, res);

			return;
		}

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/position_create_done.jsp").forward(req, res);
	}
}