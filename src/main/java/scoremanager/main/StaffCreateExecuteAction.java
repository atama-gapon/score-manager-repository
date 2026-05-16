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

public class StaffCreateExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		// パラメータの取得とFormへの詰め替え
		StaffForm form = new StaffForm(req);

		// バリデーション
		Map<String, String> errors = form.validate(school);

		if (!errors.isEmpty()) {
			prepareViewData(req, school);
			form.setAttributes(req);
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/staff_create.jsp").forward(req, res);
			return;
		}

		// 保存処理
		Staff newStaff = form.toEntity(school);
		new StaffDao().save(newStaff);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/StaffCreateDone.action");
	}

	private void prepareViewData(HttpServletRequest req, School school) throws Exception {
		req.setAttribute("position_list", new PositionDao().filter(school));
		req.setAttribute("status_list", new StatusDao().filter(school));
	}
}
