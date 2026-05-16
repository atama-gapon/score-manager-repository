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

	// 入力された在籍状態登録情報のバリデーションを行い、問題がなければデータベースに登録する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();
		String name = req.getParameter("name");
		String sortOrderStr = req.getParameter("sort_order");

		// インスタンス化
		StatusDao statusDao = new StatusDao();
		Map<String, String> errors = new HashMap<>();

		int sortOrder = 0;

		// バリデーション
		try {
			if (sortOrderStr != null && !sortOrderStr.isEmpty()) {
				sortOrder = Integer.parseInt(sortOrderStr);
				if (sortOrder < 0) {
					errors.put("sort_order", "並び順は 0 以上の整数で入力してください");
				}
			} else {
				errors.put("sort_order", "並び順を入力してください");
			}
		} catch (NumberFormatException e) {
			errors.put("sort_order", "並び順は半角数字で入力してください");
		}

		if (name != null && !name.isEmpty()) {
			if (statusDao.existsByName(name, school)) {
				errors.put("name", "同じ名前の状態がすでに存在します");
			}
		} else {
			errors.put("name", "状態名を入力してください");
		}

		// エラーがある場合は入力画面へ戻す
		if (!errors.isEmpty()) {
			req.setAttribute("name", name);
			req.setAttribute("sort_order", sortOrderStr);
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("StatusCreate.action").forward(req, res);
			return;
		}

		// DBへ反映
		Status status = new Status();
		status.setName(name);
		status.setSortOrder(sortOrder);
		status.setSchool(school);
		statusDao.save(status);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/StatusCreateDone.action");
	}
}