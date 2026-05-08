package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.School;
import bean.Staff;
import bean.Status;
import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StatusUpdateExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();
		String idStr = req.getParameter("id");
		String name = req.getParameter("name");
		String sortOrderStr = req.getParameter("sortOrder");

		int id = Integer.parseInt(idStr);

		Map<String, String> errors = new HashMap<>();
		req.setAttribute("errors", errors);

		StatusDao dao = new StatusDao();
		Status status = dao.get(id, school);

		if (status == null) {
			errors.put("id", "ステータスが存在しません");
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/status_update.jsp")
					.forward(req, res);
			return;
		}

		// 入力値セット
		status.setName(name);

		try {
			status.setSortOrder(Integer.parseInt(sortOrderStr));
		} catch (Exception e) {
			errors.put("sortOrder", "数値を入力してください");
		}

		status.setSchool(school);

		// JSP に戻す用
		req.setAttribute("status", status);

		// バリデーション
		if (name == null || name.isEmpty()) {
			errors.put("name", "ステータス名を入力してください");
		}

		if (!errors.isEmpty()) {
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/status_update.jsp").forward(req, res);
			return;
		}

		// 更新処理
		dao.update(status);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/status_update_done.jsp").forward(req, res);
	}
}