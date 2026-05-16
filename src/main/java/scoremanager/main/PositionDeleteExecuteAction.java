package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import dao.PositionDao;
import dao.StaffDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class PositionDeleteExecuteAction extends Action {

	// 削除対象の役職に対するバリデーションを行い、問題がなければデータベースから削除する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		String idStr = req.getParameter("id");

		// インスタンス化
		PositionDao positionDao = new PositionDao();
		StaffDao staffDao = new StaffDao();
		Map<String, String> errors = new HashMap<>();

		int id = 0;

		// バリデーション
		try {
			if (idStr != null && !idStr.isEmpty()) {
				id = Integer.parseInt(idStr);
			} else {
				errors.put("position", "役職IDが指定されていません");
			}
		} catch (NumberFormatException e) {
			errors.put("position", "不正な役職IDです");
		}


		if (errors.isEmpty() && positionDao.get(id) == null) {
			errors.put("position", "役職が存在していません");
		}


		if (errors.isEmpty() && staffDao.hasStaffInPosition(id)) {
			errors.put("position", "この役職を用いている職員が存在しているため削除できません");
		}

		// エラーがある場合は入力画面へ戻す
		if (!errors.isEmpty()) {
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("PositionDelete.action").forward(req, res);
			return;
		}

		// DBへ反映
		positionDao.delete(id);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/PositionDeleteDone.action");
	}
}