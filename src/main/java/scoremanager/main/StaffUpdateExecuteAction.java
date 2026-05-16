package scoremanager.main;

import java.util.Map;

import bean.School;
import bean.Staff;
import dao.PositionDao;
import dao.StaffDao;
import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StaffUpdateExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		StaffForm form = new StaffForm(req);

		// 元の職員情報
		Staff original = new StaffDao().get(form.getNo(), school);

		// バリデーション
		Map<String, String> errors = form.validateForUpdate(school);

		if (!errors.isEmpty()) {
			form.setAttributes(req);
			req.setAttribute("errors", errors);

			req.setAttribute("position_list", new PositionDao().filter(school));
			req.setAttribute("status_list", new StatusDao().filter(school));

			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/staff_update.jsp").forward(req, res);
			return;
		}

		// 更新
		Staff updated = form.toEntityForUpdate(original, school);
		new StaffDao().update(updated);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/StaffUpdateDone.action");
	}
}
