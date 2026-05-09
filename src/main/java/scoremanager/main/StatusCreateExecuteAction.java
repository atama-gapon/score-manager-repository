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

public class StatusCreateExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();
		String schoolCd = school.getCd();

		String name = req.getParameter("name");
		String sortOrderStr = req.getParameter("sortOrder");

		req.setAttribute("name", name);
		req.setAttribute("sortOrder", sortOrderStr);

		Map<String, String> errors = new HashMap<>();
		req.setAttribute("errors", errors);

		// バリデーション
		if (name == null || name.isEmpty()) {
			errors.put("name", "ステータス名を入力してください");
		}

		int sortOrder = 0;
		try {
			sortOrder = Integer.parseInt(sortOrderStr);

			if (sortOrder < 0) {
				errors.put("sortOrder", "並び順は 0 以上の整数で入力してください");
			}

		} catch (NumberFormatException e) {
			errors.put("sortOrder", "並び順は数字で入力してください");
		}

		StatusDao dao = new StatusDao();

		// 重複チェック
		if (dao.existsByName(name, schoolCd)) {
			errors.put("name", "同じ名前のステータスがすでに存在します");
		}

		// エラーがあれば戻す
		if (!errors.isEmpty()) {
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/status_create.jsp")
					.forward(req, res);
			return;
		}

		// 登録処理
		Status s = new Status();
		s.setName(name);
		s.setSortOrder(sortOrder);
		s.setSchool(school);

		boolean result = dao.save(s);

		if (!result) {
			req.setAttribute("message", "登録に失敗しました");
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/status_create.jsp")
					.forward(req, res);
			return;
		}

		// 完了画面へ
		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/status_create_done.jsp")
				.forward(req, res);
	}
}