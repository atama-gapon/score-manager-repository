package scoremanager.main;

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

		// パラメータの取得とFormへの詰め替え
		StatusForm form = new StatusForm(req);

		// バリデーション
		Map<String, String> errors = form.validate(school);

		if (!errors.isEmpty()) {
			form.setAttributes(req);
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/status_update.jsp").forward(req, res);
			return;
		}

		// 保存処理
		Status newStatus = form.toEntity(school);
		new StatusDao().update(newStatus);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/StatusUpdateDone.action");
	}
}
