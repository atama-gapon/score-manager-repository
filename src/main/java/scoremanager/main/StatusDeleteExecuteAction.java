package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import dao.StaffDao;
import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StatusDeleteExecuteAction extends Action {

	// 指定された在籍状態の削除バリデーションを行い、問題がなければデータベースから削除する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		String idStr = req.getParameter("id");

		// インスタンス化
		StatusDao statusDao = new StatusDao();
		StaffDao staffDao = new StaffDao();
		Map<String, String> errors = new HashMap<>();

		int id = 0;

		// バリデーション
		try {
			if (idStr != null && !idStr.isEmpty()) {
				id = Integer.parseInt(idStr);
			} else {
				errors.put("id", "不正なリクエストです");
			}
		} catch (NumberFormatException e) {
			errors.put("id", "不正なリクエストです");
		}

		if (errors.isEmpty()) {
			if (statusDao.get(id) == null) {
				errors.put("status", "指定された状態が存在しません");
			}

			if (staffDao.hasStaffInStatus(id)) {
				errors.put("status", "この状態を用いている職員が存在しているため削除できません");
			}
		}

		// エラーがある場合は入力画面へ戻す
		if (!errors.isEmpty()) {
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("StatusDelete.action").forward(req, res);
			return;
		}

		// DBへ反映
		statusDao.delete(id);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/StatusDeleteDone.action");
	}
}